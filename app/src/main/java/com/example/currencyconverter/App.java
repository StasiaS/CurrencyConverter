package com.example.currencyconverter;

import android.app.Application;
import android.content.Context;

import com.example.currencyconverter.Data.CurrencyConverterApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private Retrofit retrofit;
    private static CurrencyConverterApi currencyConverterApi;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.amdoren.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        currencyConverterApi = retrofit.create(CurrencyConverterApi.class);
    }

    public static CurrencyConverterApi getApi() {
        return currencyConverterApi;
    }
    public static Context getContext() {
        return context;
    }
}
