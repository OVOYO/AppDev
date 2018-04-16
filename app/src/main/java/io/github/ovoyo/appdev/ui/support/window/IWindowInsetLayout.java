package io.github.ovoyo.appdev.ui.support.window;


import android.graphics.Rect;
import android.support.v4.view.WindowInsetsCompat;

public interface IWindowInsetLayout {

    boolean applySystemWindowInsets19(Rect insets);

    boolean applySystemWindowInsets21(WindowInsetsCompat insets);

}
