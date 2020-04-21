package com.example.galeria;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class JSONParser extends AsyncTask< Vector<String>,  Vector<String>,  Vector<String>> {
    URL url=null;
   int ID;
    HttpURLConnection httpURLConnection= null;
    char[] nombre= new char[20000];

    String imagen2 = new String(nombre);
    public JSONParser(int id){
        this.ID=id;

    }
    @Override
    protected  Vector<String> doInBackground( Vector<String>... strings) {



        Vector<String> result = new Vector<String>();

        try {
            url = new URL("http://192.168.1.71:8888/lista/lista.php?ID="+ID);
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
                imagen[] imagenes   = gson.fromJson(reader,
                        imagen[].class);

                for (imagen imagenes1 : imagenes) {
                    Log.d("HOLA",imagenes1.getIdImagen());

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