package net.nanisl.zabuton.chabudai;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

import net.nanisl.zabuton.app.ZabuApp;
import net.nanisl.zabuton.chabudai.webapp.IndexPage;

/**
 *
 * @author fujiyama
 *
 */
public final class ChabuApp extends ZabuApp {

    public static final String urlJquery = "https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js";

    public static ChabuApp get() {
        return (ChabuApp) ZabuApp.get();
    }


    @Override
    protected void init() {
        super.init();

        /*
         * CSPを無効化。
         * <licket:link>タグ内でのjavascritpが制限されるため。
         * https://wicket.apache.org/news/2020/07/15/wicket-9-released.html
         */
        getCspSettings().blocking().disabled();

        /*
         * jQueryバージョン指定
         */
        getJavaScriptLibrarySettings()
            .setJQueryReference(new UrlResourceReference(
                Url.parse(urlJquery)));
    }

    private ChabuSettings settings = new ChabuSettings();

    public ChabuSettings getChabuSettings() {
        return settings;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return IndexPage.class;
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return RuntimeConfigurationType.DEVELOPMENT;
        // TODO return RuntimeConfigurationType.DEPLOYMENT;
    }
}
