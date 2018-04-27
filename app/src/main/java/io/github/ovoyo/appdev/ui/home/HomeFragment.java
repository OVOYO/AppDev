package io.github.ovoyo.appdev.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.data.network.model.Article;
import io.github.ovoyo.appdev.ui.base.BaseHomeFragment;
import me.drakeet.multitype.MultiTypeAdapter;


public class HomeFragment extends BaseHomeFragment {

    public static final String TAG = "HomeFragment";

    private MultiTypeAdapter mAdapter = new MultiTypeAdapter();

    public static HomeFragment get(){
        return new HomeFragment();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setup(View view) {

    }

    @Override
    public void loadData(boolean clear) {
        ArrayList<Article> arrayList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            arrayList.add(new Article());
        }
        mAdapter.register(Article.class,new ArticleViewBinder());
        mAdapter.setItems(arrayList);
        mAdapter.notifyDataSetChanged();
    }

}
