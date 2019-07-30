package taf.core;

import static taf.core.ConfigManager.getPathToTestFilesFolder;

public class TestRunParams {

    public static String timeSnapShot = Utils.getCurrentDate("MM-dd-HH-mm-ss");

    public static String getNameOfFolderWithAllTestRunArtifacts() {
        return "test-run-artifacts/";
    }

    public static String getPathToAllArtifactsFolders() {
        return System.getProperty("user.dir") + "/target/" + getNameOfFolderWithAllTestRunArtifacts();
    }

    public static String getPathToCurrentArtifactsFolder() {
        return getPathToAllArtifactsFolders() + getNameOfCurrentRunLogFolder();
    }

    public static String getNameOfCurrentRunLogFolder() {
        return "run_by" + timeSnapShot + "/";
    }

    public static String getPathToDownloads() {
        return getPathToTestFilesFolder();
    }

    public static String getScreenshotsLocation() {
        return getPathToAllArtifactsFolders();
    }
}
