package com.example.geobirdclient.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.UserService;
import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.api.models.User.UserLogin;
import com.example.geobirdclient.api.models.User.UserRegister;
import com.example.geobirdclient.api.models.User.UserResponse;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout textLogin;
    private TextInputLayout textPassword;
    private TextInputLayout textRepeat;
    private TextInputLayout textEmail;
    private Button registerButton;
    private ImageView image;

    private String password;
    private String passwordRepeat;

    private String email;
    private String login;

    public static final Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    ModalWidgets modalWidgets = new ModalWidgets(this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textLogin = findViewById(R.id.loginTextField);
        textPassword = findViewById(R.id.passwordTextField);
        textRepeat = findViewById(R.id.passwordRepeatTextField);
        textEmail = findViewById(R.id.emailTextField);
        registerButton = findViewById(R.id.registerButton);
        image = findViewById(R.id.imageView);
        Glide.with(this).load(getResources().getDrawable(R.drawable.logo_bird)).into(image);
        addActions();
    }



    private void addActions() {
        registerButton.setOnClickListener(view -> {
            login = textLogin.getEditText().getText().toString();
            password = textPassword.getEditText().getText().toString();
            passwordRepeat = textRepeat.getEditText().getText().toString();
            email = textEmail.getEditText().getText().toString();

            boolean chM = checkMail();
            boolean chP = checkPassword();
            boolean chL = checkLogin();
            if(chM && chP && chL){
                UserService service = Api.getRetrofit().create(UserService.class);
                Call<UserResponse> call = service.registerUser(new UserRegister(email,password,login));
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if(userResponse !=null){
                            if(userResponse.getUser() != null){
                                User user = userResponse.getUser();
                                System.out.println("Zarejestrowano: "+user.getNickname());
                                saveUserCredentials(login,password);
                                redirectToMain(user);
                            } else {
                                System.out.println("not register");
                                modalWidgets.showToast(getString(R.string.cannot_register_data));
                            }
                        } else {
                            System.out.println("problem");
                            modalWidgets.showToast(getString(R.string.error));
                        }
                    }
                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        System.out.println("Cos poszlo nie tak: "+ t.getMessage());
                    }
                });
            }
        });
    }

    private boolean checkMail(){
        Matcher matcher = emailRegex.matcher(email);
        if(email.equals("")){
            textEmail.setError(getString(R.string.mail_is_empty));
            textEmail.setErrorEnabled(true);
        }else if(!matcher.find()){
            textEmail.setError(getString(R.string.mail_incorrect));
            textEmail.setErrorEnabled(true);
        }else{
            textEmail.setErrorEnabled(false);
            return true;
        }
        return false;
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
        if(!password.equals(passwordRepeat)){
            textPassword.setError(getString(R.string.password_not_same));
            textPassword.setErrorEnabled(true);
        }else if(password.equals("")){
            textPassword.setError(getString(R.string.password_empty));
            textPassword.setErrorEnabled(true);
        } else {
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


}
