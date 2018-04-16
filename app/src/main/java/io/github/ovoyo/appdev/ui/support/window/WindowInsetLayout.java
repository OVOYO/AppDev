package io.github.ovoyo.appdev.ui.support.window;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class WindowInsetLayout extends FrameLayout implements IWindowInsetLayout {

    private WindowInsetHelper mWindowInsetHelper;

    public WindowInsetLayout(@NonNull Context context) {
        this(context, null);
    }

    public WindowInsetLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WindowInsetLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWindowInsetHelper = new WindowInsetHelper(this,this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (ViewCompat.getFitsSystemWindows(this)) {
            ViewCompat.requestApplyInsets(this);
        }
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            return applySystemWindowInsets19(insets);
        }
        return super.fitSystemWindows(insets);
    }

    @Override
    public boolean applySystemWindowInsets19(Rect insets) {
        return mWindowInsetHelper.defaultApplySystemWindowInsets19(this,insets);
    }

    @Override
    public boolean applySystemWindowInsets21(WindowInsetsCompat insets) {
        return mWindowInsetHelper.defaultApplySystemWindowInsets21(this,insets);
    }
}
