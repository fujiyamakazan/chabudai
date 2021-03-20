package net.nanisl.zabuton.chabudai.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationSettings {

    private static final Logger log =  LoggerFactory.getLogger(ApplicationSettings.class);

    private String googleAccountId;
    private String googleDriveKeyFileName;
    private String spreadsheetName;
    private String tasksheetName;

    public static void main(String[] args) {
        ApplicationSettings settings = ApplicationSettings.load();
        log.debug(settings.googleAccountId);
        settings.save();
    }



    private static ApplicationSettings load() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    private void save() {
        // TODO 自動生成されたメソッド・スタブ

    }

}
