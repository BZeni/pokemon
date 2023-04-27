package com.example.pokemon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private List<Pokemon> pokemonList;

    public PokemonAdapter(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Pokemon pokemon = pokemonList.get(position);
                v.getContext().startActivity(pokemon.getIntent(v.getContext()));
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.nameTextView.setText(pokemon.getName());
        holder.idTextView.setText(String.format("#%03d", pokemon.getId()));
        Glide.with(holder.itemView.getContext())
                .load(pokemon.getImageUrl())
                .into(holder.imageView);
        holder.typeChipGroup.removeAllViews();
        for (String type : pokemon.getTypes()) {
            Chip chip = new Chip(holder.itemView.getContext());
            chip.setText(type);
            holder.typeChipGroup.addView(chip);
        }
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView idTextView;
        public ImageView imageView;
        public ChipGroup typeChipGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            idTextView = itemView.findViewById(R.id.id_text_view);
            imageView = itemView.findViewById(R.id.image_view);
            typeChipGroup = itemView.findViewById(R.id.type_chip_group);
        }
    }
}