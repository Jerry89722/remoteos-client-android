package com.example.zhang.remoteos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhang.remoteos.R;
import com.example.zhang.remoteos.beans.ResourceBean;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    Context context;
    List<ResourceBean> mDatas;

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public FileAdapter(Context context, List<ResourceBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.file_list_item_content, viewGroup,false);
        FileViewHolder holder = new FileViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder resourceViewHolder, final int i) {
        ResourceBean resourceBean = mDatas.get(i);

        resourceViewHolder.idTv.setText("" + resourceBean.getId());
        resourceViewHolder.nameTv.setText(resourceBean.getName());

        switch (resourceBean.getType()) {
            case "video":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.ic_video);
                break;
            case "dir":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.ic_dir);
                break;
            case "music":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.ic_music);
                break;
            case "tv":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.icon_tv);
                break;
            default:
                resourceViewHolder.iconIv.setImageResource(R.mipmap.ic_launcher);
                break;
        }
        resourceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder{

        TextView idTv, nameTv;
        ImageView iconIv;
        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            idTv = itemView.findViewById(R.id.file_item_num);
            nameTv = itemView.findViewById(R.id.file_item_name);
            iconIv = itemView.findViewById(R.id.file_item_icon);
        }
    }
}
