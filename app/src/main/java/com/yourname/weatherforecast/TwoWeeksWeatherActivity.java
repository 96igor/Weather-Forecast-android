package com.yourname.weatherforecast;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwoWeeksWeatherActivity extends AppCompatActivity {

    private static final String API_KEY = "f6315e7a91792b4680f6f4acb0a45f99";
    private TextView twoWeeksWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_weeks_weather);

        twoWeeksWeatherTextView = findViewById(R.id.twoWeeksWeatherTextView);

        SharedPreferences prefs = getSharedPreferences("weather_prefs", MODE_PRIVATE);
        String city = prefs.getString("city", "Kharkiv");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);

        weatherService.getWeatherForecastForCity(city, "metric", API_KEY).enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<WeatherForecastResponse.ListItem> forecastList = response.body().getList();
                    StringBuilder builder = new StringBuilder();
                    builder.append("Прогноз на 5 дней для ").append(city).append(":\n\n");

                    Set<String> shownDates = new HashSet<>();

                    for (WeatherForecastResponse.ListItem item : forecastList) {
                        String date = item.getDtTxt().split(" ")[0];

                        if (!shownDates.contains(date)) {
                            double temp = item.getMain().getTemp();
                            String description = item.getWeather().get(0).getMain();

                            builder.append(date).append(": ")
                                    .append(temp).append("°C, ").append(description).append("\n");

                            shownDates.add(date);
                        }

                        if (shownDates.size() >= 5) break;
                    }

                    twoWeeksWeatherTextView.setText(builder.toString());
                } else {
                    showError("Ошибка загрузки прогноза");
                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                showError("Ошибка: " + t.getMessage());
            }
        });
    }

    private void showError(String msg) {
        twoWeeksWeatherTextView.setText("Ошибка: " + msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}