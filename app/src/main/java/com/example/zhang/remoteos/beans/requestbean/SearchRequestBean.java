package com.example.zhang.remoteos.beans.requestbean;

import com.example.zhang.remoteos.utils.PlayActionEnum;

public class SearchRequestBean extends RequestBaseBean {
    private String keyword;
    public SearchRequestBean(String keyword) {
        super(PlayActionEnum.search);
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
