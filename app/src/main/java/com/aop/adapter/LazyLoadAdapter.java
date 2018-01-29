package com.aop.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aop.base.BaseFragment;
import com.aop.fragment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class LazyLoadAdapter extends FragmentPagerAdapter{

    private Context mContext;

    private int[] tabTextValues = new int[]{
            R.string.wechat, R.string.contacts,
            R.string.discover, R.string.me
    };
    private int[] tabDrawables = new int[]{
            R.drawable.selector_ic_tab_chat, R.drawable.selector_ic_tab_contacts,
            R.drawable.selector_ic_tab_discover, R.drawable.selector_ic_tab_me
    };
    private List<View> tabViews;
    private List<BaseFragment> fragments;

    public LazyLoadAdapter(Context context, FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.mContext = context;
        this.fragments = fragments;
        tabViews = new ArrayList<>();
        for (int i = 0; i < tabDrawables.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_lazy_tab, null);
            view.findViewById(R.id.ilt_iv).setBackgroundResource(tabDrawables[i]);
            ((TextView) view.findViewById(R.id.ilt_tv)).setText(getPageTitle(i));
            tabViews.add(view);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(tabTextValues[position]);
    }

    public View getTabView(int position) {
        return tabViews.get(position);
    }

    /**
     * Select tab and update ui.
     */
    public void selectedTab(int position) {
        for (int i = 0; i < tabViews.size(); i++) {
            View view = tabViews.get(i);
            view.findViewById(R.id.ilt_iv).setSelected(false);
            view.findViewById(R.id.ilt_content).setAlpha(0.5f);
            ((TextView) view.findViewById(R.id.ilt_tv))
                    .setTextColor(ContextCompat.getColor(mContext, R.color.tab_normal));
        }
        View view = tabViews.get(position);
        view.findViewById(R.id.ilt_iv).setSelected(true);
        view.findViewById(R.id.ilt_content).setAlpha(1.0f);
        ((TextView) view.findViewById(R.id.ilt_tv))
                .setTextColor(ContextCompat.getColor(mContext, R.color.tab_selected));
    }
}
