package com.example.zhang.remoteos.beans;

import com.example.zhang.remoteos.apps.media.PlayActionEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ResourceBean {
    private String name;
    private String path;
    private String type;
    private int id;
    private String size;

    public ResourceBean(){

    }

    public ResourceBean(String type, String path) {
        this.path = path;
        this.type = type;
    }

    public ResourceBean(String name, String path, String type, int id, String size) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.id = id;
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        playNew,
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
                try {
                    encode_path = URLEncoder.encode(path, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                paramStr = "&type=" + type + "&path=" + encode_path;
                break;
            case playTv:
                paramStr = "&id=" + id;
                break;
            case playNew:
                encode_path = new String("");
                try {
                    encode_path = URLEncoder.encode(path, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                paramStr = "&path=" + encode_path;
                break;
            case playCur:
            case playSeek:
            case statusCheck:
                break;
        }
        return paramStr;
    }
}
