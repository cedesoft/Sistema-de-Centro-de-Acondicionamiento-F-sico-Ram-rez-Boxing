package com.example.galeria;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<Menu>ListaMenu;
    private OnclikRecycler listener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
Menu menu = ListaMenu.get(position);
holder.bind(menu,listener);
    }

    @Override
    public int getItemCount() {
        return ListaMenu.size();
    }

    public  interface OnclikRecycler{

    void OnclickItemRecycler(Menu menu);
}
public RecyclerAdapter(ArrayList<Menu>ListaMenu, OnclikRecycler listener){

    this.ListaMenu=ListaMenu;
    this.listener=listener;

}
public static class MyViewHolder extends RecyclerView.ViewHolder{
    byte[] decodedString;
ImageView imageView;

    public MyViewHolder(@NonNull View itemView) {

        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imagen);

    }
    public void bind(final Menu menu, final OnclikRecycler listener){
       decodedString = Base64.decode(menu.getIdImagen(), Base64.DEFAULT);
        Glide.with(imageView.getContext()).load(decodedString).into(imageView);
itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
listener.OnclickItemRecycler(menu);
    }
});
    }
}

}
