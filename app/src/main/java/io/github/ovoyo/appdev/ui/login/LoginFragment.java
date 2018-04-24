package io.github.ovoyo.appdev.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseFragment;
import io.github.ovoyo.appdev.ui.base.OnFragmentBack;


public class LoginFragment extends BaseFragment {

    public static final String TAG = "LoginFragment";

    OnFragmentBack mOnFragmentBack;

    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login,null);
        ButterKnife.bind(this,view);
        setup();
        return view;
    }

    public void setup(){
        mToolbar.setTitle(R.string.login_title);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(v -> {
            if (mOnFragmentBack != null){
                mOnFragmentBack.onNavBack(TAG);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentBack){
            mOnFragmentBack = (OnFragmentBack) context;
        }else {
            throw new RuntimeException("MainActivity must impl interface OnFragmentBack.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnFragmentBack = null;
    }

    @OnClick(R.id.login_wx)
    public void onClickWXLogin(){

    }

    @OnClick(R.id.login_qq)
    public void onClickQQLogin(){

    }
}
