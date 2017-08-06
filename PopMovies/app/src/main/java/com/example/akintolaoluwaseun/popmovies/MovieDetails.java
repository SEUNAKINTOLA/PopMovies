package com.example.akintolaoluwaseun.popmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.akintolaoluwaseun.popmovies.domain.RecipeBean;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.akintolaoluwaseun.popmovies.R.id.Movie_overview;
import static com.example.akintolaoluwaseun.popmovies.R.id.Movie_poster;

/**
 * Created by AKINTOLA OLUWASEUN on 7/28/2017.
 */

public class MovieDetails extends Activity {
    private TextView TitleView;
    private TextView overviewView;
    private TextView released_DateView;
    private ImageView movie_posterView;
    private TextView vote_AverageView;
    int pos;
    private static final String TAG = MovieDetails.class.getSimpleName();

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.movie_details);

        TitleView = (TextView) findViewById(R.id.Movie_Title);
        overviewView  = (TextView) findViewById(Movie_overview);
        vote_AverageView  = (TextView) findViewById(R.id.Movie_ratings);
        released_DateView  = (TextView) findViewById(R.id.Release_date);
        movie_posterView  = (ImageView) findViewById(Movie_poster);

        Intent intent = getIntent();
       String seen =  intent.getStringExtra("Details");

        RecipeBean models = JSON.parseObject(seen, RecipeBean.class);
        List<RecipeBean.AllResults> moveDetails = models.getResults();

        pos =  intent.getIntExtra("position", 0);
        Log.d(TAG, String.valueOf(moveDetails));

    setDetails(moveDetails);
    }

    private void setDetails(List<RecipeBean.AllResults> details) {
        TitleView.setText(details.get(pos).getOriginal_title());
        overviewView.setText(details.get(pos).getOverview());

        released_DateView.setText(details.get(pos).getRelease_date());
        vote_AverageView.setText(String.valueOf(details.get(pos).getVote_average()));

        Picasso.with(getBaseContext()).load(details.get(pos).getPoster_path())
                .networkPolicy(NetworkPolicy.OFFLINE).resize(200, 200).into(movie_posterView);
    }
}
