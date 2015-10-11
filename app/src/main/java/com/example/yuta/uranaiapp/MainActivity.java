package com.example.yuta.uranaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // 入力された名前
    public static final String INPUT_NAME = "InputName";
    // 計算値(添字として使用する)
    public static final String RESULT_NUMBER = "ResultNumber";
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

        // 「占う」ボタンを押した際のリスナー設定
        Button button = (Button) findViewById(R.id.divineButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            // 実際に選択されたView(Button)
            public void onClick(View v) {
                // Intentクラスのオブジェクト作成
                // 第一引数がMainActivityのコンテキスト(MainActivity.this)
                // コンテキストはアプリの情報や状態を保持しているもの
                // 第二引数が遷移先のクラス
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

                // レイアウトからEditTextを取り出す
                EditText editText = (EditText) findViewById(R.id.nameEditText);
                String name = editText.getText().toString();

                // 0~9の数字を求める為、生年月日を使用する
                int year = (int) yearSpinner.getSelectedItem();
                int month = (int) monthSpinner.getSelectedItem();
                int day = (int) daySpinner.getSelectedItem();

                // 年、月、日を足しあわせた結果を文字列に変換する
                String str = String.valueOf(year + month + day);

                // 文字列の一番後ろ(1桁目)を取り出し、整数に変換する
                Integer resultNumber = Integer.parseInt(str.substring(str.length() - 1));


                // 画面遷移前に、遷移先に移したいデータをセットしておく
                // 第一引数が、渡すデータにつける名前
                // 第二引数が、実際に渡すデータ
                intent.putExtra(INPUT_NAME, name);
                intent.putExtra(RESULT_NUMBER, resultNumber);

                // 画面遷移の処理を行うためのメソッド
                // intent(遷移先や、渡すためのデータが入っている)
                startActivity(intent);
            }
        });
    }

    /**
     * 年のArrayAdapterを作成
     */
    private ArrayAdapter<Integer> createYearAdapter() {
        // ArrayAdapterの初期化
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);

        // ドロップダウン時の画面レイアウトを設定
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

        // ドロップダウン時の画面レイアウトを設定
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

        // ドロップダウン時の画面レイアウトを設定
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
