package com.example.projekat10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.v4.os.IResultReceiver;
import android.util.Log;



public class DbHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final String DATABASE_NAME="weather.db";
    public static final int DATABASE_VERSION=1;


    public static final String TABLE_NAME="prognoza";
    public static final String KOLONA_SLIKA="Slika";
    public static final String KOLONA_DATUM_VREME="DatumVreme";
    public static final String KOLONA_DAN="Dan";
    public static final String KOLONA_GRAD="grad";
    public static final String KOLONA_TEMPERATURA="Temperatura";
    public static final String KOLONA_PRITISAK="Pritisak";
    public static final String KOLONA_VLAZNOST="VlaznostVazduha";
    public static final String KOLONA_ZALAZAK="ZalazakSunca";
    public static final String KOLONA_IZLAZAK="IzlazakSunca";
    public static final String KOLONA_BRZINA="BrzinaVetra";
    public static final String KOLONA_SMER="Smer";


    public DbHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_TABLE ="CREATE TABLE " + TABLE_NAME + " ( " +
                KOLONA_GRAD + " TEXT," +
                KOLONA_SLIKA + " TEXT," +
                KOLONA_DATUM_VREME + " TEXT," +
                KOLONA_DAN + " TEXT," +
                KOLONA_TEMPERATURA + " DOUBLE," +
                KOLONA_PRITISAK + " TEXT," +
                KOLONA_VLAZNOST + " TEXT," +
                KOLONA_ZALAZAK + " TEXT," +
                KOLONA_IZLAZAK + " TEXT," +
                KOLONA_BRZINA + " TEXT," +
                KOLONA_SMER + " TEXT" + ");";
        db.execSQL(SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insert(Prognoza prognoza){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(KOLONA_SLIKA,prognoza.getmImage());
        values.put(KOLONA_DATUM_VREME,prognoza.getmDatum());
        values.put(KOLONA_DAN,prognoza.getmDan());
        values.put(KOLONA_GRAD,prognoza.getmGrad());
        values.put(KOLONA_TEMPERATURA,prognoza.getmTemperatura());
        values.put(KOLONA_PRITISAK,prognoza.getmPritisak());
        values.put(KOLONA_VLAZNOST,prognoza.getmVlaznost());
        values.put(KOLONA_ZALAZAK,prognoza.getmZalazak());
        values.put(KOLONA_IZLAZAK,prognoza.getmIzlazak());
        values.put(KOLONA_BRZINA,prognoza.getmBrzina());
        values.put(KOLONA_SMER,prognoza.getmPravac());

        db.insert(TABLE_NAME,null,values);
        db.close();
        return true;
    }

    public Prognoza[] iscitajPrognze(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null,null);
        if(cursor.getCount()<=0){
            return null;
        }

        Prognoza[] prognoze=new Prognoza[cursor.getCount()];
        int i =0;
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            prognoze[i++]=napraviPrognozu(cursor);
        }
        close();
        return prognoze;
    }

    public void obrisiPrognozu(String grad){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME,KOLONA_GRAD+"=? ",new String[]{grad});
        close();
    }
    public Prognoza[] iscitajPrognze(String grad) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, KOLONA_GRAD + "=?", new String[] {grad}, null, null, null, null);



        if(cursor.getCount() <= 0){
            return null;
        }
        Prognoza[] prognoze = new Prognoza[cursor.getCount()];
        int i=0;
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            prognoze[i++] = napraviPrognozu(cursor);
        }
        db.close();
        return prognoze;

    }
    public Prognoza iscitajPrognozu(String grad){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,KOLONA_GRAD + "=? ", new String[]{grad},null,null,null,null);
        //String selectQuery = "SELECT * FROM " + TABLE_NAME +" where " +
          //      KOLONA_GRAD + "=? ";
        //Cursor cursor=db.rawQuery(selectQuery,new String[]{grad});

        if(cursor.getCount()<=0){
            Log.d("123","radim");
            return null;
        }
        cursor.moveToLast();

        Prognoza prognoza=napraviPrognozu(cursor);
        db.close();
        return prognoza;
    }


    private Prognoza napraviPrognozu(Cursor cursor){
            String image = cursor.getString(cursor.getColumnIndex(KOLONA_SLIKA));
            String datum = cursor.getString(cursor.getColumnIndex(KOLONA_DATUM_VREME));
            String dan = cursor.getString(cursor.getColumnIndex(KOLONA_DAN));
            String grad = cursor.getString(cursor.getColumnIndex(KOLONA_GRAD));
            Double temperatura = cursor.getDouble(cursor.getColumnIndex(KOLONA_TEMPERATURA));
            String pritisak = cursor.getString(cursor.getColumnIndex(KOLONA_PRITISAK));
            String vlazmost = cursor.getString(cursor.getColumnIndex(KOLONA_VLAZNOST));
            String izlazak = cursor.getString(cursor.getColumnIndex(KOLONA_IZLAZAK));
            String zalazak = cursor.getString(cursor.getColumnIndex(KOLONA_ZALAZAK));
            String brzina = cursor.getString(cursor.getColumnIndex(KOLONA_BRZINA));
            String smer = cursor.getString(cursor.getColumnIndex(KOLONA_SMER));


            return new Prognoza(image,datum,dan,grad,temperatura,pritisak,vlazmost,izlazak,zalazak,brzina,smer);

    }

}
