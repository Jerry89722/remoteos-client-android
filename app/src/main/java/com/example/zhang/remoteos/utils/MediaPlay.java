//package com.example.zhang.remoteos.utils;
//
//import com.example.zhang.remoteos.apps.media.PlayActionEnum;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//public class MediaPlay {
//    PlayActionEnum resourceType;
//    String which;
//
//    public MediaPlay(String which) {
//        this.which = which;
//    }
//
//    public MediaPlay(int arg){
//        this.resourceType = (PlayActionEnum)arg;
//    }
//
//    @Override
//    public String toString() {
//        String encode_which = new String("");
//        try {
//            encode_which = URLEncoder.encode(which, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        if (resourceType == PlayActionEnum.playNew) {
//            return "&path=" + encode_which;
//        }else if(resourceType == PlayActionEnum.playTv){
//            return "&id=" + encode_which;
//        }else {
//            return "";
//        }
//    }
//}
