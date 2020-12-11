package com.example.starsnow.controller.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.starsnow.controller.utility.MyViewHolder;
import com.example.starsnow.R;

import java.util.List;

import com.example.starsnow.model.SnowtamDecodeObject;

public class DecodeAdapter extends RecyclerView.Adapter<MyViewHolder>{


    List<SnowtamDecodeObject> list;

    //ajouter un constructeur prenant en entrée une liste
    public DecodeAdapter(List<SnowtamDecodeObject> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_oaci_decode_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    //c'est ici que nous allons remplir notre card avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        SnowtamDecodeObject myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
