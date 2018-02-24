package com.deaenita.popumovi.Fragmen;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deaenita.popumovi.Model.ListMovieModel;
import com.deaenita.popumovi.Model.MovieModel;
import com.deaenita.popumovi.MovieAdapter;
import com.deaenita.popumovi.R;
import com.deaenita.popumovi.connection.ApiService;
import com.deaenita.popumovi.connection.RetrofitConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragmen extends Fragment {


    public PopularFragmen() {
        // Required empty public constructor
    }


    RecyclerView recycler;
    ArrayList<MovieModel> listdata = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmenView = inflater.inflate(R.layout.fragment_popular, container, false);
        recycler = fragmenView.findViewById(R.id.rv_film);


        //data dummy
//        for (int i = 0; i < 20; i++) {
//            MovieModel movie1 = new MovieModel("dea cantik","https://cdn.brilio.net/news/2016/10/17/101731/483692-film-chelsea-islan-.jpg");
//            listdata.add(movie1);
//        }

        //data real
        ambilDataOnline();

        //2 adapter
        MovieAdapter adapter = new MovieAdapter(listdata, getActivity());
        recycler.setAdapter(adapter);

         //3 layout manager
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return fragmenView;
    }

    private void ambilDataOnline() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Mohon Bersabar");
        progress.show();

        ApiService api = RetrofitConfig.getApiService();
        Call<ListMovieModel> call = api.ambilFilmPopuler();

        call.enqueue(new Callback<ListMovieModel>() {
            @Override
            public void onResponse(Call<ListMovieModel> call, Response<ListMovieModel> response) {
                progress.dismiss();
                if(response.isSuccessful()){
                    listdata = response.body().getResults();
                    MovieAdapter adapter = new MovieAdapter(listdata, getActivity());
                    recycler.setAdapter(adapter);
                }else{
                    Toast.makeText(getActivity(), "response is not succesfull", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ListMovieModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Response Failure", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
