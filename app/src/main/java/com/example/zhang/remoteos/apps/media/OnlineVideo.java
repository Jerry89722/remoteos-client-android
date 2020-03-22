package com.example.zhang.remoteos.apps.media;

import android.app.Activity;
import android.util.Log;

import com.example.zhang.remoteos.beans.requestbean.SearchRequestBean;
import com.example.zhang.remoteos.beans.ResourceBaseBean;

import com.example.zhang.remoteos.beans.requestbean.RequestBaseBean;
import com.example.zhang.remoteos.beans.responsebean.OnlineVideoResponseBean;
import com.example.zhang.remoteos.beans.responsebean.ResponseBaseBean;

import com.example.zhang.remoteos.beans.responsebean.SearchResponseBean;
import com.example.zhang.remoteos.utils.PlayActionEnum;

import java.util.ArrayList;
import java.util.List;

public class OnlineVideo extends BaseMedia {
    String current;
    public OnlineVideo(Activity activity) {
        super(activity);
        current = "onlineSearch";
    }

    @Override
    public void itemClicked(ResourceBaseBean resourceBean) {
        /* 用于项目的点击处理 */
        RequestBaseBean requestBaseBean = resourceToRequest(resourceBean);
        if(resourceBean.getType().equals("onlineSearch")) {
            requestBaseBean.setAction(PlayActionEnum.info);
            listRequest(requestBaseBean);
        }else if(resourceBean.getType().equals("onlineVideo")){
            requestBaseBean.setAction(PlayActionEnum.playInternet);
            OnlineVideoSwitchRequest(requestBaseBean);
        }
    }

    @Override // false 退出, true 返回
    public boolean goBack(){
        if(current.equals("onlineSearch")){
            return false;
        }else {
            SearchRequestBean searchRequestBean = (SearchRequestBean) requestBean;
            searchRequest(new SearchRequestBean(searchRequestBean.getKeyword()));
            return true;
        }
    }

    private void OnlineVideoSwitchRequest(RequestBaseBean requestBean){
        mediaRequest(requestBean, "explorer", "internet");
    }

    @Override
    public void listRequest(RequestBaseBean requestBean) {
        if(requestBean == null && this.requestBean == null){
            Log.e("list request", "request bean is null");
            return;
        }
        if(requestBean == null) {
            requestBean = this.requestBean;
        }
        mediaRequest(requestBean, "explorer", "internet");
    }

    @Override
    public void searchRequest(RequestBaseBean requestBaseBean){
        requestBean = requestBaseBean;
        mediaRequest(requestBaseBean, "explorer", "internet");
    }

    @Override
    public void resourceListHandle(ResponseBaseBean responseBean){
        if(responseBean == null)
            return;
        OnlineVideoResponseBean onlineVideoResponseBean = (OnlineVideoResponseBean) responseBean;
        List<ResourceBaseBean> resourceList = new ArrayList<>();

        for(int i= 0; i < onlineVideoResponseBean.getTitles().size(); ++i){
            // int id, String type, String fingerprint, String name
            ResourceBaseBean resourceBaseBean = new ResourceBaseBean(
                    i + 1,
                    "onlineVideo",
                    String.valueOf(i),
                    onlineVideoResponseBean.getMain_title() + "-" + onlineVideoResponseBean.getTitles().get(i));
            resourceList.add(resourceBaseBean);
        }

        mResources.clear();
        mResources.addAll(resourceList);
        current = "onlineVideo";
    }

    @Override
    public void searchListHandle(ResponseBaseBean responseBean) {
        if(responseBean == null)
            return;
        SearchResponseBean searchResponseBean = (SearchResponseBean) responseBean;
        List<ResourceBaseBean> resourceList = new ArrayList<>();

        for(int i= 0; i < searchResponseBean.getTitles().size(); ++i){
            // int id, String image_src, String type, String fingerprint, String name
            ResourceBaseBean resourceBaseBean = new ResourceBaseBean(
                    i + 1,
                    searchResponseBean.getCovers().get(i),
                    "onlineSearch",
                    String.valueOf(i),
                    searchResponseBean.getTitles().get(i) + "-" + searchResponseBean.getStatus().get(i));
            resourceList.add(resourceBaseBean);
        }

        mResources.clear();
        mResources.addAll(resourceList);
        current = "onlineSearch";
    }

}
