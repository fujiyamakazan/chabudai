package net.nanisl.zabuton.chabudai;

import org.apache.wicket.Page;

import net.nanisl.zabuton.app.ZabuApp;

public final class ChabudaiApp extends ZabuApp {

    public Class<? extends Page> getHomePage() {
        //return IndexPage.class;

        return EnPage.class;
    }
}