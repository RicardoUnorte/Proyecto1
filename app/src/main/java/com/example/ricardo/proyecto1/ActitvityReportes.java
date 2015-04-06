package com.example.ricardo.proyecto1;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import com.example.ricardo.proyecto1.Database.Database;
import com.example.ricardo.proyecto1.Database.SingletonDB;

import java.util.ArrayList;

/**
 * Created by Ricardo on 31/03/2015.
 */
public class ActitvityReportes extends Activity {
    private SingletonDB db;
    private ArrayList<String> parent = new ArrayList<String>();
    private ArrayList<Object> child = new ArrayList<Object>();
    private ArrayList<Object> childT = new ArrayList<Object>();
    private ArrayList<Object> TempG = new ArrayList<Object>();
    private ArrayList<Object> TempS = new ArrayList<Object>();
    private ArrayList<Object> TempE = new ArrayList<Object>();
    private ArrayList<Object> Excedio = new ArrayList<Object>();
    private ArrayList<Object> NCumplio = new ArrayList<Object>();
    int cic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        db = new SingletonDB(this);

        ExpandableListView ExpR = (ExpandableListView) findViewById(R.id.expandableListView2);
        ExpR.setGroupIndicator(null);

        //Lleno Vectores
        Bundle extra = getIntent().getExtras();
        cic = Integer.parseInt(extra.getString("Cic"));
        for (int i = 1; i <= cic; i++) {
            //c = db.ListarRubros2(String.valueOf(i));
            parent.add("Ciclo " +String.valueOf(i));
        }

        int Re =0;
        TempG = db.BalanceGeneral(cic);
        TempS = db.BalanceSalidas(cic);
        TempE = db.BalanceEntradas(cic);
        Excedio = db.RubrosExcedidos(cic);
        NCumplio = db.RubrosNoMeta(cic);
        for(int j = 0; j <= cic-1; j++ ){
            ArrayList<Object> childT = new ArrayList<Object>();
            childT.add(Re,"Balance General: " + TempG.get(j));
            Re++;
            childT.add(Re,"Balance Salida: " + TempS.get(j));
            Re++;
            childT.add(Re,"Balance Entrada: " + TempE.get(j));
            Re++;
            childT.add(Re, Excedio.get(j));
            Re++;
            childT.add(Re, NCumplio.get(j));
            child.add(j,childT);
            Re=0;
        }

        //Adaptador
        ExpandableAdapter Adapter = new ExpandableAdapter(parent,child);
        Adapter.setInfalter(this, (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE));

        //LE AÑADO EL ADAPTADOR AL EXPANDLE
        ExpR.setAdapter(Adapter);
        //Exp.setOnChildClickListener(this);

    }
}
