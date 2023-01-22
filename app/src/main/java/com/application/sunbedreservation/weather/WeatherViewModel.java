package com.application.sunbedreservation.weather;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<Double> temperature;

    public WeatherViewModel(Application application) {
        super(application);
        temperature = new MutableLiveData<>();
    }

    public MutableLiveData<Double> getTemperature() {
        return temperature;
    }

    public void fetchTemperature(String city) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+ "61965038b1b2add53258555a70ca970e";

        Request request = new Request.Builder()
                .url(url)
                .build();

        // TODO fix the http call and display the temperature

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    WeatherResponse weatherResponse = gson.fromJson(json, WeatherResponse.class);
                    temperature.postValue(weatherResponse.main.temp);
                    Log.i("rezponse", json);
                }
            }
        });
    }
}