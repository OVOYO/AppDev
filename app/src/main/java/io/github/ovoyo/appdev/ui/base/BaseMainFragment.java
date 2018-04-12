package io.github.ovoyo.appdev.ui.base;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import butterknife.Unbinder;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.di.Injectable;
import io.github.ovoyo.appdev.ui.home.HomeFragment;
import me.yokeyword.fragmentation.SupportFragment;


public abstract class BaseMainFragment extends SupportFragment implements Injectable {

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    protected OnBackToFirstListener _mBackToFirstListener;

    private Unbinder mUnbinder;

    public void setUnBinder(Unbinder unbinder) {
        mUnbinder = unbinder;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBackToFirstListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mBackToFirstListener = null;
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (this instanceof HomeFragment) {   // 如果是 第一个Fragment 则退出app

                if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                    ActivityCompat.finishAfterTransition(_mActivity);
                } else {
                    TOUCH_TIME = System.currentTimeMillis();
                    Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
                }
            } else {                                    // 如果不是,则回到第一个Fragment
                _mBackToFirstListener.onBackToFirstFragment();
            }
        }
        return true;
    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }
}
