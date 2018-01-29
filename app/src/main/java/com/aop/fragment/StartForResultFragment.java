package com.aop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.aop.annotation.ContentView;
import com.aop.base.BaseFragment;
import com.jkb.fragment.rigger.annotation.Puppet;
import com.jkb.fragment.rigger.rigger.Rigger;

import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

import static com.aop.fragment.HomeFragment.RESULT_CALLBACK;

/**
 * Created by Administrator on 2018/1/25.
 */
@ContentView(R.layout.fragment_result)
@Puppet
public class StartForResultFragment extends BaseFragment {

    public static StartForResultFragment newInstance() {
        Bundle args = new Bundle();
        StartForResultFragment fragment = new StartForResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void load(Bundle savedInstanceState) {

    }


    @OnClick({R.id.btn_setValue})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_setValue:
                Bundle args = new Bundle();
                args.putString(RESULT_CALLBACK, "编号9527");
                Rigger.getRigger(this).setResult(Rigger.RESULT_OK, args);
                Rigger.getRigger(this).close();
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
        return "StartForResultFragment-"+ UUID.randomUUID().toString();
    }
}
