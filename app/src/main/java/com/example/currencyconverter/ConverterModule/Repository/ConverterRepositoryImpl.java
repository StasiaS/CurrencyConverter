package com.example.currencyconverter.ConverterModule.Repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.currencyconverter.ApiErrorCodes;
import com.example.currencyconverter.App;
import com.example.currencyconverter.Models.OnSuccessListener;
import com.example.currencyconverter.Models.Amount;
import com.example.currencyconverter.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConverterRepositoryImpl implements ConverterRepository {

    private final String APP_PREFERENCES = "CURRENCY_CACHE";
    private SharedPreferences sharedPreferences;
    private Context context;

    public ConverterRepositoryImpl() {
        sharedPreferences = App.getContext().getSharedPreferences(APP_PREFERENCES, App.getContext().MODE_PRIVATE);
        context = App.getContext();
    }

    @Override
    public void getCurrentRate(final String currencyFrom, final String currencyTo, final OnSuccessListener<String, String> callback) {
        Call call = App.getApi().convert(currencyFrom, currencyTo);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Double amount = ((Amount) response.body()).getAmount();
                    int error = ((Amount) response.body()).getError();
                    sendResponse(error,currencyFrom, currencyTo, amount, callback);
                }
                else
                    //TODO: Обработать ошибки от api
                    callback.onError(context.getString(R.string.request_failed));
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                //TODO: Обработать ошибки
                if (getFromCache(currencyFrom, currencyTo).length() != 0) {
                    callback.onSuccess(getFromCache(currencyFrom, currencyTo));
                } else {
                    callback.onError(context.getString(R.string.possible_not_connect));
                }
            }
        });

    }

    private void sendResponse(int error, String currencyFrom, String currencyTo, Double amount,
                              OnSuccessListener<String, String> callback) {
        switch (error) {
            case ApiErrorCodes.NO_ERRORS:
                saveToCaсhe(currencyFrom, currencyTo, String.valueOf(amount));
                callback.onSuccess(String.valueOf(amount));
                break;
            case ApiErrorCodes.INVALID_FROM_CURRENCY:
                callback.onError(context.getString(R.string.error_currency_from));
                break;
            case ApiErrorCodes.INVALID_TO_CURRENCY:
                callback.onError(context.getString(R.string.error_currency_to));
                break;
            case ApiErrorCodes.LIMIT_REACHED_FOR_THE_MONTH:
                if (getFromCache(currencyFrom, currencyTo).length() != 0) {
                    callback.onSuccess(getFromCache(currencyFrom, currencyTo));
                } else {
                    callback.onError(context.getString(R.string.api_limit_reached));
                }
                break;
            default:
                if (getFromCache(currencyFrom, currencyTo).length() != 0) {
                    callback.onSuccess(getFromCache(currencyFrom, currencyTo));
                } else {
                    callback.onError(context.getString(R.string.request_failed));
                }
        }
    }

    private void saveToCaсhe(String currencyFrom, String currencyTo, String amount) {
        SharedPreferences.Editor editorSharedPreferences = sharedPreferences.edit();
        editorSharedPreferences.putString(makeFromToCurrency(currencyFrom, currencyTo), amount);
//        editorSharedPreferences.putString(makeFromToCurrency(currencyTo, currencyFrom), convertToFrom(amount));
        editorSharedPreferences.commit();
    }

    private String getFromCache(String currencyFrom, String currencyTo) {
        String amountFromCache = sharedPreferences.getString(makeFromToCurrency(currencyFrom, currencyTo), "");
        return amountFromCache;
    }

    private String makeFromToCurrency(String currencyFrom, String currencyTo) {
        String stringFromTo = currencyFrom + "_" + currencyTo;
        return stringFromTo;
    }


//TODO: Подумать надо ли...
//    private String convertToFrom(String amount) {
//        Double fromTo = Double.valueOf(amount);
//        Double result = 1.0/fromTo;
//        result = Math.round(result*100000)/100000.0d;
//        String toFrom = String.valueOf(result);
//        return toFrom;
//    }

}
