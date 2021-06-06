package com.example.geobirdclient.ui.register;

import android.content.Intent;
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

import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.UserService;
import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.api.models.User.UserRegister;
import com.example.geobirdclient.api.models.User.UserResponse;
import com.example.geobirdclient.ui.auth.LoginActivity;
import com.example.geobirdclient.ui.auth.ModalWidgets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends AppCompatActivity {


    private EditText nickNameTextEdit;
    private EditText emailTextEdit;
    private EditText passwordTextEdit;
    private EditText repeatPasswordTextEdit;
    private Button registerButton;

    String login = "";
    String email = "";
    String password = "";
    String passwordRepeat = "";
    ModalWidgets modalWidgets = new ModalWidgets(this);
    public static final Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nickNameTextEdit = findViewById(R.id.nickNameTextEdit);
        emailTextEdit = findViewById(R.id.emailTextEdit);
        passwordTextEdit = findViewById(R.id.passwordTextEdit);
        repeatPasswordTextEdit = findViewById(R.id.repeatPasswordTextEdit);
        registerButton = findViewById(R.id.buttonRegister);
        addActions();
    }
    private void addActions(){
        nickNameTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                login = nickNameTextEdit.getText().toString();
                checkLogin();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        repeatPasswordTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordRepeat = repeatPasswordTextEdit.getText().toString();
                checkPassword();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = passwordTextEdit.getText().toString();
                checkPassword();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(getBaseContext(), R.drawable.ic_eye_solid);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);

                    if(passwordTextEdit.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){

                        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorAccent));
                        passwordTextEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        repeatPasswordTextEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                    else{

                        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorPrimary));
                        passwordTextEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        repeatPasswordTextEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    }
                    passwordTextEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_solid, 0, R.drawable.ic_eye_solid, 0);

                    return true;
                }
            }

            return false;
        });

        nickNameTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                login = nickNameTextEdit.getText().toString();
            }
        });

        emailTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                email = emailTextEdit.getText().toString();
                checkMail();
            }
        });

        registerButton.setOnClickListener(view -> {
            System.out.println(password+login+email);
            if(!checkPassword()){
                modalWidgets.showToast(getString(R.string.incorect_password));
            }
            if(!checkLogin()){
                modalWidgets.showToast(getString(R.string.login_is_empty));
            }
            if(!checkMail()){
                modalWidgets.showToast(getString(R.string.mail_is_empty));
            }
            if (checkPassword() && checkLogin() && checkMail()) {
                User user = new User(0,password,login,0,email);
                new UserService().registerUser(this, user);
            }
        });


    }
    private boolean checkMail(){
        Matcher matcher = emailRegex.matcher(email);
        if(email.equals("")){
            emailTextEdit.setVisibility(TextView.VISIBLE);
            emailTextEdit.setText(R.string.mail_is_empty);
            System.out.println("Empty email");
        }else if(!matcher.find()){
            emailTextEdit.setVisibility(TextView.VISIBLE);
            emailTextEdit.setText(R.string.mail_incorrect);
            System.out.println("Incorrect email");
        }else{
            System.out.println("Correct email");
            // emailTextEdit.setVisibility(TextView.INVISIBLE);
            // emailTextEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_email_24, 0, R.drawable.ic_check_circle_solid, 0);
            return true;
        }
        return false;
    }

    private boolean checkPassword(){

        if(!password.contains(passwordRepeat)){
            modalWidgets.showToast(getString(R.string.incorect_password));
            return false;
        }
        return true;
    }

    @Override
    public void informAboutFailedRegistered() {
        modalWidgets.showToast(getString(R.string.register_failed));
    }

    @Override
    public void informAboutSuccessfulRegistered() {
        modalWidgets.showToast(getString(R.string.register_successful));

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }



}




