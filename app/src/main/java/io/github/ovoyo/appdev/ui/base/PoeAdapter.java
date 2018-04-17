package io.github.ovoyo.appdev.ui.base;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.data.Poe;

public class PoeAdapter extends RecyclerView.Adapter<PoeAdapter.VH> {

    private List<Poe> mData;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(View view, int position, Poe Poe);

    }

    public PoeAdapter(List<Poe> data, OnItemClickListener listener) {
        mData = data;
        mOnItemClickListener = listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poe_item_layout, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        holder.item.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, position, mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<Poe> data) {
        mData = data;
    }

    static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.feed_item_rl)
        RelativeLayout item;

        @BindView(R.id.feed_item_title)
        TextView title;

        @BindView(R.id.feed_item_content)
        TextView desc;

        @BindView(R.id.feed_item_from)
        TextView from;

        @BindView(R.id.feed_item_date)
        TextView date;

        VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
