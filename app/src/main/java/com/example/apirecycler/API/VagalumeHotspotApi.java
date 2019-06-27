package com.example.apirecycler.API;
import com.example.apirecycler.Model.VagalumeHotspot;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VagalumeHotspotApi {

    @GET("hotspots.php?apikey=52433bd778677b92342a16ddf927e4bf")
    Call<VagalumeHotspot> getListaHotspot();

}
