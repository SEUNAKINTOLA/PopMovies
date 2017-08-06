package com.example.akintolaoluwaseun.popmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.akintolaoluwaseun.popmovies.domain.RecipeBean;
import com.example.akintolaoluwaseun.popmovies.domain.mov;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.akintolaoluwaseun.popmovies.R.id.progressBar;

public class MainActivity extends AppCompatActivity implements
        PopMoviesAdapter.ListItemClickListener{

    private final static String SORT_ORDER = "pref_sort_order";
    private List<RecipeBean.AllResults> movie_list;
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<mov> mov_list;
    private RecyclerView mMovieList;
    private PopMoviesAdapter adapter;
    private String mSortOrder;
    private ProgressBar mProgressBar;
    String seen;
    static String MOVIE_URL;
    static String PARAM_API;
    static String MOVIES_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        preferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        mProgressBar = (ProgressBar) findViewById(progressBar);
        mSortOrder = preferences.getString(SORT_ORDER, "popular");

        movie_list = new ArrayList<>();
        mMovieList = (RecyclerView) findViewById(R.id.rv_movies_list);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mMovieList.setLayoutManager(layoutManager);
        mMovieList.setHasFixedSize(true);
        mProgressBar.setVisibility(View.VISIBLE);


        if (isNetworkAvailable()) {
            new DownloadTask().execute("new");
        }
         else {
                Toast.makeText(MainActivity.this, "No network", Toast.LENGTH_SHORT).show();
                setNoConnectionLayout();
            }
    }

    class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            try {
                URL url = buildUrl(mSortOrder, getBaseContext());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(7000);
                connection.setReadTimeout(7000);
                if (connection.getResponseCode() == 200) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    InputStream inputStream = connection.getInputStream();
                    int len = 0;
                    byte[] b = new byte[1024];
                    while ((len = inputStream.read(b)) != -1) {
                        outputStream.write(b, 0, len);
                    }
                    seen = new String(outputStream.toByteArray());
                    return new String(outputStream.toByteArray());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            mProgressBar.setVisibility(View.GONE);
            if (seen != null) {
                movie_list.clear();

               RecipeBean models = JSON.parseObject(seen, RecipeBean.class);
                List<RecipeBean.AllResults> mod = models.getResults();

                movie_list = mod;
                Log.d(TAG, String.valueOf(movie_list));

                adapter = new PopMoviesAdapter(getApplicationContext(), movie_list, MainActivity.this);
                mMovieList.setAdapter(adapter);

            } else {
                Toast.makeText(MainActivity.this, "No result", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void setNoConnectionLayout() {
        mProgressBar.setVisibility(View.GONE);
        ((findViewById(R.id.rv_no_conn))).setVisibility(View.VISIBLE);
        ((findViewById(R.id.rv_refresh))).setVisibility(View.VISIBLE);
    }

    public void refresh(View view){
        if (isNetworkAvailable())
        {
            ((findViewById(R.id.rv_no_conn))).setVisibility(View.INVISIBLE);
            ((findViewById(R.id.rv_refresh))).setVisibility(View.INVISIBLE);
            new DownloadTask().execute("");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings)
        {
            startActivity(new Intent(this, Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public URL buildUrl(String sortBy, Context context) {
        MOVIE_URL = context.getString(R.string.movie_base_url);

        MOVIES_KEY = context.getString(R.string.my_movies_key);
        PARAM_API = context.getString(R.string.api_param);

        Uri builtUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendEncodedPath(sortBy)
                .appendQueryParameter(PARAM_API, MOVIES_KEY)
                .build();
        Log.d(TAG, String.valueOf(builtUri));

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    @Override
    public void onItemClick(int position, List<RecipeBean.AllResults> MyMovie_List  ) {
        Intent details_intent = new Intent(this, MovieDetails.class);
        details_intent.putExtra("position", position);
        details_intent.putExtra("Details", seen);
        startActivity(details_intent);
    }


    private boolean isNetworkAvailable(){
        ConnectivityManager connection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NetworkInfo = connection.getActiveNetworkInfo();
        return NetworkInfo != null && NetworkInfo.isConnected();
    }


    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(

                        SharedPreferences sharedPreferences, String key) {
                    if (key.equals(SORT_ORDER))
                    {
                        mSortOrder = sharedPreferences.getString(SORT_ORDER, "popular");
                        if (isNetworkAvailable())
                            new DownloadTask().execute("url");
                    }
                }
            };
}
