package com.example.projekat10;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DetailsPage extends AppCompatActivity implements View.OnClickListener {
    TextView twoPageTextView2,twoPageTextView4,tempeturaTextView,pritisakTextView,vlaznostTextView,izlazakTextView,zalazakTextView,brzinaVetraTextView,pravacTextView;
    Spinner twoPageSppiner;
    String strDay="";
    String strGrad;
    String[] strSppnr;
    ArrayAdapter<String> adapter;
    LinearLayout tempLayout,sunRisesSunSetLayout,windLayout;
    Button buttWind,buttTemperature,buttSunRises;
    ImageView imageView;

    private HttpHelper httpHelper;
    public static String CITYURL    =   "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String grad   =  null;
    public static String CITYKEY    =   "&APPID=74db79a6620a4bc5e436705afa3268e9";
    public static String IMAGEURL="http://openweathermap.org/img/w/";

    String sTemperatura,sPritisak,sVlaznost,sIzlazak,sZalazak,sBrzinaVetra,sImage;
    double sPravac;

    Drawable d1;
    private HttpHelper httpHelper2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);


        //Grad
        twoPageTextView2=findViewById(R.id.pageTwoTextView2);
        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        strGrad=b.getString("text1");
        grad=strGrad;
        twoPageTextView2.setText(strGrad);

        //Dan
        Calendar cal=Calendar.getInstance();
        int day=cal.get(Calendar.DAY_OF_WEEK);

        switch (day){
            case Calendar.MONDAY:
                strDay="Ponedeljak";
                break;

            case Calendar.TUESDAY:
                strDay="Utorak";
                break;

            case Calendar.WEDNESDAY:
                strDay="Sreda";
                break;

            case Calendar.THURSDAY:
                strDay="Cetvrtak";
                break;

            case Calendar.FRIDAY:
                strDay="Petak";
                break;

            case Calendar.SATURDAY:
                strDay="Subota";
                break;

            case Calendar.SUNDAY:
                strDay="Nedelja";
                break;

        }
        twoPageTextView4= findViewById(R.id.pageTwoTextView4);
        twoPageTextView4.setText(strDay);

        //Layout i button
        tempLayout= findViewById(R.id.temperatureLayout);
        sunRisesSunSetLayout= findViewById(R.id.sunRisesSunSetLayout);
        windLayout=findViewById(R.id.windLayout);

        buttSunRises= findViewById(R.id.buttonSunRises);
        buttTemperature=findViewById(R.id.buttonTemperature);
        buttWind=findViewById(R.id.buttonWind);

        tempLayout.setVisibility(View.GONE);
        sunRisesSunSetLayout.setVisibility(View.GONE);
        windLayout.setVisibility(View.GONE);

        buttSunRises.setOnClickListener(this);
        buttWind.setOnClickListener(this);
        buttTemperature.setOnClickListener(this);

        //Zadatak 3.0
        tempeturaTextView=findViewById(R.id.temperaturaTextView);
        pritisakTextView=findViewById(R.id.pritisakTextView);
        vlaznostTextView=findViewById(R.id.vlaznostTextView);
        izlazakTextView=findViewById(R.id.izlazakTextView);
        zalazakTextView=findViewById(R.id.zalazakTextView);
        brzinaVetraTextView=findViewById(R.id.brzinaVetraTextView);
        pravacTextView=findViewById(R.id.pravacVetraTextView);

        httpHelper = new HttpHelper();

        new Thread(new Runnable() {

            public void run() {
                try{
                    final String URL1=CITYURL + grad +"&units=metric" +CITYKEY;
                    JSONObject jsonObject=httpHelper.getJSONObjectFromURL(URL1);

                    JSONObject main=(JSONObject) jsonObject.get("main");
                    JSONObject sys=(JSONObject) jsonObject.get("sys");
                    JSONObject wind=(JSONObject) jsonObject.get("wind");
                    JSONArray weather=(JSONArray) jsonObject.get("weather");
                    JSONObject weather0=(JSONObject) weather.get(0);

                    sImage=weather0.getString("icon");
                    sTemperatura=main.getString("temp");
                    sVlaznost=main.getString("humidity");
                    sPritisak=main.getString("pressure");
                    sIzlazak=sys.getString("sunrise");
                    sZalazak=sys.getString("sunset");
                    sBrzinaVetra=wind.getString("speed");
                    sPravac=Double.parseDouble(wind.getString("deg"));

                    final String URL2=IMAGEURL+sImage+".png";

                    //Iconica
                    d1=httpHelper2.getImageFromURL(URL2);
                    imageView=findViewById(R.id.imageView);
                    imageView.setBackground(d1);


                    tempeturaTextView.setText(sTemperatura+" °C");
                    vlaznostTextView.setText(sVlaznost+" %");
                    pritisakTextView.setText(sPritisak+" mb");
                    brzinaVetraTextView.setText(sBrzinaVetra+" m/s");

                    TimeZone tz= TimeZone.getTimeZone("GMT+2");
                    SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
                    df.setTimeZone(tz);

                    Date izlazakVreme=new Date(Integer.parseInt(sIzlazak)*1000L);
                    String vreme1=df.format(izlazakVreme);
                    izlazakTextView.setText(vreme1);

                    Date zalazakVreme=new Date(Integer.parseInt(sZalazak)*1000L);
                    String vreme2=df.format(zalazakVreme);
                    zalazakTextView.setText(vreme2);

                    String pravac=null;
                    if(sPravac>=337.5 || sPravac<22.5) pravac="Sever";
                    if(sPravac>=22.5 && sPravac<67.5) pravac="Severo-Istok";
                    if(sPravac>=67.5 && sPravac<112.5) pravac="Istok";
                    if(sPravac>=112.5 && sPravac<157.5) pravac="Jugo-Istok";
                    if(sPravac>=157.5 && sPravac<202.5) pravac="Jug";
                    if(sPravac>=202.5 && sPravac<247.5) pravac="Jugo-Zapad";
                    if(sPravac>=247.5 && sPravac<292.5) pravac="Zapad";
                    if(sPravac>=292.5 && sPravac<337.5) pravac="Severo-Zapad";

                    pravacTextView.setText(pravac+" ("+sPravac+"°)");

                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();


        //Spiner
        twoPageSppiner=findViewById(R.id.pageTwoSpinner);
        strSppnr=new String[]{"C","F"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strSppnr);
        twoPageSppiner.setAdapter(adapter);

        twoPageSppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private String izabrani,prethodni=null;
            private double razlika=0;
            @Override
            public void onItemSelected(AdapterView adapter, View view, int position, long id) {

                izabrani= adapter.getItemAtPosition(position).toString();

                if(izabrani=="C" && prethodni=="F"){
                    razlika=Double.parseDouble(tempeturaTextView.getText().toString().substring(0,tempeturaTextView.getText().toString().length()-3));
                    razlika=(razlika-32)*5/9;
                    razlika=razlika*100;
                    razlika=Math.round(razlika);
                    razlika=razlika/100;
                    tempeturaTextView.setText(String.valueOf(razlika)+" °C");
                    prethodni="C";
                }
                if(izabrani=="F"){
                    if(!tempeturaTextView.getText().toString().isEmpty()) {
                        razlika = Double.parseDouble(tempeturaTextView.getText().toString().substring(0, tempeturaTextView.getText().toString().length() - 3));
                        razlika = razlika * 9 / 5 + 32;
                        razlika = razlika * 100;
                        razlika = Math.round(razlika);
                        razlika = razlika / 100;
                        tempeturaTextView.setText(String.valueOf(razlika) + " °F");
                        prethodni = "F";
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonTemperature){
            if(tempLayout.getVisibility()==View.VISIBLE){
                tempLayout.setVisibility(View.GONE);
                buttTemperature.setBackground(getDrawable(R.drawable.shape));
            }else {
                tempLayout.setVisibility(View.VISIBLE);
                buttTemperature.setBackground(getDrawable(R.drawable.shapeonclick));
            }
            buttWind.setBackground(getDrawable(R.drawable.shape));
            buttSunRises.setBackground(getDrawable(R.drawable.shape));
            sunRisesSunSetLayout.setVisibility(View.GONE);
            windLayout.setVisibility(View.GONE);
        }else if(v.getId()==R.id.buttonSunRises){
            if(sunRisesSunSetLayout.getVisibility()==View.VISIBLE){
                sunRisesSunSetLayout.setVisibility(View.GONE);
                buttSunRises.setBackground(getDrawable(R.drawable.shape));
            }else {
                sunRisesSunSetLayout.setVisibility(View.VISIBLE);
                buttSunRises.setBackground(getDrawable(R.drawable.shapeonclick));
            }
            buttWind.setBackground(getDrawable(R.drawable.shape));
            buttTemperature.setBackground(getDrawable(R.drawable.shape));
            tempLayout.setVisibility(View.GONE);
            windLayout.setVisibility(View.GONE);
        }else if(v.getId()==R.id.buttonWind){
            if(windLayout.getVisibility()==View.VISIBLE){
                windLayout.setVisibility(View.GONE);
                buttWind.setBackground(getDrawable(R.drawable.shape));
            }else {
                windLayout.setVisibility(View.VISIBLE);
                buttWind.setBackground(getDrawable(R.drawable.shapeonclick));
            }
            buttSunRises.setBackground(getDrawable(R.drawable.shape));
            buttTemperature.setBackground(getDrawable(R.drawable.shape));
            tempLayout.setVisibility(View.GONE);
            sunRisesSunSetLayout.setVisibility(View.GONE);
        }

    }
}
