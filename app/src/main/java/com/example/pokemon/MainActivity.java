package com.example.pokemon;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etUser;
    EditText etPass;
    Button btnIniciar;
    TextView tvRegister;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        tvRegister = (TextView) findViewById(R.id.tvRegister);

        db = new DBHelper(this);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = etUser.getText().toString();
                String password = etPass.getText().toString();
                Boolean checkUserPass = db.checkUserPass(user, password);

                if (checkUserPass == true){
                    Intent i = new Intent(MainActivity.this, PokemonActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Usuário ou senha incorreta", Toast.LENGTH_LONG).show();
                }
            }
        });
    }




}