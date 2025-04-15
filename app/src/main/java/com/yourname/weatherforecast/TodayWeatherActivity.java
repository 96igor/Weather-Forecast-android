package com.yourname.weatherforecast;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodayWeatherActivity extends AppCompatActivity {

    private static final String API_KEY = "f6315e7a91792b4680f6f4acb0a45f99";
    private TextView todayWeatherText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_weather);

        todayWeatherText = findViewById(R.id.todayWeatherText);

        // Получаем сохранённый город
        SharedPreferences prefs = getSharedPreferences("weather_prefs", MODE_PRIVATE);
        String city = prefs.getString("city", "Kharkiv");

        // Запрашиваем данные
        fetchWeather(city);
    }

    private void fetchWeather(String city) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getWeatherForCity(city, "metric", API_KEY);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse data = response.body();
                    String weatherInfo = "Weather in " + data.getName() + ": " + data.getMain().getTemp() + "°C";
                    todayWeatherText.setText(weatherInfo);
                } else {
                    todayWeatherText.setText("Failed to load data");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                todayWeatherText.setText("Error: " + t.getMessage());
            }
        });
    }
}