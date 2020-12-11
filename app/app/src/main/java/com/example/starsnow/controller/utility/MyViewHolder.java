package com.example.starsnow.controller.utility;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.starsnow.R;
import com.example.starsnow.model.SnowtamDecodeObject;

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 card
    public MyViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        textViewView = (TextView) itemView.findViewById(R.id.textView_item);
        imageView = (ImageView) itemView.findViewById(R.id.imageView_item);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction du décodage
    public void bind(SnowtamDecodeObject myObject){
        textViewView.setText(myObject.getText());
        imageView.setImageDrawable(myObject.getDrawable());
    }
}