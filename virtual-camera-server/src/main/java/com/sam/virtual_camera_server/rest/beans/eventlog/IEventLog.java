package com.sam.virtual_camera_server.rest.beans.eventlog;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(as = EventLogBean.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"timestamp", "description"})
@ApiModel(value = "Virtual Camera EventLog", description = "Event log entry from Virtual Camera")
public interface IEventLog
{
    @ApiModelProperty(name = "timestamp", value = "Specifies timestamp of the event log", position = 1, example="2019-01-16T19:39:01.764+0000")
    @JsonProperty(value = "timestamp", index = 1)
    public Date getTimestamp();

    public void setTimestamp(Date timestamp);

    @ApiModelProperty(name = "description", value = "Specifies display name of the timezone.", position = 2, example="user started viewing live video")
    @JsonProperty(value = "description", index = 2)
    public String getDescription();

    public void setDescription(String description);
}
