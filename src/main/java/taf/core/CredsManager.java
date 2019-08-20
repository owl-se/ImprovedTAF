package taf.core;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import taf.models.User;


import java.util.logging.Logger;

public class CredsManager {

    private final static String credsTag = Constants.CREDS_FILE_MAIN_TAG;

    private static Logger log = Logger.getLogger("");

    private static Config creds;

    public static Config getCreds() {
        return creds;
    }

    public static void uploadCredsValues() {
        log.info("uploading credentials");
        String valFromSystem = System.getProperty("creds");
        String credsFileName = "creds_main";
        if (valFromSystem != null) {
            credsFileName = valFromSystem;
        }
        log.info("creds file is: " + credsFileName);
        creds = ConfigFactory.load(credsFileName);
    }

    public static User getUserFromCredsFile(String userNodeName) {
        log.info("getting user " + userNodeName + " from creds file");
        String path = credsTag + "." + userNodeName + ".";
        return new User(
                creds.getString(path + "username"),
                creds.getString(path + "password"),
                creds.getString(path + "firstname"),
                creds.getString(path + "lastname"),
                creds.getString(path + "email")
        );
    }
}