package com.github.fujiyamakazan.zabuton.chabudai.apps;

import java.io.Serializable;

import com.github.fujiyamakazan.zabuton.chabudai.common.ChabuApp;
import com.github.fujiyamakazan.zabuton.util.text.KeyValuesText;
import com.github.fujiyamakazan.zabuton.util.text.SeparateKeyValuesText;

public class TaskSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private String googleAccountId;
    private String googleDriveKeyFileName;
    private String spreadsheetName;
    private String tasksheetName;


    public static TaskSettings get() {
        return ChabuApp.get().getChabuSettings();
    }

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

    private KeyValuesText keyValues = new SeparateKeyValuesText("data/ApplicationSettings.txt");

    /**
     * コンストラクタ。
     */
    public TaskSettings() {
        TaskSettings me = this;
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
}
