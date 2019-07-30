package taf.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static taf.core.ConfigManager.getPathToSampleFilesFolder;
import static taf.core.ConfigManager.isSelenoid;


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
            oInStream = new FileInputStream(getPathToSampleFilesFolder() + sampleFileName);
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

    public static void renameFile(String pathToFile, String oldFileName, String newFileName) {
        File file = new File(pathToFile + oldFileName);
        file.renameTo(new File(newFileName));
        log.info("file [" + oldFileName + "] is renamed to [" + newFileName + "]");
    }

    public static void deleteFileByPartialName(String namePart, String folderPath) {
        final File folder = new File(folderPath);
        for (File f : folder.listFiles()) {
            if (f.getName().contains(namePart)) {
                f.delete();
            }
        }
    }

    public static boolean waitUntilFileIsDownloaded(String fileName) {
        final Logger log = Logger.getLogger("");
        int timeoutSec = 30;
        int refreshStepSec = 1;
        int spentTimeSec = 0;
        boolean status = true;

        File file = new File(getPathToDownloads() + fileName);

        log.info("waiting up to " + timeoutSec + " sec until file [" + fileName + "] is downloaded");
        while (!file.exists() && (spentTimeSec < timeoutSec)) {
            sleepMsec(refreshStepSec * 1000);
            spentTimeSec = spentTimeSec + refreshStepSec;
            log.info("it's been " + spentTimeSec + " seconds");
        }

        if (!file.exists()) {
            log.error("the file was not downloaded after " + timeoutSec + " seconds: " + fileName);
            if (isSelenoid()) {
                log.error("check out if the file is available on selenoid docker image");
            }
            status = false;
        }
        return status;
    }

    public static void sleepMsec(final long msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {

        }
    }

    private static void wgetFile(String url) {
        final Logger log = Logger.getLogger("");
        String[] array = url.split("/");
        String fileName = array[array.length - 1];
        log.info("trying to download file [" + fileName +"] from [" + url + "]");
        String command = "wget -O " + getPathToDownloads() + fileName + " " + url;
        try {
            Runtime.getRuntime().exec(command).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitUntilFileIsDownloaded(fileName);
    }

    public static String getRandomNumberString() {
        Random rand = new Random();
        return "" + rand.nextInt(1000);
    }

    public static String getRandomNumberString(int number) {
        Random rand = new Random();
        return "" + rand.nextInt(number);
    }

    public static int getRandomNumber(int number) {
        Random rand = new Random();
        return rand.nextInt(number);
    }

    public static String getTextFromFile(String filePath) {
        StringBuilder text = new StringBuilder();
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(filePath));
            while ((sCurrentLine = br.readLine()) != null) {
                text.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return text.toString();
    }

    public static boolean doesFileContainText(String filePath, String text) {
        String fileText = getTextFromFile(filePath);
        return filePath.contains(text);
    }

    public static boolean isFileDownloaded(String fileName) {
        boolean isDownloaded = false;
        String downloadsPath = getPathDownloads();
        File file = new File(downloadsPath + fileName);
        if (file.exists()) {
            isDownloaded = true;
        }
        return isDownloaded;
    }

    public static List<String> sortAlphaBet(List<String> list) {
        final Logger log = Logger.getLogger("");
        Collections.sort(list);
        log.info("sorted from A to Z list contains " + list.size() + " items");
        log.info("sorted list: first item is [" + list.get(0) +
                "]; last item is [" + list.get(list.size() - 1) + "]");
        return list;
    }

    public static String getStringOfCharacters(String pattern, int length) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < length; i ++) {
            string.append(pattern);
        }
        return string.toString();
    }

    public static void manageFileDownloading(String fileName, RemoteWebDriver driver) {
        if (isSelenoid()) {
            log.info("downloading file from selenoid docker image");
            sleepMsec(5000);
            String gridFileUrl = ConfigManager.getGridHost() + "/download/" +
                    driver.getSessionId() + "/" +
                    fileName;
            wgetFile(gridFileUrl);
        }
        else {
            waitUntilFileIsDownloaded(fileName);
        }
    }

    public static String getCurrentOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String getText1000Symbols() {
        String sFilePath = getPathToSampleFilesFolder() + "text_1000_symbols.txt";
        return getTextFromFile(sFilePath);
    }

    public static String removePackageNameFromPath(String str) {
        str = str.replace("project.steps.", "");
        str = str.replace("project.pages.example.", "");
        str = str.replace("project.cases.", "");
        return str;
    }

    public static Screenshot makeScreenshot(WebDriver driver) {
        log.info("making screenshot");
        return new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(driver);
    }

    public static void saveScreenshotAsFile(String fileName, Screenshot screenshot) {
        String filePath = TestRunParams.getScreenshotsLocation() + fileName;
        final BufferedImage image = screenshot.getImage();
        try {
            ImageIO.write(image, "PNG", new File(filePath));
            log.info("screenshot is saved here: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printLine() {
        String line = Utils.getStringOfCharacters("-", 70);
        log.info(line);
    }

    public static void printDoubleLine() {
        String line = Utils.getStringOfCharacters("=", 70);
        log.info(line);
    }

    public static void printDashedLine() {
        String line = Utils.getStringOfCharacters("- ", 35);
        log.info(line);
    }

    public static long getCurrentThreadId() {
        return Thread.currentThread().getId();
    }

    private static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double round(String stringValue, int places) {
        stringValue = stringValue.replace(",", ".").trim();
        double value = Double.parseDouble(stringValue.trim());
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
