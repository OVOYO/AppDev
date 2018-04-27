package io.github.ovoyo.appdev.ui.support;


import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;

public class SwipeRefreshDelegate {

    @BindView(R.id.app_swipe_fresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private OnSwipeRefreshListener providedListener;


    public interface OnSwipeRefreshListener {

        void onSwipeRefresh();
    }


    public SwipeRefreshDelegate(OnSwipeRefreshListener listener) {
        this.providedListener = listener;
    }


    public void attach(Activity activity) {
        ButterKnife.bind(this, activity);
        trySetupSwipeRefresh();
    }


    public void attach(View fragment) {
        ButterKnife.bind(this, fragment);
        trySetupSwipeRefresh();
    }


    private void trySetupSwipeRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    providedListener.onSwipeRefresh();
                }
            });
        }
    }


    public void setRefresh(boolean requestDataRefresh) {
        if (swipeRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
    }


    public void setEnabled(boolean enable) {
        if (swipeRefreshLayout == null) {
            throw new IllegalAccessError("The SwipeRefreshLayout has not been initialized.");
        }
        swipeRefreshLayout.setEnabled(enable);
    }


    public boolean isShowingRefresh() {
        return swipeRefreshLayout.isRefreshing();
    }


    public void setProgressViewOffset(boolean scale, int start, int end) {
        swipeRefreshLayout.setProgressViewOffset(scale, start, end);
    }

}