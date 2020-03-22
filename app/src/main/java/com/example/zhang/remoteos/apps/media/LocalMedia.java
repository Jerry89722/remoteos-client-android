package com.example.zhang.remoteos.apps.media;

import android.app.Activity;
import android.util.Log;

import com.example.zhang.remoteos.beans.requestbean.LocalRequestBean;
import com.example.zhang.remoteos.beans.ResourceBaseBean;
import com.example.zhang.remoteos.beans.responsebean.ResourcesResponseBean;
import com.example.zhang.remoteos.beans.requestbean.RequestBaseBean;
import com.example.zhang.remoteos.beans.responsebean.ResponseBaseBean;
import com.example.zhang.remoteos.utils.PlayActionEnum;

public class LocalMedia extends BaseMedia {

    public LocalMedia(Activity activity) {
        super(activity, new LocalRequestBean("/"));
    }

    @Override
    public void resourceListHandle(ResponseBaseBean responseBaseBean){
        ResourcesResponseBean resourcesResponseBean = (ResourcesResponseBean) responseBaseBean;
        mResources.clear();
        mResources.addAll(resourcesResponseBean.getList());
    }

    @Override
    public void searchListHandle(ResponseBaseBean responseBean) {

    }

    @Override
    public void searchRequest(RequestBaseBean requestBean) {
        Log.e("search", "local");
    }

    @Override
    public boolean goBack(){
        String curPath = requestBean.getFingerprint();
        if(curPath.equals("/")) {
            return false;
        }else {

            if(curPath.charAt(curPath.length() - 1) == '/') {
                curPath = curPath.substring(0, curPath.lastIndexOf('/', curPath.length()-2)+1);
            }else{
                curPath = curPath.substring(0, curPath.lastIndexOf('/') + 1);
            }
            Log.e("last path", curPath);

            listRequest(new LocalRequestBean(curPath));
            return true;
        }
    }

    @Override
    public void itemClicked(ResourceBaseBean resourceBaseBean) {
        RequestBaseBean requestBean = resourceToRequest(resourceBaseBean);
        switch (resourceBaseBean.getType()) {
            case "dir":
                requestBean.setAction(PlayActionEnum.list);
                listRequest(requestBean);
                break;
            case "video":
            case "audio":
                requestBean.setAction(PlayActionEnum.playLocal);
                mediaCtrlRequest(requestBean);
                break;
            default:
                break;

        }
    }
}
