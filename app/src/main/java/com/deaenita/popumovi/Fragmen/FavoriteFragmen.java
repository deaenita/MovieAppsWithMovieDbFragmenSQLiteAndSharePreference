package com.deaenita.popumovi.Fragmen;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deaenita.popumovi.Model.MovieModel;
import com.deaenita.popumovi.MovieAdapter;
import com.deaenita.popumovi.R;
import com.deaenita.popumovi.database.MovieContract;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

public class FavoriteFragmen extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public FavoriteFragmen() {
        // Required empty public constructor
    }


    RecyclerView recycler;
    ArrayList<MovieModel> listdata = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmenView = inflater.inflate(R.layout.fragment_favorite, container, false);
        recycler = fragmenView.findViewById(R.id.rv_film);


        //data dummy
//        for (int i = 0; i < 20; i++) {
//            MovieModel movie1 = new MovieModel("dea cantik","https://cdn.brilio.net/news/2016/10/17/101731/483692-film-chelsea-islan-.jpg");
//            listdata.add(movie1);
//        }

        //data real
        getActivity().getSupportLoaderManager().initLoader(100, null, this);

        //2 adapter
        MovieAdapter adapter = new MovieAdapter(listdata, getActivity());
        recycler.setAdapter(adapter);

        //3 layout manager
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return fragmenView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 100:
                return new CursorLoader(getActivity(), MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);

            default:
                throw new RuntimeException("Loader Not Working");
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.getCount() > 0){
            if(data.moveToFirst()){
                do {
                    MovieModel movie = new MovieModel();
                    movie.setId(data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)));
                    movie.setTitle(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_JUDUL)));
                    movie.setPosterPath(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)));
                    movie.setOverview(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
                    movie.setVoteAverage(data.getDouble(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING)));
                    movie.setReleaseDate(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATE)));
                    listdata.add(movie);
                    MovieAdapter adapter = new MovieAdapter(listdata, getActivity());
                    recycler.setAdapter(adapter);
                }while (data.moveToNext());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}

