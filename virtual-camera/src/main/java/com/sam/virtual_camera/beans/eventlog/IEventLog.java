package com.sam.virtual_camera.beans.eventlog;

import java.util.Date;

public interface IEventLog
{
    public Date getTimestamp();

    public void setTimestamp(Date timestamp);

    public String getDescription();

    public void setDescription(String description);
}
