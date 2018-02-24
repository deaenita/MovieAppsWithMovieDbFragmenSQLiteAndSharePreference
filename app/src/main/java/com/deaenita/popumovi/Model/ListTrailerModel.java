package com.deaenita.popumovi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by USER on 15/12/2017.
 */
public class ListTrailerModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private ArrayList<TrailerModel> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<TrailerModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<TrailerModel> results) {
        this.results = results;
    }
}
