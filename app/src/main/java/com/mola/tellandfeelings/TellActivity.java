package com.mola.tellandfeelings;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mola.tellandfeelings.control.adapters.MyFragmentAdapter;
import com.mola.tellandfeelings.control.fragments.FeelingFragment;
import com.mola.tellandfeelings.control.fragments.TellFragment;
import com.mola.tellandfeelings.control.interfaces.TellInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TellActivity extends AppCompatActivity{
    /**
     * view
     */
    //获得appbar
    @BindView(R.id.appbar)
    AppBarLayout mAppBar;
    //最上的fab
    @BindView(R.id.tell_fab_up)
    FloatingActionButton tellFabUp;
    //中间的fab
    @BindView(R.id.tell_fab_down)
    FloatingActionButton tellFabDown;
    //最下的fab
    @BindView(R.id.tell_fab_middle)
    FloatingActionButton tellFabMid;
    //装载fragment的vp
    @BindView(R.id.myFragmentViewPager)
    ViewPager mFragmentViewPager;


    private List<Fragment> myFragment;
    private MyFragmentAdapter myFragmentAdapter;
    private static final String TAG = "TellActivity";
    //关闭appbar的监听比率
    private static final float CLOSE_RATE=4.32f;
    //interfaces
    private TellInterface tellInterface;
    //当前所在page
    private int page=1;
    //是否切换完毕后没有了操作,屏蔽flag
    private boolean changeState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell);
        //初始化BK
        ButterKnife.bind(this);
        //初始化view
        initViews();
        //初始化fragment
        initFragment();
    }
    //初始化fragment和adapter
    private void initFragment(){
        //fragment条目
        myFragment=new ArrayList<>();
        myFragment.add(new TellFragment());
        myFragment.add(new FeelingFragment());
        //初始化adapter
        myFragmentAdapter=new MyFragmentAdapter(getSupportFragmentManager(),myFragment);
        mFragmentViewPager.setAdapter(myFragmentAdapter);
        mFragmentViewPager.setOffscreenPageLimit(2);
        //获得listener
        tellInterface=(TellInterface) myFragment.get(0);
    }
    private void initViews(){
        //给页面设置工具栏
        final android.support.v7.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置工具栏标题
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("tell your story");
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //差距值
                int abs=Math.abs(getSupportActionBar().getHeight()-appBarLayout.getHeight()-verticalOffset);
                //总值
                int total=Math.abs(getSupportActionBar().getHeight()-appBarLayout.getHeight());
                if(abs<total/CLOSE_RATE){
                    if(page==1&&!changeState)
                        tellFabDown.show();
                }
                else if(page==1&&!changeState)
                        tellFabDown.hide();
                else
                    changeState=false;

            }
        });
        tellFabDown.hide();
        tellFabMid.hide();
        //初始化viewpager
        mFragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //记录滑动方向的标准
            float markOffset=0.0f;
            boolean isExpand=true;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //如果状态为初始，返回
                if(positionOffset==0) return;
                //设置专场动画
                if(positionOffset-markOffset>0&&positionOffset>0.5f&&isExpand) {
                    // to page 2
                    mAppBar.setExpanded(false);
                    tellFabMid.show();
                    tellFabDown.hide();
                    page=2;
                    changeState=true;
                    isExpand=false;
                }
                else if(positionOffset-markOffset<0&&positionOffset<0.5f&&!isExpand) {
                    // to page 1
                    mAppBar.setExpanded(true);
                    tellFabMid.hide();
                    page=1;
                    changeState=true;
                    isExpand=true;
                }
                markOffset=positionOffset;
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println(state);
            }
        });
    }
   //使用bk的注解注入
   @OnClick({R.id.tell_fab_up,R.id.tell_fab_down})
   public void onClickTellFabUp(View v){
       switch (v.getId()) {
           case R.id.tell_fab_up: {
               tellInterface.addOneItem();
               break;
           }
           case R.id.tell_fab_down: {
               tellInterface.addOneItem();
               break;
           }
       }
   }
}
