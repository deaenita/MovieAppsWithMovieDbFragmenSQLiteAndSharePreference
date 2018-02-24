package com.deaenita.popumovi;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.deaenita.popumovi.Model.TrailerModel;

import java.util.ArrayList;

/**
 * Created by USER on 15/12/2017.
 */

public class TrailerAdapter extends  RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    private ArrayList<TrailerModel> listData;
    private Context context;

    //construktor
    public TrailerAdapter(ArrayList<TrailerModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    //sambung dengan layout item list
    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.trailer_item_list, parent, false);
        return new TrailerAdapter.MyViewHolder(itemView);
    }

    //settext
    @Override
    public void onBindViewHolder(TrailerAdapter.MyViewHolder holder, final int position) {
        holder.vdTrailerFilm.setText(listData.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + listData.get(position).getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + listData.get(position).getKey()));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }
            }
        });

    }

    //hitung data
    @Override
    public int getItemCount() {
        return listData.size();
    }

    //sambung id
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button vdTrailerFilm;
        public MyViewHolder(View itemView) {
            super(itemView);
            vdTrailerFilm = itemView.findViewById(R.id.btn_youtube);

        }
    }
}
