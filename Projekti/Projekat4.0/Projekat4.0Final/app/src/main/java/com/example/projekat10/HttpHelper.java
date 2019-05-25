package com.example.projekat10;

import android.accounts.NetworkErrorException;
import android.graphics.drawable.Drawable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    private static String CITYURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String CITYKEY    =   "&APPID=74db79a6620a4bc5e436705afa3268e9";

    private static final int SUCCESS=200;

    public boolean zahtev(String CityName) throws IOException, JSONException {
        HttpURLConnection urlConnection;
        java.net.URL url = new URL(CITYURL+CityName+CITYKEY);

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

        urlConnection.setReadTimeout(1000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );

        try {
            urlConnection.connect();
        } catch (IOException e) {
            return false;
        }

        int responseCode = urlConnection.getResponseCode();
        if (responseCode==SUCCESS){
            return true;
        }else{
            return false;
        }

    }

    public JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException{
        HttpURLConnection urlConnection;
        URL url=new URL(urlString);
        urlConnection=(HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Accept","application/json");
        urlConnection.setReadTimeout(100);
        urlConnection.setConnectTimeout(10000);
        try{
            urlConnection.connect();
        }catch (IOException e){
            return null;
        }

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb=new StringBuilder();
        String line;
        while((line=br.readLine())!=null){
            sb.append(line+"\n");
        }
        br.close();

        String jsonString=sb.toString();
        Log.d("HTTP GET","JSON obj- "+jsonString);
        int responseCode=urlConnection.getResponseCode();
        urlConnection.disconnect();
        Log.d("httpHelper","OK");
        return responseCode==SUCCESS?new JSONObject(jsonString):null;
    }
    public Drawable getImageFromURL(String urlString) {
        try{
            InputStream is=(InputStream) new URL(urlString).getContent();
            Drawable d=Drawable.createFromStream(is,"image");
            return d;
        }catch (Exception e){
            System.out.print("Exc:"+e);
            return null;
        }
    }

}
