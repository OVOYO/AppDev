package io.github.ovoyo.appdev.ui.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.data.Feed;
import io.github.ovoyo.appdev.ui.detail.DetailActivity;
import io.github.ovoyo.appdev.ui.home.HomeAdapter;
import io.github.ovoyo.appdev.ui.support.LoadMoreDelegate;
import io.github.ovoyo.appdev.ui.support.SwipeRefreshDelegate;

public abstract class BaseListFragment
        extends BaseMainFragment
        implements SwipeRefreshDelegate.OnSwipeRefreshListener,LoadMoreDelegate.LoadMoreSubject{

    @BindView(R.id.app_recycler_view)
    public RecyclerView mRecyclerView;

    public SwipeRefreshDelegate mRefreshDelegate;
    public LoadMoreDelegate mLoadMoreDelegate;

    private AtomicInteger mLoadingCount;
    private boolean mIsEnd = false;

    public HomeAdapter mHomeAdapter;
    public List<Feed> mData;

    private HomeAdapter.OnItemClickListener mOnItemClickListener = new HomeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position, Feed feed) {
            _mActivity.startActivity(new Intent(_mActivity.getApplicationContext(), DetailActivity.class));
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mData = new ArrayList<>(0);
        mHomeAdapter = new HomeAdapter(mData,mOnItemClickListener);
        mRefreshDelegate = new SwipeRefreshDelegate(this);
        mLoadMoreDelegate = new LoadMoreDelegate(this);
        mLoadingCount = new AtomicInteger(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank,container,false);
        setUnBinder(ButterKnife.bind(this,view));
        applyRecyclerView(view);
        setup();
        loadData(true);
        return view;
    }

    private void applyRecyclerView(View view){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mHomeAdapter);

        mRefreshDelegate.attach(view);

        mLoadMoreDelegate.attach(mRecyclerView);
    }

    public abstract void setup();
    public abstract void loadData(boolean clear);

    public void swipeToRefreshEnable(boolean enable){
        mRefreshDelegate.setEnabled(enable);
    }

    @Override
    public void onSwipeRefresh() {
        loadData(true);
    }

    public void setRefresh(boolean refresh){
        mRefreshDelegate.setRefresh(refresh);
    }

    public boolean isRefreshing(){
        return mRefreshDelegate.isShowingRefresh();
    }

    @Override
    public void onLoadMore() {
        if (!isEnd()){
            if (!onInterceptLoadMore()){
                loadData(false);
            }
        }
    }

    public boolean isEnd() {
        return mIsEnd;
    }

    public void setEnd(boolean end) {
        mIsEnd = end;
    }

    public boolean onInterceptLoadMore() {
        return false;
    }

    @Override
    public boolean isLoading() {
        return mLoadingCount.get() > 0;
    }

    public void notifyLoadingStart(){
        mLoadingCount.incrementAndGet();
    }

    public void notifyLoadingFinished(){
        mLoadingCount.decrementAndGet();
    }

    public void smoothScrollToPosition(int position){
        mRecyclerView.smoothScrollToPosition(position);
    }
}
