package com.comcast.crm.generic.webdriverutility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JavaUtility {
	public int getRandomNum() {
		Random ranDom = new Random();
		return ranDom.nextInt(5000);
	}

	public String getSystemDateYYYYMMDD() {
		Date dateObj = new Date(); // import java.util.Date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.format(dateObj);
	}

	public String getRequiredDateYYYYMMDD(String startDate, int days) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sd = sdf.parse(startDate);

		Calendar cal = Calendar.getInstance();
				cal.setTime(sd); // Set the calendar to the specified start date
				cal.add(Calendar.DAY_OF_MONTH, days); // Add the number of days
				
		return sdf.format(cal.getTime()); // Return the formatted end date
	}	
}
