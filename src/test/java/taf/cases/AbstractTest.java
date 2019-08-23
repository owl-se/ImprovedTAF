package taf.cases;

import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import taf.core.ConfigManager;
import taf.core.CredsManager;
import taf.core.RunTimeDataStorage;
import taf.core.Utils;
import taf.driverFactory.DriverFactory;
import taf.models.Driver;

import static taf.core.TestRunParams.getPathToAllArtifactsFolders;
import static taf.core.TestRunParams.getPathToCurrentArtifactsFolder;
import static taf.core.Utils.createFolder;
import static taf.core.Utils.deleteTempFiles;
import static taf.core.Utils.getCurrentThreadId;

public abstract class AbstractTest {

    private Logger log = Logger.getLogger("");

    SoftAssertions softAssertj;

    SoftAssert softAssert;

    private Driver d;

    public WebDriver getDriver() {
        if (d != null) {
            return d.getDriver();
        }
        else {
            return null;
        }
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeAll() {
        beforeAllActions();
    }

    private void beforeAllActions() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");

        RunTimeDataStorage.Statistic.resetCaseOrderNumber();

        ConfigManager.uploadRunConfigValues();
        ConfigManager.uploadEnvConfigValues();
        ConfigManager.uploadDbConfigValues();
        CredsManager.uploadCredsValues();

        deleteTempFiles();
        createFolder(getPathToAllArtifactsFolders());
        createFolder(getPathToCurrentArtifactsFolder());

        log.info("=== OS: " + Utils.getCurrentOS());
        log.info("=== Grid?: " + ConfigManager.isGrid());
        log.info("=== Selenoid?: " + ConfigManager.isSelenoid());
        log.info("=== Headless?: " + ConfigManager.isHeadless());
    }

    @AfterSuite(alwaysRun = true)
    public void afterRun() {
        //
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestCase() {
        beforeTestCaseActions();
    }

    private void beforeTestCaseActions() {
        softAssertj = new SoftAssertions();
        softAssert = new SoftAssert();
        RunTimeDataStorage.Statistic.incrementCaseOrderNumber();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestCase(ITestResult result) {
        //
    }

    Driver openBrowser() {
        d = DriverFactory.createNewDriverInstance(ConfigManager.getBrowserName());
        Utils.printDashedLine();
        log.info("Browser: " + d.getBrowserName() + " | thread: " + getCurrentThreadId());
        log.info("Driver: " + d.getDriver());
        Utils.printDashedLine();
        d.getDriver().manage().window().setSize(new Dimension(1366, 768));
        return d;
    }

    void closeBrowser() {
        if (d != null) {
            log.info("closing browser");
            Utils.printDashedLine();
            log.info("Browser: " + d.getBrowserName() + " | thread: " + getCurrentThreadId());
            log.info("Driver: " + d.getDriver());
            Utils.printDashedLine();
            try {
                d.getDriver().quit();
                Utils.sleepMsec(500);
            }
            catch (WebDriverException e) {
                //
            }
        }
        else {
            log.info("Driver is null!");
        }
    }
}
