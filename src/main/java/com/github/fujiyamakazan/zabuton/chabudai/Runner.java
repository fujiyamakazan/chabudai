package com.github.fujiyamakazan.zabuton.chabudai;

import com.github.fujiyamakazan.zabuton.chabudai.common.ChabuApp;
import com.github.fujiyamakazan.zabuton.runnable.WicketBootByTomcat;
import com.github.fujiyamakazan.zabuton.tool.AbstractWebContainerStarter;
import com.github.fujiyamakazan.zabuton.tool.WebContainerInvokeFrame;

public class Runner {

    /**
     * このアプリケーションを起動します。
     * @param args ignore
     */
    public static void main(String[] args) {

        AbstractWebContainerStarter starter = new WicketBootByTomcat(ChabuApp.class);
        WebContainerInvokeFrame.show("CHABU-DAI", starter);

    }
}
