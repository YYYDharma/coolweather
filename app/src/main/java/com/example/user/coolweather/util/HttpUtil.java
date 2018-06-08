package com.example.user.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 网络访问
 * @author
 */
public class HttpUtil {

    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
