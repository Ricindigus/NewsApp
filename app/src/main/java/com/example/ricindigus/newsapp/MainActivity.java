package com.example.ricindigus.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    ListView listView;
    TextView emptyView;
    ProgressBar progressBar;
    ListaAdapter listaAdapter;

    String BASE_URL_REQUEST =
            "https://content.guardianapis.com/search?format=json&show-fields=all&order-by=newest&api-key=test&page-size=10";
    String TAG_REQUEST = "&tag=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lista);
        emptyView = findViewById(R.id.emptyView);
        progressBar = findViewById(R.id.loading_progress);

        listView.setEmptyView(emptyView);
        listaAdapter = new ListaAdapter(this,new ArrayList<NewsItem>());
        listView.setAdapter(listaAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String urlWeb = listaAdapter.getItem(position).getWeb();
                openWeb(urlWeb);
            }
        });


        if (existeConexionInternet()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0,null,this);
        }else{
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No hay conexion a internet");
        }
    }

    private void openWeb(String urlWeb) {
        Uri uri = Uri.parse(urlWeb);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String stringTag = sharedPreferences.getString("tag","sport/sport");
        String url = BASE_URL_REQUEST + TAG_REQUEST + stringTag;
        Uri uri = Uri.parse(url);
        return new NewsLoader(this,uri.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        progressBar.setVisibility(View.GONE);
        listaAdapter.clear();
        if (data != null && !data.isEmpty()) listaAdapter.addAll(data);
        emptyView.setText("No hay datos para mostrar");
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        listaAdapter.clear();
    }

    public boolean existeConexionInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings){
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
