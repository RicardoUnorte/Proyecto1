package com.example.ricardo.proyecto1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardo.proyecto1.Database.Database;
import com.example.ricardo.proyecto1.Database.SingletonDB;

import org.w3c.dom.Text;

/**
 * Created by Ricardo on 20/03/2015.
 */
public class ActivityRubro extends ActionBarActivity {

    private SingletonDB db;
    private Database D;
    private Cursor c;
    private ListView List, List2;
    private GridView Grid;
    private boolean sw=false,swD=false;
    Bundle extra;
    int cic;

    String[] from = new String[]{Database.Rubro,Database.Tipo,Database.ValorEsp,Database.ValorActrual};
    int [] to = new int[] {R.id.Rubro,R.id.Tipo,R.id.Valor,R.id.ValorA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubros);


        db = new SingletonDB(this);

        Bundle ex = getIntent().getExtras();
        int Ciclo = Integer.parseInt(ex.getString("Cic"));

        if(Ciclo>1)
        {
            c = db.ListarRubros2(String.valueOf(Ciclo));
            RefreshRubros(c,from,to);

        }
        else{
            c = db.ListarRubros();
             RefreshRubros(c,from,to);
        }


        //c.close();

    }

    public void RefreshRubros(Cursor c,String[] from,int[] to)
    {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.item_layout,c,from,to);
        List = (ListView) findViewById(R.id.listView);
        List.setAdapter(adapter);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rubro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        List.setClickable(false);

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.Ins:
            //Agregar nuevo Rubro

                   extra = getIntent().getExtras();
                    if(extra != null){
                        cic = Integer.parseInt(extra.getString("Cic"));
                    }
                     final     AlertDialog.Builder build = new AlertDialog.Builder(this);
                     final    LayoutInflater infalter = this.getLayoutInflater();
                     final    LayoutInflater inf = this.getLayoutInflater();

                     final View dialogV = infalter.inflate(R.layout.frag_rubros, null);

                     build.setView(dialogV);
                     build.setTitle("Agregar Rubro");


                       build.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText r = (EditText) dialogV.findViewById(R.id.RubroT);
                                EditText e = (EditText) dialogV.findViewById(R.id.EstadoT);
                                EditText v = (EditText) dialogV.findViewById(R.id.ValorT);
                                db.InsertarRubro(r.getText().toString(),e.getText().toString(),Long.parseLong(v.getText().toString()),cic);
                                Cursor cT= db.ListarRubros2(String.valueOf(cic));
                                RefreshRubros(cT,from,to);
                            }
                        });
                      build.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int id) {
                          }
                      });
                build.create();
                build.show();
                break;


            case R.id.Mod:
            //Modificar Rubro
                //List.setEnabled(true);
                extra = getIntent().getExtras();
                if(extra != null){
                    cic = Integer.parseInt(extra.getString("Cic"));
                }
                List.setClickable(true);
                Context context = getApplicationContext();
                CharSequence text = "Seleccione el Rubro";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


                 List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(final AdapterView<?> parent,final View view, int position, long id) {

                               final AlertDialog.Builder build = new AlertDialog.Builder(view.getContext());
                               final LayoutInflater infalter = LayoutInflater.from(view.getContext());
                               final View dialogV = infalter.inflate(R.layout.frag_modificar, null);
                               build.setView(dialogV);
                               build.setTitle("Modificar Rubro");
                               final Button bc = (Button) findViewById(R.id.button4);


                               build.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int id) {

                                       TextView selectR = (TextView) view.findViewById(R.id.Rubro);
                                       TextView selectV = (TextView) view.findViewById(R.id.Valor);
                                       Long ac = db.Buscar_Rubro(selectR.getText().toString());
                                       EditText r = (EditText) dialogV.findViewById(R.id.Mod);
                                       Long Sum = Long.parseLong(r.getText().toString());

                                       db.ModRubros(Long.parseLong(selectV.getText().toString()), ac, Sum, selectR.getText().toString(),String.valueOf(cic));
                                       Cursor cT = db.ListarRubros2(String.valueOf(cic));
                                       while (cT.moveToNext()){
                                           String lel = cT.getString(0);
                                           String lal =cT.getString(1);
                                           String lol = cT.getString(2);
                                       }
                                       RefreshRubros(cT, from, to);
                                        List.setClickable(false);

                                   }
                               });


                               build.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                   }
                               });
                               build.create();
                               build.show();


                       }
                   });

                break;

            case R.id.Del:
                //List.setEnabled(true);
                List.setClickable(true);
                extra = getIntent().getExtras();
                if(extra != null){
                    cic = Integer.parseInt(extra.getString("Cic"));
                }
                swD = true;
                context = getApplicationContext();
                CharSequence Text = "Seleccione el Rubro a Borrar";
                int duration1 = Toast.LENGTH_SHORT;
                Toast ToastD = Toast.makeText(context, Text, duration1);
                ToastD.show();

                    List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override

                        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                            final AlertDialog.Builder build2 = new AlertDialog.Builder(view.getContext());
                            build2.setTitle("Borrar Rubros");
                            build2.setMessage("Desea borrar este rubro?");

                            build2.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    TextView selectR = (TextView) view.findViewById(R.id.Rubro);
                                    db.ELiminar_Rubro(selectR.getText().toString(),String.valueOf(cic));
                                    Cursor cT = db.ListarRubros2(String.valueOf(cic));
                                    RefreshRubros(cT, from, to);
                                    //List.setEnabled(false);
                                    List.setClickable(false);
                                }
                            });


                            build2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            build2.create();
                            build2.show();
                        }

                    });
                    break;
        }

        return super.onOptionsItemSelected(item);
    }
}
