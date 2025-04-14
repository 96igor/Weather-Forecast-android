package com.yourname.weatherforecast;

public class WeatherResponse {
    private Main main;
    private String name;

    // Геттеры
    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public static class Main {
        private double temp;

        public double getTemp() {
            return temp;
        }
    }
}