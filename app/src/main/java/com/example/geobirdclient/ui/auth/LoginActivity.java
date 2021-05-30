package com.example.geobirdclient.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.http.UserLoginCallback;
import com.example.geobirdclient.models.User.User;
import com.example.geobirdclient.models.UserData.UserLogin;

public class LoginActivity extends AppCompatActivity implements UserLoginCallback {

    private EditText editLogin;
    private EditText editPassword;
    private Button loginButton;
    private ModalWidgets modalWidgets;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login);
        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.loginButton);

        addActions();
    }

    @Override
    public void redirectToMain(User user) {
        MainActivity.isUser = true;
        MainActivity.currentUser = loggedUser;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void informAboutFailedLogin() {
        System.out.println("Nieprawidłowe dane logowania");
        modalWidgets.showToast(getString(R.string.incorrect_data));
    }

    @Override
    public void saveUserCredentials(String login, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_CREDENTIALS_LOGIN", login);
        editor.apply();
        editor.putString("USER_CREDENTIALS_PASSWORD", password);
        editor.apply();
    }

    private boolean checkPassword(){
        if (editPassword.getText().toString().equals("")){
            textViewPassword.setVisibility(TextView.VISIBLE);
            editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_solid, 0, R.drawable.ic_times_circle_solid, 0);

            return false;
        }
        textViewPassword.setVisibility(TextView.INVISIBLE);
        editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_solid,0, R.drawable.ic_check_circle_solid, 0);

        return true;
    }

    private boolean checkLogin(){
        if (editLogin.getText().toString().equals("")){
            textViewLogin.setVisibility(TextView.VISIBLE);
            editLogin.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_solid, 0, R.drawable.ic_times_circle_solid, 0);

            return false;
        }
        editLogin.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_solid,0, R.drawable.ic_check_circle_solid, 0);
        textViewLogin.setVisibility(TextView.INVISIBLE);

        return true;
    }

    private void addActions() {
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkLogin();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginButton.setOnClickListener(view -> {
            boolean isCorrectLogin = checkLogin();
            boolean isCorrectPassword = checkPassword();
            if (!isCorrectLogin) {
                modalWidgets.showToast(getString(R.string.login_is_empty));
            }
            if (!isCorrectPassword) {
                modalWidgets.showToast(getString(R.string.password_is_empty));
            }
            if (isCorrectLogin && isCorrectPassword) {
                new UserService().getUser(
                        this,
                        new AuthData(editLogin.getText().toString(), editPassword.getText().toString())
                );
            }
        });

        editPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editPassword.getRight() - editPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(getBaseContext(), R.drawable.ic_eye_solid);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);

                    if (editPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorAccent));
                        editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorPrimary));
                        editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_solid, 0, R.drawable.ic_eye_solid, 0);

                    return true;
                }
            }
            return false;
        });
    }

}
