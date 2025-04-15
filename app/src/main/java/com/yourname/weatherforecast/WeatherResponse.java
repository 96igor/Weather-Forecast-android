package com.yourname.weatherforecast;

import java.util.List;

public class WeatherResponse {
    private String name;
    private List<Weather> weather;
    private Main main;
    private Coord coord;

    public String getName() {
        return name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Coord getCoord() {
        return coord;
    }

    public static class Weather {
        private String description;

        public String getDescription() {
            return description;
        }
    }

    public static class Main {
        private double temp;

        public double getTemp() {
            return temp;
        }
    }

    public static class Coord {
        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }
    }
}