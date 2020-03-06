package com.example.zhang.remoteos.beans;

import com.example.zhang.remoteos.apps.media.PlayActionEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ResourceBean {
    private String name;
    private String fingerprint;
    private String type;
    private String size;

    private int id;

    public ResourceBean(){

    }

    public ResourceBean(String type) {
        this.type = type;
    }

    public ResourceBean(String type, String fingerprint) {
        this.fingerprint = fingerprint;
        this.type = type;
    }

    public ResourceBean(String name, String fingerprint, String type, int id, String size) {
        this.name = name;
        this.fingerprint = fingerprint;
        this.type = type;
        this.size = size;
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*
        playLocal,
        playTv,
        playSeek,

        list

        playCur,
        statusCheck,
     */
    public String toString(PlayActionEnum action) {
        String paramStr = "";
        switch (action){
            case list:
                String encode_path = new String("");
                if(fingerprint != null) {
                    try {
                        encode_path = URLEncoder.encode(fingerprint, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                paramStr = "&type=" + type + "&fingerprint=" + encode_path;
                break;
            case playTv:
                paramStr = "&fingerprint=" + fingerprint + "&type=" + type;
                break;
            case playLocal:
                encode_path = new String("");
                try {
                    encode_path = URLEncoder.encode(fingerprint, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                paramStr = "&fingerprint=" + encode_path + "&type=" + type;
                break;
            case playCur:
            case playSeek:
            case statusCheck:
                break;
        }
        return paramStr;
    }
}
