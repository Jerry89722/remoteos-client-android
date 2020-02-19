package com.example.zhang.remoteos.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ROSUtils {
    public static String secToTime(long secs){
        String pattern;
        if(secs >= 3600){
            pattern = "hh:mm:ss";
        }else {
            pattern = "mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(secs * 1000)); // Date的时间单位是ms
    }
}
