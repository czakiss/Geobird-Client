package com.example.geobirdclient.ui.auth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.UserService;
import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.api.models.User.UserLogin;
import com.example.geobirdclient.api.models.User.UserResponse;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textLogin;
    private TextInputLayout textPassword;
    private Button loginButton;
    private Button goRegisterButton;
    private ImageView image;
    private Activity activity;
    private String login;
    private String password;
    ModalWidgets modalWidgets = new ModalWidgets(this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textLogin = findViewById(R.id.loginTextField);
        textPassword = findViewById(R.id.passwordTextField);
        loginButton = findViewById(R.id.loginButton);
        goRegisterButton = findViewById(R.id.goRegisterButton);
        image =findViewById(R.id.imageView);
        Glide.with(this).load(getResources().getDrawable(R.drawable.logo_bird)).into(image);
        addActions();
    }

    private void addActions() {
        loginButton.setOnClickListener(view -> {
            login = textLogin.getEditText().getText().toString();
            password = textPassword.getEditText().getText().toString();

            if(checkLogin()){
                UserService service = Api.getRetrofit().create(UserService.class);
                Call<UserResponse> call = service.loginUser(new UserLogin(login,password));
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if(userResponse !=null){
                            User user = userResponse.getUser();
                            System.out.println("Zalogowano: "+user.getNickname());
                            saveUserCredentials(login,password);
                            redirectToMain(user);
                        } else {
                            modalWidgets.showToast(getString(R.string.incorrect_login_data));
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        System.out.println("Cos poszlo nie tak: "+ t.getMessage());
                    }
                });
            }

        });

        goRegisterButton.setOnClickListener(view ->{
            redirectToRegister();
        });
    }

    private boolean checkLogin(){
        if(login.equals("")){
            textLogin.setError(getString(R.string.login_is_empty));
            textLogin.setErrorEnabled(true);
        }else{
            textLogin.setErrorEnabled(false);
            return true;
        }
        return false;
    }

    private boolean checkPassword(){
        if(password.equals("")){
            textPassword.setError(getString(R.string.password_empty));
            textPassword.setErrorEnabled(true);
        }else{
            textPassword.setErrorEnabled(false);
            return true;
        }
        return false;
    }

    public void saveUserCredentials(String login, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_CREDENTIALS_LOGIN", login);
        editor.apply();
        editor.putString("USER_CREDENTIALS_PASSWORD", password);
        editor.apply();
    }

    public void redirectToMain(User loggedUser) {
        MainActivity.currentUser = loggedUser;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void redirectToRegister() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

}
