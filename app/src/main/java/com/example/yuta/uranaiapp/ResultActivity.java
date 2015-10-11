package com.example.yuta.uranaiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 遷移元でセットしたインテントを取得
        Intent intent = getIntent();

        // インテントにセットしてあるデータを取得
        // 引数には、遷移元でつけた名前(putExtraの第一引数)を指定
        String name = intent.getStringExtra(MainActivity.INPUT_NAME);

        // 第二引数の整数は、正常な値が返ってこなかった場合の値
        int number = intent.getIntExtra(MainActivity.RESULT_NUMBER, 0);

        // strings.xmlで定義した配列を持ってくる
        String[] array = getResources().getStringArray(R.array.results);

        // レイアウトよりTextViewを取得
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

        // TextViewに名前をセット
        nameTextView.setText(name);
        resultTextView.setText(array[number]);
    }
}
