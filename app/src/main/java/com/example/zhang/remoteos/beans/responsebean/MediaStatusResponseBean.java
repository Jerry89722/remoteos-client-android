package com.example.zhang.remoteos.beans.responsebean;

import com.example.zhang.remoteos.utils.ROSUtils;

public class MediaStatusResponseBean extends ResponseBaseBean{
    private String uuid;
    private String status;
    private String name;
    private String total_time;
    private String cur_time;
    private String volume;

    public long getTimeAsSec(boolean isTotal) {
        if(isTotal)
            return Integer.valueOf(total_time).longValue();
        else
            return Integer.valueOf(cur_time).longValue();
    }

    public String getTotal_time() {
        return ROSUtils.secToTime(Integer.valueOf(total_time).longValue());
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getCur_time() {
        return ROSUtils.secToTime(Integer.valueOf(cur_time).longValue());
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setCur_time(String cur_time) {
        this.cur_time = cur_time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
