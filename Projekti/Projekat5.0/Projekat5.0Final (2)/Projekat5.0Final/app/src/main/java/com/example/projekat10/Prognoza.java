package com.example.projekat10;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.projekat10.DetailsPage.IMAGEURL;


public class Prognoza {
    private String mDatum;
    private String mDan;
    private String mGrad;
    private Double mTemperatura;
    private String mPritisak;
    private String mVlaznost;
    private String mZalazak;
    private String mIzlazak;
    private String mBrzina;
    private String mPravac;
    public Double mSmer;
    private String mImage;
    HttpHelper httpHelper;
    public Prognoza(String url) {
         httpHelper=new HttpHelper();

         try {
            JSONObject jsonObject = httpHelper.getJSONObjectFromURL(url);

            JSONObject main = (JSONObject) jsonObject.get("main");
            JSONObject sys = (JSONObject) jsonObject.get("sys");
            JSONObject wind = (JSONObject) jsonObject.get("wind");
            JSONArray weather = (JSONArray) jsonObject.get("weather");
            JSONObject weather0 = (JSONObject) weather.get(0);

            this.mImage = weather0.getString("icon");
            this.mTemperatura = Double.parseDouble(main.getString("temp"));
            this.mVlaznost = main.getString("humidity");
            this.mPritisak = main.getString("pressure");
            this.mIzlazak = sys.getString("sunrise");
            this.mZalazak = sys.getString("sunset");
            this.mBrzina = wind.getString("speed");
            this.mSmer = Double.parseDouble(wind.getString("deg"));

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String strDatum = df.format(c);
            this.mDatum=strDatum;
            this.mGrad=jsonObject.getString("name");

            Date izlazakVreme = new Date(Integer.parseInt(mIzlazak) * 1000L);
            String vreme1 = df.format(izlazakVreme);
            this.mIzlazak=vreme1;

            Date zalazakVreme = new Date(Integer.parseInt(mZalazak) * 1000L);
            String vreme2 = df.format(zalazakVreme);
            this.mZalazak=vreme2;


            String pravac = null;
            if (mSmer >= 337.5 || mSmer < 22.5) pravac = "Sever";
            if (mSmer >= 22.5 && mSmer < 67.5) pravac = "Severo-Istok";
            if (mSmer >= 67.5 && mSmer < 112.5) pravac = "Istok";
            if (mSmer >= 112.5 && mSmer < 157.5) pravac = "Jugo-Istok";
            if (mSmer >= 157.5 && mSmer < 202.5) pravac = "Jug";
            if (mSmer >= 202.5 && mSmer < 247.5) pravac = "Jugo-Zapad";
            if (mSmer >= 247.5 && mSmer < 292.5) pravac = "Zapad";
            if (mSmer >= 292.5 && mSmer < 337.5) pravac = "Severo-Zapad";

            this.mPravac=pravac;


        }catch(JSONException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Prognoza(String mImage,String mDatum, String mDan,String mGrad, Double mTemperatura, String mPritisak, String mVlaznost, String mZalazak, String mIzlazak, String mBrzina, String mPravac) {

        this.mDatum = mDatum;
        this.mDan = mDan;
        this.mGrad = mGrad;
        this.mTemperatura = mTemperatura;
        this.mPritisak = mPritisak;
        this.mVlaznost = mVlaznost;
        this.mZalazak = mZalazak;
        this.mIzlazak = mIzlazak;
        this.mBrzina = mBrzina;
        this.mPravac = mPravac;
        this.mImage=mImage;
    }

    public String getmImage(){return mImage;}

    public String getmDatum() {
        return mDatum;
    }

    public String getmGrad() {
        return mGrad;
    }

    public Double getmTemperatura() {
        return mTemperatura;
    }

    public String getmPritisak() {
        return mPritisak;
    }

    public String getmVlaznost() {
        return mVlaznost;
    }

    public String getmIzlazak(){
        return mIzlazak;
    }

    public String getmZalazak() {
        return mZalazak;
    }

    public String getmBrzina() {
        return mBrzina;
    }

    public String getmPravac(){
        return mPravac;
    }

    public String getmDan(){
        return mDan;
    }
}
