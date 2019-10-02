package com.sam.virtual_camera.beans.eventlog;

import java.util.Date;

/***
 * Bean class for EventLog.
 * 
 * @author Sameer Shaikh
 */
public class EventLogBean implements IEventLog
{

    private Date timestamp;
    private String description;

    @Override
    public Date getTimestamp()
    {
        return timestamp;
    }

    @Override
    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @Override
    public String toString()
    {
        return "EventLog [timestamp=" + timestamp + ", description=" + description + "]";
    }

}
