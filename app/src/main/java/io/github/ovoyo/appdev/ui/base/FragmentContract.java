package io.github.ovoyo.appdev.ui.base;


public class FragmentContract {

    public interface OnFragmentBack {

        void onNavBack(String tag);

    }

    public interface OnFragmentShow {

        void onFragmentShow(String tag);

    }
}
