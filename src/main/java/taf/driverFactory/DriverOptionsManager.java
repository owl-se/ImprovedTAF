package taf.driverFactory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import taf.core.ConfigManager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static taf.core.ConfigManager.isSelenoid;
import static taf.core.TestRunParams.getPathToDownloads;

public class DriverOptionsManager {

    FirefoxOptions getFirefoxOptions() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.SEVERE);

        FirefoxBinary firefoxBinary = new FirefoxBinary();

        if (ConfigManager.isHeadless()) {
            firefoxBinary.addCommandLineOptions("--headless");
        }

        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

        FirefoxOptions firefoxOptions = new FirefoxOptions();

        firefoxOptions.setBinary(firefoxBinary);
        firefoxOptions.addPreference("dom.disable_beforeunload", true);
        firefoxOptions.addPreference("browser.download.folderList", 2);
        firefoxOptions.addPreference("browser.download.manager.showWhenStarting", false);

        if (!isSelenoid()) {
            firefoxOptions.addPreference("browser.download.dir", getPathToDownloads());
        }

        firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk",
                "text/plain, image/png, application/zlib, application/x-gzip, application/x-compressed, text/csv, " +
                        "application/x-gtar, multipart/x-gzip, application/tgz, application/pdf, " +
                        "application/gnutar, application/x-tar, application/gzip, application/tar+gzip, application/octet-stream, " +
                        "text/html");
        firefoxOptions.addPreference("browser.download.manager.focusWhenStarting", false);
        firefoxOptions.addPreference("browser.download.manager.useWindow", false);
        firefoxOptions.addPreference("browser.download.manager.showAlertOnComplete", false);
        firefoxOptions.addPreference("browser.download.manager.closeWhenDone", false);
        firefoxOptions.addPreference("pdfjs.disabled", true);

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("webdriver_enable_native_events", false);
        firefoxOptions.setCapability(FirefoxDriver.PROFILE, profile);
        firefoxOptions.setCapability("browserName", "firefox");
        firefoxOptions.setCapability("enableVNC", true);
        firefoxOptions.setCapability("enableVideo", false);

        return firefoxOptions;
    }

    ChromeOptions getChomeOptions() {
        ChromeOptions options = new ChromeOptions();

        if (ConfigManager.isHeadless()) {
            options.addArguments("headless");
        }

        Map<String, Object> prefs = new HashMap<String, Object>();

        if (!isSelenoid()) {
            prefs.put("download.default_directory", getPathToDownloads());
        }

        prefs.put("download.prompt_for_download", false);
        options.setExperimentalOption("prefs", prefs);
        options.setCapability("enableVNC", true);
        options.setCapability("enableVideo", false);

        return options;
    }
}
