package com.myntra.whattowear;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by 8302 on 4/13/2015.
 */
@RealmClass
public class WeatherData extends RealmObject {
    @PrimaryKey
    private String date;

    private int weathercode;
    private double humidity;
    private String weathercondition;
    private String weatherdetail;

    // Standard getters & setters generated by your IDE…
    public String getDate() { return date; }
    public void   setDate(String date) { this.date = date; }
    public double getHumidity() { return humidity; }
    public void   setHumidity(double hum) { this.humidity = hum; }
    public String getWeathercondition() { return weathercondition; }
    public void   setWeathercondition(String weather) { this.weathercondition = weather; }
    public String getWeatherdetail() { return weatherdetail; }
    public void   setWeatherdetail(String weather) { this.weatherdetail = weather; }
    public int    getWeathercode() { return weathercode; }
    public void   setWeathercode(int code) { this.weathercode = code; }
}

