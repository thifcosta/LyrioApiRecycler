package com.example.apirecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.apirecycler.API.VagalumeHotspotApi;
import com.example.apirecycler.Adapters.HotspotAdapter;
import com.example.apirecycler.Model.Hotspot;
import com.example.apirecycler.Model.VagalumeHotspot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "VAGALUME";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private HotspotAdapter hotspotAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciar retrofit para buscar infos da API
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vagalume.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Configurar retrofit
        recyclerView = findViewById(R.id.recycler_view_id);
        hotspotAdapter = new HotspotAdapter(this); // "this" adicionado por causa do Glide
        recyclerView.setAdapter(hotspotAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Executar retrofit para buscar dados da API
        getRetrofitData();
    }

    private void  getRetrofitData(){
        VagalumeHotspotApi service = retrofit.create(VagalumeHotspotApi.class);
        Call<VagalumeHotspot> vagalumeHotspotCall = service.getListaHotspot();
        vagalumeHotspotCall.enqueue(new Callback<VagalumeHotspot>() {
            @Override
            public void onResponse(Call<VagalumeHotspot> call, Response<VagalumeHotspot> response) {
                if(response.isSuccessful()){
                    VagalumeHotspot vagalumeHotspot = response.body();
                    ArrayList<Hotspot> listaHotspot = vagalumeHotspot.getHotspots();
                    hotspotAdapter.adicionarListaHotspot(listaHotspot);

                    // Logar no console cada info recebida pela API
                    for(int i=0; i<listaHotspot.size(); i++){
                        Hotspot h = listaHotspot.get(i);
                        Log.i(TAG, " Hotspot: " +h.getTitle());
                    }

                }else {Log.e(TAG, " onResponse: "+response.errorBody());}
            }

            @Override
            public void onFailure(Call<VagalumeHotspot> call, Throwable t){Log.e(TAG, " onFailure: "+t.getMessage());}
        });
    }
}
