package io.github.ovoyo.appdev.ui.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.data.network.model.Article;
import me.drakeet.multitype.ItemViewBinder;


public class ArticleViewBinder extends ItemViewBinder<Article,ArticleViewBinder.VH> {

    @NonNull
    @Override
    protected VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_article_view,parent,false);
        return new VH(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull VH holder, @NonNull Article item) {

    }

    static class VH extends RecyclerView.ViewHolder{

        VH(View itemView) {
            super(itemView);
        }
    }
}
