package io.github.ovoyo.appdev.ui.home;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.VH> {

    private List<String> mData;

    public HomeAdapter(List<String> data) {
        mData = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_layout, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class VH extends RecyclerView.ViewHolder {

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