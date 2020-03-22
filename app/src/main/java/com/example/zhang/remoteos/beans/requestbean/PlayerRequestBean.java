package com.example.zhang.remoteos.beans.requestbean;

import com.example.zhang.remoteos.utils.PlayActionEnum;

public class PlayerRequestBean extends RequestBaseBean{
    private int value;

    public PlayerRequestBean(PlayActionEnum action, int value) {
        super(action);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
