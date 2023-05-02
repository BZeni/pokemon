package com.example.pokemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ItemPokemonActivity extends AppCompatActivity {

    Switch btnFavorito;
    TextView name_text_view;
    DBHelper db;
    PokemonActivity pokemonActivity;
    public PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(this);
        pokemonActivity = new PokemonActivity();
        List<Pokemon> pokemons = pokemonActivity.pokemonList;
        setContentView(R.layout.item_pokemon);
        btnFavorito = findViewById(R.id.btnFavorito);
        name_text_view = findViewById(R.id.name_text_view);

        btnFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = btnFavorito.isChecked();
                if (isChecked){
                    for (int x = 0; x < pokemons.size(); x++) {
                        if (pokemons.get(x).getName().equals(name_text_view.getText())) {
                            db.inserirNovoFavorito(db.idLogado, pokemons.get(x).getId());
                            break;
                        }
                    }
                }else {
                    for (int x = 0; x < pokemons.size(); x++) {
                        db.removerFavorito(db.idLogado, pokemons.get(x).getId());
                        break;
                    }
                }
            }
        });
    }
}
