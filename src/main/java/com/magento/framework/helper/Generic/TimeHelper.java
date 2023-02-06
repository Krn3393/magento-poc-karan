package com.magento.framework.helper.Generic;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
//import java.util.Random;
import java.util.Properties;
public class TimeHelper {

	public String getTimeStamp(String format)
	{
		/*Example format are : 
			"yyyy MMM dd" for "2013 Nov 28"
			"yyyyMMdd_HHmmss" for "20130131000000"
			"yyyy MMM dd HH:mm:ss" for "2013 Jan 31 00:00:00"
			"dd MMM yyyy" for "28 Nov 2017"*/
		
		 DateFormat dateFormat = new SimpleDateFormat(format);
	 	 Date date = new Date();
	 	 return dateFormat.format(date);
	}
	
	/**
	 * Add minutes to given time
	 * @param time time value e.g. 9:30
	 * @param mins mins value e.g. 30
	 * @return new time
	 */
	public String addMinutesToTime(String time, int mins)
	{
		String hourpart = time.split(":")[0];
		String minpart = time.split(":")[1];
		
		int hour = Integer.parseInt(hourpart);
		int sum = mins + Integer.parseInt(minpart);
		
		//Increasing hour count by 1 if mins >=60
		if(sum >= 60)
		{
			mins = sum-60;
			hour = hour + 1;
		}
		
		String finalhour= Integer.toString(hour);
		String finalmins=Integer.toString(mins);
		
		//If mins=0, converting it to mins format
		if(finalmins.equals("0"))
		{
			finalmins = "00";
		}

		return finalhour + ":" +  finalmins;
				
	}
	
	public String getMethodName()
	{
		return Thread.currentThread().getStackTrace()[3].getMethodName();
	}
	
	public String timeStamp()
	{
		return getTimeStamp("_yyyyMMdd_HHmmss");
	}
	
	public String currentDate()
	{
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		return date;
	}
	
	public int currentHour()
	{
		return LocalDateTime.now().getHour();
	}
	
	public int currentMinute()
	{
		return LocalDateTime.now().getMinute();
	}
	
	public void sleep(int seconds) throws InterruptedException
	{
		Thread.sleep(seconds*1000);
	}
	
	
	//Reading properties file
	public static String getPropertyValue(String propertyfile, String propertyname)
	{
		Properties prop = accessPropertiesFile(propertyfile);
		return prop.getProperty(propertyname);
	}
	
	public static Properties accessPropertiesFile(String propertyfile )
	{
		Properties prop = new Properties();

		// try retrieve data from file
		try 
		{
			prop.load(new FileInputStream(propertyfile));
		}
		// catch exception in case properties file does not exist
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return prop;
	}
	
	//Deleting all the files from specified directory
	public void deleteAllFilesFromDirectory(String directory)
	{
		File dir = new File(directory);
		File[] files = dir.listFiles();

		for (File f : files)
		{
			f.delete();
		}
	}
	
	
public static void main(String args[]) {
	
	
	TimeHelper obj = new TimeHelper();
	obj.getTimeStamp("yyyyMMdd_HHmmss");
	System.out.println(obj.getTimeStamp("yyyyMMdd_HHmmss"));
	
	System.out.println(obj.currentMinute());
	
	
	
}
	
	
}
