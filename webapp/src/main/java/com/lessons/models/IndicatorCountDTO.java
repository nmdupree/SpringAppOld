package com.lessons.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IndicatorCountDTO {

    @JsonProperty("total")
    private Integer count;

    public IndicatorCountDTO() {}

    public IndicatorCountDTO(Integer aCount){
        this.count = aCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
