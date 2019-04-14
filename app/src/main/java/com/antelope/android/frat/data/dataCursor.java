package com.antelope.android.frat.data;

import java.util.List;

public class dataCursor {
    private String cursor;
    private int count;
    private List<Datastreams> datastreams;

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setDatastreams(List<Datastreams> datastreams) {
        this.datastreams = datastreams;
    }

    public List<Datastreams> getDatastreams() {
        return datastreams;
    }
}
