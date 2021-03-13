package net.nanisl.zabuton.chabudai;

import net.nanisl.zabuton.tool.AbstractWebContainerStarter;
import net.nanisl.zabuton.tool.WebContainerInvokeFrame;
import net.nanisl.zabuton.tool.WicketBootByTomcat;

public class Runner {

    /**
     * このアプリケーションを起動します。
     * @param args ignore
     */
    public static void main(String[] args) {

        AbstractWebContainerStarter starter = new WicketBootByTomcat(ChabudaiApp.class);
        WebContainerInvokeFrame.show("ちゃぶ台", starter);

    }
}
