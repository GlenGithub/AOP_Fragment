package com.aop.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aop.annotation.ContentView;
import com.aop.base.BaseFragment;
import com.aop.utils.AnimationHelper;
import com.jkb.fragment.rigger.annotation.LazyLoad;
import com.jkb.fragment.rigger.annotation.Puppet;
import com.jkb.fragment.rigger.utils.Logger;

import java.util.UUID;

import butterknife.BindView;

@ContentView(R.layout.fragment_content)
@LazyLoad
@Puppet
public class ContainerFragment extends BaseFragment {

  @BindView(R.id.rl_content)
  RelativeLayout rl_content;

  @BindView(R.id.iv_icon)
  ImageView iv_icon;

  protected static final String BUNDLE_KEY = "/bundle/key";

  public static ContainerFragment newInstance(int value, boolean customAnim) {
    Bundle args = new Bundle();
    args.putInt(BUNDLE_KEY, value);
    args.putBoolean(BUNDLE_KEY + 1, customAnim);
    ContainerFragment fragment = new ContainerFragment();
    fragment.setArguments(args);
    return fragment;
  }

  private int position;
  private boolean customAnim;
  private int[] icons = new int[]{
      R.mipmap.heart, R.mipmap.block, R.mipmap.motorcycle, R.mipmap.bear, R.mipmap.content_cloud
  };
  private int[] colors = new int[]{
      R.color.bg_heart, R.color.bg_block, R.color.bg_motorcycle, R.color.bg_bear, R.color.bg_cloud
  };

  @Override
  protected void load(Bundle savedInstanceState) {
    Logger.d(this, "init isUserHintVisible=" + getUserVisibleHint());
    Bundle args = savedInstanceState == null ? getArguments() : savedInstanceState;
    position = args.getInt(BUNDLE_KEY);
    customAnim = args.getBoolean(BUNDLE_KEY + 1);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(BUNDLE_KEY, position);
    outState.putBoolean(BUNDLE_KEY + 1, customAnim);
  }

  @Override
  public void onLazyLoadViewCreated(Bundle savedInstanceState) {
    Logger.d(this, "onLazyLoadViewCreated()");
    iv_icon.setImageResource(icons[position % icons.length]);
    rl_content.setBackgroundColor(ContextCompat.getColor(mContext, colors[position % icons.length]));
  }

  public int[] getPuppetAnimations() {
    return new int[]{
        R.anim.push_left_in_no_alpha,
        R.anim.push_right_out_no_alpha,
        R.anim.push_right_in_no_alpha,
        R.anim.push_left_out_no_alpha
    };
  }

  @Override
  public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
    if (customAnim) return super.onCreateAnimation(transit, enter, nextAnim);
    if (enter) {
      return AnimationHelper.createRotate3dEnterAnimation();
    } else {
      return AnimationHelper.createRotate3dExitAnimation();
    }
  }

  public void onRiggerBackPressed() {
    Logger.i(this, position + "::onRiggerBackPressed");
  }

  @Override
  public String getFragmentTag() {
    return "ContainerFragment-"+ UUID.randomUUID().toString();
  }
}
