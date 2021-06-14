package com.example.geobirdclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.UserService;
import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.api.models.User.UserLogin;
import com.example.geobirdclient.api.models.User.UserResponse;
import com.example.geobirdclient.ui.auth.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static User currentUser;
    MainActivity mainActivity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String login = sharedPreferences.getString("USER_CREDENTIALS_LOGIN", "");
        String password = sharedPreferences.getString("USER_CREDENTIALS_PASSWORD", "");

        UserService service = Api.getRetrofit().create(UserService.class);
        Call<UserResponse> call = service.loginUser(new UserLogin(login,password));
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if(userResponse == null){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {

                    currentUser = response.body().getUser();
                    setContentView(R.layout.activity_main);
                    BottomNavigationView navView = findViewById(R.id.nav_view);
                    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_gallery)
                            .build();
                    NavController navController = Navigation.findNavController(mainActivity, R.id.nav_host_fragment);
                    NavigationUI.setupActionBarWithNavController(mainActivity, navController, appBarConfiguration);
                    NavigationUI.setupWithNavController(navView, navController);

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}