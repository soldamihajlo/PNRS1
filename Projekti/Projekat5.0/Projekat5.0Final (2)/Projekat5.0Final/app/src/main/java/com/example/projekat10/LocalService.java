package com.example.projekat10;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class LocalService extends Service {
    String sPritisak,sVlaznost,sIzlazak,sZalazak,sBrzinaVetra,sImage;
    double sTemperatura,sPravac;
    String strDatum;
    String vreme2,vreme1;
    String strDay="";
    String url;
    String pravac;

    private static final String TAG = "Tag BOUND";
    private static final long PERIOD = 5000L;

    private RunnableWeather mRunnable;

    public static String CITYURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String CITYKEY = "&APPID=74db79a6620a4bc5e436705afa3268e9";
    private static String CITY;

    private HttpHelper httpHelper;
    private DbHelper mWeatherHelper;

    private final IBinder binder = new LocalBinder();



    @Override
    public void onCreate(){
        super.onCreate();
        httpHelper=new HttpHelper();
        mWeatherHelper=new DbHelper(this);
        mRunnable=new RunnableWeather();
        mRunnable.start();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRunnable.stop();
    }


    public class LocalBinder extends Binder{
        LocalService getService(){
            return LocalService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        CITY = intent.getStringExtra("city");
        Log.d(TAG, "123456" + CITY);
        return binder;
    }



    private class RunnableWeather  implements Runnable{
        private Handler mHandler;
        private boolean mRun=false;

        public RunnableWeather(){mHandler=new Handler(getMainLooper());}

        public void start(){
            mRun=true;
            mHandler.postDelayed(this,PERIOD);
        }
        public void stop(){
            mRun=false;
            mHandler.removeCallbacks(this);
        }
        @Override
        public void run(){
            if(!mRun){
                return;
            }
            url=CITYURL+CITY+CITYKEY;


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = httpHelper.getJSONObjectFromURL(url);

                        final JSONObject main = (JSONObject) jsonObject.get("main");
                        final JSONObject sys = (JSONObject) jsonObject.get("sys");
                        final JSONObject wind = (JSONObject) jsonObject.get("wind");
                        final JSONArray weather = (JSONArray) jsonObject.get("weather");
                        final JSONObject weather0 = (JSONObject) weather.get(0);

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

                        Prognoza prognoza = new Prognoza(sImage, strDatum, danUNedelji(), CITY, sTemperatura, sPritisak, sVlaznost, vreme2, vreme1, sBrzinaVetra, pravac);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            Prognoza prognozaCitaj=mWeatherHelper.iscitajPrognozu(CITY);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(LocalService.this);
            builder.setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.icons)
                    .setTicker("Weather Forecast")
                    .setContentTitle("Temperature updated --> " + prognozaCitaj.getmGrad())
                    .setContentText(prognozaCitaj.getmTemperatura() + " °C")
                    .setContentInfo("INFO");

            NotificationManager notification = (NotificationManager) LocalService.this.getSystemService(Context.NOTIFICATION_SERVICE);
            notification.notify(1, builder.build());

            Log.d(TAG, "JSON done, base updated");
            mHandler.postDelayed(this, PERIOD);
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
