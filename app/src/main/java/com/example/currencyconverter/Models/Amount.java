package com.example.currencyconverter.Models;

import com.google.gson.annotations.SerializedName;

public class Amount {

    @SerializedName("amount")
    private Double amount;

    @SerializedName("error")
    private int error;

    public int getError() {
        return error;
    }

    public Double getAmount() {
        return amount;
    }
}
