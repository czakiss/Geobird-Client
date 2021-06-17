package com.example.geobirdclient.ui.user;

import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.UserService;
import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.api.models.User.UserEdit;
import com.example.geobirdclient.api.models.User.UserRegister;
import com.example.geobirdclient.api.models.User.UserResponse;
import com.example.geobirdclient.ui.auth.ModalWidgets;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_user extends Fragment {

    private TextInputLayout textLogin;
    private TextInputLayout textPassword;
    private TextInputLayout textRepeat;
    private Button registerButton;
    private Button registerCancelButton;
    private ImageView image;

    private String password;
    private String passwordRepeat;

    private String email;
    private String login;
    private EditUserViewModel mViewModel;

    private ModalWidgets modalWidgets;

    private View root;

    public static Edit_user newInstance() {
        return new Edit_user();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.edit_user_fragment, container, false);
        modalWidgets = new ModalWidgets(root.getContext());

        textLogin = root.findViewById(R.id.nameTextField);
        textPassword = root.findViewById(R.id.passwordTextField);
        textRepeat = root.findViewById(R.id.passwordRepeatTextField);
        registerButton = root.findViewById(R.id.registerButton);
        registerCancelButton = root.findViewById(R.id.registerCancelButton);

        addActions();

        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditUserViewModel.class);
        // TODO: Use the ViewModel
    }

    private void addActions() {
        registerButton.setOnClickListener(view -> {
            login = textLogin.getEditText().getText().toString();
            password = textPassword.getEditText().getText().toString();
            passwordRepeat = textRepeat.getEditText().getText().toString();

            boolean chP = checkPassword();
            boolean chL = checkLogin();
            if(chP && chL){
                UserService service = Api.getRetrofit().create(UserService.class);
                Call<UserResponse> call = service.editUser(new UserEdit(email,password,login));
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if(userResponse !=null){
                            if(userResponse.getUser() != null){
                                User user = userResponse.getUser();
                                System.out.println("Edycja pomyślna : "+user.getNickname());
                                saveUserCredentials(login,password);
                                redirectToMain(user);
                            } else {
                                modalWidgets.showToast(getString(R.string.cannot_register_data));
                            }
                        } else {
                            modalWidgets.showToast(getString(R.string.error));
                            System.out.println(call.hashCode()  + response.code() + response.body().getStatus());
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_CREDENTIALS_LOGIN", login);
        editor.apply();
        editor.putString("USER_CREDENTIALS_PASSWORD", password);
        editor.apply();
    }

    public void redirectToMain(User loggedUser) {
        MainActivity.currentUser = loggedUser;
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}