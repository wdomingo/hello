package com.yelc.proyecto2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.yelc.proyecto2.adapters.A_Country;
import com.yelc.proyecto2.models.Countrie;

import java.util.ArrayList;

/**
 * Created by yelc-lopez on 3/31/18.
 */

public class Countries {

    SQLiteDatabase db;
    Context context;
    ListView listaPaises;
    ArrayList<Countrie> paises;
    A_Country paisAdapter;

    public Countries(Context context, ListView listaPaises){
        this.context = context;
        this.listaPaises = listaPaises;
    }

    public boolean getCountries(String selection, String[] selectionArgs){


        db = SQLiteDatabase.openDatabase("/data/data/" +
                context.getPackageName() +
                "/databases/paises.db",null, SQLiteDatabase.OPEN_READWRITE);

        paises = new ArrayList<>();

        String[] projection = {
                "code",
                "name",
                "native",
                "capital"
        };

        Cursor cursor = db.query("countries",
                projection,selection,selectionArgs,null,null,null );


        if (cursor.moveToFirst()){
            do {
                paises.add(new Countrie(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }else{
            return false;
        }

        cursor.close();

        paisAdapter = new A_Country(context,paises);

        listaPaises.setAdapter(paisAdapter);

        db.close();

        return true;
    }


    public boolean getfilterCountry(String pais){

        db = SQLiteDatabase.openDatabase("/data/data/" +
                context.getPackageName() +
                "/databases/paises.db",null, SQLiteDatabase.OPEN_READWRITE);

        paises = new ArrayList<>();

        String sql = String.format("select countries.code, countries.name, countries.native, countries.capital " +
                "from countries where name like '%s' or native like '%s'", pais + "%", pais + "%");


        Cursor cursor = db.rawQuery(sql, null);


        if (cursor.moveToFirst()) {
            do {
                paises.add(new Countrie(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }


        cursor.close();

        paisAdapter = new A_Country(context,paises);


        listaPaises.setAdapter(paisAdapter);

        db.close();

        return true;
    }
}
