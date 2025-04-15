package com.yourname.weatherforecast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SelectCityActivity extends AppCompatActivity {

    private EditText editTextCity;
    private Button buttonSaveCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        editTextCity = findViewById(R.id.editTextCity);
        buttonSaveCity = findViewById(R.id.buttonSaveCity);

        buttonSaveCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCity = editTextCity.getText().toString().trim();

                if (!selectedCity.isEmpty()) {
                    // Сохраняем город в SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("weather_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("city", selectedCity);
                    editor.apply();

                    // Возвращаемся на главный экран
                    Intent intent = new Intent(SelectCityActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }
}