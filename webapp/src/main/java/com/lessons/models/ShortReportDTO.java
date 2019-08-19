package com.lessons.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShortReportDTO {

    @JsonProperty("Row ID")
    private Integer id;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Display Name") // Changes the JSON key returned to the front end
    private String displayName;

    /**
     * Default Constructor
     */
    public ShortReportDTO(){}

    /**
     * Overloaded Constructor
     * @param aId Integer
     * @param aDescription
     * @param aDisplayName
     */
    public ShortReportDTO(Integer aId, String aDescription, String aDisplayName){
        this.id = aId;
        this.description = aDescription;
        this.displayName = aDisplayName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
