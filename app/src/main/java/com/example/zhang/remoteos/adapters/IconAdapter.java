package com.example.zhang.remoteos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhang.remoteos.R;
import com.example.zhang.remoteos.beans.AppBean;

import java.util.List;

public class IconAdapter extends BaseAdapter{
    private Context context;
    private List<AppBean> mdatas;

    OnItemClickListener onItemClickListener;

    public IconAdapter(Context context, List<AppBean> mdatas) {
        this.context = context;
        this.mdatas = mdatas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }
    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mdatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View v, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_item_content, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        AppBean ib = mdatas.get(i);

        viewHolder.iv.setImageResource(R.mipmap.ic_launcher);
        viewHolder.tv.setText(ib.getName());
        viewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, i);
            }
        });

        return view;
    }

    class ViewHolder<T>{
        TextView tv;
        ImageView iv;
        Class<T> mClass;

        public ViewHolder(View view) {
            iv = view.findViewById(R.id.app_item_icon);
            tv = view.findViewById(R.id.app_item_text);
        }
    }
}
