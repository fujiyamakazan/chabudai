package com.github.fujiyamakazan.zabuton.chabudai.pg.selen;

import java.io.File;

import com.github.fujiyamakazan.zabuton.util.HttpConnector;

public class SelenTest {

    public static void main(String[] args) throws Exception {


        String driverUrl = "https://msedgedriver.azureedge.net/91.0.864.48/edgedriver_win32.zip";

        File f = new File("C:\\tmp\\test.dat");
        HttpConnector.download(driverUrl, f, "10.2.0.4", "8080");



//        File exe = new File("C:\\tmp\\driver\\msedgedriver.exe");
//        System.setProperty("webdriver.edge.driver", exe.getAbsolutePath());
//        WebDriver driver = new EdgeDriver();
//        driver.get("https://google.com");
//        driver.quit();


    }

}
