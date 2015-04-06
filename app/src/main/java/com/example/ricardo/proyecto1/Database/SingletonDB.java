package com.example.ricardo.proyecto1.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.ricardo.proyecto1.Proyecto1;

import java.util.ArrayList;

/**
 * Created by Ricardo on 21/03/2015.
 */
public class SingletonDB {
    private SQLiteDatabase db;
    private static SingletonDB instance;
    private Database database;

    public SingletonDB(Context context) {
       database = new Database(context);
       db = database.getWritableDatabase();

    }

    public ContentValues Generar(String Rubro,String tipo,long valoresp,int ciclo){
        ContentValues values = new ContentValues();
        values.put(Database.Rubro,Rubro);
        values.put(Database.Tipo,tipo);
        values.put(Database.ValorEsp,valoresp);
        values.put(Database.Ciclo,ciclo);
        values.put(Database.ValorActrual,valoresp);

        return values;

    }

     public void InsertarRubro(String Rubro,String tipo,long valoresp,int ciclo ){

            db =database.getWritableDatabase();
            db.insert(Database.Taba_Rubros,null,Generar(Rubro,tipo,valoresp,ciclo));
            //db.close();
        }
    public ContentValues Generar2(long valoract){
        ContentValues values = new ContentValues();
        values.put(Database.ValorActrual,valoract);
        return values;
    }

    public ContentValues Generar3(String Rubro,String tipo,long valoresp,long valorac,int ciclo) {
        ContentValues values = new ContentValues();
        values.put(Database.Rubro, Rubro);
        values.put(Database.Tipo, tipo);
        values.put(Database.ValorEsp, valoresp);
        values.put(Database.Ciclo, ciclo);
        values.put(Database.ValorActrual, valorac);

        return values;
    }

        public void ModRubros (long valoresp,long valact,long valSumar,String Rubro,String ciclo){

            long S;
            if(valoresp==valact){
                 S = valoresp + valSumar;
            }
            else{
                 S = valact + valSumar;
            }
            db.update(Database.Taba_Rubros,Generar2(S),Database.Rubro + "=? AND "+ Database.Ciclo + "=?",new String[]{String.valueOf(Rubro),ciclo});
        }

        public Cursor ListarRubros(){

            db = database.getWritableDatabase();
            String[] Columnas = new String[] {Database.IdRubro,Database.Rubro,Database.Tipo,Database.ValorEsp,Database.ValorActrual};
           return db.query(Database.Taba_Rubros,Columnas,null,null,null,null,null);

        }

    public Cursor ListarRubros2(String Ciclo){

        db = database.getWritableDatabase();
        String[] Columnas = new String[] {Database.IdRubro,Database.Rubro,Database.Tipo,Database.ValorEsp,Database.ValorActrual};
        return db.query(Database.Taba_Rubros,Columnas,Database.Ciclo+"=?",new String[]{Ciclo},null,null,null);

    }

        public boolean  IsElemets (){
            SQLiteDatabase db = database.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM "+Database.Taba_Rubros, null);
            while (c.moveToNext()) {
                c.close();
                db.close();
                return true;
            }
           // c.close();
            db.close();
            return false;
        }

    public long Buscar_Rubro(String Rubro) {

        SQLiteDatabase DB = database.getReadableDatabase();


        String Query = "SELECT " + Database.ValorActrual + " FROM " + Database.Taba_Rubros + " WHERE " + Database.Rubro + " LIKE '" + Rubro + "'";
        Cursor c = DB.rawQuery(Query, null);

        Long ac = null;
        if (c != null && c.moveToFirst()) {
            ac = c.getLong(0);

        }
        return ac;
    }

    public void ELiminar_Rubro(String Rubro,String ciclo){

        ContentValues values = new ContentValues();
        values.put(Database.ValorActrual,0);
        values.put(Database.ValorEsp,0);

        db.update(Database.Taba_Rubros,values,Database.Rubro + "=? AND "+ Database.Ciclo + "=?",new String[]{String.valueOf(Rubro),ciclo});

    }

    public void CambioCiclo(int ciclo){

        if(ciclo>1) {
            int tempc = ciclo -1;
            db = database.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT " + Database.Rubro + "," + Database.Tipo + "," + Database.ValorEsp + "," + Database.ValorActrual + " FROM " + Database.Taba_Rubros + " WHERE " +Database.Ciclo +"="+ tempc, null);
            if (c != null && c.moveToFirst()) {
                do {

                    String R = c.getString(0);
                    String T = c.getString(1);
                    String E = c.getString(2);
                    String A = c.getString(3);
                    db.insert(Database.Taba_Rubros, null, Generar3(R, T, Long.parseLong(E), Long.parseLong(A), ciclo));

                } while (c.moveToNext());


            }
        }
        else {
                db = database.getWritableDatabase();
                Cursor c = db.rawQuery("SELECT " + Database.Rubro + "," + Database.Tipo + "," + Database.ValorEsp + "," + Database.ValorActrual + " FROM " + Database.Taba_Rubros, null);
                if (c != null && c.moveToFirst()) {
                do {

                    String R = c.getString(0);
                    String T = c.getString(1);
                    String E = c.getString(2);
                    String A = c.getString(3);
                    db.insert(Database.Taba_Rubros, null, Generar3(R, T, Long.parseLong(E), Long.parseLong(A), ciclo));

                } while (c.moveToNext());
            }

        }

    }

    public ArrayList<Object> Child(int ciclo){
         ArrayList<Object> child = new ArrayList<Object>();
        for (int j=1;j<=ciclo; j++){
            db = database.getWritableDatabase();
            ArrayList<String> ch = new ArrayList<String>();
            Cursor cR = db.rawQuery("SELECT " + Database.Rubro + "," + Database.ValorEsp + "," + Database.ValorActrual + " FROM " + Database.Taba_Rubros + " WHERE " + Database.Ciclo + "=" + j, null);
            if (cR != null && cR.moveToFirst()) {
                do {
                    ch.add(cR.getString(0)+" " + cR.getString(1)+" " + cR.getString(2));
                } while (cR.moveToNext());
            }
            child.add(ch);
        }
        return child;

    }

    public ArrayList<Object> BalanceGeneral(int ciclo){
        ArrayList<Object> child = new ArrayList<Object>();
        Long EntradaB=0L,SalidaB=0L;
        //Balance General
        for (int i=1;i<=ciclo;i++){
            db = database.getWritableDatabase();
            ArrayList<String> chE = new ArrayList<String>();
            ArrayList<String> chS = new ArrayList<String>();
            Cursor CgE = db.rawQuery("SELECT " + Database.ValorEsp + ","+ Database.ValorActrual + " FROM " + Database.Taba_Rubros + " WHERE " + Database.Ciclo + "=" + i+ " AND " + Database.Tipo + " LIKE 'Entrada'",null);
            Cursor CgS = db.rawQuery("SELECT " + Database.ValorEsp + ","+ Database.ValorActrual + " FROM " + Database.Taba_Rubros + " WHERE " + Database.Ciclo + "=" + i+ " AND " + Database.Tipo + " LIKE 'Salida'",null);
            if(CgE !=null && CgE.moveToFirst()){
                do{
                    if(Long.parseLong(CgE.getString(0).toString()) == Long.parseLong(CgE.getString(1).toString()) ){
                        EntradaB = Long.parseLong(CgE.getString(0).toString());
                    }
                    else{
                        EntradaB = EntradaB +  (Long.parseLong(CgE.getString(1).toString()) - Long.parseLong(CgE.getString(0).toString()));
                    }

                }while(CgE.moveToNext());
            }

            if(CgS !=null && CgS.moveToFirst()){
                do{
                    if(Long.parseLong(CgS.getString(0).toString()) <= Long.parseLong(CgS.getString(1).toString()) ){
                        SalidaB = SalidaB +  (Long.parseLong(CgS.getString(1).toString()) - Long.parseLong(CgS.getString(0).toString()));

                    }
                    else{
                        if(Long.parseLong(CgS.getString(0).toString()) >= Long.parseLong(CgS.getString(1).toString())){
                            SalidaB = SalidaB +  Long.parseLong(CgS.getString(1).toString());
                        }

                    }
                }while(CgS.moveToNext());
            }
            child.add(String.valueOf(EntradaB-SalidaB));
            EntradaB=0L;
            SalidaB=0L;
        }


        return child;
    }

    public ArrayList<Object> BalanceSalidas (int ciclo){
        ArrayList<Object> child = new ArrayList<Object>();
        Long SalidasE=0L,SalidaR=0L;
        //Balance Salida
        for (int i=1;i<=ciclo;i++){
            db = database.getWritableDatabase();
            ArrayList<String> chE = new ArrayList<String>();
            ArrayList<String> chS = new ArrayList<String>();
            Cursor Cbs = db.rawQuery("SELECT " + Database.ValorEsp +  " FROM " + Database.Taba_Rubros + " WHERE " + Database.Ciclo + "=" + i+ " AND " + Database.Tipo + " LIKE 'Salida'",null);
            Cursor Cbr = db.rawQuery("SELECT " + Database.ValorEsp + ","+ Database.ValorActrual + " FROM " + Database.Taba_Rubros + " WHERE " + Database.Ciclo + "=" + i+ " AND " + Database.Tipo + " LIKE 'Salida'",null);
            if(Cbs!=null && Cbs.moveToFirst()){
                do{
                        SalidasE = SalidasE + Long.parseLong(Cbs.getString(0).toString());
                }while(Cbs.moveToNext());
            }

            if(Cbr !=null && Cbr.moveToFirst()){
                do{
                    if(Long.parseLong(Cbr.getString(0).toString()) <= Long.parseLong(Cbr.getString(1).toString()) ){
                        SalidaR = SalidaR +  (Long.parseLong(Cbr.getString(1).toString()) - Long.parseLong(Cbr.getString(0).toString()));

                    }
                    else{
                        if(Long.parseLong(Cbr.getString(0).toString()) >= Long.parseLong(Cbr.getString(1).toString()) ){
                            SalidaR = SalidaR + Long.parseLong(Cbr.getString(1).toString());
                        }

                    }
                }while(Cbr.moveToNext());
            }
            child.add(String.valueOf(SalidasE-SalidaR));
            SalidasE=0L;
            SalidaR=0L;
        }


      return child;
    }

    public ArrayList<Object> BalanceEntradas (int ciclo){
        ArrayList<Object> child = new ArrayList<Object>();
        Long EntradasE=0L,EntradasR=0L;
        for (int i=1;i<=ciclo;i++) {
            db = database.getWritableDatabase();
            ArrayList<String> chE = new ArrayList<String>();
            ArrayList<String> chS = new ArrayList<String>();
            Cursor CGE = db.rawQuery("SELECT " + Database.ValorEsp  + " FROM " + Database.Taba_Rubros + " WHERE " + Database.Ciclo + "=" + i+ " AND " + Database.Tipo + " LIKE 'Entrada'",null);
            Cursor CGe = db.rawQuery("SELECT " + Database.ValorEsp + ","+ Database.ValorActrual + " FROM " + Database.Taba_Rubros + " WHERE " + Database.Ciclo + "=" + i+ " AND " + Database.Tipo + " LIKE 'Entrada'",null);
            if(CGE !=null && CGE.moveToFirst()){
                do {
                    EntradasE = EntradasE + Long.parseLong(CGE.getString(0).toString());
                }while(CGE.moveToNext());
            }

            if(CGe !=null && CGe.moveToFirst()){
                do{
                    if(Long.parseLong(CGe.getString(0).toString()) < Long.parseLong(CGe.getString(1).toString()) ){
                        EntradasR = EntradasR +  (Long.parseLong(CGe.getString(1).toString()) - Long.parseLong(CGe.getString(0).toString()));

                    }
                    else {
                        if (Long.parseLong(CGe.getString(0).toString()) > Long.parseLong(CGe.getString(1).toString())) {
                            EntradasR = EntradasR + Long.parseLong(CGe.getString(1).toString());
                        }
                    }
                        if(Long.parseLong(CGe.getString(0).toString()) == Long.parseLong(CGe.getString(1).toString())){

                            EntradasR = EntradasR + Long.parseLong(CGe.getString(1).toString());
                    }
                }while(CGe.moveToNext());
            }
            child.add(String.valueOf(EntradasE-EntradasR));
            EntradasE=0L;
            EntradasR=0L;
        }
        return child;
    }

    public ArrayList<Object> RubrosExcedidos(int ciclo){
        ArrayList<Object> child = new ArrayList<Object>();
        String ex="";
        Long a=0L,b=0L,percent=0L,c=0L;
        for(int i=1;i<=ciclo;i++){
            db = database.getWritableDatabase();
            ArrayList<String> CHe = new ArrayList<String>();
            Cursor Cex = db.rawQuery("SELECT " + Database.Rubro+ "," + Database.ValorEsp + ","+ Database.ValorActrual + " FROM " + Database.Taba_Rubros + " WHERE " + Database.Ciclo + "=" + i+ " AND " + Database.ValorEsp  +"<"+ Database.ValorActrual,null);
            if(Cex != null && Cex.moveToFirst()){
                do {
                   a = Long.parseLong(Cex.getString(2)) + Long.parseLong(Cex.getString(1));
                   b = a -  Long.parseLong(Cex.getString(1));
                   c = b - Long.parseLong(Cex.getString(1));
                   percent = (c*100)/ Long.parseLong(Cex.getString(1));
                   ex = ex +"\nEl Rubro " +Cex.getString(0) + " se excedio en "+ c + " (" + percent + "%)";
                }while(Cex.moveToNext());
            }
            child.add(ex);
            a=0L;
            b=0L;
            c=0L;
            ex="";
        }


        return child;
    }

    public ArrayList<Object> RubrosNoMeta(int ciclo){
        ArrayList<Object> child = new ArrayList<Object>();
        Long pec = 50L,a=0L;
        String ex="";
        for(int i=1;i<=ciclo;i++){
            db = database.getWritableDatabase();
            Cursor Cnm = db.rawQuery("SELECT " + Database.Rubro+ "," + Database.ValorEsp + ","+ Database.ValorActrual + " FROM " + Database.Taba_Rubros + " WHERE " + Database.Ciclo + "=" + i+ " AND " + Database.ValorEsp  +">"+ Database.ValorActrual,null);
            if(Cnm !=null && Cnm.moveToFirst()){
                do{
                    a = (100 * Long.parseLong(Cnm.getString(2)))/Long.parseLong(Cnm.getString(1));
                    if(a < pec){
                        ex = ex + "\nEl " + Cnm.getString(0) + " no alcanzó su meta en "+ Cnm.getString(1)+"("+pec+"%)";

                    }
                }while(Cnm.moveToNext());
                child.add(ex);
                a=0L;
                ex="";
            }
        }
        return child;
    }








    }

