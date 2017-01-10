package com.example.tanya.hw4;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tanya on 12/22/16.
 */
public class ListAppsAdaptor extends RecyclerView.Adapter<ListAppsAdaptor.ViewHolder> implements Filterable {
    private List<AppInfo> data;
    private List<AppInfo> originalData;
    private ArrayList<AppInfo> filteredData;

    final int MAX_VALUE = 12;
    public  int checkedCounter = 0;

    private static ClickListener clickListener;

    private ListFilter filter = new ListFilter();

    public ListAppsAdaptor(List<AppInfo> data) {

        this.data = data;
        originalData = new ArrayList<>(data);
        filteredData = new ArrayList<>(data);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public List<AppInfo> getChecked(){
        return data;
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView text;
        public ImageView icon;
        public CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
            icon = (ImageView) view.findViewById(R.id.icon);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    @Override
    public ListAppsAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.text.setText(data.get(position).getAppName());
        holder.icon.setBackground(data.get(position).getAppIcon());
        holder.checkBox.setChecked(data.get(position).isChecked());

        if ((checkedCounter >= MAX_VALUE) && (!data.get(position).isChecked())) {
            holder.checkBox.setEnabled(false);
        }
        else {
            holder.checkBox.setEnabled(true);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ((checkedCounter < MAX_VALUE) && (!data.get(position).isChecked())){
                    checkedCounter++;
                }
                if (data.get(position).isChecked()){
                    checkedCounter--;
                }
                data.get(position).setChecked(!data.get(position).isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            data.clear();
            filteredData.clear();
            FilterResults filterResults = new FilterResults();

            if (constraint != null || constraint.length() != 0) {
                for (AppInfo a : originalData) {
                    if (a.getAppName().contains(constraint.toString())) {
                        filteredData.add(a);
                    }
                }
            }
            else {
                filteredData.addAll(data);
            }

            filterResults.values = filteredData;
            filterResults.count = filteredData.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.addAll(((List<AppInfo>) results.values));
            notifyDataSetChanged();
        }
    }


}

