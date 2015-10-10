package com.example.yuta.uranaiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // 年選択用のSpinner
    private Spinner yearSpinner;

    // 月選択用のSpinner
    private Spinner monthSpinner;

    // 日選択用のSpinner
    private Spinner daySpinner;

    // 開始年
    private static final int START_YEAR = 1950;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // レイアウトからSpinnerを持ってくる
        yearSpinner = (Spinner) findViewById(R.id.yaerSpinner);
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);

        // Spinnerにデータをセットする
        yearSpinner.setAdapter(createYearAdapter());
    }

    /**
     * 年のArrayAdapterを作成
     * @return
     */
    private ArrayAdapter<Integer> createYearAdapter(){
        // ArrayAdapterの初期化
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);

        // 現在、西暦何年かを取得する
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        for (int i=START_YEAR; i < year; i++){
            yearAdapter.add(i);
        }

        return yearAdapter;
    }
}
