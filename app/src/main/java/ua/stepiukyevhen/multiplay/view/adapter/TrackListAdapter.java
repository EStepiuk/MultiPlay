package ua.stepiukyevhen.multiplay.view.adapter;


import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.View;

import ua.stepiukyevhen.multiplay.databinding.ListItemBinding;
import ua.stepiukyevhen.multiplay.model.base.Track;
import ua.stepiukyevhen.multiplay.view.adapter.base.BaseAdapter;

public class TrackListAdapter extends BaseAdapter<TrackListAdapter.ViewHolder>{

    public TrackListAdapter(@LayoutRes int layout) {
        super(layout);
    }

    @Override
    protected ViewHolder viewHolder(View v) {
        return new ViewHolder(v);
    }

    public class ViewHolder extends BaseAdapter.ViewHolder {

        private ListItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(Track item) {
            binding.setTrack(item);
        }
    }
}
