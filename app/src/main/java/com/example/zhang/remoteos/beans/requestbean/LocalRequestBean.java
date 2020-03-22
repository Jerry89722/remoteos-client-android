package com.example.zhang.remoteos.beans.requestbean;

import com.example.zhang.remoteos.utils.PlayActionEnum;

public class LocalRequestBean extends RequestBaseBean {
    String path;

    public LocalRequestBean(String path) {
        super("video", path, PlayActionEnum.list);
        this.path = path;
    }
}
