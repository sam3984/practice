package com.sam.virtual_camera_server.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.sam.virtual_camera_server.rest.beans.eventlog.IEventLogList;
import com.sam.virtual_camera_server.rest.service.RestVirtualCameraService;

@CrossOrigin
@RestController
@RequestMapping("/v1/camera")
@Api(value = "Camera Controller", description = "APIs for Virtual Camera Assignment")
public class CameraController
{

    @Autowired
    RestVirtualCameraService virtualCameraService;

    @ApiOperation(value = "Retrieves the logs for virtual camera")
    @RequestMapping(path = "/logs", method = RequestMethod.GET, produces = {"application/json"})
    @ApiResponse(code = 200, message = "All available camera logs fetched successfully")
    public DeferredResult<IEventLogList> getLogs() throws IOException
    {
        DeferredResult<IEventLogList> eventLogList = new DeferredResult<IEventLogList>();
        virtualCameraService.setEventLogs(1, eventLogList);

        DeferredResult<String> result = virtualCameraService.getResult(1);
        if (result != null)
        {
            result.setResult("Logs");
        }
        else
        {
            //Condition when connection between app and server has is closed
            System.out.println("Race condition when connection between camera and server is closed need to retry.Lets retry 3 times");
            int retryAttempts = 0;
            while(result == null && retryAttempts  < 3)
            {
                result = virtualCameraService.getResult(1);
                retryAttempts++;
            }
            
            if(result == null)
            {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Camera and Server connection lost");
            }
        }
        
        eventLogList.onCompletion(new Runnable() {
            public void run()
            {
                virtualCameraService.removeEventLogs(1);
            }
        });

        return eventLogList;
    }

    @RequestMapping(path = "/wait-for-request", method = RequestMethod.GET, produces = {"application/json"})
    public DeferredResult<String> handleReqDefResult(HttpServletResponse response)
    {
        DeferredResult<String> output = new DeferredResult<String>((long)80000, "reset");

        virtualCameraService.setResult(1, output);

        output.onCompletion(new Runnable() {
            public void run()
            {
                virtualCameraService.removeResult(1);
            }
        });

        return output;
    }

    @ApiOperation(value = "Create Virtual Camera logs")
    @RequestMapping(value = "/logs", method = RequestMethod.POST, produces = {"application/json"})
    public @ApiResponse(code = 200, message = "") @ResponseBody void createLogs(@ApiParam(value = "event_log_list", required = true) @RequestBody(required = true) IEventLogList eventLogList,
                                                                                HttpServletResponse response,
                                                                                UriComponentsBuilder uriBuilder)
        throws ResponseStatusException
    {
        DeferredResult<IEventLogList> deferredEventLogList = virtualCameraService.getEventLogs(1);
        
        if (deferredEventLogList == null)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Camera post request fired but GET:/logs has been abruptly closed");
        }
            
        deferredEventLogList.setResult(eventLogList);
        virtualCameraService.setEventLogs(1, deferredEventLogList);

    }
}