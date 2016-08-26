package ua.stepiukyevhen.multiplay.view.adapter.base;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ua.stepiukyevhen.multiplay.model.base.Track;

public abstract class BaseAdapter<V extends BaseAdapter.ViewHolder> extends RecyclerView.Adapter<V>{

    private final int layout;
    private List<Track> items = new ArrayList<>();

    public BaseAdapter(@LayoutRes int layout) {
           this.layout =  layout;
    }

    public List<Track> getItems() {
        return items;
    }

    public void add(List<? extends Track> newItems) {
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void replaceItems(List<? extends Track> newItems) {
        if (items.size() == newItems.size()) return;
        items.clear();
        add(newItems);
    }

    public void add(Track track) {
        items.add(track);
        notifyDataSetChanged();
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        holder.bind(items.get(position));
    }

    protected abstract V viewHolder(View v);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(Track track);
    }
}
