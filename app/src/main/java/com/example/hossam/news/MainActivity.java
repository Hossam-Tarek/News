package com.example.hossam.news;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.prefs.PreferenceChangeListener;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Item>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String REQUEST_URL =
            "https://content.guardianapis.com/search";

    private static final String API_KEY = "cb41f6c2-b6a9-410b-a262-3edbf5fc82ed";
    private NewsAdapter mAdapter;
    private static final String TAG = "MainActivity";
    private static final int LOADER_ID = 0;
    private TextView mEmptyTextView;
    private ProgressBar mProgressBar;
    private LoaderManager mLoaderManager;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Item> items = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new NewsAdapter(items, new NewsAdapter.ListItemClickListener() {
            @Override
            public void onListItemClicked(NewsAdapter adapter, View view, int position) {
                Item item = adapter.getItem(position);
                Uri url = Uri.parse(item.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, url);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        mEmptyTextView = findViewById(R.id.empty_text_view);
        mProgressBar = findViewById(R.id.loading_spinner);

        if (isInternetWorking()) {
            mLoaderManager = getLoaderManager();
            mLoaderManager.initLoader(LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet);
        }
    }

    private boolean isInternetWorking() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public Loader<ArrayList<Item>> onCreateLoader(int i, Bundle bundle) {
        mSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        String pageSize = mSharedPreferences.getString(
                getString(R.string.page_size_key),
                getString(R.string.page_size_default));

        String orderBy = mSharedPreferences.getString(
                getString(R.string.order_by_key),
                getString(R.string.order_by_default));

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key", API_KEY);
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", pageSize);
        uriBuilder.appendQueryParameter("order-by", orderBy);

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Item>> loader, ArrayList<Item> items) {
        mAdapter.clearAdapter();
        mProgressBar.setVisibility(View.GONE);
        if (items != null && !items.isEmpty()) {
            mAdapter.updateAdapter(items);
        } else {
            mEmptyTextView.setText(R.string.no_news);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Item>> loader) {
        mAdapter.clearAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        mLoaderManager.restartLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
