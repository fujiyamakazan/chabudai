package net.nanisl.zabuton.chabudai;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.nanisl.zabuton.util.file.KeyValuesFileObj;
import net.nanisl.zabuton.util.file.SeparateKeyFileObj;

public class ChabuSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(ChabuSettings.class);

    private String googleAccountId;
    private String googleDriveKeyFileName;
    private String spreadsheetName;
    private String tasksheetName;



    public String getGoogleAccountId() {
        return googleAccountId;
    }

    public void setGoogleAccountId(String googleAccountId) {
        this.googleAccountId = googleAccountId;
    }

    public String getGoogleDriveKeyFileName() {
        return googleDriveKeyFileName;
    }

    public void setGoogleDriveKeyFileName(String googleDriveKeyFileName) {
        this.googleDriveKeyFileName = googleDriveKeyFileName;
    }

    public String getSpreadsheetName() {
        return spreadsheetName;
    }

    public void setSpreadsheetName(String spreadsheetName) {
        this.spreadsheetName = spreadsheetName;
    }

    public String getTasksheetName() {
        return tasksheetName;
    }

    public void setTasksheetName(String tasksheetName) {
        this.tasksheetName = tasksheetName;
    }

    public static ChabuSettings get() {
        return ChabuApp.get().getChabuSettings();
    }

    private KeyValuesFileObj keyValues = new SeparateKeyFileObj("data/ApplicationSettings.txt");

    /**
     * コンストラクタ。
     */
    public ChabuSettings() {
        ChabuSettings me = this;
        me.googleAccountId = keyValues.get("googleAccountId");
        me.googleDriveKeyFileName = keyValues.get("googleDriveKeyFileName");
        me.spreadsheetName = keyValues.get("spreadsheetName");
        me.tasksheetName = keyValues.get("tasksheetName");
    }

    /**
     * ファイルに保存します。
     */
    public void save() {
        keyValues.set("googleAccountId", googleAccountId);
        keyValues.set("googleDriveKeyFileName", googleDriveKeyFileName);
        keyValues.set("spreadsheetName", spreadsheetName);
        keyValues.set("tasksheetName", tasksheetName);

        keyValues.write();
    }

    /**
     * 開発中の単体テスト。
     * @param args no-use
     */
    public static void main(String[] args) {
        ChabuSettings me = new ChabuSettings();
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
