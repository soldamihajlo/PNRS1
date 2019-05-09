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
    LinearLayout tempLayout;
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
            tempLayout.setVisibility(View.VISIBLE);
            sunRisesSunSetLayout.setVisibility(View.GONE);
            windLayout.setVisibility(View.GONE);
        }else if(v.getId()==R.id.buttonSunRises){
            tempLayout.setVisibility(View.GONE);
            sunRisesSunSetLayout.setVisibility(View.VISIBLE);
            windLayout.setVisibility(View.GONE);
        }else if(v.getId()==R.id.buttonWind){
            tempLayout.setVisibility(View.GONE);
            sunRisesSunSetLayout.setVisibility(View.GONE);
            windLayout.setVisibility(View.VISIBLE);

        }
    }
}
