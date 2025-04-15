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

    // Получение прогноза погоды на 7 дней для города (через старую версию API)
    @GET("forecast")
    Call<WeatherForecastResponse> getWeatherForecastForCity(
            @Query("q") String city,
            @Query("units") String units,
            @Query("appid") String apiKey
    );

    // Получение прогноза погоды по координатам (One Call API)
    @GET("onecall")
    Call<WeekWeatherResponse> getWeatherForWeek(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}