package com.lessons.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportStatsDTO {
    private Integer report;
    private String displayName;
    @JsonProperty("num_indicators")
    private Integer indicatorCount;

    public Integer getReportId() {
        return report;
    }

    public void setReportId(Integer reportId) {
        this.report = reportId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getIndicatorCount() {
        return indicatorCount;
    }

    public void setIndicatorCount(Integer indicatorCount) {
        this.indicatorCount = indicatorCount;
    }
}
