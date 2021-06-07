package com.example.geobirdclient.ui.useredit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.ui.auth.ModalWidgets;

public class UserEdit extends Fragment {

    private EditText editNick;
    private EditText editPassword;
    private EditText editRepeatPassword;
    private Button saveDatasButton;
//TODO: sprawdzic !

    private ModalWidgets modal;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_edit_datas_user, container, false);
        editNick = root.findViewById(R.id.edit_user_nick);
        editPassword = root.findViewById(R.id.edit_password_user);
        editRepeatPassword = root.findViewById(R.id.edit_password_repeat);
        modal = new ModalWidgets(root.getContext());

        saveDatasButton = root.findViewById(R.id.save_datas_button);
        saveDatasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: dodać zapis danych
                updateGoal(v);
            }
        });
        return root;
    }

    public void updateGoal(View view){

        String nick = editNick.getText().toString();
        String password = editPassword.getText().toString();
        String repeatPassword = editRepeatPassword.getText().toString();


        boolean isCorrect = true;
        if (nick.equals("") || password.equals("") || repeatPassword.equals("")){
            isCorrect = false;
        }else if(!password.equals(repeatPassword)){
            isCorrect = false;
        }

        if(isCorrect){

            User user = MainActivity.currentUser;
            if(!user.getNickname().equals(nick)){
                user.setNickname(nick);
            }

            if (!user.getPassword().equals(password)){
                user.setPassword(password);
            }
        }else{
            notify(getString(R.string.incorrect_data));
        }
    }
    public void notify(String msg) {
        this.modal.showToast(msg);
    }
}
