package com.example.pokemon;


import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

public class PokemonActivity extends AppCompatActivity {
    private List<Pokemon> pokemonList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new PokemonAdapter(pokemonList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);
        String url = "https://pokeapi.co/api/v2/pokemon?limit=151";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject pokemonObject = results.getJSONObject(i);
                            String name = pokemonObject.getString("name");
                            String urlDetail = pokemonObject.getString("url");

                            JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, urlDetail, null,
                                    detailResponse -> {
                                        try {
                                            int id = detailResponse.getInt("id");
                                            String weight = String.valueOf(detailResponse.getDouble("weight")) + " KG";
                                            String height = String.valueOf(detailResponse.getDouble("height")) + " M";

                                            String stat0 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(0).getInt("base_stat"));
                                            String stat1 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(1).getInt("base_stat"));
                                            String stat2 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(2).getInt("base_stat"));
                                            String stat3 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(3).getInt("base_stat"));
                                            String stat4 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(4).getInt("base_stat"));
                                            String stat5 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(5).getInt("base_stat"));

                                            String imageUrl = detailResponse.getJSONObject("sprites").getJSONObject("other").getJSONObject("home").getString("front_default");
                                            List<String> types = new ArrayList<>();
                                            JSONArray typesArray = detailResponse.getJSONArray("types");
                                            for (int j = 0; j < typesArray.length(); j++) {
                                                types.add(typesArray.getJSONObject(j).getJSONObject("type").getString("name"));
                                            }

                                            Pokemon pokemon = new Pokemon(name, id, imageUrl, types, weight, height, stat0, stat1, stat2, stat3, stat4, stat5);
                                            pokemonList.add(pokemon);
                                            adapter.notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    },
                                    error -> {
                                        error.printStackTrace();
                                    }
                            );
                            requestQueue.add(requestDetail);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                }
        );
        requestQueue.add(request);
    }

    public void GoToPokeApi(View view){
        String url = "https://pokeapi.co";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}

