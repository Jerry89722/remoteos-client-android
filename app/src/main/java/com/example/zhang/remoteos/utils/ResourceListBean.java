package com.example.zhang.remoteos.utils;

import java.util.List;

public class ResourceListBean {
    String uuid;
    List<ResourceBean> list;

    public ResourceListBean() {
    }

    public ResourceListBean(String uuid, List<ResourceBean> list) {
        this.uuid = uuid;
        this.list = list;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public  List<ResourceBean> getList() {
        return list;
    }

    public void setList(List<ResourceBean> list) {
        this.list = list;
    }
}
