package com.antelope.android.frat.data;

import java.util.List;

public class Datastreams {

    public List<Datapoints> datapoints;
    public String id;

    public void setDatapoints(List<Datapoints> datapoints) {
        this.datapoints = datapoints;
    }

    public List<Datapoints> getDatapoints() {
        return datapoints;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
