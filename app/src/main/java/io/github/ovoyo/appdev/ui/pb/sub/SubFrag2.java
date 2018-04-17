package io.github.ovoyo.appdev.ui.pb.sub;


import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.github.ovoyo.appdev.data.Poe;
import io.github.ovoyo.appdev.ui.base.BaseListFragment;

public class SubFrag2 extends BaseListFragment {

    private String mLastId;

    public static SubFrag2 get() {
        return new SubFrag2();
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
    }

    private void loadLocalData() {
        Poe poe;
        for (int i = 0; i < 30; i++) {
            poe = new Poe();
            poe.setLastId(String.valueOf(i));
            mData.add(poe);
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
