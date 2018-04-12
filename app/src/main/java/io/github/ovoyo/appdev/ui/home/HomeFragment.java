package io.github.ovoyo.appdev.ui.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseMainFragment;

public class HomeFragment extends BaseMainFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.app_swipe_fresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.app_recycler_view)
    RecyclerView mRecyclerView;

    List<String> mData = new ArrayList<>(0);
    HomeAdapter mAdapter;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blank, container, false);
        setUnBinder(ButterKnife.bind(this,root));

        fakeData();

        initViews();

        return root;
    }

    private void initViews(){
        mToolbar.setTitle(R.string.app_name);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(),linearLayoutManager.getOrientation()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new HomeAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {

    }

    private void fakeData(){
        for (int i = 0; i < 30; i++) {
            mData.add(String.valueOf(i));
        }
    }
}
