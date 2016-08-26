package ua.stepiukyevhen.multiplay.view.adapter;


import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.View;

import ua.stepiukyevhen.multiplay.databinding.ListItemBinding;
import ua.stepiukyevhen.multiplay.model.base.Track;
import ua.stepiukyevhen.multiplay.view.adapter.base.BaseAdapter;

//TODO: reimplement adapter
public class TrackListAdapter extends BaseAdapter<TrackListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public TrackListAdapter(@LayoutRes int layout) {
        super(layout);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    protected ViewHolder viewHolder(View v) {
        return new ViewHolder(v);
    }

    public class ViewHolder extends BaseAdapter.ViewHolder implements View.OnClickListener {

        private ListItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(Track item) {
            binding.setTrack(item);
            binding.content.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
