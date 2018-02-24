package com.deaenita.popumovi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deaenita.popumovi.Model.DetailActivity;
import com.deaenita.popumovi.Model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by USER on 09/12/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private ArrayList<MovieModel> listData;
    private Context context;

    //construktor

    public MovieAdapter(ArrayList<MovieModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    //sambung dengan layout item list
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.movie_item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    //settext
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvJudulFilm.setText(listData.get(position).getTitle());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+listData.get(position).getPosterPath()).into(holder.ivGambarFilm);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(context, DetailActivity.class);
                pindah.putParcelableArrayListExtra("DATA_FILM", listData);
                pindah.putExtra("DATA_POSISI", position);
                context.startActivity(pindah);
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
       ImageView ivGambarFilm;
       TextView tvJudulFilm;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivGambarFilm = itemView.findViewById(R.id.gambarMovie);
            tvJudulFilm = itemView.findViewById(R.id.judulMovie);
        }
    }
}
