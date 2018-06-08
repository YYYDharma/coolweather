package com.example.user.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //有天气缓存时，直接加载该页面
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weather=prefs.getString("weather",null);
        if (weather!=null){
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
        }
    }
}
