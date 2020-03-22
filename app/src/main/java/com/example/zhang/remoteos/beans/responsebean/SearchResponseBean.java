package com.example.zhang.remoteos.beans.responsebean;

import java.util.List;

public class SearchResponseBean extends ResponseBaseBean {
    private String keyword;
    private List<String> description;
    private List<String> indexes;
    private List<String> covers;
    private List<String> hrefs;
    private List<String> director;
    private List<String> status;
    private List<String> date;
    private List<String> types;
    private List<String> actors;
    private List<String> titles;
    private List<String> areas;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<String> indexes) {
        this.indexes = indexes;
    }

    public List<String> getCovers() {
        return covers;
    }

    public void setCovers(List<String> covers) {
        this.covers = covers;
    }

    public List<String> getHrefs() {
        return hrefs;
    }

    public void setHrefs(List<String> hrefs) {
        this.hrefs = hrefs;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }
}
