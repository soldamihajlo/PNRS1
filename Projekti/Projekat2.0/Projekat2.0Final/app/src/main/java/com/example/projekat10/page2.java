package com.example.projekat10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class page2 extends AppCompatActivity implements View.OnClickListener {
    TextView twoPageTextView2;
    TextView twoPageTextView4;
    Spinner twoPageSppiner;
    String strDay="";
    String[] strSppnr;
    ArrayAdapter<String> adapter;
    LinearLayout tempLayouta
    LinearLayout sunRisesSunSetLayout;
    LinearLayout windLayout;
    Button buttWind;
    Button buttTemperature;
    Button buttSunRises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

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

        twoPageTextView2=findViewById(R.id.pageTwoTextView2);
        twoPageTextView4= findViewById(R.id.pageTwoTextView4);
        twoPageSppiner=findViewById(R.id.pageTwoSpinner);
        tempLayout= findViewById(R.id.temperatureLayout);
        sunRisesSunSetLayout= findViewById(R.id.sunRisesSunSetLayout);
        windLayout=findViewById(R.id.windLayout);
        buttSunRises= findViewById(R.id.buttonSunRises);
        buttTemperature=findViewById(R.id.buttonTemperature);
        buttWind=findViewById(R.id.buttonWind);

        twoPageTextView2.setText(getIntent().getStringExtra("text1"));
        twoPageTextView4.setText(strDay);

        strSppnr=new String[]{"C","F"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strSppnr);

        twoPageSppiner.setAdapter(adapter);


        tempLayout.setVisibility(View.GONE);
        sunRisesSunSetLayout.setVisibility(View.GONE);
        windLayout.setVisibility(View.GONE);


        buttSunRises.setOnClickListener(this);
        buttWind.setOnClickListener(this);
        buttTemperature.setOnClickListener(this);
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
