package com.sam.virtual_camera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.virtual_camera.beans.eventlog.EventLogListBean;
import com.sam.virtual_camera.beans.eventlog.IEventLog;
import com.sam.virtual_camera.beans.eventlog.IEventLogList;

public class PostEventLogs implements Runnable
{

    @Override
    public void run()
    {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://localhost:8443/v1/camera/logs");
        post.setHeader("Content-type", "application/json; charset=UTF-8");
        HttpResponse response = null;

        IEventLogList eventLogList = new EventLogListBean();
        List<IEventLog> eventLogs = VirtualCamera.eventRecoder.getEventLogList();
        eventLogList.setEventLogs(eventLogs);

        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = null;
        try
        {
            jsonStr = Obj.writeValueAsString(eventLogList);

            StringEntity input = null;
            input = new StringEntity(jsonStr);
            post.setEntity(input);

            response = client.execute(post);

            BufferedReader rd = null;
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null)
            {
                System.out.println(line);
            }
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (UnsupportedOperationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
