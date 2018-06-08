package com.example.user.coolweather.util;

import android.text.TextUtils;

import com.example.user.coolweather.db.City;
import com.example.user.coolweather.db.County;
import com.example.user.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;

/**
 * 处理服务器返回的json数据
 * @author
 */
public class Utility {

    /**
     * 处理省级数据
     */
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            //如果response不为null或者""
            try{
                JSONArray allProvinces=new JSONArray(response);
                for (int i=0;i<allProvinces.length();i++){
                    JSONObject object=allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(object.getString("name"));
                    province.setProvinceCode(object.getInt("id"));
                    province.save();//保存至数据库
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 处理市级数据
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities=new JSONArray(response);
                for (int i=0;i<allCities.length();i++){
                    JSONObject object=allCities.getJSONObject(i);
                    City city=new City();
                    city.setCityName(object.getString("name"));
                    city.setCityCode(object.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * 处理区县数据
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties=new JSONArray(response);
                for (int i=0;i<allCounties.length();i++) {
                    JSONObject object = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(object.getString("name"));
                    county.setWeatherId(object.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
