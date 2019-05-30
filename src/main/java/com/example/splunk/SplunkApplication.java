package com.example.splunk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import java.io.*;
import java.util.*;
import org.springframework.boot.Banner;



//@SpringBootApplication
public class SplunkApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(SplunkApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setLogStartupInfo(false);
		app.run(args);

	}

	@Override
	public void run(String[] args) throws Exception {

		/*FileInputStream in;
		Properties SegmentProperties = new Properties();

		try {
			in = new FileInputStream("./segment.properties");
			SegmentProperties.load(in);
		}
		catch (Exception e)  {
			e.printStackTrace();
		}*/
		if(args.length < 1)  {
			System.err.println("you need to specify the input file");
			System.exit(-1);
		}


		SplunkEngine mysplunk = new SplunkEngine(args, "search", args[0]);
		mysplunk.connect();
		mysplunk.insertToSplunk();
	}



}
