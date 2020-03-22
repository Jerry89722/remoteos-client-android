package com.example.zhang.remoteos.beans;

import com.example.zhang.remoteos.utils.ResourceTypeEnum;

public class ResourceBaseBean {
    private int id;
    private String image_src;
    private String type;
    private String fingerprint;     // 资源唯一识别码
    private String name;
    private int typeInt;

    public ResourceBaseBean() {
    }

    public ResourceBaseBean(int typeInt) {
        this.typeInt = typeInt;
    }

    public ResourceBaseBean(int typeInt, String fingerprint) {
        this.fingerprint = fingerprint;
        this.typeInt = typeInt;
    }

    public ResourceBaseBean(int id, String image_src, String type, String fingerprint, String name) {
        this.id = id;
        this.image_src = image_src;
        this.type = type;
        this.fingerprint = fingerprint;
        this.name = name;
    }

    public ResourceBaseBean(int id, String type, String fingerprint, String name) {
        this.id = id;
        this.type = type;
        this.fingerprint = fingerprint;
        this.name = name;
    }

    public ResourceBaseBean(int id, String image_src, String type, String fingerprint, String name, int typeInt) {
        this.id = id;
        this.image_src = image_src;
        this.type = type;
        this.fingerprint = fingerprint;
        this.name = name;
        this.typeInt = typeInt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public String getType() {
        if(typeInt > 0){
            return ResourceTypeEnum.getTypeString(typeInt);
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(int typeInt) {
        this.typeInt = typeInt;
    }

}
