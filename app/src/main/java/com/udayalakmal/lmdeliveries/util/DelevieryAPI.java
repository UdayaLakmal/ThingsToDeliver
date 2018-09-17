package com.udayalakmal.lmdeliveries.util;

import com.udayalakmal.lmdeliveries.model.Deliveries;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Lakmal on 9/16/18.
 */

public interface DelevieryAPI {

        @GET("deliveries")
        Call<List<Deliveries>> getDeliveries(@QueryMap Map<String, String> options);

}
