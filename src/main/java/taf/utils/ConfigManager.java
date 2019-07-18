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
    }
}
