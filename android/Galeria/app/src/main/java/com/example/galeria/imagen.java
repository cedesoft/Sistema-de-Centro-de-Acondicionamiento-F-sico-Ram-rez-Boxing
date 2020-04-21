package com.example.galeria;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class imagen {
    String imagen;
    public imagen(   ) {

        char[] nombre;
        nombre = new char[20000];
     this.imagen = new String(nombre);

    }
    public   String getIdImagen(){
        return imagen;

    }

}
