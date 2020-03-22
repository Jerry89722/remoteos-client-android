package com.example.zhang.remoteos.beans.responsebean;

import com.example.zhang.remoteos.beans.ResourceBaseBean;

import java.util.List;

public class ResourcesResponseBean extends ResponseBaseBean{
    private String uuid;
    private List<ResourceBaseBean> list;

    public ResourcesResponseBean(){}

    public ResourcesResponseBean(String uuid, List<ResourceBaseBean> list) {
        this.uuid = uuid;
        this.list = list;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public  List<ResourceBaseBean> getList() {
        return list;
    }

    public void setList(List<ResourceBaseBean> list) {
        this.list = list;
    }
}
