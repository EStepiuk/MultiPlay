package ua.stepiukyevhen.multiplay.views.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ua.stepiukyevhen.multiplay.R;
import ua.stepiukyevhen.multiplay.databinding.ListItemBinding;
import ua.stepiukyevhen.multiplay.models.Track;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder>{

    List<Track> items = new ArrayList<>();

    public void addItems(List<Track> newItems) {
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void replaceItems(List<Track> newItems) {
        if (items.size() == newItems.size()) return;
        items.clear();
        addItems(newItems);
    }

    public void addItems(Track track) {
        items.add(track);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(Track item) {
            binding.setTrack(item);
        }
    }
}
