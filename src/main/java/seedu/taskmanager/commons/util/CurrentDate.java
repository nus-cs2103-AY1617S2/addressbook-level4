package seedu.taskmanager.commons.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CurrentDate {

	String currentDay = "";
	String currentDate = "";
	
	public CurrentDate() {
	currentDay = getDay();
	currentDate = getDate();
	}

	public String getDay() {
		String newday = "";
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //getTime() returns the current date in default time zone
        Date date = calendar.getTime();
        int day = calendar.get(Calendar.DATE);
        //Note: +1 the month for current month
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
      
      if (day == 1) {
    	  newday = "Sunday";
    	  return newday;
      } else {      if (day == 2) {
    	  newday = "Monday";
    	  return newday;
      } else {      if (day == 3) {
    	  newday = "Tuesday";
    	  return newday;
      } else {      if (day == 4) {
    	  newday = "Wednesday";
    	  return newday;
      } else {      if (day == 5) {
    	  newday = "Thursday";
    	  return newday;
      } else {      if (day == 6) {
    	  newday = "Friday";
    	  return newday;
      } else {
    	  newday = "Saturday";
    	  return newday;
      }}}}}}
	  
    }
	
	public String getDate() {
		String newdate = "";
		String stringDay = "";
		String stringMonth = "";
		String stringYear = "";
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //getTime() returns the current date in default time zone
        Date date = calendar.getTime();
        int day = calendar.get(Calendar.DATE);
        //Note: +1 the month for current month
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        
        if (day < 10) {
        	stringDay = "0" + Integer.toString(day);
        } else stringDay = Integer.toString(day);
        
        if (month < 10) {
        	stringMonth = "0" + Integer.toString(month);
        } else stringMonth = Integer.toString(month);
        
        stringYear = Integer.toString(year).substring(Math.max(Integer.toString(year).length()-2,0));
        
        newdate = stringDay + "/" + stringMonth + "/" + stringYear;
        
        return newdate;
	}

}
