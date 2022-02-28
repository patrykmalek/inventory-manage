package kruszywo.sa.computers.manage.model;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonFunctions {

	
    public static InputStream getFileFromResourceAsStream(String fileName) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found exception! " + fileName);
        } else {
            return inputStream;
        }

    }
    
    public static File getFileFromResourceAsFile(String fileName) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        if (!file.exists()) {
            throw new IllegalArgumentException("File not found exception! " + fileName);
        } else {
            return file;
        }

    }
    
	public static Date parseDate(String date, SimpleDateFormat simpleDateFormat) {
	     try {
	         return simpleDateFormat.parse(date);
	     } catch (ParseException e) {
	    	 e.printStackTrace();
	 		return null;
	     }
	  }
	
	public static Date parseDateTime(String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     try {
	         return simpleDateFormat.parse(date);
	     } catch (ParseException e) {
	    	 e.printStackTrace();
	         return null;
	     }
	  }
	
	public static Date parseDateTime(String date, String stringPattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(stringPattern);
	     try {
	         return simpleDateFormat.parse(date);
	     } catch (ParseException e) {
	    	 e.printStackTrace();
	         return null;
	     }
	  }
	
	public static Date parseDate(int year, int month, int day) {
		String date = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     try {
	         return simpleDateFormat.parse(date);
	     } catch (ParseException e) {
	    	 e.printStackTrace();
	         return null;
	     }
	  }
	
	public static String millisToShortDHMS(long duration) {
	    String res = "";    // java.util.concurrent.TimeUnit;
	    long days       = TimeUnit.MILLISECONDS.toDays(duration);
	    long hours      = TimeUnit.MILLISECONDS.toHours(duration) -
	                      TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
	    long minutes    = TimeUnit.MILLISECONDS.toMinutes(duration) -
	                      TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
	    long seconds    = TimeUnit.MILLISECONDS.toSeconds(duration) -
	                      TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
	    long millis     = TimeUnit.MILLISECONDS.toMillis(duration) - 
	                      TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));
	    if (days == 0)      res = String.format("%02d:%02d:%02d.%04d", hours, minutes, seconds, millis);
	    else                res = String.format("%dd %02d:%02d:%02d.%04d", days, hours, minutes, seconds, millis);
	    return res;
	}
}
