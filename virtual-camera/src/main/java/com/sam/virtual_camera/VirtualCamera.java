package com.sam.virtual_camera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author Sameer Shaikh
 *
 */
public class VirtualCamera
{
    public static EventRecorder eventRecoder;

    public static void main(String[] args)
    {
        eventRecoder = eventRecoder.getInstance();

        // TODO need to decide exit condition when camera retries for server
        // connection multiple times.
        while (true)
        {

            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build())
            {
                HttpGet httpGetRequest = new HttpGet("http://localhost:8443/v1/camera/wait-for-request");
                RequestConfig.Builder requestConfig = RequestConfig.custom();
                requestConfig.setSocketTimeout(60 * 1000);
                httpGetRequest.setConfig(requestConfig.build());

                // Execute HTTP request
                HttpResponse httpResponse = httpClient.execute(httpGetRequest);

                System.out.println("----------------------------------------");
                System.out.println(httpResponse.getStatusLine());
                System.out.println("----------------------------------------");

                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE)
                {
                    System.out.println("Please check if virtual camera server is up");
                    httpGetRequest.abort();
                    continue;
                }

                // Get hold of the response entity
                HttpEntity entity = httpResponse.getEntity();

                // If the response does not enclose an entity, there is no need
                // to bother about connection release
                if (entity != null)
                {
                    InputStream inputStream = entity.getContent();
                    try
                    {
                        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null)
                        {
                            response.append(inputLine);
                        }

                        in.close();
                        /*
                         * TODO we will make webserver to give valid json object JSONObject myResponse = new
                         * JSONObject(response.toString()); String result = myResponse.getString("result");
                         */
                        if (response != null && response.length() != 0 && response.toString().equals("Logs"))
                        {
                            System.out.println("----------------------------------------");
                            System.out.println("Request for get logs recieved - " + response);
                            System.out.println("----------------------------------------");
                            PostEventLogs eventLogs = new PostEventLogs();
                            Thread t1 = new Thread(eventLogs);
                            t1.start();
                        }
                        if (response != null && response.length() != 0 && response.toString().equals("reset"))
                        {
                            System.out.println("----------------------------------------");
                            System.out.println("Request for reset recieved after 1 minute- " + response);
                            System.out.println("----------------------------------------");
                            inputStream.close();                            
                        }

                    }
                    catch (IOException ioException)
                    {
                        // In case of an IOException the connection will be
                        // released
                        // back to the connection manager automatically
                        ioException.printStackTrace();
                    }
                    catch (RuntimeException runtimeException)
                    {
                        // In case of an unexpected exception you may want to
                        // abort
                        // the HTTP request in order to shut down the underlying
                        // connection immediately.
                        httpGetRequest.abort();
                        runtimeException.printStackTrace();
                    }

                }
            }

            catch (ClientProtocolException e)
            {
                // thrown by httpClient.execute(httpGetRequest)
                e.printStackTrace();
            }
            catch (SocketTimeoutException e)
            {
                System.out.println("Resetting the connection after 1 minute");               
            }
            catch (IOException e)
            {
                // thrown by entity.getContent();
                e.printStackTrace();
            }

        }

    }
}
