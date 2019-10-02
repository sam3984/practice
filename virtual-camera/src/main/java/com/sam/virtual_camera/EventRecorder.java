package com.sam.virtual_camera;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

import com.sam.virtual_camera.beans.eventlog.EventLogBean;
import com.sam.virtual_camera.beans.eventlog.IEventLog;

/**
 * Singleton class for List of EventLogs
 * 
 * @author Sameer Shaikh
 *
 */
public class EventRecorder
{

    private List<IEventLog> eventLogList;
    private static EventRecorder instance;
    Timer eventLogUpdater;

    private EventRecorder()
    {
        System.out.println("Started generating event logs for virtual camera");
        eventLogList = Collections.synchronizedList(new ArrayList<IEventLog>());
        eventLogUpdater = new Timer();
        eventLogUpdater.schedule(new EventGenerationTask(), 0, 10000);
    }

    class EventGenerationTask extends TimerTask
    {
        public void run()
        {
            try
            {
                generateEventLogs();
            }
            catch (Exception e)
            {
                // TODO log error
            }
        }
    }

    public static EventRecorder getInstance()
    {
        if (instance == null)
        {
            synchronized (EventRecorder.class)
            {
                if (instance == null)
                {
                    instance = new EventRecorder();
                }
            }
        }

        return instance;
    }

    public List<IEventLog> getEventLogList()
    {
        return eventLogList;
    }

    public void generateEventLogs()
    {
        IEventLog eventLog = new EventLogBean();
        Random ran = new Random();
        int randomNum = ran.nextInt(100);
        eventLog.setDescription("detected motion at 40, " + randomNum);
        eventLog.setTimestamp(new Date());
        
        eventLogList.add(eventLog);
        
        System.out.println(eventLog.toString());
    }
}
