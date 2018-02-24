package com.deaenita.popumovi.Model;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deaenita.popumovi.R;
import com.deaenita.popumovi.TrailerAdapter;
import com.deaenita.popumovi.connection.ApiService;
import com.deaenita.popumovi.connection.RetrofitConfig;
import com.deaenita.popumovi.database.MovieContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ArrayList<MovieModel> listData = new ArrayList<>();
    Integer posisi;
    SharedPreferences pref;
    Boolean isFavorit = false;
    FloatingActionButton fab;
    RecyclerView recycler;
    TextView textView;
    ArrayList<TrailerModel> listdatatrailer = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setting preference
        pref = PreferenceManager.getDefaultSharedPreferences(DetailActivity.this);


        //terima data
        listData = getIntent().getParcelableArrayListExtra("DATA_FILM");
        posisi = getIntent().getIntExtra("DATA_POSISI", 0);

        //set data
        getSupportActionBar().setTitle(listData.get(posisi).getTitle());

        TextView tvSinopsis = (TextView) findViewById (R.id.tv_overview);
        tvSinopsis.setText(listData.get(posisi).getOverview());

        TextView tvDate = (TextView) findViewById (R.id.tv_date);
        tvDate.setText(listData.get(posisi).getReleaseDate());

        TextView tvRating = (TextView) findViewById(R.id.tv_rating);
        tvRating.setText(Double.toString(listData.get(posisi).getVoteAverage()));

        ImageView iv_backdrop = (ImageView) findViewById(R.id.iv_detail_gambar);
        Picasso.with(DetailActivity.this).load("https://image.tmdb.org/t/p/w500"+listData.get(posisi).getPosterPath()).into(iv_backdrop);

        //*
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorit){
                    //kita rubah preference dari true jadi false
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("FAVORIT"+listData.get(posisi).getId(), false);
                    isFavorit = false;
                    editor.commit();
                    Snackbar.make(view, "Favorit dihapus", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    hapusDatabase();

                }else{
                    //kita rubah preferebce false jadi true
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("FAVORIT"+listData.get(posisi).getId(), true);
                    isFavorit = true;
                    editor.commit();
                    Snackbar.make(view, "Favorit ditambah", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    simpanDatabase();
                }
                updateFAB();
            }




        });
        isFavorit = pref.getBoolean("FAVORIT"+listData.get(posisi).getTitle(), false);

        //harus di bawah fab

        recycler = (RecyclerView) findViewById(R.id.rv_youtube);
        updateFAB();

        //data dummy
//        for (int i = 0; i < 20; i++) {
//            MovieModel movie1 = new MovieModel("dea cantik","https://cdn.brilio.net/news/2016/10/17/101731/483692-film-chelsea-islan-.jpg");
//            listdata.add(movie1);
//        }

        //data real
        recycler.setLayoutManager(new GridLayoutManager(DetailActivity.this, 2));
        ambilTrailer();

        //2 adapter
        ///TrailerAdapter adapter = new TrailerAdapter(listdatatrailer, DetailActivity.this);
        //recycler.setAdapter(adapter);

        //3 layout manager


        //kalau favorit lovenya full
        // kalau endak favorit nanti tanpa cinta
    }
    private void ambilTrailer() {
        final ProgressDialog progress = new ProgressDialog(DetailActivity.this);
        progress.setTitle("Loading");
        progress.setMessage("Mohon Bersabar");
        progress.show();

        ApiService api = RetrofitConfig.getApiService();
        Call<ListTrailerModel> call = api.ambilTrailer();

        call.enqueue(new Callback<ListTrailerModel>() {
            @Override
            public void onResponse(Call<ListTrailerModel> call, Response<ListTrailerModel> response) {
                progress.dismiss();
                if(response.isSuccessful()){
                    listdatatrailer = response.body().getResults();
                    TrailerAdapter adapter = new TrailerAdapter(listdatatrailer, DetailActivity.this);
                    recycler.setAdapter(adapter);
                }else{
                    Toast.makeText(DetailActivity.this, "response is not succesfull", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ListTrailerModel> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Response Failure", Toast.LENGTH_SHORT).show();

            }
        });
    }
        private void hapusDatabase() {
            getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI
                            .buildUpon()
                            .appendPath(String.valueOf(listData.get(posisi).getId()))
                            .build(), null, null);
        }

        private void simpanDatabase() {
            ContentValues cv = new ContentValues();
            cv.put(MovieContract.MovieEntry.COLUMN_ID, listData.get(posisi).getId());
            cv.put(MovieContract.MovieEntry.COLUMN_JUDUL, listData.get(posisi).getTitle());
            cv.put(MovieContract.MovieEntry.COLUMN_POSTER, listData.get(posisi).getPosterPath());
            cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, listData.get(posisi).getOverview());
            cv.put(MovieContract.MovieEntry.COLUMN_DATE, listData.get(posisi).getReleaseDate());
            cv.put(MovieContract.MovieEntry.COLUMN_RATING, listData.get(posisi).getVoteAverage());


            Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, cv);
            if (ContentUris.parseId(uri) > 0){
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
            }
        }
        private void updateFAB() {
            if (isFavorit){
                fab.setImageResource(R.drawable.ic_action_favorite);
        } else {
            fab.setImageResource(R.drawable.ic_action_notfavorit);
        }
    }
}
