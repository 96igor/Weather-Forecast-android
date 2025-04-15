package com.yourname.weatherforecast;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherForecastResponse {

    @SerializedName("list")
    private List<ListItem> list;

    public List<ListItem> getList() {
        return list;
    }

    public static class ListItem {

        @SerializedName("dt_txt")
        private String dtTxt;

        @SerializedName("main")
        private Main main;

        @SerializedName("weather")
        private List<Weather> weather;

        public String getDtTxt() {
            return dtTxt;
        }

        public Main getMain() {
            return main;
        }

        public List<Weather> getWeather() {
            return weather;
        }
    }

    public static class Main {
        @SerializedName("temp")
        private double temp;

        public double getTemp() {
            return temp;
        }
    }

    public static class Weather {
        @SerializedName("main")
        private String main;

        public String getMain() {
            return main;
        }
    }
}
