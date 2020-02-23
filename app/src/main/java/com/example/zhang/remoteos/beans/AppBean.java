package com.example.zhang.remoteos.beans;

import android.content.Context;

public class AppBean {
    int icon;
    String name;
    Class cls;

    public AppBean(int icon, String name, Class cls) {
        this.icon = icon;
        this.name = name;
        this.cls = cls;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void abAccess(Context context, Class cls){
//        Intent intent = new Intent(context, cls);
//        startActivity(intent);
    }
}
