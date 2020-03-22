package com.example.zhang.remoteos.apps.media;

import android.app.Activity;
import android.util.Log;

import com.example.zhang.remoteos.beans.requestbean.TelevisionRequestBean;
import com.example.zhang.remoteos.beans.ResourceBaseBean;
import com.example.zhang.remoteos.beans.responsebean.ResourcesResponseBean;
import com.example.zhang.remoteos.beans.requestbean.RequestBaseBean;
import com.example.zhang.remoteos.beans.responsebean.ResponseBaseBean;
import com.example.zhang.remoteos.utils.PlayActionEnum;

public class Television extends BaseMedia {
    public Television(Activity activity) {
        super(activity, new TelevisionRequestBean());
    }

    @Override
    public void itemClicked(ResourceBaseBean resourceBean) {
        RequestBaseBean requestTvBean = resourceToRequest(resourceBean);
        requestTvBean.setAction(PlayActionEnum.playTv);
        mediaCtrlRequest(requestTvBean);
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
        mediaRequest(requestBean, "explorer", "tv");
    }

    @Override
    public void resourceListHandle(ResponseBaseBean responseBean) {
        ResourcesResponseBean resourcesResponseBean = (ResourcesResponseBean) responseBean;
        mResources.clear();
        mResources.addAll(resourcesResponseBean.getList());
    }

    @Override
    public void searchListHandle(ResponseBaseBean responseBean) {

    }

    @Override
    public void searchRequest(RequestBaseBean requestBean) {

    }

}
