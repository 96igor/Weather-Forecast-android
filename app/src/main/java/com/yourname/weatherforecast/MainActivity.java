package com.yourname.weatherforecast;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnChooseCity, btnToday, btnWeek, btnTwoWeeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChooseCity = findViewById(R.id.btnChooseCity);
        btnToday = findViewById(R.id.btnToday);
        btnWeek = findViewById(R.id.btnWeek);
        btnTwoWeeks = findViewById(R.id.btnTwoWeeks);

        // Заглушки переходов
        btnChooseCity.setOnClickListener(view -> {
            // TODO: открыть экран выбора города
        });

        btnToday.setOnClickListener(view -> {
            // TODO: открыть экран прогноза на сегодня
        });

        btnWeek.setOnClickListener(view -> {
            // TODO: открыть экран прогноза на 7 дней
        });

        btnTwoWeeks.setOnClickListener(view -> {
            // TODO: открыть экран прогноза на 14 дней
        });
    }
}