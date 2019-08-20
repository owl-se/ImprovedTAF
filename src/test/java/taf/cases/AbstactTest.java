package taf.cases;

import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import taf.core.ConfigManager;
import taf.core.CredsManager;
import taf.core.RunTimeDataStorage;
import taf.core.Utils;
import taf.models.Driver;

import static taf.core.TestRunParams.getPathToAllArtifactsFolders;
import static taf.core.TestRunParams.getPathToCurrentArtifactsFolder;
import static taf.core.Utils.createFolder;
import static taf.core.Utils.deleteTempFiles;

public abstract class AbstactTest {

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
}
