package com.example.splunk;

import com.splunk.*;
import java.io.*;
import java.util.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class SplunkEngine {

    private Service service;
    private Command command;
    private String fileName;

    public SplunkEngine(String[] args, String usage, String fileName) {
        command = Command.splunk(usage);
        command.parse(args);

        this.fileName = fileName;



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
        BufferedReader reader = new BufferedReader(new FileReader(
                fileName));
        String line = reader.readLine();
        while (line != null) {
            out.write(reader + "\r\n");
        }

        out.flush();
        //myIndex.submit(this.fileName);
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
