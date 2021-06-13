package com.example.geobirdclient.ui.editUsers;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.UserService;
import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.api.models.User.UserDelete;
import com.example.geobirdclient.api.models.User.UserLogin;
import com.example.geobirdclient.api.models.User.UserRegister;
import com.example.geobirdclient.api.models.User.UserResponse;
import com.example.geobirdclient.ui.auth.ModalWidgets;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserFragment extends AppCompatActivity {

    private Spinner spinerUsers;
    private EditText editTextNickname;
    private EditText nickNewPassword;
    private EditText nickNewPasswordRepeat;
    private EditText nickChangeEmail;
    private Button deleteUser;
    private Button saveChanges;
    ModalWidgets modalWidgets = new ModalWidgets(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_counts);
        spinerUsers = findViewById(R.id.spinner_users);
        editTextNickname = findViewById(R.id.edit_text_nickname);
        nickNewPassword = findViewById(R.id.nick_new_password_input);
        nickNewPasswordRepeat = findViewById(R.id.nic_new_password_repeat_input);
        nickChangeEmail = findViewById(R.id.nick_change_email_input);
        deleteUser = findViewById(R.id.delete_user_button);
        saveChanges = findViewById(R.id.save_changes_button);
        addActions();
    }

    private void addActions() {
        getUsersList();
        //TODO: tu poprawic usuwanie
            deleteUser.setOnClickListener((View v) -> {
                new UserService().deleteUser(this, getUsersList());
            });

            //--------------------Zmiana danych uzytkownika
            saveChanges.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    User user = getUsersList();
                    if(!user.getNickname().equals(editTextNickname.getText())){
                        user.setNickname(editTextNickname.toString());
                    }
                    if(!user.getPassword().equals(nickNewPassword)){
                        if(nickNewPassword.equals(nickNewPasswordRepeat)){
                            user.setPassword(nickNewPassword.toString());
                        }else{
                            modalWidgets.showToast(getString(R.string.wrong_repeat_password));
                        }
                    }
                    if(!user.getEmail().equals(nickChangeEmail)){
                        user.setEmail(nickChangeEmail.toString());
                    }
                }
            });
    }

    private User getUsersList(){

        UserService service = Api.getRetrofit().create(UserService.class);
        Call<List<User>> call = service.getUsers();
        User selectedUser;

//-------------Pobieranie uæytkowników-------------------------------------------------------------------------------------------
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> list) {
                List<User> userResponse = list.body();

                if (userResponse != null){
                    List<User> usersList = userResponse.subList(0,userResponse.size());
                    System.out.println("Lista uzytkownikow " + usersList);

                    //-----------------------------------------------------------------------------------------------------------------------------
                    //String okList = usersList.toString();
                    //TODO: czemu nie ?? https://www.youtube.com/watch?v=N4PuIa1P0PM 15:24

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,usersList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinerUsers.setAdapter(adapter);
                    //-----------------------------------------------------------------------------------------------------------------------------

                     selectedUser = spinerUsers.getSelectedItem().toString();
                     //TODO: na mrówke ta arrayka ! o co mu chodzi????!!!

                }else{
                    modalWidgets.showToast(getString(R.string.incorrect_get_users));
                }


            }


            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
        //----------------------------Koniec pobierania uæytkowników
        return selectedUser;
    }



}
