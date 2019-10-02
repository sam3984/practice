package com.sam.virtual_camera.beans.eventlog;

import java.util.List;

public interface IEventLogList
{
    public List<IEventLog> getEventLogs();

    void setEventLogs(List<IEventLog> eventLogList);
}
