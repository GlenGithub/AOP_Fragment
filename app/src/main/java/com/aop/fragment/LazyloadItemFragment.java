package com.aop.fragment;

import android.os.Bundle;

import com.aop.annotation.ContentView;
import com.aop.base.BaseFragment;
import com.jkb.fragment.rigger.annotation.LazyLoad;
import com.jkb.fragment.rigger.annotation.Puppet;
import com.jkb.fragment.rigger.utils.Logger;

import java.util.UUID;

/**
 * Created by Administrator on 2018/1/29.
 */

@ContentView(R.layout.fragment_item_lazyload)
@LazyLoad(false)
@Puppet
public class LazyloadItemFragment extends BaseFragment {

    public static LazyloadItemFragment newInstance() {
        Bundle args = new Bundle();
        LazyloadItemFragment fragment = new LazyloadItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void load(Bundle savedInstanceState) {
        Logger.d(this,"load()");
        /*ViewPager预加载机制，所有fragment都会执行*/
    }

    @Override
    public void onLazyLoadViewCreated(Bundle savedInstanceState) {
         /*懒加载，只有需要初始化时才会被调用*/
        //do something in here
        Logger.d(this,"onLazyLoadViewCreated()");
    }

    @Override
    public String getFragmentTag() {
        return "LazyloadItemFragment-"+ UUID.randomUUID().toString();
    }
}
