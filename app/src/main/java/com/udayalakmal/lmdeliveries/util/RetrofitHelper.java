package com.udayalakmal.lmdeliveries.util;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lakmal on 9/16/18.
 */

public class RetrofitHelper {
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";
    private static Context mContext;
    private static  Retrofit retrofit;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(provideOfflineCacheInterceptor())
            .addNetworkInterceptor(provideCacheInterceptor())
            .cache(provideCache());


    public  Retrofit getRetrofit(Context context){
        mContext = context;

        if(retrofit!=null ){
            return retrofit;
        }else{
            retrofit  =new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    // Add your adapter factory to handler Errors
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;


    }


    private Cache provideCache() {
        Cache cache = null;

        try {
            cache = new Cache(new File(mContext.getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e("RetrofitHelper", "Could not create Cache!");
        }

        return cache;
    }

    private Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());

            CacheControl cacheControl;

            if (isConnected()) {
                cacheControl = new CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build();
            } else {
                cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();
            }

            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();

        };
    }

    private Interceptor provideOfflineCacheInterceptor() {
        return chain -> {
            Request request = chain.request();

            if (!isConnected()) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
    }

    public boolean isConnected() {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) mContext.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w("RetrofitHelper", e.toString());
        }

        return false;
    }
}
