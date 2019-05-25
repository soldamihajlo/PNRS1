package com.example.projekat10;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class PageStatistika extends AppCompatActivity {
DbHelper dbHelper;
String strGrad,strDay;

TextView gradTextViewPageStatistika;

TextView tableDanPonedeljak,tableDanUtorak,tableDanSreda,tableDanCetvrtak,tableDanPetak,tableDanSubota,tableDanNedelja;
TextView tableTemperaturaPonedeljak,tableTemperaturaUtorak,tableTemperaturaSreda,tableTemperaturaCetvrtak,tableTemperaturaPetak,tableTemperaturaSubota,tableTemperaturaNedelja;
TextView tablePritisakPonedeljak,tablePritisakUtorak,tablePritisakSreda,tablePritisakCetvrtak,tablePritisakPetak,tablePritisakSubota,tablePritisakNedelja;
TextView tableVlaznostPonedeljak,tableVlaznostUtorak,tableVlaznostSreda,tableVlaznostCetvrtak,tableVlaznostPetak,tableVlaznostSubota,tableVlaznostNedelja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_statistika);

        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        strGrad=b.getString("text2");
        gradTextViewPageStatistika=findViewById(R.id.gradTextViewStatistika);
        gradTextViewPageStatistika.setText(strGrad);

        tableDanPonedeljak=findViewById(R.id.tableDanPonedeljak);
        tableDanUtorak=findViewById(R.id.tableDanUtorak);
        tableDanSreda=findViewById(R.id.tableDanSreda);
        tableDanCetvrtak=findViewById(R.id.tableDanCetvrtak);
        tableDanPetak=findViewById(R.id.tableDanPetak);
        tableDanSubota=findViewById(R.id.tableDanSubota);
        tableDanNedelja=findViewById(R.id.tableDanNedelja);

        //TEMPERATURA
        tableTemperaturaPonedeljak=findViewById(R.id.tableTemperaturaPonedeljak);
        tableTemperaturaUtorak=findViewById(R.id.tableTemperaturaUtorak);
        tableTemperaturaSreda=findViewById(R.id.tableTemperaturaSreda);
        tableTemperaturaCetvrtak=findViewById(R.id.tableTemperaturaCetvrtak);
        tableTemperaturaPetak=findViewById(R.id.tableTemperaturaPetak);
        tableTemperaturaSubota=findViewById(R.id.tableTemperaturaSubota);
        tableTemperaturaNedelja=findViewById(R.id.tableTemperaturaNedelja);
        //PRITISAK
        tablePritisakPonedeljak=findViewById(R.id.tablePritisakPonedeljak);
        tablePritisakUtorak=findViewById(R.id.tablePritisakUtorak);
        tablePritisakSreda=findViewById(R.id.tablePritisakSreda);
        tablePritisakCetvrtak=findViewById(R.id.tablePritisakCetvrtak);
        tablePritisakPetak=findViewById(R.id.tablePritisakPetak);
        tablePritisakSubota=findViewById(R.id.tablePritisakSubota);
        tablePritisakNedelja=findViewById(R.id.tablePritisakNedelja);

        dbHelper=new DbHelper(this);

        Prognoza[] prognoze=dbHelper.iscitajPrognze(strGrad);


        for(int i=0;i<=prognoze.length;i++) {
            switch (danUNedelji()) {
                case "Ponedeljak":
                    tableDanPonedeljak.setTypeface(null, Typeface.BOLD);
                    tableTemperaturaPonedeljak.setText(prognoze[i].getmTemperatura());
                    break;
                case "Utorak":
                    tableDanUtorak.setTypeface(null, Typeface.BOLD);
                    tableTemperaturaUtorak.setText(prognoze[i].getmTemperatura());
                    break;
                case "Sreda":
                    tableDanSreda.setTypeface(null, Typeface.BOLD);
                    tableTemperaturaSreda.setText(prognoze[i].getmTemperatura());
                    break;
                case "Cetvrtak":
                    tableDanCetvrtak.setTypeface(null, Typeface.BOLD);
                    tableTemperaturaCetvrtak.setText(prognoze[i].getmTemperatura());
                    break;
                case "Petak":
                    tableDanPetak.setTypeface(null, Typeface.BOLD);
                    tableTemperaturaPetak.setText(prognoze[i].getmTemperatura());
                    break;
                case "Subota":
                    tableDanSubota.setTypeface(null, Typeface.BOLD);
                    tableTemperaturaSubota.setText(prognoze[i].getmTemperatura());
                    break;
                case "Nedelja":
                    tableDanNedelja.setTypeface(null, Typeface.BOLD);
                    tableTemperaturaNedelja.setText(prognoze[i].getmTemperatura());
                    break;
            }
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
