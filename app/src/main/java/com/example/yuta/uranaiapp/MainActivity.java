package com.example.yuta.uranaiapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // 開始年
    private static final int START_YEAR = 1950;
    // 年選択用のSpinner
    private Spinner yearSpinner;
    // 月選択用のSpinner
    private Spinner monthSpinner;
    // 日選択用のSpinner
    private Spinner daySpinner;

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
        monthSpinner.setAdapter(createMonthAdapter());
        daySpinner.setAdapter(createDayAdapter());

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                daySpinner.setAdapter(createDayAdapter());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinnerで値が選択された時のイベント
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                daySpinner.setAdapter(createDayAdapter());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 年のArrayAdapterを作成
     */
    private ArrayAdapter<Integer> createYearAdapter() {
        // ArrayAdapterの初期化
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);

        // 現在、西暦何年かを取得する
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        for (int i = START_YEAR; i < year; i++) {
            yearAdapter.add(i);
        }

        return yearAdapter;
    }

    /**
     * ArrayAdapterに月データをセットする
     */
    private ArrayAdapter<Integer> createMonthAdapter() {
        // ArrayAdapterの初期化
        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);

        for (int i = 1; i <= 12; i++) {
            monthAdapter.add(i);
        }

        return monthAdapter;
    }

    /**
     * ArrayAdapterに日データをセットする
     */
    private ArrayAdapter<Integer> createDayAdapter() {
        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);

        // 今、何月が選択されているかを取得
        int month = (int) monthSpinner.getSelectedItem();

        // 選択されている月が4,6,9,11の場合、1~30までとする
        int day = 31;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        }

        // 選択されている月が2月の場合
        else if (month == 2) {
            int year = (int) yearSpinner.getSelectedItem();

            // 西暦の年が4で割り切れる年はうるう年
            // ただし、100で割り切れる場合はうるう年ではない → 100で割り切れない場合はうるう年
            // ただし、400で割り切れる場合はうるう年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                day = 29;
            } else {
                day = 28;
            }
        }

        for (int i = 1; i <= day; i++) {
            dayAdapter.add(i);
        }

        return dayAdapter;
    }
}
