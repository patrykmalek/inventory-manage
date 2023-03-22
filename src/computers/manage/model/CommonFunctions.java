package computers.manage.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import computers.manage.exception.SystemOperationException;

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
	
	public static String formatDate(Date date, String stringPattern) {
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(stringPattern);
	     return simpleDateFormat.format(date);
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
	
	public static boolean validateID(int ID) {
		if(ID < 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Nie zaznaczono wiersza!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

    public static byte[] readFileAsByte(File file) {
        ByteArrayOutputStream bos = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
            fis.close();
        } catch (FileNotFoundException | NullPointerException e) {
        	new SystemOperationException("Brak pliku", e);
        } catch (IOException e2) {
            new SystemOperationException("Błąd wejścia/wyjścia", e2);
        }
        return bos != null ? bos.toByteArray() : null;
    }
    
    public static File getFileFromInputStream(String fileName, InputStream inputStream) {
    	File tempFile = null;
    	FileOutputStream fos = null;
		try {
			tempFile = File.createTempFile("license_temp", ".tmp");
			tempFile.deleteOnExit();
			
			fos = new FileOutputStream(tempFile);
	        byte[] buffer = new byte[1024];
	        while (inputStream.read(buffer) > 0) {
	            fos.write(buffer);
	        }
		} catch (IOException e) {
			new SystemOperationException("Błąd tworzenia pliku ze strumienia", e);
		}
        
        return tempFile;
    }
}
