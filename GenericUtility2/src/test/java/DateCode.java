

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCode {

	public static void main(String[] args) {
		Date dateObj = new Date();
		System.out.println(dateObj.toString());
		
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		
		String date = sim.format(dateObj);
		System.out.println(date);
		
		Calendar cal = sim.getCalendar();
		System.out.println("Actual date : " + sim.format(cal.getTime()));
		cal.add(Calendar.DAY_OF_MONTH, -30);
		System.out.println("30 Days before date : " + sim.format(cal.getTime()));
		
		
	}

}
