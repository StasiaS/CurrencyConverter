package com.example.currencyconverter.ConverterModule.Presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencyconverter.App;
import com.example.currencyconverter.R;

public class ConverterActivity extends AppCompatActivity implements ConverterView{
    private ConverterPresenter converterPresenter;
    private EditText etCurrentFrom;
    private EditText etCurrentTo;
    private TextView tvResult;
    private LinearLayout llResult;
    private boolean showResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        converterPresenter = new ConverterPresenterImpl(this);

        setContentView(R.layout.activity_converter);
        etCurrentFrom = findViewById(R.id.etConvertFrom);
        etCurrentTo = findViewById(R.id.etConvertTo);
        tvResult = findViewById(R.id.tvResult);
        llResult = findViewById(R.id.llResult);


        Button btnResult = findViewById(R.id.btnResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                converterPresenter.onClickResult(
                        etCurrentFrom.getText().toString(),
                        etCurrentTo.getText().toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putString("CurrentFrom", etCurrentFrom.getText().toString());
            outState.putString("CurrentTo", etCurrentTo.getText().toString());
            outState.putString("ConvertResult", tvResult.getText().toString());
            outState.putBoolean("ShowResult", showResult);
            converterPresenter.onSavedInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        if (inState != null) {
            if (inState.containsKey("CurrentFrom")) {
                etCurrentFrom.setText(inState.getString("CurrentFrom"));
            }
            if (inState.containsKey("CurrentTo")) {
                etCurrentTo.setText(inState.getString("CurrentTo"));
            }
            if (inState.containsKey("ConvertResult")) {
                tvResult.setText(inState.getString("ConvertResult"));
            }
            if (inState.containsKey("ShowResault")) {
                showResult = inState.getBoolean("ShowResult");
                if(showResult) {
                    llResult.setVisibility(View.VISIBLE);
                } else {
                    llResult.setVisibility(View.GONE);
                }
            }
            converterPresenter.onRestoreInstanceState(inState);
        }
    }

    @Override
    public void setAnswer(String rate) {
        tvResult.setText(rate);
        showResult = true;
        llResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorToast(String errorMessage) {
        Toast.makeText(App.getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }
}
