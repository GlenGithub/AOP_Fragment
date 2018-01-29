package com.aop.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aop.annotation.ContentView;
import com.jkb.fragment.rigger.rigger.Rigger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by glen1943 on 2018/1/27.
 */

public abstract class BaseFragment extends Fragment {
    private View rootView;//缓存Fragment view
    private Unbinder mBind;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView ;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBind = ButterKnife.bind(this,view);
        load(savedInstanceState);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind.unbind();
    }

    /*常规业务逻辑处理*/
    protected abstract void load(Bundle savedInstanceState);

    /*打印当前栈结构*/
    protected void printStack(){
        Rigger.getRigger(this).printStack();
    }

    /*结束当前*/
    protected void close(){
        Rigger.getRigger(this).close();
    }

    /*
       懒加载
    * 子类需要添加注解@LazyLoad，并重载此方法
    * */
    public void onLazyLoadViewCreated(Bundle savedInstanceState){

    }

    /*自定义Fragment tag
    * 对于同一个fragment多次初始化，需要区分tag，不然会报错；
    * */
    public String getFragmentTag(){
        return "Custom Tag";
    }

    /*onBackPressed拦截*/
    public void onRiggerBackPressed(){
        Rigger.getRigger(this).onBackPressed();
        //如果需要拦截返回键，不要写上行代码，上行代码可以手动调用onBackPressed方法。
    }
}
