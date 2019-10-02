package com.sam.virtual_camera_server.rest.beans.eventlog;

import java.util.List;

public class EventLogListBean implements IEventLogList
{
    private List<IEventLog> eventLogList;
    
    public EventLogListBean()
    {
        
    }
    
    public EventLogListBean(List<IEventLog> eventLogList)
    {
        this.eventLogList = eventLogList;
    }
    
    @Override
    public List<IEventLog> getEventLogs()
    {
        return eventLogList;
    }
    
    @Override
    public void setEventLogs(List<IEventLog> eventLogList)
    {
        this.eventLogList = eventLogList;
    }

}
