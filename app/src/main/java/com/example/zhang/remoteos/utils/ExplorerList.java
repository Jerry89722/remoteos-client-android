package com.example.zhang.remoteos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ExplorerList {
    private String type;
    private String path;

    public ExplorerList(String type, String path) {
        this.type = type;
        this.path = path;
    }

    @Override
    public String toString() {
        String encode_path = new String("");
        try {
            encode_path = URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "&type=" + type + "&path=" + encode_path;
    }
}
