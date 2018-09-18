package com.example.currencyconverter.ConverterModule.Presentation;

import android.os.Bundle;

public interface ConverterPresenter {
    public void onClickResult(String currentFrom, String currentTo);
    public void onSavedInstanceState(Bundle outState);
    public void onRestoreInstanceState(Bundle inState);
}
