package com.example.hossam.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));
        items.add(new Item("aaa", "bbb", "ccc", ""));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NewsAdapter adapter = new NewsAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}
