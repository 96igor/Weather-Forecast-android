package com.yourname.weatherforecast;

import java.util.List;

public class WeekWeatherResponse {

    private List<Daily> daily;

    public List<Daily> getDaily() {
        return daily;
    }

    public static class Daily {
        private Temp temp;
        private List<Weather> weather;

        public Temp getTemp() {
            return temp;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public static class Temp {
            private double day;

            public double getDay() {
                return day;
            }
        }

        public static class Weather {
            private String main;

            public String getMain() {
                return main;
            }
        }
    }
}