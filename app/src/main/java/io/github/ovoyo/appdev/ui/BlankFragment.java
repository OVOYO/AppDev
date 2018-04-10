package io.github.ovoyo.appdev.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseFragment;


public class BlankFragment extends BaseFragment {

    public static final String TAG = "BlankFragment";

    public BlankFragment get(){
        return new BlankFragment();
    }

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
