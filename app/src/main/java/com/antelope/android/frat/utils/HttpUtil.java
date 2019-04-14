package com.antelope.android.frat.utils;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static void sendOkHttpRequest(String address, String dataType, String year, String month, String day, okhttp3.Callback callback) {
        //1、创建OkHttpClient实例
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.d("historyf", "onViewClicked: " + dataType);
        String content = "datastream_id=" + dataType + "&start=" + year + "-" + month + "-" + day + "T00:00:00&limit=10";
        Log.d("HttpUtil", "sendOkHttpRequest: " + content);

        Request request = new Request.Builder()
                .url(address + content)//这里是api的地址
                .addHeader("api-key","Uv=e=yMBymo8In9FVA4Ub16Oleo=")
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

}
