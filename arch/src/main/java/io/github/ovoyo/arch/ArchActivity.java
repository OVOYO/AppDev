package io.github.ovoyo.arch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;


public abstract class ArchActivity extends AppCompatActivity {

    private static final String TAG = "ArchActivity";

    private QMUIWindowInsetLayout mFragmentContainer;

    protected abstract int getContextViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        mFragmentContainer = new QMUIWindowInsetLayout(this);
        mFragmentContainer.setId(getContextViewId());
        setContentView(mFragmentContainer);
    }

    public FrameLayout getFragmentContainer() {
        return mFragmentContainer;
    }

    public ArchFragment getCurrentFragment() {
        return (ArchFragment) getSupportFragmentManager().findFragmentById(getContextViewId());
    }

    @Override
    public void onBackPressed() {
        ArchFragment archFragment = getCurrentFragment();
        if (archFragment != null) {
            archFragment.popBackStack();
        }
    }

    public void startFragment(ArchFragment archFragment) {
        startFragment(archFragment, true);
    }

    public void startFragment(ArchFragment archFragment, boolean addToBackStack) {
        ArchFragment.TransitionConfig transitionConfig = archFragment.onFetchTransitionConfig();
        String tagName = archFragment.getClass().getSimpleName();

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit, transitionConfig.popEnter, transitionConfig.popOut)
                .replace(getContextViewId(), archFragment, tagName);

        if (addToBackStack) {
            transaction.addToBackStack(tagName);
        }
        transaction.commit();
    }

    public void popBackStack() {

        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            ArchFragment fragment = getCurrentFragment();
            if (fragment == null) {
                finish();
                return;
            }

            ArchFragment.TransitionConfig config = fragment.onFetchTransitionConfig();
            Object lastExec = fragment.onLastFragmentFinish();
            if (lastExec != null) {
                if (lastExec instanceof ArchFragment) {
                    ArchFragment archFragment = (ArchFragment) lastExec;
                    startFragment(archFragment);
                } else if (lastExec instanceof Intent) {
                    Intent data = (Intent) lastExec;
                    finish();
                    startActivity(data);
                    overridePendingTransition(config.popEnter, config.popOut);
                } else {
                    throw new Error("can not handle onLastFragmentFinish");
                }
            } else {
                finish();
                overridePendingTransition(config.popEnter, config.popOut);
            }
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }

    }

    /**
     * 弹出该 clz 之上的所有 ArchFragment 不包含该 clz
     * 如果堆栈没有clazz或者就是当前的clazz，就相当于popBackStack()
     *
     * @param clz 要弹出的 ArchFragment Class
     */
    public void popBackStack(Class<? extends ArchFragment> clz) {
        getSupportFragmentManager().popBackStack(clz.getSimpleName(), 0);
    }

    /**
     * 弹出该 clz 之上的所有 ArchFragment 包含该 clz
     *
     * @param clazz 要弹出的 ArchFragment Class
     */
    public void popBackStackInclusive(Class<? extends ArchFragment> clazz) {
        getSupportFragmentManager().popBackStack(clazz.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
