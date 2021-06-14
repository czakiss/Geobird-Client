package com.example.geobirdclient.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.ui.auth.LoginActivity;
import com.example.geobirdclient.ui.gallery.GalleryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    private Button loginButton;
    private Button checkButton;
    private MainActivity activity;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        loginButton = root.findViewById(R.id.logoutButton);
        checkButton = root.findViewById(R.id.check_button);
        addActions();

        return root;
    }

    private void addActions() {
        loginButton.setOnClickListener(view -> {
            dropUserCredentials();
            MainActivity.currentUser = null;
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.fragmet_gallery_nav);
                BottomNavigationView navView = root.findViewById(R.id.nav_view);
                NavigationUI.setupWithNavController(navView, navController);
            }
        });
    }


    public void dropUserCredentials() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("USER_CREDENTIALS_LOGIN");
        editor.apply();
        editor.remove("USER_CREDENTIALS_PASSWORD");
        editor.apply();
    }
}
//https://developer.android.com/guide/navigation/navigation-getting-started