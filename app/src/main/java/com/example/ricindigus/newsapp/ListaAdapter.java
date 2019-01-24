package com.example.ricindigus.newsapp;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListaAdapter extends ArrayAdapter<NewsItem> {
    public ListaAdapter(Context context, List<NewsItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       NewsItem currentItem = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_news,parent,false);
        TextView txtTitulo = convertView.findViewById(R.id.txtTitulo);
        TextView txtResumen = convertView.findViewById(R.id.txtResumen);
        TextView txtFecha= convertView.findViewById(R.id.txtFecha);
        ImageView imageView = convertView.findViewById(R.id.imagen);

        txtTitulo.setText(currentItem.getTitulo());
        txtResumen.setText(Html.fromHtml(currentItem.getResumen()));
        String fechaFormat = currentItem.getFecha().substring(0,10);
        txtFecha.setText(fechaFormat);

        String urlImage = currentItem.getImagen();

        Picasso.get().load(urlImage).into(imageView);

        return convertView;
    }
}
