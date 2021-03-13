package net.nanisl.zabuton.chabudai;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication {

    public static void main(String[] args) {

    }

    public Class<? extends Page> getHomePage() {
        //return IndexPage.class;
        return EnPage.class;
    }

}
