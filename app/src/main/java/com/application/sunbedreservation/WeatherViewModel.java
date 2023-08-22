package com.application.sunbedreservation;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<WeatherData> weatherData = new MutableLiveData<>();

    public LiveData<WeatherData> getWeatherData() {
        return weatherData;
    }

    public void fetchWeatherData(Context context, double latitude, double longitude) {
        String apiKey = "61965038b1b2add53258555a70ca970e";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                "&lon=" + longitude + "&appid=" + apiKey;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject mainObject = response.getJSONObject("main");
                        double temperature = mainObject.getDouble("temp");
                        int celsiusTemperature = (int) (temperature - 273.15);
                        int humidity = mainObject.getInt("humidity");

                        JSONArray weatherArray = response.getJSONArray("weather");
                        JSONObject weatherObject = weatherArray.getJSONObject(0);
                        String weatherDescription = weatherObject.getString("description");

                        WeatherData data = new WeatherData(celsiusTemperature, humidity, weatherDescription);
                        weatherData.setValue(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error cases
                }
        );

        // Add the request to the RequestQueue (initialized in your activity or fragment)
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }
}
