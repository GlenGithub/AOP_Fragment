package com.aop.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aop.annotation.ContentView;
import com.aop.base.BaseFragment;
import com.jkb.fragment.rigger.annotation.Animator;
import com.jkb.fragment.rigger.annotation.Puppet;
import com.jkb.fragment.rigger.rigger.Rigger;

import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/25.
 * 在需要支持的Fragment中添加注解@Animator。
 * 该注解有四个参数:enter/exit/popEnter/popExit
 * 分别对应自身进入动画/自身退出动画/栈顶元素进入动画/栈顶元素退出动画。
 * 自定义Xml动画
 */
@ContentView(R.layout.fragment_start)
@Animator(enter = R.anim.push_left_in_no_alpha, exit = R.anim.push_right_out_no_alpha,
        popEnter = R.anim.push_right_in_no_alpha, popExit = R.anim.push_left_out_no_alpha)
@Puppet(containerViewId = R.id.fl_content)
public class StartFragment extends BaseFragment {

    @BindView(R.id.tv_index)
    TextView tv_index;

    protected static final String BUNDLE_KEY = "key";
    private int mIndex;

    public static StartFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY, index);
        StartFragment fragment = new StartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_KEY, mIndex);
    }

    @Override
    protected void load(Bundle savedInstanceState) {
        Bundle args = savedInstanceState == null ? getArguments() : savedInstanceState;
        mIndex = args.getInt(BUNDLE_KEY);
        tv_index.setText(String.valueOf(mIndex));
    }

    @OnClick({R.id.btn_startFragment,R.id.btn_print,R.id.btn_close})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_startFragment:
                Rigger.getRigger(this).startFragment(StartFragment.newInstance(mIndex + 1));
                break;
            case R.id.btn_print:
                printStack();
                break;
            case R.id.btn_close:
                close();
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
        return "StartFragment-"+ UUID.randomUUID().toString();
    }
}
