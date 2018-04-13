package io.github.ovoyo.appdev.ui.home;


import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.data.Feed;
import io.github.ovoyo.appdev.ui.base.BaseListFragment;

public class HomeFragment extends BaseListFragment {

    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;

    private boolean mInAtTop = true;
    private int mScrollTotal;
    private OnBottomNavBarShowHide mOnBottomNavBarShowHide;

    private String mLastId;

    public interface OnBottomNavBarShowHide {

        void show();

        void hide();

    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBottomNavBarShowHide) {
            mOnBottomNavBarShowHide = (OnBottomNavBarShowHide) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBottomNavBarShowHide");
        }
    }

    @Override
    public void setup() {
        initViews();
    }

    @Override
    public void loadData(boolean clear) {
        loadLocalData();
        mHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwipeRefresh() {
        resetLastId();
        loadRemoteData(true);
    }

    @Override
    public boolean onInterceptLoadMore() {
        if (!isLoading()) {
            mLastId = mData.get(mData.size() - 1).getLastId();
            loadRemoteData(false);
        }
        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnBottomNavBarShowHide = null;
    }


    private void initViews() {
        mToolbar.setTitle(R.string.app_name);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }
//                if (dy > 5) {
//                    mOnBottomNavBarShowHide.hide();
//                } else if (dy < -5) {
//                    mOnBottomNavBarShowHide.show();
//                }
            }
        });
    }

    private void loadLocalData() {
        Feed feed;
        for (int i = 0; i < 30; i++) {
            feed = new Feed();
            feed.setLastId(String.valueOf(i));
            mData.add(feed);
        }
    }

    public void onTabSelected() {
        if (mInAtTop) {
            setRefresh(true);
            onSwipeRefresh();
        } else {
            smoothScrollToPosition(0);
        }
    }

    private void resetLastId() {
        mLastId = null;
    }

    public void loadRemoteData(boolean clear) {
        notifyLoadingStart();
        new Handler().postDelayed(() -> {

            List<Feed> tempFeeds = clear ? new ArrayList<>() : mData;
            Feed feed;
            for (int i = 30; i < 60; i++) {
                feed = new Feed();
                feed.setLastId(String.valueOf(i));
                tempFeeds.add(feed);
            }
            mHomeAdapter.setData(tempFeeds);
            mHomeAdapter.notifyDataSetChanged();

            setRefresh(false);
            notifyLoadingFinished();
        }, 2000);
    }
}
