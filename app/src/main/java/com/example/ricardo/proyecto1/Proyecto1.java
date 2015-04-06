package com.example.ricardo.proyecto1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ricardo.proyecto1.Database.Database;
import com.example.ricardo.proyecto1.Database.SingletonDB;


public class Proyecto1 extends Activity {

   private SingletonDB db;
    private int ciclo = 1;



    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        db = new SingletonDB(this);
        final  Button bc = (Button) findViewById(R.id.button4);

        bc.setText(String.valueOf(ciclo));

        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ciclo++;
                db.CambioCiclo(ciclo);
                bc.setText(String.valueOf(ciclo));

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto1);
        db = new SingletonDB(this);
        final  Button bc = (Button) findViewById(R.id.button4);



        if(!db.IsElemets()) {
            db.InsertarRubro("Mensualidad","Entrada",500000,1);
            db.InsertarRubro("Buses", "Salida", 250000,1);
            db.InsertarRubro("Comida", "Salida", 100000,1);
            db.InsertarRubro("Varios","Salida",150000,1);
        }






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proyecto1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goRubros (View v){
        ActivityRubro r  = new ActivityRubro();
        Button b = (Button) findViewById(R.id.button4);
        Intent inten = new Intent(this,ActivityRubro.class);
        inten.putExtra("Cic",b.getText().toString());
        startActivity(inten);


    }

    public void goCiclos (View v){
        ActivityCiclos c = new ActivityCiclos();
        Button b = (Button) findViewById(R.id.button4);
        Intent inten2 = new Intent(this,ActivityCiclos.class);
        inten2.putExtra("Cic",b.getText().toString());
        startActivity(inten2);
    }

    public void goReportes (View v){
       ActitvityReportes Re = new ActitvityReportes();
        Button b = (Button) findViewById(R.id.button4);
        Intent inten3 = new Intent(this,ActitvityReportes.class);
        inten3.putExtra("Cic",b.getText().toString());
        startActivity(inten3);
    }

}
