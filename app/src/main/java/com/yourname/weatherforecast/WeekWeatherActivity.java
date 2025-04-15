package com.yourname.weatherforecast;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeekWeatherActivity extends AppCompatActivity {

    private static final String API_KEY = "f6315e7a91792b4680f6f4acb0a45f99";
    private TextView weekWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_weather);

        weekWeatherTextView = findViewById(R.id.weekWeatherTextView);

        SharedPreferences prefs = getSharedPreferences("weather_prefs", MODE_PRIVATE);
        String city = prefs.getString("city", "Kharkiv");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);

        // Логируем запрос для города
        Log.d("WeatherRequest", "Запрос прогноза для города: " + city);

        // Получаем прогноз погоды на 5 дней (с шагом 3 часа)
        weatherService.getWeatherForecastForCity(city, "metric", API_KEY).enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("WeatherForecast", "Ответ получен");

                    StringBuilder builder = new StringBuilder();
                    builder.append("5-дневный прогноз для ").append(city).append(":\n\n");

                    int daysCount = 0;
                    for (WeatherForecastResponse.ListItem item : response.body().getList()) {
                        if (item.getDtTxt().contains("12:00:00")) {
                            double dayTemp = item.getMain().getTemp();
                            String weather = item.getWeather().get(0).getMain();

                            builder.append("День ").append(daysCount + 1).append(": ")
                                    .append(dayTemp).append("°C, ").append(weather).append("\n");

                            daysCount++;
                            if (daysCount == 7) break;
                        }
                    }

                    weekWeatherTextView.setText(builder.toString());
                } else {
                    try {
                        Log.e("API_ERROR", "Код ответа: " + response.code());
                        Log.e("API_ERROR", "Ошибка: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    showError("Ошибка загрузки прогноза");
                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                showError(t.getMessage());
                Log.e("API_ERROR", "Ошибка сети: " + t.getMessage());
            }
        });
    }

    private void showError(String msg) {
        weekWeatherTextView.setText("Ошибка: " + msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}