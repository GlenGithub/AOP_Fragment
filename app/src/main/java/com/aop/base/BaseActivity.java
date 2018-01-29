package com.aop.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aop.annotation.ContentView;
import com.jkb.fragment.rigger.rigger.Rigger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 借鉴：https://github.com/JustKiddingBaby/FragmentRigger/blob/master/README-CN.md
 * Created by glen1943 on 2018/1/27.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);
        load(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    @LayoutRes
    public int getLayoutId(){
        for (Class c = getClass(); c != Context.class; c = c.getSuperclass()) {
            ContentView annotation = (ContentView) c.getAnnotation(ContentView.class);
            if (annotation != null) {
                return annotation.value();
            }
        }
        return 0;
    }

    /*加载子类逻辑*/
    protected abstract void load(Bundle savedInstanceState);

    /*打印当前栈结构*/
    protected void printStack(){
        Rigger.getRigger(this).printStack();
    }

    /*结束当前*/
    protected void close(){
        Rigger.getRigger(this).close();
    }
}
