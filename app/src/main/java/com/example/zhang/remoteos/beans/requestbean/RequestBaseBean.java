package com.example.zhang.remoteos.beans.requestbean;

import android.util.Log;

import com.example.zhang.remoteos.utils.PlayActionEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RequestBaseBean {
    private PlayActionEnum action;
    private String type;
    private String fingerprint;

    public RequestBaseBean() {
    }

    public RequestBaseBean(PlayActionEnum action) {
        this.action = action;
    }
    public RequestBaseBean(String type, PlayActionEnum action) {
        this.type = type;
        this.action = action;
    }

    public RequestBaseBean(String type, String fingerprint) {
        this.type = type;
        this.fingerprint = fingerprint;
    }

    public RequestBaseBean(String type, String fingerprint, PlayActionEnum action) {
        this.type = type;
        this.fingerprint = fingerprint;
        this.action = action;
    }

    public PlayActionEnum getAction() {
        return action;
    }

    @Override
    public String toString(){
        Log.e("RequestBaseBean", "enter to string");

        String encode_path = new String("");
        if(fingerprint != null) {
            try {
                encode_path = URLEncoder.encode(fingerprint, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "&type=" + type + "&fingerprint=" + encode_path;
    }

    public void setAction(PlayActionEnum action) {
        this.action = action;
    }

    public String getFingerprint() {
        return fingerprint;
    }
}
