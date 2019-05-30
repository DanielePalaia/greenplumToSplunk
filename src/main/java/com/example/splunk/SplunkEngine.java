package com.example.splunk;

import com.splunk.*;
import java.io.*;
import java.util.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class SplunkEngine {

    private Service service;
    private Command command;
    private String query;
    private String earliestTime;
    private String latestTime;


    public SplunkEngine(String[] args, String usage, String earliest, String latest) {
        command = Command.splunk(usage);
        command.parse(args);

        this.earliestTime = earliest;
        this.latestTime = latest;


    }

    public void connect() {
        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
        service = Service.connect(command.opts);
    }


    public void insertToSplunk()   {
    // Retrieve the index for the data
    Index myIndex = service.getIndexes().get("main");

    // Set up a timestamp
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    String date = sdf.format(new Date());


    try

    {
        // Open a socket and stream
        Socket socket = myIndex.attach();
        OutputStream ostream = socket.getOutputStream();
        Writer out = new OutputStreamWriter(ostream, "UTF8");

        // Send events to the socket then close it
        out.write(date + "Event one!\r\n");
        out.write(date + "Event two!\r\n");
        out.flush();
    }
    catch(Exception e)  {
        e.printStackTrace();
    }
    /*finally

    {
        socket.close();
    }*/
}





}
