package com.aop.fragment;

import android.os.Bundle;
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

@ContentView(R.layout.fragment_show)
@Puppet
public class ShowFragment extends BaseFragment {


    public static ShowFragment newInstance() {
        Bundle args = new Bundle();
        ShowFragment fragment = new ShowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.fl_content1,R.id.fl_content2,R.id.fl_content3,R.id.fl_content4})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.fl_content1:
                Rigger.getRigger(this)
                    .replaceFragment(ContainerFragment.newInstance(new Random().nextInt(5), true), R.id.fl_content1);
                break;
            case R.id.fl_content2:
                Rigger.getRigger(this)
                        .replaceFragment(ContainerFragment.newInstance(new Random().nextInt(5), true), R.id.fl_content2);
                break;
            case R.id.fl_content3:
                Rigger.getRigger(this)
                        .replaceFragment(ContainerFragment.newInstance(new Random().nextInt(5), false), R.id.fl_content3);
                break;
            case R.id.fl_content4:
                Rigger.getRigger(this)
                        .replaceFragment(ContainerFragment.newInstance(new Random().nextInt(5), false), R.id.fl_content4);
                break;
        }
    }

    @Override
    protected void load(Bundle savedInstanceState) {
        Rigger.getRigger(this)
                .replaceFragment(ContainerFragment.newInstance(new Random().nextInt(5), true), R.id.fl_content1);
        Rigger.getRigger(this)
                .replaceFragment(ContainerFragment.newInstance(new Random().nextInt(5), true), R.id.fl_content2);
        Rigger.getRigger(this)
                .replaceFragment(ContainerFragment.newInstance(new Random().nextInt(5), false), R.id.fl_content3);
        Rigger.getRigger(this)
                .replaceFragment(ContainerFragment.newInstance(new Random().nextInt(5), false), R.id.fl_content4);
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
        return "ShowFragment-" + UUID.randomUUID().toString();
    }
}
