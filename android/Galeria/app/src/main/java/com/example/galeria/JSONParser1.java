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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class JSONParser1 extends AsyncTask<Vector<String>,  Vector<String>,  Vector<String>> {
    URL url=null;
    int ID;
    HttpURLConnection httpURLConnection= null;

    Vector<String> result = new Vector<String>();
    public JSONParser1(int id){
        this.ID=id;

    }
    @Override
    protected  Vector<String> doInBackground( Vector<String>... strings) {


        try {
            url = new URL("http://192.168.1.68:8888/lista/titulo.php?ID="+ID+"");
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
                imagen1[] imagenes   = gson.fromJson(reader,
                        imagen1[].class);

                for (imagen1 imagenes1 : imagenes) {
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