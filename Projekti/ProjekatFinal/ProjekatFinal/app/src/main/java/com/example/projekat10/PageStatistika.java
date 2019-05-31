package com.example.projekat10;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class PageStatistika extends AppCompatActivity implements View.OnClickListener {
    DbHelper dbHelper;
    String strGrad, strDay;

    TextView gradTextViewPageStatistika;

    TextView tableDanPonedeljak, tableDanUtorak, tableDanSreda, tableDanCetvrtak, tableDanPetak, tableDanSubota, tableDanNedelja;
    TextView tableTemperaturaPonedeljak, tableTemperaturaUtorak, tableTemperaturaSreda, tableTemperaturaCetvrtak, tableTemperaturaPetak, tableTemperaturaSubota, tableTemperaturaNedelja;
    TextView tablePritisakPonedeljak, tablePritisakUtorak, tablePritisakSreda, tablePritisakCetvrtak, tablePritisakPetak, tablePritisakSubota, tablePritisakNedelja;
    TextView tableVlaznostPonedeljak, tableVlaznostUtorak, tableVlaznostSreda, tableVlaznostCetvrtak, tableVlaznostPetak, tableVlaznostSubota, tableVlaznostNedelja;
    TextView minTemperatura, maxTemperatura;
    ImageButton sunButtonStatistika, snowflakeButtonStatistika;
    Prognoza[] prognoze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_statistika);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        strGrad = b.getString("text2");
        gradTextViewPageStatistika = findViewById(R.id.gradTextViewStatistika);
        gradTextViewPageStatistika.setText(strGrad);

        tableDanPonedeljak = findViewById(R.id.tableDanPonedeljak);
        tableDanUtorak = findViewById(R.id.tableDanUtorak);
        tableDanSreda = findViewById(R.id.tableDanSreda);
        tableDanCetvrtak = findViewById(R.id.tableDanCetvrtak);
        tableDanPetak = findViewById(R.id.tableDanPetak);
        tableDanSubota = findViewById(R.id.tableDanSubota);
        tableDanNedelja = findViewById(R.id.tableDanNedelja);

        //TEMPERATURA
        tableTemperaturaPonedeljak = findViewById(R.id.tableTemperaturaPonedeljak);
        tableTemperaturaUtorak = findViewById(R.id.tableTemperaturaUtorak);
        tableTemperaturaSreda = findViewById(R.id.tableTemperaturaSreda);
        tableTemperaturaCetvrtak = findViewById(R.id.tableTemperaturaCetvrtak);
        tableTemperaturaPetak = findViewById(R.id.tableTemperaturaPetak);
        tableTemperaturaSubota = findViewById(R.id.tableTemperaturaSubota);
        tableTemperaturaNedelja = findViewById(R.id.tableTemperaturaNedelja);
        //PRITISAK
        tablePritisakPonedeljak = findViewById(R.id.tablePritisakPonedeljak);
        tablePritisakUtorak = findViewById(R.id.tablePritisakUtorak);
        tablePritisakSreda = findViewById(R.id.tablePritisakSreda);
        tablePritisakCetvrtak = findViewById(R.id.tablePritisakCetvrtak);
        tablePritisakPetak = findViewById(R.id.tablePritisakPetak);
        tablePritisakSubota = findViewById(R.id.tablePritisakSubota);
        tablePritisakNedelja = findViewById(R.id.tablePritisakNedelja);
        //VLAZNOST
        tableVlaznostPonedeljak = findViewById(R.id.tableVlaznstPonedeljak);
        tableVlaznostUtorak = findViewById(R.id.tableVlaznstUtorak);
        tableVlaznostSreda = findViewById(R.id.tableVlaznstSreda);
        tableVlaznostCetvrtak = findViewById(R.id.tableVlaznstCetvrtak);
        tableVlaznostPetak = findViewById(R.id.tableVlaznstPetak);
        tableVlaznostSubota = findViewById(R.id.tableVlaznstSubota);
        tableVlaznostNedelja = findViewById(R.id.tableVlaznstNedelja);

        minTemperatura = findViewById(R.id.minTemperatura);
        maxTemperatura = findViewById(R.id.maxTemperatura);

        //BUTTON
        snowflakeButtonStatistika = findViewById(R.id.snowflakeButtonStatistika);
        sunButtonStatistika = findViewById(R.id.sunButtonStatistika);

        snowflakeButtonStatistika.setOnClickListener(this);
        sunButtonStatistika.setOnClickListener(this);

        dbHelper = new DbHelper(this);

        prognoze = dbHelper.iscitajPrognze(strGrad);
        String minDan = prognoze[0].getmDan(), maxDan = prognoze[0].getmDan();
        Double min = prognoze[0].getmTemperatura(), max = prognoze[0].getmTemperatura();
        for (int i = 1; i < prognoze.length; i++) {
            if (min < prognoze[i].getmTemperatura()) {
                minDan = prognoze[i].getmDan();
                min = prognoze[i].getmTemperatura();
            }
            if (max > prognoze[i].getmTemperatura()) {
                maxDan = prognoze[i].getmDan();
                max = prognoze[i].getmTemperatura();
            }
            switch (prognoze[i].getmDan()) {
                case "Ponedeljak":
                    tableTemperaturaPonedeljak.setText(prognoze[i].getmTemperatura().toString());
                    tablePritisakPonedeljak.setText(prognoze[i].getmPritisak());
                    tableVlaznostPonedeljak.setText(prognoze[i].getmVlaznost());
                    break;
                case "Utorak":
                    tableTemperaturaUtorak.setText(prognoze[i].getmTemperatura().toString());
                    tablePritisakUtorak.setText(prognoze[i].getmPritisak());
                    tableVlaznostUtorak.setText(prognoze[i].getmVlaznost());
                    break;

                case "Sreda":
                    tableTemperaturaSreda.setText(prognoze[i].getmTemperatura().toString());
                    tablePritisakSreda.setText(prognoze[i].getmPritisak());
                    tableVlaznostSreda.setText(prognoze[i].getmVlaznost());
                    break;
                case "Cetvrtak":
                    tableTemperaturaCetvrtak.setText(prognoze[i].getmTemperatura().toString());
                    tablePritisakCetvrtak.setText(prognoze[i].getmPritisak());
                    tableVlaznostCetvrtak.setText(prognoze[i].getmVlaznost());
                    break;
                case "Petak":
                    tableTemperaturaPetak.setText(prognoze[i].getmTemperatura().toString());
                    tablePritisakPetak.setText(prognoze[i].getmPritisak());
                    tableVlaznostPetak.setText(prognoze[i].getmVlaznost());
                    break;
                case "Subota":
                    tableTemperaturaSubota.setText(prognoze[i].getmTemperatura().toString());
                    tablePritisakSubota.setText(prognoze[i].getmPritisak());
                    tableVlaznostSubota.setText(prognoze[i].getmVlaznost());
                    break;
                case "Nedelja":
                    tableTemperaturaNedelja.setText(prognoze[i].getmTemperatura().toString());
                    tablePritisakNedelja.setText(prognoze[i].getmPritisak());
                    tableVlaznostNedelja.setText(prognoze[i].getmVlaznost());
                    break;
            }
        }
        minTemperatura.setText(minTemperatura.getText() + "    " + minDan + "   " + min);
        maxTemperatura.setText(maxTemperatura.getText() + "    " + maxDan + "   " + max);

        switch (danUNedelji()) {
            case "Ponedeljak":
                tableDanPonedeljak.setTypeface(null, Typeface.BOLD);

                break;
            case "Utorak":
                tableDanUtorak.setTypeface(null, Typeface.BOLD);

                break;
            case "Sreda":
                tableDanSreda.setTypeface(null, Typeface.BOLD);

                break;
            case "Cetvrtak":
                tableDanCetvrtak.setTypeface(null, Typeface.BOLD);

                break;
            case "Petak":
                tableDanPetak.setTypeface(null, Typeface.BOLD);

                break;
            case "Subota":
                tableDanSubota.setTypeface(null, Typeface.BOLD);

                break;
            case "Nedelja":
                tableDanNedelja.setTypeface(null, Typeface.BOLD);

                break;
        }

    }


    public String danUNedelji() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:

                strDay = "Ponedeljak";

                break;

            case Calendar.TUESDAY:
                strDay = "Utorak";
                break;

            case Calendar.WEDNESDAY:
                strDay = "Sreda";
                break;

            case Calendar.THURSDAY:
                strDay = "Cetvrtak";
                break;

            case Calendar.FRIDAY:
                strDay = "Petak";
                break;

            case Calendar.SATURDAY:
                strDay = "Subota";
                break;

            case Calendar.SUNDAY:
                strDay = "Nedelja";
                break;

        }
        return strDay;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sunButtonStatistika) {
            for (int i = 0; i < prognoze.length; i++) {
                if (prognoze[i].getmTemperatura() <= 10) {
                    switch (prognoze[i].getmDan()) {
                        case "Ponedeljak":
                            tableTemperaturaPonedeljak.setVisibility(View.GONE);
                            tablePritisakPonedeljak.setVisibility(View.GONE);
                            tableVlaznostPonedeljak.setVisibility(View.GONE);
                            break;
                        case "Utorak":
                            tableTemperaturaUtorak.setVisibility(View.GONE);
                            tablePritisakUtorak.setVisibility(View.GONE);
                            tableVlaznostUtorak.setVisibility(View.GONE);
                            break;

                        case "Sreda":
                            tableTemperaturaSreda.setVisibility(View.GONE);
                            tablePritisakSreda.setVisibility(View.GONE);
                            tableVlaznostSreda.setVisibility(View.GONE);
                            break;
                        case "Cetvrtak":
                            tableTemperaturaCetvrtak.setVisibility(View.GONE);
                            tablePritisakCetvrtak.setVisibility(View.GONE);
                            tableVlaznostCetvrtak.setVisibility(View.GONE);
                            break;
                        case "Petak":
                            tableTemperaturaPetak.setVisibility(View.GONE);
                            tablePritisakPetak.setVisibility(View.GONE);
                            tableVlaznostPetak.setVisibility(View.GONE);
                            break;
                        case "Subota":
                            tableTemperaturaSubota.setVisibility(View.GONE);
                            tablePritisakSubota.setVisibility(View.GONE);
                            tableVlaznostSubota.setVisibility(View.GONE);
                            break;
                        case "Nedelja":
                            tableTemperaturaNedelja.setVisibility(View.GONE);
                            tablePritisakNedelja.setVisibility(View.GONE);
                            tableVlaznostNedelja.setVisibility(View.GONE);
                            break;
                    }
                } else {
                    switch (prognoze[i].getmDan()) {
                        case "Ponedeljak":
                            tableTemperaturaPonedeljak.setVisibility(View.VISIBLE);
                            tablePritisakPonedeljak.setVisibility(View.VISIBLE);
                            tableVlaznostPonedeljak.setVisibility(View.VISIBLE);
                            break;
                        case "Utorak":
                            tableTemperaturaUtorak.setVisibility(View.VISIBLE);
                            tablePritisakUtorak.setVisibility(View.VISIBLE);
                            tableVlaznostUtorak.setVisibility(View.VISIBLE);
                            break;

                        case "Sreda":
                            tableTemperaturaSreda.setVisibility(View.VISIBLE);
                            tablePritisakSreda.setVisibility(View.VISIBLE);
                            tableVlaznostSreda.setVisibility(View.VISIBLE);
                            break;
                        case "Cetvrtak":
                            tableTemperaturaCetvrtak.setVisibility(View.VISIBLE);
                            tablePritisakCetvrtak.setVisibility(View.VISIBLE);
                            tableVlaznostCetvrtak.setVisibility(View.VISIBLE);
                            break;
                        case "Petak":
                            tableTemperaturaPetak.setVisibility(View.VISIBLE);
                            tablePritisakPetak.setVisibility(View.VISIBLE);
                            tableVlaznostPetak.setVisibility(View.VISIBLE);
                            break;
                        case "Subota":
                            tableTemperaturaSubota.setVisibility(View.VISIBLE);
                            tablePritisakSubota.setVisibility(View.VISIBLE);
                            tableVlaznostSubota.setVisibility(View.VISIBLE);
                            break;
                        case "Nedelja":
                            tableTemperaturaNedelja.setVisibility(View.VISIBLE);
                            tablePritisakNedelja.setVisibility(View.VISIBLE);
                            tableVlaznostNedelja.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        }
        if (v.getId() == R.id.snowflakeButtonStatistika) {
            for (int i = 0; i < prognoze.length; i++) {
                if (prognoze[i].getmTemperatura() > 10) {
                    switch (prognoze[i].getmDan()) {
                        case "Ponedeljak":
                            tableTemperaturaPonedeljak.setVisibility(View.GONE);
                            tablePritisakPonedeljak.setVisibility(View.GONE);
                            tableVlaznostPonedeljak.setVisibility(View.GONE);
                            break;
                        case "Utorak":
                            tableTemperaturaUtorak.setVisibility(View.GONE);
                            tablePritisakUtorak.setVisibility(View.GONE);
                            tableVlaznostUtorak.setVisibility(View.GONE);
                            break;

                        case "Sreda":
                            tableTemperaturaSreda.setVisibility(View.GONE);
                            tablePritisakSreda.setVisibility(View.GONE);
                            tableVlaznostSreda.setVisibility(View.GONE);
                            break;
                        case "Cetvrtak":
                            tableTemperaturaCetvrtak.setVisibility(View.GONE);
                            tablePritisakCetvrtak.setVisibility(View.GONE);
                            tableVlaznostCetvrtak.setVisibility(View.GONE);
                            break;
                        case "Petak":
                            tableTemperaturaPetak.setVisibility(View.GONE);
                            tablePritisakPetak.setVisibility(View.GONE);
                            tableVlaznostPetak.setVisibility(View.GONE);
                            break;
                        case "Subota":
                            tableTemperaturaSubota.setVisibility(View.GONE);
                            tablePritisakSubota.setVisibility(View.GONE);
                            tableVlaznostSubota.setVisibility(View.GONE);
                            break;
                        case "Nedelja":
                            tableTemperaturaNedelja.setVisibility(View.GONE);
                            tablePritisakNedelja.setVisibility(View.GONE);
                            tableVlaznostNedelja.setVisibility(View.GONE);
                            break;
                    }
                } else {
                    switch (prognoze[i].getmDan()) {
                        case "Ponedeljak":
                            tableTemperaturaPonedeljak.setVisibility(View.VISIBLE);
                            tablePritisakPonedeljak.setVisibility(View.VISIBLE);
                            tableVlaznostPonedeljak.setVisibility(View.VISIBLE);
                            break;
                        case "Utorak":
                            tableTemperaturaUtorak.setVisibility(View.VISIBLE);
                            tablePritisakUtorak.setVisibility(View.VISIBLE);
                            tableVlaznostUtorak.setVisibility(View.VISIBLE);
                            break;

                        case "Sreda":
                            tableTemperaturaSreda.setVisibility(View.VISIBLE);
                            tablePritisakSreda.setVisibility(View.VISIBLE);
                            tableVlaznostSreda.setVisibility(View.VISIBLE);
                            break;
                        case "Cetvrtak":
                            tableTemperaturaCetvrtak.setVisibility(View.VISIBLE);
                            tablePritisakCetvrtak.setVisibility(View.VISIBLE);
                            tableVlaznostCetvrtak.setVisibility(View.VISIBLE);
                            break;
                        case "Petak":
                            tableTemperaturaPetak.setVisibility(View.VISIBLE);
                            tablePritisakPetak.setVisibility(View.VISIBLE);
                            tableVlaznostPetak.setVisibility(View.VISIBLE);
                            break;
                        case "Subota":
                            tableTemperaturaSubota.setVisibility(View.VISIBLE);
                            tablePritisakSubota.setVisibility(View.VISIBLE);
                            tableVlaznostSubota.setVisibility(View.VISIBLE);
                            break;
                        case "Nedelja":
                            tableTemperaturaNedelja.setVisibility(View.VISIBLE);
                            tablePritisakNedelja.setVisibility(View.VISIBLE);
                            tableVlaznostNedelja.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        }
    }
}

