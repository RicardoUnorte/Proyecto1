package com.example.ricardo.proyecto1.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ricardo on 20/03/2015.
 */
public class Database extends SQLiteOpenHelper {
    private static final String BDname = "Contabilidad";
    private static final int Vesion = 1;
    public static final String TAG = Database.class.getSimpleName();
    private SQLiteDatabase db;
    private Database database;

    //Tabla rubros
    public static final String Taba_Rubros = "Rubros";
    public static final String IdRubro = "_id";
    public static final String Rubro ="Rubro";
    public static final String Tipo = "Tipo";
    public static final String Ciclo ="Ciclo";
    public static final String ValorEsp = "ValorEsperado";
    public static final String ValorActrual = "ValorActual";




    public Database(Context context) {
        super(context, BDname, null, Vesion);
        Log.d(TAG, "DatabaseHandler Constructor");
        db = this.getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_RUBRO = "CREATE TABLE " + Taba_Rubros + "("
                               + IdRubro + " integer primary key autoincrement," +Rubro + " text,"
                               + Tipo + " text," + Ciclo + " integer," + ValorEsp + " long,"
                               + ValorActrual + " long" + ")";

        db.execSQL(CREATE_RUBRO);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
