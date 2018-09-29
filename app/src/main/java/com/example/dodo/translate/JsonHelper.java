package com.example.dodo.translate;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class JsonHelper {
    private static JsonHelper helper;
    public static StringBuilder result = new StringBuilder();
    private static String key = "trnsl.1.1.20180821T123608Z.266179947af465ba.641d3675275d3e122fcda62b8e9cfedff81f2c07";
    private static String lang = "en-ru";
    private static MyTask mt;
    // private String uRl ="https://translate.yandex.net/api/v1.5/tr.json/translate?key="+key+"&lang="+lang+"&text=";

    public static List<String> getJsonStringYandex(String text)  {
        final StringBuilder result = new StringBuilder();
        if (text == "") return null;
        else {
            try {
                mt = new MyTask();
                return mt.execute(text).get();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    static class MyTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... params) {
            final StringBuilder result = new StringBuilder();
            String uRl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + key + "&lang=" + lang + "&text=";
            HttpURLConnection urlConnection;
            BufferedReader reader;
            try {
                URL url = new URL(uRl + params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                result.append(buffer.toString());
            } catch (Exception e) {
                result.append(e.getMessage());
            }
            return (getParseFromJSON(result.toString()));
        }

    }

    public static List<String> getParseFromJSON(String str) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        ParseJson text = gson.fromJson(str,ParseJson. class);

        return text.text;
    }

}
