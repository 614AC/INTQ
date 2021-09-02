package com.example.intq.common.bean.instance;

public class Link {
    private String entity_type;
    private String entity_url;
    private Integer start_index;
    private Integer end_index;
    private String entity;

    public Link(String entity_type, String entity_url, Integer start_index, Integer end_index, String entity) {
        this.entity_type = entity_type;
        this.entity_url = entity_url;
        this.start_index = start_index;
        this.end_index = end_index;
        this.entity = entity;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(String entity_type) {
        this.entity_type = entity_type;
    }

    public String getEntity_url() {
        return entity_url;
    }

    public void setEntity_url(String entity_url) {
        this.entity_url = entity_url;
    }

    public Integer getStart_index() {
        return start_index;
    }

    public void setStart_index(Integer start_index) {
        this.start_index = start_index;
    }

    public Integer getEnd_index() {
        return end_index;
    }

    public void setEnd_index(Integer end_index) {
        this.end_index = end_index;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
