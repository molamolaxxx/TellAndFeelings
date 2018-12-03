package com.mola.tellandfeelings.control.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mola.tellandfeelings.R;
import com.mola.tellandfeelings.control.adapters.TellAdapter;
import com.mola.tellandfeelings.control.interfaces.TellInterface;
import com.mola.tellandfeelings.pojo.Setting;
import com.mola.tellandfeelings.pojo.Tell;
import com.mola.tellandfeelings.webmodel.service.TellRefresherService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by molamola
 * 存放tell的fragment
 *
 */
public class TellFragment extends Fragment implements TellInterface{
    /**
     * view
     */
    @BindView(R.id.tell_list)
    RecyclerView tellListView;

    private TellAdapter mTellAdapter;

    private List<Tell> tells;
    //上下文和activity
    private Context mContext;
    private Activity mActivity;
    //销毁时解绑BK
    private Unbinder unbinder;
    private Handler handler;
    public TellFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        //获得activity context
        mContext=context;
        mActivity=getActivity();
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTellList();
        //启动广播
        mActivity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //收到广播刷新
                addOne();
            }
        },new IntentFilter("com.mola.refreshList"));
        //启动服务
        Intent intent=new Intent(mContext, TellRefresherService.class);
        mActivity.startService(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_tell,container,false);
        //绑定BK，返回一个unbind对象
        unbinder= ButterKnife.bind(this,v);
        //初始化views
        initListViews();
        //设置add效果的animator
        initItemAnimator();
        return v;
    }
    //初始化telllist
    private void initListViews(){
        mTellAdapter=new TellAdapter(mContext,tells);
        tellListView.setAdapter(mTellAdapter);
        tellListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
    }
    //初始化tell数组
    private void initTellList(){
        tells=new ArrayList<>();
        for(int i=0;i<=Setting.getSettingInstance().getMaxTellNum();i++){
            tells.add(new Tell());
        }
    }
    //activity的接口回调函数
    @Override
    public void addOneItem() {
        addOne();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑BK
        unbinder.unbind();
    }
    private void initItemAnimator(){
        DefaultItemAnimator defaultItemAnimator=new DefaultItemAnimator();
        //设置出现时的时间
        defaultItemAnimator.setAddDuration(Setting.getSettingInstance().getShowTranslateTime());
        //设置移除时的时间
        defaultItemAnimator.setRemoveDuration(Setting.getSettingInstance().getShowTranslateTime());
        //给listview设置动画
        tellListView.setItemAnimator(defaultItemAnimator);
    }
    //判断有没有超出list最大限制

    /**
     * @return ture overflow
     * false 没有overflow
     */
    private boolean judgeIfOverflow(){
        if(tells.size()>Setting.getSettingInstance().getMaxTellNum())
            return true;
        else
            return false;
    }
    private void addOne(){
        tells.add(new Tell());
        mTellAdapter.notifyItemInserted(1);
        //判断是否超出list的设定最大长度
        if(judgeIfOverflow()) {
            //remove掉最后一条消息
            tells.remove(tells.size()-1);
            mTellAdapter.notifyItemRemoved(tells.size());
        }
    }
}
