package com.example.currencyconverter.ConverterModule.Repository;

import com.example.currencyconverter.Models.OnSuccessListener;

public interface ConverterRepository {
    void getCurrentRate(String currencyFrom, String currencyTo, final OnSuccessListener<String, String> callback);

}
