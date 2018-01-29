package com.aop.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.aop.adapter.LazyLoadAdapter;
import com.aop.annotation.ContentView;
import com.aop.base.BaseFragment;
import com.jkb.fragment.rigger.annotation.Puppet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/25.
 */

@ContentView(R.layout.fragment_lazyload)
@Puppet
public class LazyLoadFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private LazyLoadAdapter adapter;
    private List<BaseFragment> fragments = new ArrayList<>();

    private int mSelectedPosition = 0;
    protected static final String BUNDLE_KEY = "/bundle/key";

    public static LazyLoadFragment newInstance() {
        Bundle args = new Bundle();
        LazyLoadFragment fragment = new LazyLoadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_KEY, mSelectedPosition);
    }

    @Override
    protected void load(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mSelectedPosition = savedInstanceState.getInt(BUNDLE_KEY);
        }

        fragments.clear();
        fragments.add(LazyloadItemFragment.newInstance());
        fragments.add(LazyloadItemFragment.newInstance());
        fragments.add(LazyloadItemFragment.newInstance());
        fragments.add(LazyloadItemFragment.newInstance());

        adapter = new LazyLoadAdapter(getActivity(), getChildFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab == null) continue;
            tab.setCustomView(adapter.getTabView(i));
        }
        adapter.selectedTab(mSelectedPosition);
        initListener();
    }

    private void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mSelectedPosition = tabLayout.getSelectedTabPosition();
                adapter.selectedTab(mSelectedPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        return "LazyLoadFragment"+ UUID.randomUUID().toString();
    }


}
