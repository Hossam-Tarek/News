package com.example.hossam.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private String TAG = "NewsAdapter";
    private ArrayList<Item> mItems;
    private ListItemClickListener mListener;

    public NewsAdapter(ArrayList<Item> items, ListItemClickListener listener) {
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
        holder.getDateTextView().setText(formatDate(item.getDate()));
        holder.getAuthorNameTextView().setText(item.getAuthorName());
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }

    private String formatDate(String defaultDate) {
        defaultDate = defaultDate.substring(0, defaultDate.length() - 1);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date;
        String output = "";
        try {
            date = inputFormat.parse(defaultDate);
            output = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date.", e);
        }
        return output;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAdapter(ArrayList<Item> items) {
        clearAdapter();
        if (items != null & !items.isEmpty()) {
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void clearAdapter() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClicked(NewsAdapter adapter, View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mSectionTextView;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mAuthorNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mSectionTextView = itemView.findViewById(R.id.section);
            mTitleTextView = itemView.findViewById(R.id.title);
            mDateTextView = itemView.findViewById(R.id.date);
            mAuthorNameTextView = itemView.findViewById(R.id.author_name);
            itemView.setOnClickListener(this);
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

        public TextView getAuthorNameTextView() {
            return mAuthorNameTextView;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onListItemClicked(NewsAdapter.this, itemView, position);
        }
    }
}
