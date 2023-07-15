package com.application.sunbedreservation;

public class WeatherData {
    private double temperature;
    private int humidity;
    private String weatherDescription;

    public WeatherData(double temperature, int humidity, String weatherDescription) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.weatherDescription = weatherDescription;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }
}


