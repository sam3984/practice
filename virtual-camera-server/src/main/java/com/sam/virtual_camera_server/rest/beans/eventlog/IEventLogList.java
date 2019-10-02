package com.sam.virtual_camera_server.rest.beans.eventlog;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(as = EventLogListBean.class)
@JsonPropertyOrder({"eventLogs"})
@ApiModel(value = "Virtual Camera Event Log  list")
public interface IEventLogList
{
    @ApiModelProperty(name = "eventLogs", value = "List of all virtual camera event logs", position = 1)
    @JsonProperty(value = "eventLogs", index = 1)
    public List<IEventLog> getEventLogs();

    void setEventLogs(List<IEventLog> eventLogList);
}
