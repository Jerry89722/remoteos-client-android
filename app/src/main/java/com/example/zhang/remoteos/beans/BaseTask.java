package com.example.zhang.remoteos.beans;

import android.util.Log;

import com.example.zhang.remoteos.beans.requestbean.PlayerRequestBean;
import com.example.zhang.remoteos.beans.responsebean.OnlineVideoResponseBean;
import com.example.zhang.remoteos.beans.requestbean.RequestBaseBean;
import com.example.zhang.remoteos.beans.requestbean.SearchRequestBean;
import com.example.zhang.remoteos.beans.responsebean.ResourcesResponseBean;
import com.example.zhang.remoteos.beans.responsebean.SearchResponseBean;
import com.example.zhang.remoteos.utils.PlayActionEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

public abstract class BaseTask {
    private String uuid;
    private PlayActionEnum actionEnum;
    private RequestBaseBean resource;

    private String action;

    public BaseTask() {

    }
    public BaseTask(PlayActionEnum action, RequestBaseBean resourceBean) {
        this.uuid = uuidGen();
        this.actionEnum = action;
        this.resource = resourceBean;
        this.action = actionGet();
    }

    private String uuidGen(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    private String actionGet(){
        String act = "";
        switch (actionEnum){
            case list:
                act = "list";
                break;
            case playTv:
            case playCur:
            case playLocal:
            case playInternet:
                act = "play";
                break;
            case playSeek:
                act = "seek";
                break;
            case statusCheck:
                act = "status";
                break;
            case playStop:
                act = "stop";
                break;
            case volMute:
            case volHigh:
            case volLow:
                act = "volume";
                break;
            case search:
                act = "search";
                break;
            case info:
                act = "info";
                break;
        }
        return act;
    }

    @Override
    public String toString() {
        String paramStr = "?uuid=" + uuid + "&action=" + action;
        switch (actionEnum){
            case list:
            case playTv:
            case playLocal:
                paramStr = paramStr + resource.toString();
                break;
            case playInternet:
                paramStr = paramStr + "&channel=0" + "" + "&index=" + resource.getFingerprint();
                break;
            case info:
                paramStr = paramStr + "&index=" + resource.getFingerprint();
                break;
            case playSeek:
                PlayerRequestBean mediaSeekRequestBean = (PlayerRequestBean) resource;
                paramStr = paramStr + "&progress=" + mediaSeekRequestBean.getValue();
                break;
            case volHigh:
            case volLow:
            case volMute:
                PlayerRequestBean mediaVolRequestBean = (PlayerRequestBean) resource;
                paramStr = paramStr + "&volume=" + mediaVolRequestBean.getValue();
                break;
            case playCur:
            case statusCheck:
            case playStop:
                break;
            case search:
                SearchRequestBean searchRequestBean = (SearchRequestBean) resource;
                String encodeStr = new String("");
                try {
                    encodeStr = URLEncoder.encode(searchRequestBean.getKeyword(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                paramStr = paramStr + "&keyword=" + encodeStr;
                break;
        }
        Log.e("param string", paramStr);
        return  paramStr;
    }

    public void responseHandle(String response) {
        switch (actionEnum){
            case list:
                listResponseHandle(response, ResourcesResponseBean.class);
                break;
            case playTv:
            case playLocal:
            case playInternet:
            case playCur:
            case statusCheck:
            case playStop:
            case playSeek:
            case volHigh:
            case volLow:
            case volMute:
                mediaStatusResponseHandle(response);
                break;
            case info:
                listResponseHandle(response, OnlineVideoResponseBean.class);
                break;
            case search:
                searchResponseHandle(response, SearchResponseBean.class);
                break;
        }
    }

    public abstract <T> void listResponseHandle(String response, Class<T> clazz);

    public abstract void mediaStatusResponseHandle(String response);

    public abstract  <T> void searchResponseHandle(String response, Class<T> clazz);
}
