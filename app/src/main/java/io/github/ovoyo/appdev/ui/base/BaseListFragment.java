package io.github.ovoyo.appdev.ui.base;

import android.support.v7.widget.RecyclerView;

import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.support.LoadMoreDelegate;
import io.github.ovoyo.appdev.ui.support.SwipeRefreshDelegate;

public abstract class BaseListFragment extends BaseFragment
        implements SwipeRefreshDelegate.OnSwipeRefreshListener,LoadMoreDelegate.LoadMoreSubject{

    @BindView(R.id.app_recycler_view)
    public RecyclerView mRecyclerView;

    public SwipeRefreshDelegate mRefreshDelegate;
    public LoadMoreDelegate mLoadMoreDelegate;

    private AtomicInteger mLoadingCount;
    private boolean mIsEnd = false;

}
