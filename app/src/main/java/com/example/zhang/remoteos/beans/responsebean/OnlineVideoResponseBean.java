package com.example.zhang.remoteos.beans.responsebean;

import java.util.List;

// 列表页面数据
public class OnlineVideoResponseBean extends ResponseBaseBean{
    private String main_title;
    private String num;        // 用于标记在Search 结果中的哪一项
    private List<String> titles;
    private List<String> hrefs;
    private List<Integer> indexes;

    public String getMain_title() {
        return main_title;
    }

    public void setMain_title(String main_title) {
        this.main_title = main_title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getHrefs() {
        return hrefs;
    }

    public void setHrefs(List<String> hrefs) {
        this.hrefs = hrefs;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Integer> indexes) {
        this.indexes = indexes;
    }
}
