package com.example.zhang.remoteos.utils;
import android.util.Log;

public enum ResourceTypeEnum{
    Unknown(0x0, "unknown"),
    Tv(0x1, "tv"),
    Dir(0x2, "dir"),
    File(0x4, "file"),
    Video(0x8, "video"),
    Music(0x10, "audio"),
    AllFile(0x20, "allFile"),
    OnlineVideo(0x40, "onlineVideo"),
    OnlineSearch(0x80, "onlineSearch");

    private int _value;
    private String _name;

    ResourceTypeEnum(int value, String name)
    {
        _value = value;
        _name = name;
    }

    public int value() {
        return _value;
    }

    public String getName() {
        return _name;
    }

    public static int multiType(ResourceTypeEnum... typeEnums){
        int multiValue = 0;
        for (ResourceTypeEnum ens : typeEnums) {
            multiValue |= ens.value();
        }
        return multiValue;
    }

    public static ResourceTypeEnum getType(int type){
        for(ResourceTypeEnum te: ResourceTypeEnum.values()){
            if ((te.value()&type) > 0){
                return te;
            }
        }
        return Unknown;
    }

    public static String getTypeString(int types){
        StringBuilder src = new StringBuilder();
        for(ResourceTypeEnum te: ResourceTypeEnum.values()){
            if ((te.value()&types) > 0){
                if(src.length() == 0)
                    src.append(te.getName());
                else
                    src.append("/").append(te.getName());
            }
        }

        Log.e("type string", src.toString());
        return src.toString();
    }
}