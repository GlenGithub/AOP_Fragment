package com.aop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.aop.annotation.ContentView;
import com.aop.base.BaseFragment;
import com.jkb.fragment.rigger.annotation.Puppet;
import com.jkb.fragment.rigger.rigger.Rigger;

import java.util.Random;
import java.util.UUID;

import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/25.
 */
@ContentView(R.layout.fragment_replace)
@Puppet
public class ReplaceFragment extends BaseFragment {

    public static ReplaceFragment newInstance() {
        Bundle args = new Bundle();
        ReplaceFragment fragment = new ReplaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void load(Bundle savedInstanceState) {
        ContainerFragment fragment = ContainerFragment.newInstance(new Random().nextInt(5), false);
        Rigger.getRigger(this).replaceFragment(fragment, R.id.fr_content);
    }

    @OnClick({R.id.fr_content})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fr_content:
                Rigger.getRigger(this)
                        .replaceFragment(ContainerFragment.newInstance(new Random().nextInt(5), false), R.id.fr_content);
                break;
        }
    }

    /*
    注意，方式一的优先级高于方式二
    方式一 V1.1.0
    * 调用如下代码设置自定义的Tag。
        Fragment fragment = Fragment.newInstance();
        Rigger.getRigger(fragment).setFragmentTag("Custom tag");

    方式二 V1.1.1
    在需要自定义tag的Fragment中，添加如下方法：
      public String getFragmentTag(){
         return "Custom Tag";
        }
    * */
    @Override
    public String getFragmentTag() {
        return "ReplaceFragment-"+ UUID.randomUUID().toString();
    }
}
