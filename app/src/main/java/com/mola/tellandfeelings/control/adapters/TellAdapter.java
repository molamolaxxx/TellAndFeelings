package com.mola.tellandfeelings.control.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mola.tellandfeelings.R;
import com.mola.tellandfeelings.pojo.Tell;

import java.util.List;

public class TellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * @param mtellList 保存tell的list
     */
    private Context mContext;
    private List<Tell> mTellList;
    public static final int VIEW_HEAD=0;
    public static final int VIEW_NORMAL=1;
    public TellAdapter(Context context,List<Tell> mTellList) {
        this.mContext=context;
        this.mTellList=mTellList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEW_HEAD)
            return new TellViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tell_header, parent, false),VIEW_HEAD);
        else
            return new TellViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tell_item, parent, false),VIEW_NORMAL);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTellList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return VIEW_HEAD;
        else
            return VIEW_NORMAL;
    }

    class TellViewHolder extends RecyclerView.ViewHolder{
        TextView authorTextView;
        TextView contentTextView;
        public TellViewHolder(View v,int viewType){
            super(v);
            if(viewType==VIEW_HEAD)
                return;

        }
    }
}
