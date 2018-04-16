package io.github.ovoyo.arch;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.qmuiteam.qmui.util.QMUIViewHelper;

import java.util.ArrayList;


public abstract class ArchFragment extends Fragment {

    private static final String TAG = "ArchFragment";

    protected static final TransitionConfig SLIDE_TRANSITION_CONFIG = new TransitionConfig(
            R.anim.slide_in_right, R.anim.slide_out_left,
            R.anim.slide_in_left, R.anim.slide_out_right);

    public static final int RESULT_CANCELED = Activity.RESULT_CANCELED;
    public static final int RESULT_OK = Activity.RESULT_CANCELED;
    public static final int RESULT_FIRST_USER = Activity.RESULT_FIRST_USER;

    private static final int NO_REQUEST_CODE = 0;
    private int mSourceRequestCode = NO_REQUEST_CODE;
    private Intent mResultData = null;
    private int mResultCode = RESULT_CANCELED;

    private int mBackStackIndex = 0;

    public static final int ANIMATION_ENTER_STATUS_NOT_START = -1;
    public static final int ANIMATION_ENTER_STATUS_STARTED = 0;
    public static final int ANIMATION_ENTER_STATUS_END = 1;

    private int mEnterAnimationStatus = ANIMATION_ENTER_STATUS_NOT_START;
    private boolean mCalled = true;
    private ArrayList<Runnable> mDelayRenderRunnableList = new ArrayList<>();

    private View mBaseView;

    public ArchActivity getBaseArchActivity() {
        return (ArchActivity) getActivity();
    }

    public boolean isAttachedToActivity() {
        return !isRemoving() && mBaseView != null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBaseView = null;
    }

    public void startFragment(ArchFragment archFragment) {
        startFragment(archFragment, true);
    }

    public void startFragment(ArchFragment archFragment, boolean addToBackStack) {
        ArchActivity baseArchActivity = this.getBaseArchActivity();
        if (baseArchActivity != null) {
            if (this.isAttachedToActivity()) {
                baseArchActivity.startFragment(archFragment, addToBackStack);
            } else {
                Log.e(TAG, "fragment not attached:" + this);
            }
        } else {
            Log.e(TAG, "start fragment null:" + this);
        }
    }

    /**
     * 模拟 startActivityForResult/onActivityResult
     *
     * @param fragment    所要启动的 Fragment
     * @param requestCode 所要启动 Fragment 的 requestCode
     */
    public void startFragmentForResult(ArchFragment fragment, int requestCode) {
        if (requestCode == NO_REQUEST_CODE) {
            throw new RuntimeException("requestCode can not be " + requestCode);
        }
        fragment.setTargetFragment(this, requestCode);
        mSourceRequestCode = requestCode;
        startFragment(fragment);
    }

    public void setFragmentResult(int resultCode, Intent resultData) {
        int targetRequestCode = getTargetRequestCode();
        if (targetRequestCode == 0) {
            Log.w(TAG, "call setFragmentResult,but no requestCode exists");
            return;
        }
        Fragment fragment = getTargetFragment();
        if (fragment == null || !(fragment instanceof ArchFragment)) {
            Log.w(TAG, "targetFragment null or not extends ArchFragment");
            return;
        }
        ArchFragment targetFragment = (ArchFragment) getTargetFragment();
        if (targetFragment.mSourceRequestCode == targetRequestCode) {
            targetFragment.mResultCode = resultCode;
            targetFragment.mResultData = resultData;
        }
    }

    /**
     * 在 onStart 中执行
     *
     * @param requestCode 请求码
     * @param resultCode  回复码
     * @param resultData  回复数据
     */
    public void onFragmentResult(int requestCode, int resultCode, Intent resultData) {

    }

    /******************************  生命周期 *************************************/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            int backStackEntryCount = fragmentManager.getBackStackEntryCount();
            for (int i = backStackEntryCount - 1; i >= 0; i--) {
                FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(i);
                if (getClass().getName().equals(backStackEntry.getName())) {
                    mBackStackIndex = i;
                    break;
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        int requestCode = mSourceRequestCode;
        int resultCode = mResultCode;
        Intent data = mResultData;

        // 重置数据
        mSourceRequestCode = NO_REQUEST_CODE;
        mResultCode = RESULT_CANCELED;
        mResultData = null;

        if (requestCode != NO_REQUEST_CODE) {
            onFragmentResult(requestCode, resultCode, data);
        }
    }

    protected abstract View onCreateView();
    /**
     * 沉浸式处理，返回 false，则状态栏下为内容区域，返回 true, 则状态栏下为 padding 区域
     */
    protected boolean translucentFull() {
        return false;
    }

    private View newCreateView(){
        View root = onCreateView();
        if (translucentFull()){
            root.setFitsSystemWindows(true);
        }else {
            root.setFitsSystemWindows(false);
        }
        return root;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = newCreateView();

        ViewCompat.setTranslationZ(view,mBackStackIndex);

        if (getActivity() != null){
            QMUIViewHelper.requestApplyInsets(getActivity().getWindow());
        }

        return view;
    }

    public void popBackStack() {
        if (mEnterAnimationStatus != ANIMATION_ENTER_STATUS_END){
            return;
        }
        getBaseArchActivity().popBackStack();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (!enter && getParentFragment() != null && getParentFragment().isRemoving()) {
            // This is a workaround for the bug where child fragments disappear when
            // the parent is removed (as all children are first removed from the parent)
            // See https://code.google.com/p/android/issues/detail?id=55228
            Animation doNothingAnim = new AlphaAnimation(1, 1);
            int duration = getResources().getInteger(R.integer.qmui_anim_duration);
            doNothingAnim.setDuration(duration);
            return doNothingAnim;
        }
        Animation animation = null;
        if (enter) {
            try {
                animation = AnimationUtils.loadAnimation(getContext(), nextAnim);

            } catch (Resources.NotFoundException ignored) {

            } catch (RuntimeException ignored) {

            }
            if (animation != null) {
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        onEnterAnimationStart(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        checkAndCallOnEnterAnimationEnd(animation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                onEnterAnimationStart(null);
                checkAndCallOnEnterAnimationEnd(null);
            }
        }
        return animation;
    }

    protected void onEnterAnimationStart(@Nullable Animation animation) {
        mEnterAnimationStatus = ANIMATION_ENTER_STATUS_STARTED;
    }

    private void checkAndCallOnEnterAnimationEnd(@Nullable Animation animation) {
        mCalled = false;
        onEnterAnimationEnd(animation);
        if (!mCalled) {
            throw new RuntimeException("QMUIFragment " + this + " did not call through to super.onEnterAnimationEnd(Animation)");
        }
    }

    protected void onEnterAnimationEnd(@Nullable Animation animation) {
        if (mCalled) {
            throw new IllegalAccessError("don't call #onEnterAnimationEnd() directly");
        }
        mCalled = true;
        mEnterAnimationStatus = ANIMATION_ENTER_STATUS_END;
        if (mDelayRenderRunnableList.size() > 0) {
            for (int i = 0; i < mDelayRenderRunnableList.size(); i++) {
                mDelayRenderRunnableList.get(i).run();
            }
            mDelayRenderRunnableList.clear();
        }
    }

    /**
     * 在动画开始前或动画结束后都会被直接执行
     *
     * @param runnable 所要执行的任务
     */
    public void runAfterAnimation(Runnable runnable) {
        runAfterAnimation(runnable, false);
    }

    /**
     * 异步数据渲染时，调用这个方法可以保证数据是在转场动画结束后进行渲染。
     * 转场动画过程中进行数据渲染时，会造成卡顿，从而影响用户体验
     *
     * @param runnable 所要执行的任务
     * @param onlyEnd 是否只在动画结束时执行
     */
    public void runAfterAnimation(Runnable runnable, boolean onlyEnd){
        Utils.assertInMainThread();
        boolean ok = onlyEnd ? mEnterAnimationStatus == ANIMATION_ENTER_STATUS_END :
                mEnterAnimationStatus != ANIMATION_ENTER_STATUS_STARTED;
        if (ok) {
            runnable.run();
        } else {
            mDelayRenderRunnableList.add(runnable);
        }
    }

    /**
     * 如果是最后一个Fragment，finish后执行的方法
     */
    public Object onLastFragmentFinish() {
        return null;
    }

    /**
     * 转场动画控制
     */
    public TransitionConfig onFetchTransitionConfig() {
        return SLIDE_TRANSITION_CONFIG;
    }

    /**
     * Fragment 进入、退出动画
     */
    public static final class TransitionConfig {
        public final int enter;
        public final int exit;
        public final int popEnter;
        public final int popOut;

        public TransitionConfig(int enter, int popOut) {
            this(enter, 0, 0, popOut);
        }

        public TransitionConfig(int enter, int exit, int popEnter, int popOut) {
            this.enter = enter;
            this.exit = exit;
            this.popEnter = popEnter;
            this.popOut = popOut;
        }
    }
}
