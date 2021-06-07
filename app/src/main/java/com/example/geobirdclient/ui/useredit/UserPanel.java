package com.example.geobirdclient.ui.useredit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.TargetService;
import com.example.geobirdclient.api.models.Target.Target;

public class UserPanel extends AppCompatActivity {

    private TextView nickNameInput;
    private TextView emialUserInput;
    private TextView passwordUserInput;
    private TextView numberOfCreaturesInput;
    private Button editYourDatasButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_board);
        nickNameInput = findViewById(R.id.nickNameTextEdit);
        emialUserInput = findViewById(R.id.emailTextEdit);
        passwordUserInput = findViewById(R.id.passwordTextEdit);
        numberOfCreaturesInput = findViewById(R.id.number_character_text_edit);
        editYourDatasButton = findViewById(R.id.edit_datas_button);
        addActions();
    }

    private void addActions(){

        nickNameInput.setText(MainActivity.currentUser.getNickname());
        emialUserInput.setText(MainActivity.currentUser.getEmail());
        passwordUserInput.setText(MainActivity.currentUser.getPassword());
        //numberOfCreaturesInput.setText(//TODO: dodac liczbe celów uzytkownika (lista.length i bd git));
        editYourDatasButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editDatas(v);
            }
        });
    }

    public void editDatas(View view){
        Intent intent = new Intent(this, UserEdit.class);
        startActivity(intent);
    }
}
