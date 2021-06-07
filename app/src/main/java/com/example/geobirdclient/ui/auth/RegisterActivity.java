package com.example.geobirdclient.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        addActions();
    }



    private void addActions() {
        registerButton.setOnClickListener(view -> {
            login = textLogin.getEditText().getText().toString();
            password = textPassword.getEditText().getText().toString();
            passwordRepeat = textRepeat.getEditText().getText().toString();
            email = textEmail.getEditText().getText().toString();

            if(checkMail()){
                UserService service = Api.getRetrofit().create(UserService.class);
                Call<UserResponse> call = service.registerUser(new UserRegister(email,login,password));
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if(userResponse !=null){
                            User user = userResponse.getUser();
                            System.out.println("Zarejestrowano: "+user.getNickname());
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

    private boolean checkPassword(){
        if(password.equals(passwordRepeat)){
            textPassword.setError(getString(R.string.password_not_same));
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


}
