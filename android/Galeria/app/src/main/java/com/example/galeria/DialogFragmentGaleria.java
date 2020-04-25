package com.example.galeria;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;


public class DialogFragmentGaleria extends DialogFragment {
View view;
TextView titulo;
RecyclerView recyclerViewGaleria;
ImageView imagen;
ArrayList<Menu> listaMenu;
RecyclerAdapter adapter;
    InputStream is= null;
    Bitmap decodedByte;
    byte[] decodedString;
    int ID;
    public DialogFragmentGaleria(int id){

        this.ID=id;

    }
    public DialogFragmentGaleria(){



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog_fragment_galeria, container, false);
titulo= (TextView)view.findViewById(R.id.Titulo);
imagen = (ImageView)view.findViewById(R.id.imagen);
recyclerViewGaleria =(RecyclerView)view.findViewById(R.id.galeria);
imagen.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
getDialog().dismiss();


    }
});
recyclerViewGaleria.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        listaMenu=new Menu(ID).ListMenu();
if(listaMenu.size()>0){
        adapter=new RecyclerAdapter(listaMenu, new RecyclerAdapter.OnclikRecycler() {
            @Override
    public void OnclickItemRecycler(Menu menu) {
                decodedString = Base64.decode(menu.getIdImagen(), Base64.DEFAULT);
                Glide.with(getContext()).load(decodedString).into(imagen);
                titulo.setText(menu.getTitulo());
    }
});

recyclerViewGaleria.setAdapter(adapter);}
        return view;
    }



}
