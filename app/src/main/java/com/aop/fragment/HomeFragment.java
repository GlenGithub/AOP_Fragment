package com.aop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aop.annotation.ContentView;
import com.aop.base.BaseFragment;
import com.aop.utils.ToastUtil;
import com.jkb.fragment.rigger.annotation.Puppet;
import com.jkb.fragment.rigger.rigger.Rigger;
import com.jkb.fragment.rigger.utils.Logger;

import java.util.UUID;

import butterknife.OnClick;

/**
 * Created by glen1943 on 2018/1/25.
 * Rigger在Fragment在被创建的时候（构造函数中）默认生成可保证唯一性的tag，规则是采用UUID+类名，所以没有规律性。
 */

@ContentView(R.layout.fragment_home)
@Puppet
public class HomeFragment extends BaseFragment {

    public static final int REQUEST_CODE = 0x9527;
    public static final String RESULT_CALLBACK = "value";

    /*可携带参数初始化，这里没有传参*/
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*接收参数*/
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @OnClick({R.id.btn_startfragment,R.id.btn_startfragmentforresult,R.id.btn_replacefragment,R.id.btn_showfragment,R.id.btn_lazyload})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_startfragment:
                Rigger.getRigger(this).startFragment(StartFragment.newInstance(0));
                break;

            case R.id.btn_startfragmentforresult:
                Rigger.getRigger(this).startFragmentForResult(this, StartForResultFragment.newInstance(), REQUEST_CODE);
                break;

            case R.id.btn_replacefragment:
                Rigger.getRigger(this).startFragment(ReplaceFragment.newInstance());
                break;

            case R.id.btn_showfragment:
                Rigger.getRigger(this).startFragment(ShowFragment.newInstance());
                break;

            case R.id.btn_lazyload:
                Rigger.getRigger(this).startFragment(LazyLoadFragment.newInstance());
                break;
        }
    }

    @Override
    protected void load(Bundle savedInstanceState) {

    }

    public void onFragmentResult(int requestCode, int resultCode, Bundle args) {
        Logger.i(this, "requestCode=" + requestCode);
        Logger.i(this, "resultCode=" + resultCode);
        if (resultCode != Rigger.RESULT_OK) return;
        String value = args.getString(RESULT_CALLBACK);
        ToastUtil.showToast(value);
        Logger.i(this, value);
    }


    /*V1.1.0
    * 调用如下代码设置自定义的Tag。
        Fragment fragment = Fragment.newInstance();
        Rigger.getRigger(fragment).setFragmentTag("Custom tag");

    V1.1.1
    在需要自定义tag的Fragment中，添加如下方法：
      public String getFragmentTag(){
         return "Custom Tag";
        }
    * */
    @Override
    public String getFragmentTag() {
        return "HomeFragment"+ UUID.randomUUID().toString();
    }

}
