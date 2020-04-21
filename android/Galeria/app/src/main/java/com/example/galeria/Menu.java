package com.example.galeria;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class Menu {
    private Context context;

    imagen[] imagenes= null;

    URL url1=null;

    HttpURLConnection httpURLConnection1= null;
    Vector<String> result1 = new Vector<String>();
    Vector<String> result = new Vector<String>();




    private static final String TAG_Persona = "nombre";
    private static final String TAG_Persona1 = "titulo";
int ID=0;
   private String idImagen;
    private String titulo;
    public Menu(int id){
        ID=id;
        char[] nombre;
        nombre = new char[20000];
       idImagen= new String(nombre);

        titulo="";
    }




    public Menu( String idImagen, String titulo) {

        this.idImagen = idImagen;
    this.titulo=titulo;
    }
    public String getIdImagen(){
        return idImagen;

    }
    public  String getTitulo(){

        return titulo;
    }
    public  ArrayList<Menu> ListMenu() {



        Menu menu;
        ArrayList<Menu> lista = new ArrayList<Menu>();
        JSONParser obj = new JSONParser(ID);





        JSONParser1 obj1 = new JSONParser1(ID);
        result=obj.doInBackground();
        result1=obj1.doInBackground();

if(result1.size()>0){
        for (int i1 = 0; i1 < result1.size(); i1++) {




                menu = new Menu(result.get(i1),result1.get(i1));

                lista.add(menu);

        }}
        return lista;
    }

}
