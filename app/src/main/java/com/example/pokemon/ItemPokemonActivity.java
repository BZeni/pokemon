package com.example.pokemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
        setContentView(R.layout.item_pokemon);
        db = new DBHelper(this);
        pokemonActivity = new PokemonActivity();
        List<Pokemon> pokemons = pokemonActivity.pokemonList;
        btnFavorito = findViewById(R.id.btnFavorito);
        name_text_view = findViewById(R.id.name_text_view);

        btnFavorito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isChecked = btnFavorito.isChecked();
                if (isChecked){
                    for (int x = 0; x < pokemons.size(); x++) {
                        if (pokemons.get(x).getName().equals(name_text_view.getText())) {
//                            pokemons.get(x).setFavorite(true);
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
