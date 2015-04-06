package com.example.ricardo.proyecto1;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.example.ricardo.proyecto1.Database.Database;
import com.example.ricardo.proyecto1.Database.SingletonDB;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Ricardo on 26/03/2015.
 */
public class ActivityCiclos extends Activity {

    private SingletonDB db;
    private SQLiteDatabase D;
    private Database database;
    private Cursor c,cc;
    private ArrayList<String> parent = new ArrayList<String>();
    private ArrayList<Object> child = new ArrayList<Object>();
    int cic;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo);
        db = new SingletonDB(this);

        ExpandableListView Exp = (ExpandableListView) findViewById(R.id.expandableListView);
        Exp.setGroupIndicator(null);

        //Lleno Vectores
        Bundle extra = getIntent().getExtras();
        cic = Integer.parseInt(extra.getString("Cic"));
        for (int i = 1; i <= cic; i++) {
            //c = db.ListarRubros2(String.valueOf(i));
            parent.add("Ciclo " +String.valueOf(i));
        }
        child=db.Child(cic);

        //Adaptador
        ExpandableAdapter adapter = new ExpandableAdapter(parent,child);
        adapter.setInfalter(this, (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE));

        //LE AÑADO EL ADAPTADOR AL EXPANDLE
        Exp.setAdapter(adapter);
        //Exp.setOnChildClickListener(this);






        }
    }


