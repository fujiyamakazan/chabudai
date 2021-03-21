package net.nanisl.zabuton.chabudai.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.nanisl.zabuton.util.file.KeyValuesFileObj;
import net.nanisl.zabuton.util.file.SeparateKeyFileObj;

public class ApplicationSettings {

    private static final Logger log = LoggerFactory.getLogger(ApplicationSettings.class);

    private String googleAccountId;
    private String googleDriveKeyFileName;
    private String spreadsheetName;
    private String tasksheetName;

    /**
     * 開発中の単体テスト。
     * @param args no-use
     */
    public static void main(String[] args) {
        ApplicationSettings me = new ApplicationSettings();
        KeyValuesFileObj keyValues = new SeparateKeyFileObj("data/ApplicationSettings2.txt");

        me.googleAccountId = keyValues.get("googleAccountId");
        me.googleDriveKeyFileName = keyValues.get("googleDriveKeyFileName");
        me.spreadsheetName = keyValues.get("spreadsheetName");
        me.tasksheetName = keyValues.get("tasksheetName");

        log.debug(me.googleAccountId);

        me.googleAccountId = "test";
        log.debug(me.googleAccountId);

        keyValues.set("googleAccountId", me.googleAccountId);
        keyValues.set("googleDriveKeyFileName", me.googleDriveKeyFileName);
        keyValues.set("spreadsheetName", me.spreadsheetName);
        keyValues.set("tasksheetName", me.tasksheetName);

        keyValues.write();
    }

}
