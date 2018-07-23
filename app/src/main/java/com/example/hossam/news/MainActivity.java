package com.example.hossam.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Item>> {
    private static final String REQUEST_URL =
            "https://content.guardianapis.com/search?api-key=cb41f6c2-b6a9-410b-a262-3edbf5fc82ed";

    private NewsAdapter mAdapter;
    private static final String TAG = "MainActivity";
    private static final int LOADER_ID = 0;
    private TextView mEmptyTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Item> items = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new NewsAdapter(items, new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
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
            getLoaderManager().initLoader(LOADER_ID, null, this);
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
        return new NewsLoader(this, REQUEST_URL);
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
}
