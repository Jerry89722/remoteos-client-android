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
import com.example.zhang.remoteos.beans.ResourceBaseBean;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder> {

    private Context context;
    private List<ResourceBaseBean> mDatas;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public ResourceAdapter(Context context, List<ResourceBaseBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.resource_list_item_content, viewGroup,false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder resourceViewHolder, final int i) {
        ResourceBaseBean resourceBean = mDatas.get(i);

        resourceViewHolder.idTv.setText(String.valueOf(resourceBean.getId()));
        resourceViewHolder.nameTv.setText(resourceBean.getName());

        switch (resourceBean.getType()) {
            case "file":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.ic_file);
                break;
            case "video":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.ic_video);
                break;
            case "dir":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.ic_dir);
                break;
            case "audio":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.ic_music);
                break;
            case "tv":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.icon_tv);
                break;
            case "onlineVideo":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.icon_online);
                break;
            case "onlineSearch":
                resourceViewHolder.iconIv.setImageResource(R.mipmap.icon_search);
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

    class ResourceViewHolder extends RecyclerView.ViewHolder{

        TextView idTv, nameTv;
        ImageView iconIv;
        ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            idTv = itemView.findViewById(R.id.resource_item_num);
            nameTv = itemView.findViewById(R.id.resource_item_name);
            iconIv = itemView.findViewById(R.id.resource_item_icon);
        }
    }
}
