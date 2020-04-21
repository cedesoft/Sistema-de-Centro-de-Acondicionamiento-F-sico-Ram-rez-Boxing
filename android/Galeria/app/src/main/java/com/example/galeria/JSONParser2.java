package com.example.galeria;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class JSONParser2 extends AsyncTask< Vector<Integer>,  Vector<Integer>,  Vector<Integer>> {
    URL url=null;
    int ID;
    HttpURLConnection httpURLConnection= null;
    char[] nombre= new char[20000];
    public JSONParser2(Integer[] id){
        this.ID=id[0];

    }
    String imagen2 = new String(nombre);
    @Override
    protected  Vector<Integer> doInBackground( Vector<Integer>... strings) {



        Vector<Integer> result = new Vector<Integer>();
        try {
            url = new URL("http://192.168.1.71:8888/lista/id.php?ID="+ID+"");
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(httpURLConnection.getInputStream()));

                Gson gson = new Gson();
                imagen2[] imagenes   = gson.fromJson(reader,
                        imagen2[].class);

                for (imagen2 imagenes1 : imagenes) {
                    Log.d("HOLA",""+imagenes1.getIdImagen());
                    result.add(imagenes1.getIdImagen());
                }
                reader.close();
            } else {
            }
        } catch (Exception e) {
            Log.e("Asteroides", e.getMessage(), e);
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();

        }
        return result;
    }
}