package com.example.currencyconverter.Models;

public interface OnSuccessListener<T, V> {
    void onSuccess(T response);
    void onError(V response);
}
