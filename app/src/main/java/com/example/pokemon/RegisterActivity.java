package com.example.pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText etUser;
    EditText etPass;
    Button btnRegister;
    EditText etRepass;
    TextView tvRegister;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUser = (EditText) findViewById(R.id.etUserReg);
        etPass = (EditText) findViewById(R.id.etPassReg);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        tvRegister = (TextView) findViewById(R.id.tvIniciar);
        etRepass = (EditText) findViewById(R.id.etPassReg2);

        db = new DBHelper(this);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etUser.getText().toString();
                String password = etPass.getText().toString();
                String rePassword = etRepass.getText().toString();

                if (password.equals(rePassword)) {
                    Boolean checker = db.checkUsername(user);
                    if (checker == false) {
                        Boolean insert = db.insertNewUser(user, password);
                        if (insert == true) {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    }
                }
            }
        });
    }
}
