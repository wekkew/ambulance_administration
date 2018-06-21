package com.carapp.sheets.parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class JSONparser {

    private static final String SheetName = "Sheet1";
    private static final String BASE_URL = "https://script.google.com/macros/s/AKfycbz_wpfJN787dOKsxGL9B4PZjCLGvQAGY37x2Tvcp6AxxAEWqWI/exec";

    private static final String SheetKey = "LicencePlate";

    public static final String TAG = "TAG";

    private static Response response;

    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();

            String requestUrl = BASE_URL + "?" +  SheetKey + "=" + SheetName;

            Request request = new Request.Builder()
                    .url(requestUrl)
                    .build();
            response = client.newCall(request).execute();
            String answereText = response.body().string();
            return new JSONObject(answereText);
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}