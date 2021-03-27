package net.nanisl.zabuton.chabudai.webapp;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

import net.nanisl.zabuton.chabudai.ChabuSettings;

public class SpreadSheet implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(SpreadSheet.class);

    /**
     * コンストラクタです。
     * @param settings 設定情報
     */
    public SpreadSheet(ChabuSettings settings) {
        String accountId = settings.getGoogleAccountId();
        File keyFile = new File("data/" + settings.getGoogleDriveKeyFileName());
        try {
            SpreadsheetService service = getService(accountId, keyFile);

            SpreadsheetEntry book = getBook(service, settings.getSpreadsheetName());

            TextConstruct title = book.getWorksheets().get(0).getTitle();
            log.debug(title.toString());

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    private static final List<String> SCOPES = Arrays.asList(
        "https://docs.google.com/feeds",
        "https://spreadsheets.google.com/feeds");

    /**
     * 認証します。
     * @param accountId サービスアカウントID（メール）
     * @param keyFile P12形式の秘密鍵
     * @throws IOException 認証処理が失敗したとき
     * @throws GeneralSecurityException 認証処理が失敗したとき
     */
    private SpreadsheetService getService(String accountId, File keyFile) throws GeneralSecurityException, IOException {

        String applicationName = getClass().getName();
        SpreadsheetService service = new SpreadsheetService(applicationName);
        service.setProtocolVersion(SpreadsheetService.Versions.V3);

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleCredential credential = new GoogleCredential.Builder()
            .setTransport(httpTransport)
            .setJsonFactory(jsonFactory)
            .setServiceAccountId(accountId)
            .setServiceAccountPrivateKeyFromP12File(keyFile)
            .setServiceAccountScopes(SCOPES)
            .build();

        boolean ret = credential.refreshToken();
        log.debug("refreshToken:" + ret);

        if (credential != null) {
            log.debug("AccessToken:" + credential.getAccessToken());
        }
        service.setOAuth2Credentials(credential);

        return service;
    }

    private static final String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

    private SpreadsheetEntry getBook(SpreadsheetService service, String name) throws IOException, ServiceException {
        SpreadsheetQuery sheetQuery = new SpreadsheetQuery(new URL(SPREADSHEET_URL));
        sheetQuery.setTitleQuery(name);
        SpreadsheetFeed feed = service.query(sheetQuery, SpreadsheetFeed.class);
        SpreadsheetEntry ssEntry = null;
        if (feed.getEntries().size() > 0) {
            ssEntry = feed.getEntries().get(0);
        }
        return ssEntry;
    }

}
