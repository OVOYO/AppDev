package io.github.ovoyo.appdev.ui.pa;


import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.github.ovoyo.appdev.data.Poe;
import io.github.ovoyo.appdev.ui.base.BaseListFragment;

public class HomeFrag extends BaseListFragment {

    private boolean mInAtTop = true;
    private int mScrollTotal;

    private String mLastId;

    public static HomeFrag get() {
        return new HomeFrag();
    }

    @Override
    protected View onCreateView() {
        return makeView();
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

    private void initViews(){
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
        Poe poe;
        for (int i = 0; i < 30; i++) {
            poe = new Poe();
            poe.setLastId(String.valueOf(i));
            mData.add(poe);
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

            List<Poe> tempFeeds = clear ? new ArrayList<>() : mData;
            Poe feed;
            for (int i = 30; i < 60; i++) {
                feed = new Poe();
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
