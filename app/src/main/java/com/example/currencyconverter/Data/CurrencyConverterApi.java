package com.example.currencyconverter.Data;

import com.example.currencyconverter.Models.Amount;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyConverterApi {

    @GET("/api/currency.php?api_key=csE7dyXDapVkr8fynTxDTmiR2A8Btx")
    Call <Amount> convert(@Query("from") String currencyFrom, @Query("to") String currencyTo);
}
