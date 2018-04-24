package io.github.ovoyo.appdev.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.support.LoadMoreDelegate;
import io.github.ovoyo.appdev.ui.support.SwipeRefreshDelegate;

public abstract class BaseListFragment extends BaseFragment implements SwipeRefreshDelegate.OnSwipeRefreshListener,LoadMoreDelegate.LoadMoreSubject{

    @BindView(R.id.app_recycler_view)
    public RecyclerView mRecyclerView;

    public SwipeRefreshDelegate mRefreshDelegate;
    public LoadMoreDelegate mLoadMoreDelegate;
    private AtomicInteger mLoadingCount;
    private boolean mIsEnd = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.refresh_load_more_layout,null);
        ButterKnife.bind(this,view);

        setup(view);

        mRefreshDelegate = new SwipeRefreshDelegate(this);
        mLoadMoreDelegate = new LoadMoreDelegate(this);
        mLoadingCount = new AtomicInteger(0);

        applyRecyclerView(view);

        loadData(true);

        return view;
    }

    private void applyRecyclerView(View view){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(getAdapter());

        mRefreshDelegate.attach(view);

        mLoadMoreDelegate.attach(mRecyclerView);
    }

    public abstract RecyclerView.Adapter getAdapter();
    public abstract void setup(View view);
    public abstract void loadData(boolean clear);

    /**
     * 下拉刷新时触发
     */
    @Override
    public void onSwipeRefresh() {
        loadData(true);
    }

    /**
     * 是否正在刷新
     * @return true 正在刷新，false 没有
     */
    public boolean isRefreshing(){
        return mRefreshDelegate.isShowingRefresh();
    }

    /**
     * 设置是否开启下拉刷新
     * @param enable true 开启，false 不开启
     */
    public void setRefreshEnable(boolean enable){
        mRefreshDelegate.setRefresh(enable);
    }

    /**
     * 是否是加载更多状态
     * @return true 正在加载，false 没有加载
     */
    @Override
    public boolean isLoading() {
        return mLoadingCount.get() > 0;
    }

    /**
     * 加载更多开始时调用
     */
    public void notifyLoadingStart(){
        mLoadingCount.incrementAndGet();
    }

    /**
     * 加载更多结束时调用
     */
    public void notifyLoadingFinished(){
        mLoadingCount.decrementAndGet();
    }

    /**
     * 加载更多时触发
     */
    @Override
    public void onLoadMore() {
        if(!isEnd()){
            if (!onInterceptLoadMore()){
                loadData(false);
            }
        }
    }

    /**
     * 判断是否已经滑动到底部
     * @return true 已经到底部，false 未到底部
     */
    public boolean isEnd() {
        return mIsEnd;
    }

    /**
     * 设置是否已经滑动到底部
     * @param end true 已经到底部，false 未到底部
     */
    public void setEnd(boolean end) {
        mIsEnd = end;
    }

    /**
     * 拦截加载更多事件
     * @return true 拦截，false 不拦截，默认不拦截
     */
    public boolean onInterceptLoadMore() {
        return false;
    }
}
