package com.antelope.android.frat.data;

public class Picture {

    private String update_at;
    private String id;
    private String create_time;
    private Index current_value;

    public class Index{
        private String index;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }
    }


    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Index getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(Index current_value) {
        this.current_value = current_value;
    }
}
