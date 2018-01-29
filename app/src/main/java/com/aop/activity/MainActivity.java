package com.aop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aop.annotation.ContentView;
import com.aop.base.BaseActivity;
import com.aop.fragment.HomeFragment;
import com.aop.fragment.R;
import com.aop.utils.ToastUtil;
import com.jkb.fragment.rigger.annotation.Puppet;
import com.jkb.fragment.rigger.rigger.Rigger;

/**
 * Created by glen1943 on 2018/1/24.
 * https://github.com/JustKiddingBaby/FragmentRigger/blob/master/README-CN.md
 * 在需要使用本框架的Activity/Fragment上添加@Puppet注解,
 * 但你项目的Fragment必须是android.support.v4.app.Fragment的子类，
 * 你的Activity也必须是android.support.v4.app.FragmentActivity的子类.
 *
 * V7 中的AppCompatActivity继承于V4
 *
 * containerViewId
 可选参数，此处需要传入fragment在添加时候占用的控件的id，
 作为fragment在添加的时候代替的位置。如果是0的话，fragment将不会代替任何位置.默认值是0.

 这个参数将会在Rigger#startFragment方法中被使用。
 *
 * bondContainerView
 可选参数，bondContainerView是boolean类型.

 如果值为true: 当被操纵的类(Activity/Fragment)中的栈底fragment和栈顶fragment是同一个元素并且该fragment被关闭的时候，
 这个寄主类也会被关闭，并且栈顶的fragment不会执行退出转场动画.
 如果值为false: 当寄主类内部的最后一个fragment出栈的时候，寄主类不会做任何动作.并且所有的转场动画都是正常执行的。
 这个参数将会在寄主的Activity#onBackPressed中被调用
 */

@ContentView(R.layout.activity_main)
@Puppet(containerViewId = R.id.main_root,bondContainerView = true)
public class MainActivity extends BaseActivity {
    @Override
    protected void load(Bundle savedInstanceState) {
        //开启log日志
        Rigger.enableDebugLogging(true);
        //显示HomeFragment UI
        if (savedInstanceState == null){
            Rigger.getRigger(this).startFragment(HomeFragment.newInstance());
        }
        //初始化Toast
        ToastUtil.init(this);
    }
}
