package com.example.geobirdclient.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.TargetService;
import com.example.geobirdclient.api.UserTargetService;
import com.example.geobirdclient.api.models.Target.Target;
import com.example.geobirdclient.api.models.usertarget.UserTarget;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetByResponse;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetByUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    private Button loginButton;
    private Button editUserButton;
    private Button newTargetButton;
    private TextView nickname;
    private TextView email;
    private TextView points;
    private List<UserTarget> targetList = new ArrayList<UserTarget>();
    private Integer summary;
    List<Target> targets;
    Fragment fragment = this;
    NavController navigation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        loginButton = root.findViewById(R.id.logoutButton);
        newTargetButton = root.findViewById(R.id.button_add_taget);
        nickname = root.findViewById(R.id.nickname_view);
        email = root.findViewById(R.id.email_text_view);
        points = root.findViewById(R.id.points_text_view);
        editUserButton = root.findViewById(R.id.button_edit_user);

        editUserButton.setOnClickListener(e->{
            MainActivity.navController.navigate(R.id.action_navigation_home_to_navigation_edit_user);
        });

        newTargetButton.setOnClickListener(e->{
            MainActivity.navController.navigate(R.id.action_navigation_home_to_navigation_qr_scan);
        });

        addActions();

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void addActions() {
        loginButton.setOnClickListener(view -> {
            dropUserCredentials();
            MainActivity.currentUser = null;
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

       nickname.setText(MainActivity.currentUser.getNickname());
       email.setText(MainActivity.currentUser.getEmail());

        UserTargetService serviceUTarget = Api.getRetrofit().create(UserTargetService.class);
        Call<UserTargetGetByResponse> callUTarget = serviceUTarget.getUserTargetByUser(new UserTargetGetByUser(MainActivity.currentUser.getId()));
        callUTarget.enqueue(new Callback<UserTargetGetByResponse>() {
            @Override
            public void onResponse(Call<UserTargetGetByResponse> call, Response<UserTargetGetByResponse> response) {
                UserTargetGetByResponse userTargetGetByResponse = response.body();
                if(userTargetGetByResponse !=null){
                    targetList = userTargetGetByResponse.getUserTargetDataDTOS();

                    TargetService serviceTarget = Api.getRetrofit().create(TargetService.class);
                    Call<List<Target>> callTarget = serviceTarget.getTargets();



                    callTarget.enqueue(new Callback<List<Target>>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(Call<List<Target>> call, Response<List<Target>> response) {
                            targets = response.body();
                            int sum = 0;
                            if(targets != null){
                                for (Target target: targets) {
                                    List<UserTarget> checker = targetList.stream().filter(x -> x.getIdTarget() == target.getId()).collect(Collectors.toList());
                                    if( checker.size() == 0 ){
                                        sum += target.getPoints();
                                    }
                                }
                            } else {
                                System.out.println(response.message());
                            }
                            summary = sum;
                            points.setText(summary.toString()+ " pkt.");
                        }
                        @Override
                        public void onFailure(Call<List<Target>> call, Throwable t) {

                        }
                    });

                } }
            @Override
            public void onFailure(Call<UserTargetGetByResponse> call, Throwable t) {
                System.out.println("Cos poszlo nie tak: "+ t.getMessage());
            }
        });



        if(MainActivity.currentUser.getPermission() == 1){
            newTargetButton.setVisibility(View.VISIBLE);
        }else{
            newTargetButton.setVisibility(View.GONE);
        }

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