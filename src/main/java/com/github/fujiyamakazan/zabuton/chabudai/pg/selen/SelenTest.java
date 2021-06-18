package com.github.fujiyamakazan.zabuton.chabudai.pg.selen;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.github.fujiyamakazan.zabuton.util.HttpConnector;
import com.github.fujiyamakazan.zabuton.util.file.ZipUtils;
import com.github.fujiyamakazan.zabuton.util.file.ZipUtils.UnzipTask;

public class SelenTest {

    public static void main(String[] args) throws Exception {


        File zip = new File("C:\\tmp\\tmpSelenTest.zip");
        if (zip.exists() == false) {
            String driverUrl = "https://msedgedriver.azureedge.net/91.0.864.48/edgedriver_win32.zip";
            HttpConnector.download(driverUrl, zip, "10.2.0.4", "8080");
        }

        File exe = new File("C:\\tmp\\msedgedriver.exe");
        ZipUtils.unzip(zip, new UnzipTask() {

            private static final long serialVersionUID = 1L;

            @Override
            public void run(String entryName, File unZipFile) throws IOException {
                try {
                    if (StringUtils.equals(entryName, exe.getName())) {
                        FileUtils.copyFile(unZipFile, exe);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println(exe.exists());
        System.out.println(exe.getAbsolutePath());
        System.setProperty("webdriver.edge.driver", exe.getAbsolutePath());
        WebDriver driver = new EdgeDriver();
        driver.get("https://google.com");
        //driver.quit();




    }

}
