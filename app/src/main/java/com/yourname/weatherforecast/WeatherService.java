package com.yourname.weatherforecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    // Получение текущей погоды для города с указанием единиц измерения (например, "metric" для Цельсия)
    @GET("weather")
    Call<WeatherResponse> getWeatherForCity(
            @Query("q") String city,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}