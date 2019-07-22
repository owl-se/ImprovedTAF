package taf.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import static taf.testRunParams.Dictionary.*;

public class ConfigManager {

    private static String env = "not defined";

    private static final String mainConfigTag = "config";

    private static Config currentConfigArray;

    private static Config commonConfigArray;

    public static void defineEnv() {
        String providedEnv = "" + System.getProperty("env");
        if (providedEnv.equalsIgnoreCase(getDictLoc())
                || providedEnv.equalsIgnoreCase(getDictProd())
                || providedEnv.equalsIgnoreCase(getDictSt())
                || providedEnv.equalsIgnoreCase(getDictTest())
                || providedEnv.equalsIgnoreCase(getDictDev()) ) {
            env = providedEnv;
        }
        else {
            env = getDefaultEnv();
        }
    }

    public static String getEnv() {
        return env;
    }

    private static String getCurrentConfigsFileName() {
        return "config_" + getEnv();
    }

    private static void defineCurrentConfig() {
        currentConfigArray = ConfigFactory.load(getCurrentConfigsFileName());
    }

    private static String getCommonConfigsFileName() {
        return "config_common";
    }

    public static void defineCommonConfigs() {
        commonConfigArray = ConfigFactory.load(getCommonConfigsFileName());
    }

    private static String getDefaultEnv() {
        return commonConfigArray.getString(mainConfigTag + ".default_env");
    }

    public static boolean isScreenshotOnPass() {
        return Boolean.valueOf(commonConfigArray.getString(mainConfigTag + ".screenshotOnTestSuccess"));
    }

    public static boolean isScreenshotOnFail() {
        return Boolean.valueOf(commonConfigArray.getString(mainConfigTag + ".screenshotOnTestFailure"));
    }

    public static boolean isPageSourceOnPass() {
        return Boolean.valueOf(commonConfigArray.getString(mainConfigTag + ".htmlSourceOnTestSuccess"));
    }

    public static boolean isPageSourceOnFails() {
        return Boolean.valueOf(commonConfigArray.getString(mainConfigTag + ".htmlSourceOnTestFailure"));
    }

    public static String getPathToSampleFilesFolder() {
        String path = System.getProperty("user.dir") + commonConfigArray.getString(mainConfigTag + ".pathToSampleFiles");
        if (Utils.getCurrentOS().contains("wind")) {
            path = path.replace("/", "\\");
        }
        return path;
    }

    static boolean isHeadless() {
        String headlessModeFromCmdStr = "" + System.getProperty("headless");
        if (!headlessModeFromCmdStr.equalsIgnoreCase("null")) {
            return Boolean.valueOf(headlessModeFromCmdStr);
        }
        else {
            return getDefaultHeadless();
        }
    }

    private static Boolean getDefaultHeadless() {
        return Boolean.valueOf(commonConfigArray.getString(mainConfigTag + ".default_headless"));
    }

    private static String getDefaultBrowserName() {
        return commonConfigArray.getString(mainConfigTag + ".default_browser");
    }

    public static String getRequiredBrowserName() {
        String browserCmdLine = "" + System.getProperty("browser");
        if (!browserCmdLine.equalsIgnoreCase("null")) {
            return browserCmdLine;
        } else {
            return getDefaultBrowserName();
        }
    }

    public static int getWaitForPageSec() {
        return currentConfigArray.getInt(mainConfigTag + ".wait_for_page_upload_sec");
    }

    public static String getGridHubUrl() {
        return commonConfigArray.getString(mainConfigTag + "gridHubUrl");
    }

    public static String getGridHost() {
        return commonConfigArray.getString(mainConfigTag + ".gridHost");
    }

    public static boolean isGrid() {
        if (isSelenoid()) {
            return true;
        } else {
            String browserCmdStr = "" + System.getProperty("grid");
            if (!browserCmdStr.equalsIgnoreCase("null")) {
                return Boolean.valueOf(browserCmdStr);
            } else {
                return isGridByDefault();
            }
        }
    }

    public static boolean isSelenoid() {
        String browserCmdStr = "" + System.getProperty("selenoid");
        if (!browserCmdStr.equalsIgnoreCase("null")) {
            return Boolean.valueOf(browserCmdStr);
        } else {
            return isSelenoidByDefault();
        }
    }

    private static boolean isGridByDefault() {
        return Boolean.valueOf(commonConfigArray.getString(mainConfigTag + ".useGridByDefault"));
    }

    private static boolean isSelenoidByDefault() {
        return Boolean.valueOf(commonConfigArray.getString(mainConfigTag + ".useSelenoidByDefault"));
    }


}
