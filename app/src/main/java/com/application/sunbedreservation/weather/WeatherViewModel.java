package com.application.sunbedreservation.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherViewModel extends ViewModel {
    private OkHttpClient client = new OkHttpClient();
    private MutableLiveData<Weather> weather = new MutableLiveData<>();
    private String apiKey = "YOUR_API_KEY";

    public void fetchWeather(String city) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder();
        urlBuilder.addQueryParameter("q", city);
        urlBuilder.addQueryParameter("appid", apiKey);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            private MutableLiveData<Weather> weather;

            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Weather weather = gson.fromJson(response.body().string(), Weather.class);
                this.weather.postValue(weather);
            }
        });
    }

    public LiveData<Weather> getWeather() {
        return weather;
    }
}
