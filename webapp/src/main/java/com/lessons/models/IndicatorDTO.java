package com.lessons.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class IndicatorDTO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("value")
    private String value;
    @JsonProperty("createdDate")
    private Timestamp createdDate;

    // DTO data members may be added and removed to handle front-end changes without altering SQL
    // queries and row mapper objects

    /*
    @JsonProperty("created_by")
    private String createdBy;
    */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /*
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
     */

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /*
    public String getCreatedBy() {
        return createdBy;
    }
     */
}
