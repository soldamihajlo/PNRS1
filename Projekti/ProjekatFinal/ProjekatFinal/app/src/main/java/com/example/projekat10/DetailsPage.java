package com.example.projekat10;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
    private static final String TAG="FROM_CHICAGO_YOURS";
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

    String sPritisak,sVlaznost,sIzlazak,sZalazak,sBrzinaVetra,sImage;
    double sTemperatura,sPravac;
    String strDatum;
    String vreme2;

    private LocalService mService;
    private static boolean mBound;

    Drawable d1;
    HttpHelper httpHelper2;
    String pravac = null;
    TextView lastUpdateTextView;
    ImageButton updateButton;
    Button buttonStatistika;
    DbHelper dbHelper;
    String vreme1;
    Handler handler;

    private MyNDK myNDK;


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

        //DATUM
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        strDatum = df.format(c);
        twoPageTextView4= findViewById(R.id.pageTwoTextView4);
        twoPageTextView4.setText(strDatum);


        //Layout i button
        tempLayout= findViewById(R.id.temperatureLayout);
        sunRisesSunSetLayout= findViewById(R.id.sunRisesSunSetLayout);
        windLayout=findViewById(R.id.windLayout);

        buttSunRises= findViewById(R.id.buttonSunRises);
        buttTemperature=findViewById(R.id.buttonTemperature);
        buttWind=findViewById(R.id.buttonWind);
        updateButton=findViewById(R.id.updateButton);

        tempLayout.setVisibility(View.GONE);
        sunRisesSunSetLayout.setVisibility(View.GONE);
        windLayout.setVisibility(View.GONE);

        buttSunRises.setOnClickListener(this);
        buttWind.setOnClickListener(this);
        buttTemperature.setOnClickListener(this);
        updateButton.setOnClickListener(this);


        //Zadatak 3.0
        tempeturaTextView=findViewById(R.id.temperaturaTextView);
        pritisakTextView=findViewById(R.id.pritisakTextView);
        vlaznostTextView=findViewById(R.id.vlaznostTextView);
        izlazakTextView=findViewById(R.id.izlazakTextView);
        zalazakTextView=findViewById(R.id.zalazakTextView);
        brzinaVetraTextView=findViewById(R.id.brzinaVetraTextView);
        pravacTextView=findViewById(R.id.pravacVetraTextView);

        lastUpdateTextView=findViewById(R.id.lastUpdateTextView);
        handler=new Handler();
        httpHelper = new HttpHelper();

        buttonStatistika=findViewById(R.id.statistikaButton);
        buttonStatistika.setOnClickListener(this);



        //Spiner
        twoPageSppiner=findViewById(R.id.pageTwoSpinner);
        strSppnr=new String[]{"C","F"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strSppnr);
        twoPageSppiner.setAdapter(adapter);
        dbHelper=new DbHelper(this);
        ispisiPodatke();

        myNDK=new MyNDK();


        twoPageSppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private String izabrani,prethodni=null;
            @Override
            public void onItemSelected(AdapterView adapter, View view, int position, long id) {

                izabrani= adapter.getItemAtPosition(position).toString();

                if(izabrani=="C" && prethodni=="F"){
                    sTemperatura=myNDK.promeni(sTemperatura,false);
                    tempeturaTextView.setText(sTemperatura+" °C");
                    prethodni="C";
                }
                if(izabrani=="F"){
                    if(!tempeturaTextView.getText().toString().isEmpty()) {
                        sTemperatura=myNDK.promeni(sTemperatura,true);
                        tempeturaTextView.setText(sTemperatura+ " °F");
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
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocalService.class);
        Bundle bundle = new Bundle();
        bundle.putString("city", strGrad);
        intent.putExtras(bundle);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.d("Bind Notification","bindujem");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        mBound = false;
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


    private void ispisiPodatke() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String URL1 = CITYURL + strGrad + "&units=metric" + CITYKEY;
                JSONObject jsonObject = null;
                try {
                    jsonObject = httpHelper.getJSONObjectFromURL(URL1);
                    final String GRAD = strGrad;

                    final JSONObject main = (JSONObject) jsonObject.get("main");
                    final JSONObject sys = (JSONObject) jsonObject.get("sys");
                    final JSONObject wind = (JSONObject) jsonObject.get("wind");
                    final JSONArray weather = (JSONArray) jsonObject.get("weather");
                    final JSONObject weather0 = (JSONObject) weather.get(0);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                sImage = weather0.getString("icon");
                                sTemperatura = Double.parseDouble(main.getString("temp"));
                                sVlaznost = main.getString("humidity");
                                sPritisak = main.getString("pressure");
                                sIzlazak = sys.getString("sunrise");
                                sZalazak = sys.getString("sunset");
                                sBrzinaVetra = wind.getString("speed");
                                sPravac = Double.parseDouble(wind.getString("deg"));

                                TimeZone tz = TimeZone.getTimeZone("GMT+2");
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                df.setTimeZone(tz);

                                Date izlazakVreme = new Date(Integer.parseInt(sIzlazak) * 1000L);
                                vreme1 = df.format(izlazakVreme);

                                Date zalazakVreme = new Date(Integer.parseInt(sZalazak) * 1000L);
                                vreme2 = df.format(zalazakVreme);

                                if (sPravac >= 337.5 || sPravac < 22.5) pravac = "Sever";
                                if (sPravac >= 22.5 && sPravac < 67.5) pravac = "Severo-Istok";
                                if (sPravac >= 67.5 && sPravac < 112.5) pravac = "Istok";
                                if (sPravac >= 112.5 && sPravac < 157.5) pravac = "Jugo-Istok";
                                if (sPravac >= 157.5 && sPravac < 202.5) pravac = "Jug";
                                if (sPravac >= 202.5 && sPravac < 247.5) pravac = "Jugo-Zapad";
                                if (sPravac >= 247.5 && sPravac < 292.5) pravac = "Zapad";
                                if (sPravac >= 292.5 && sPravac < 337.5) pravac = "Severo-Zapad";

                                Prognoza prognoza = new Prognoza(sImage, strDatum, danUNedelji(), strGrad, sTemperatura, sPritisak, sVlaznost, vreme2, vreme1, sBrzinaVetra, pravac);
                                Prognoza dummy=new Prognoza("123","15.maj.2019.","Sreda","Kula",20.0,"1000","85","07:00","19:00","56","Sever");

                                dbHelper.insert(dummy);
                                dbHelper.insert(prognoza);

                                twoPageTextView2.setText(GRAD);
                                twoPageTextView4.setText(prognoza.getmDatum());

                                tempeturaTextView.setText(prognoza.getmTemperatura() + " °C");
                                pritisakTextView.setText(prognoza.getmPritisak() + "");
                                vlaznostTextView.setText(prognoza.getmVlaznost() + "%");
                                izlazakTextView.setText(prognoza.getmIzlazak());
                                zalazakTextView.setText(prognoza.getmZalazak());
                                brzinaVetraTextView.setText(prognoza.getmBrzina() + "m/s");
                                pravacTextView.setText(prognoza.getmPravac());
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }



    @Override
    public void onClick(View v) {
        //                                                              Button Temperatura
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
            //                                                              Button SunRises
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
            //                                                          Button WIND
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
            //                                                          Button UPDATE
        }else if(v.getId()==R.id.updateButton){
            lastUpdateTextView.setVisibility(View.GONE);
            ispisiPodatke();
            //                                                      Button Statistika
        }else if(v.getId()==R.id.statistikaButton){
            Intent i=new Intent(this,PageStatistika.class);
            Bundle b=new Bundle();
            b.putString("text2",strGrad);
            i.putExtras(b);
            this.startActivity(i);
        }

    }



    public String danUNedelji(){
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
        return strDay;
    }
}
