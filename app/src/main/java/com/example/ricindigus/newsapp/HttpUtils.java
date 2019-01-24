package com.example.ricindigus.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class HttpUtils {

    public static final String LOG_TAG = HttpUtils.class.getSimpleName().toUpperCase();
    private HttpUtils() {
    }

    public static URL createURL(String strUrl){
        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        if (url == null) return jsonResponse;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.setReadTimeout(1500);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG,"Error al realizar el request");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
            if (inputStream != null) inputStream.close();
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        if (inputStream != null){
            inputStreamReader = new InputStreamReader(inputStream,Charset.forName("UTF-8"));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    public static List<NewsItem> parsingJSON(String newsJSON){
        List<NewsItem> noticias = new ArrayList<>();
        if (TextUtils.isEmpty(newsJSON)) return  null;
        try {
            JSONObject jsonObject = new JSONObject(newsJSON);
            JSONObject jsonObjectResponse = jsonObject.getJSONObject("response");
            JSONArray jsonArray = jsonObjectResponse.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectNoticia = jsonArray.getJSONObject(i);

                JSONObject jsonFields = jsonObjectNoticia.getJSONObject("fields");

                String titulo = jsonFields.getString("headline");

                String resumen = jsonFields.getString("trailText");

                String fecha = jsonFields.getString("lastModified");

                String imagen = jsonFields.getString("thumbnail");

                String web = jsonFields.getString("shortUrl");

                noticias.add(new NewsItem(titulo,resumen,fecha,imagen,web));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return noticias;
    }

    public static List<NewsItem> fetchDataNews(String requestUrl){
        URL url = createURL(requestUrl);

        String response = "";
        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<NewsItem> noticias = parsingJSON(response);

        return noticias;
    }
}
