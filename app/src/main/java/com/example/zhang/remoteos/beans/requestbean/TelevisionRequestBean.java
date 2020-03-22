package com.example.zhang.remoteos.beans.requestbean;

import com.example.zhang.remoteos.utils.PlayActionEnum;

public class TelevisionRequestBean extends RequestBaseBean {

    public TelevisionRequestBean() {
        super("tv", PlayActionEnum.list);
    }
}
