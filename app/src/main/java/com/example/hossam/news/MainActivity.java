package com.example.hossam.news;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Item>> {
    private ArrayList<Item> mItems;
    private static final String REQUEST_URL =
            "https://content.guardianapis.com/search?api-key=cb41f6c2-b6a9-410b-a262-3edbf5fc82ed";

    private NewsAdapter mAdapter;
    private static final String TAG = "MainActivity";
    private static final int LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItems = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new NewsAdapter(mItems);
        recyclerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public Loader<ArrayList<Item>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Item>> loader, ArrayList<Item> items) {
        mAdapter.updateDataSet(items);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Item>> loader) {
        mAdapter.clearDataSet();
    }
}
