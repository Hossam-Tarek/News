package com.example.hossam.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private String TAG = "NewsAdapter";
    private ArrayList<Item> mItems;
    private OnItemClickListener mListener;

    public NewsAdapter(ArrayList<Item> items, OnItemClickListener listener) {
        mItems = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mItems.get(position);
        holder.getSectionTextView().setText(item.getSection());
        holder.getTitleTextView().setText(item.getTitle());
        holder.getDateTextView().setText(item.getDate());
        holder.onListClick(item, mListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateDataSet(ArrayList<Item> items) {
        mItems.clear();
        if (items != null && !items.isEmpty()) {
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void clearDataSet() {
        mItems.clear();
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSectionTextView;
        private TextView mTitleTextView;
        private TextView mDateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mSectionTextView = itemView.findViewById(R.id.section);
            mTitleTextView = itemView.findViewById(R.id.title);
            mDateTextView = itemView.findViewById(R.id.date);
        }

        public void onListClick(final Item item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }

        public TextView getSectionTextView() {
            return mSectionTextView;
        }

        public TextView getTitleTextView() {
            return mTitleTextView;
        }

        public TextView getDateTextView() {
            return mDateTextView;
        }
    }
}
