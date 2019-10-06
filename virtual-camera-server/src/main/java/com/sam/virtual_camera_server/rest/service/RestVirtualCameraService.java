package com.sam.virtual_camera_server.rest.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.sam.virtual_camera_server.rest.beans.eventlog.IEventLogList;

@Service
public class RestVirtualCameraService
{
    //These can be used for multiple cameras were key will be the Camera Id
    //All Apis will have Id as the query parameter or path parameter
    Map<Integer, DeferredResult<IEventLogList>> logMap;
    Map<Integer, DeferredResult<String>> waitResultMap;
  
    public RestVirtualCameraService()
    {
        logMap = new ConcurrentHashMap<Integer, DeferredResult<IEventLogList>>();
        waitResultMap = new ConcurrentHashMap<Integer, DeferredResult<String>>();
    }

    public DeferredResult<IEventLogList> getEventLogs(int i)
    {
        return logMap.get(i);
    }

    public void removeEventLogs(int i)
    {
        logMap.remove(i);
    }
    public DeferredResult<String> getResult(int i)
    {
        return waitResultMap.get(i);
    }

    public void setResult(int i, DeferredResult<String> result)
    {
        waitResultMap.put(i, result);
    }

    public void removeResult(int i)
    {
        waitResultMap.remove(i);
    }

    public void setEventLogs(int i, DeferredResult<IEventLogList> eventLogList)
    {
        logMap.put(i, eventLogList);
    }

}
