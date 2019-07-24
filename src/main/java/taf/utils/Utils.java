package taf.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class Utils {

    private static Logger log = Logger.getLogger("");

    private static final String TEST_TEXT_FILE_NAME = "textFile.txt";

    private static final String TEST_PNG_FILE_NAME = "pngFile.png";

    private static String getTestTextTemplateFileName() {
        return TEST_TEXT_FILE_NAME;
    }

    private static String getTestPngTemplateFileName() {
        return TEST_PNG_FILE_NAME;
    }

    public static String getCurrentTimeWithSec(String timeZone) {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return dateFormat.format(d);
    }

    public static String getCurrentTimeWithSec() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(d);
    }

    public static String getCurrentTimeWithMSec() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_HHmmss");
        return dateFormat.format(d) + getRandomNumber(999);
    }

    public static String getUniqueString() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_HHmmss");
        return dateFormat.format(d) + getRandomNumber(999);
    }

    public static String getUniqueStringCharsOnly(int len) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String getUniqueStringNumbersOnly(int len) {
        String SALTCHARS = "0123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String getUniqueStringFromPossibleChars(String possibleChars, int len) {
        String SALTCHARS = possibleChars;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < len) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String getCurrentDate_YYYY_MM_dd() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return dateFormat.format(d);
    }

    public static String getCurrentDate_YYYY_MM_dd_UTC() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(d);
    }

    public static String getCurrentDate_YYYY_MM_dd_Slashes() {
        
    }
}
