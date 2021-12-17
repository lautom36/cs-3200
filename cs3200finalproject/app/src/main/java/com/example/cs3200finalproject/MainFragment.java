package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3200finalproject.adapters.TotalsAdapter;
import com.example.cs3200finalproject.databinding.FragmentMainBinding;
import com.example.cs3200finalproject.databinding.FragmentSigninBinding;
import com.example.cs3200finalproject.models.User;
import com.example.cs3200finalproject.viewmodels.TotalViewModel;
import com.example.cs3200finalproject.viewmodels.TransactionViewModel;
import com.example.cs3200finalproject.viewmodels.TypeViewModel;
import com.example.cs3200finalproject.viewmodels.UserViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.navigation.NavigationView;

public class MainFragment extends Fragment {
     String tag = "Mylog MainFragment";

     DrawerLayout drawerLayout;
     NavigationView navigationView;
     Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag, "started MainFragment");
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);

        TransactionViewModel transactionViewModel = new ViewModelProvider(getActivity())
                .get(TransactionViewModel.class);

        TypeViewModel typeViewModel = new ViewModelProvider(getActivity())
                .get(TypeViewModel.class);

        TotalViewModel totalViewModel = new ViewModelProvider(getActivity())
                .get(TotalViewModel.class);

        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);


        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            // action bar things ---------------------------------------
            drawerLayout = binding.drawerLayout;
            navigationView = binding.navView;
            toolbar = binding.toolbar;

            navigationView.bringToFront();
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    getActivity(),
                    drawerLayout,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(view -> {
                switch (view.getItemId()) {
                    case R.id.nav_pastTrans:
                        NavHostFragment.findNavController(this)
                                .navigate(R.id.action_mainFragment_to_pastTransactionFragment);
                        transactionViewModel.setSelectedTransaction(null);
                        break;
                    case R.id.nav_newType:
                        NavHostFragment.findNavController(this)
                                .navigate(R.id.action_mainFragment_to_allTypesFragment);

                        break;
                    case R.id.nav_logout:
                        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
                        viewModel.logout();

                        NavHostFragment.findNavController(this)
                                .navigate(R.id.action_mainFragment_to_signinFragment);
                        Log.d(tag, "sign out button pressed");
                        break;
                }
                return true;



            });
            // action bar things ---------------------------------------

            // display totals for each type

            binding.totalsRecyclerView.setAdapter(
                    new TotalsAdapter(totalViewModel.getTotals(user.uid))
            );
            binding.totalsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // ads
            binding.bannerAdd.loadAd(
                    new AdRequest.Builder().build()
            );
        });

        return binding.getRoot();
    }
}