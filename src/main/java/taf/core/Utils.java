package taf.core;

import org.openqa.selenium.WebDriver;

import java.io.*;
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
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
        return dateFormat.format(d);
    }

    public static String getYesterdayDate_YYYY_MM_dd_Slashes() {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, -1);
        d = c.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
        return dateFormat.format(d);
    }

    public static String getTimeStamp() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return "[" + dateFormat.format(d) + "]";
    }

    public static void createFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            log.info("create folder: " + folderPath);
            file.mkdir();
            log.info("folder created: " + folderPath);
        }
        log.info("such folder already exists: " + folderPath);
    }

    public static void createFile(String filePath, String content) throws IOException {
        log.info("create file: " + filePath);
        File file = new File(filePath);
        file.createNewFile();
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    public static String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public static String generateFileReturnName(String fileType, String stringInsideFileName) {
        String runTime = getUniqueString();
        String newFileName = runTime;
        String sampleFileName = "";

        if (fileType.equalsIgnoreCase(Dictionary.TXT)) {
            newFileName = stringInsideFileName + runTime + getRandomNumberString() + ".txt";
            sampleFileName = TEST_TEXT_FILE_NAME;
        }

        if (fileType.equalsIgnoreCase(Dictionary.PNG)) {
            newFileName = stringInsideFileName + runTime + getRandomNumberString() + ".png";
            sampleFileName = TEST_PNG_FILE_NAME;
        }

        InputStream oInStream = null;
        OutputStream oOutStream = null;

        try {
            oInStream = new FileInputStream(ConfigManager.getPathToSampleFilesFolder() + sampleFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            oOutStream = new FileOutputStream(ConfigManager.getPathToTestFilesFolder() + newFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] oBytes = new byte[1024];
        int nLength;
        BufferedInputStream oBuffInputStream = new BufferedInputStream( oInStream );
        try {
            while ((nLength = oBuffInputStream.read(oBytes)) > 0)
            {
                oOutStream.write(oBytes, 0, nLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oInStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("created file: " + newFileName);
        return newFileName;
    }

    public static void deleteTempFiles() {
        log.info("deleting temp files");
        deleteTempFilesByExt(".txt");
        deleteTempFilesByExt(".png");
    }

    public static void deleteTempFilesByExt(String ext) {
        GenericExtFilter filer = new GenericExtFilter(ext);
        File fileDir = new File(ConfigManager.getPathToTestFilesFolder());

        String[] list = fileDir.list(filer);

        // CHECK
        if (list.length != 0) {
            File fileDelete;
            for (String file : list) {
                String temp = new StringBuffer(ConfigManager.getPathToTestFilesFolder())
                        .append(File.separator)
                        .append(file).toString();
                fileDelete = new File(temp);
                fileDelete.delete();
            }
        }


    }

    public static class GenericExtFilter implements FilenameFilter {

        private String ext;

        public GenericExtFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }
    }


}
