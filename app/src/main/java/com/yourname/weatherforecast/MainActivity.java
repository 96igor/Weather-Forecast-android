package com.yourname.weatherforecast;

import com.yourname.weatherforecast.SelectCityActivity;
import com.yourname.weatherforecast.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_CITY = "Kharkiv";  // Город по умолчанию
    private static final String API_KEY = "f6315e7a91792b4680f6f4acb0a45f99";  // Ключ API
    private EditText cityEditText;
    private TextView weatherInfoTextView;

    Button btnChooseCity, btnToday, btnWeek, btnTwoWeeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChooseCity = findViewById(R.id.btnChooseCity);
        btnToday = findViewById(R.id.btnToday);
        btnWeek = findViewById(R.id.btnWeek);
        btnTwoWeeks = findViewById(R.id.btnTwoWeeks);

        cityEditText = findViewById(R.id.cityEditText);
        weatherInfoTextView = findViewById(R.id.weatherInfoTextView);

        // Загружаем город из SharedPreferences
        SharedPreferences prefs = getSharedPreferences("weather_prefs", MODE_PRIVATE);
        String savedCity = prefs.getString("city", DEFAULT_CITY);
        cityEditText.setText(savedCity);

        // Кнопка "Выбрать город"
        btnChooseCity.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SelectCityActivity.class));
        });

        // Погода на сегодня
        btnToday.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TodayWeatherActivity.class);
            startActivity(intent);
        });

        btnWeek.setOnClickListener(view -> {
            // Открываем экран погоды на неделю
            Intent intent = new Intent(MainActivity.this, WeekWeatherActivity.class);
            startActivity(intent);
        });

        btnTwoWeeks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TwoWeeksWeatherActivity.class);
            startActivity(intent);
        });
    }

    private void fetchWeather() {
        String city = cityEditText.getText().toString();
        if (city.isEmpty()) {
            city = DEFAULT_CITY;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);

        Call<WeatherResponse> call = weatherService.getWeatherForCity(city, "metric", API_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    String weatherInfo = "Weather in " + weatherResponse.getName() +
                            ": " + weatherResponse.getMain().getTemp() + "°C";
                    weatherInfoTextView.setText(weatherInfo);
                } else {
                    weatherInfoTextView.setText("Failed to get weather data");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherInfoTextView.setText("Error: " + t.getMessage());
            }
        });
    }
}