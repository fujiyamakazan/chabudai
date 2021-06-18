package com.github.fujiyamakazan.zabuton.chabudai.pg.selen;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.github.fujiyamakazan.zabuton.util.HttpConnector;
import com.github.fujiyamakazan.zabuton.util.file.ZipUtils;
import com.github.fujiyamakazan.zabuton.util.file.ZipUtils.UnzipTask;

public class SelenTest {

    public static void main(String[] args) throws Exception {


        String driverUrl = "https://msedgedriver.azureedge.net/91.0.864.48/edgedriver_win32.zip";

        File f = new File("C:\\tmp\\tmpSelenTest.zip");
        HttpConnector.download(driverUrl, f, "xx.xx.xx.xx", "8080");


        ZipUtils.unzip(f, new UnzipTask() {

            private static final long serialVersionUID = 1L;

            @Override
            public void run(String entryName, File unZipFile) throws IOException {
                try {
                    FileUtils.copyFile(unZipFile, new File("C:\\tmp\\" + entryName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


//        File exe = new File("C:\\tmp\\driver\\msedgedriver.exe");
//        System.setProperty("webdriver.edge.driver", exe.getAbsolutePath());
//        WebDriver driver = new EdgeDriver();
//        driver.get("https://google.com");
//        driver.quit();


    }

}
