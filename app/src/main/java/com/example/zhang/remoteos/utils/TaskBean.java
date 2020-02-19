package com.example.zhang.remoteos.utils;

import android.util.Log;

import com.example.zhang.remoteos.apps.media.PlayActionEnum;

import java.util.UUID;

public class TaskBean {
    private String uuid;
    private PlayActionEnum actionEnum;
    private int progress;
    private ResourceBean resource;

    private String action;

    public TaskBean(){
    }

    public TaskBean(PlayActionEnum action, ResourceBean resourceBean) {
        String uuid = UUID.randomUUID().toString();
        this.uuid = uuid.replaceAll("-", "");
        this.actionEnum = action;
        this.resource = resourceBean;
        this.action = action_get();
    }

    public TaskBean(PlayActionEnum action, ResourceBean resourceBean, int progress) {
        String uuid = UUID.randomUUID().toString();
        this.uuid = uuid.replaceAll("-", "");
        this.actionEnum = action;
        this.progress = progress;
        this.resource = resourceBean;
        this.action = action_get();
    }

    private String action_get(){
        String act = "";
        switch (actionEnum){
            case list:
                act = "list";
                break;
            case playTv:
            case playCur:
            case playNew:
                act = "play";
                break;
            case playSeek:
                act = "seek";
                break;
            case statusCheck:
                act = "status";
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
            case playNew:
                paramStr = paramStr + resource.toString(actionEnum);
                break;
            case playSeek:
                paramStr = paramStr + "&progress=" + progress;
                break;
            case playCur:
            case statusCheck:
                break;
        }
        Log.e("param string", paramStr);
        return  paramStr;
    }
}
