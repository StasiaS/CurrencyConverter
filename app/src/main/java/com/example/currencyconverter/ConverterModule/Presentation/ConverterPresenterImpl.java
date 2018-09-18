package com.example.currencyconverter.ConverterModule.Presentation;

import android.os.Bundle;

import com.example.currencyconverter.App;
import com.example.currencyconverter.ConverterModule.Repository.ConverterRepository;
import com.example.currencyconverter.ConverterModule.Repository.ConverterRepositoryImpl;
import com.example.currencyconverter.Models.OnSuccessListener;
import com.example.currencyconverter.R;

public class ConverterPresenterImpl implements ConverterPresenter {
    private ConverterView converterView;
    private ConverterRepository converterRepository;
    private int cur = 0;


    public ConverterPresenterImpl(ConverterView converterView) {
        this.converterView = converterView;
        converterRepository = new ConverterRepositoryImpl();
    }

    @Override
    public void onClickResult(String currentFrom, String currentTo) {
        if (currentFrom.length() == 0) {
            converterView.showErrorToast(App.getContext().getString(R.string.missed_convert_from));
            return;
        }
        if (currentTo.length() == 0) {
            converterView.showErrorToast(App.getContext().getString(R.string.missed_convert_to));
            return;
        }
        converterRepository.getCurrentRate(currentFrom, currentTo, new OnSuccessListener<String, String>() {
            @Override
            public void onSuccess(String response) {
                converterView.setAnswer(response);
            }

            @Override
            public void onError(String response) {
                converterView.showErrorToast(response);
            }
        });


    }

    @Override
    public void onSavedInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt("CurrentValue", cur);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        if (inState != null) {
            cur = inState.getInt("CurrentValue");
        }
    }
}
