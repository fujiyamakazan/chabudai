package com.github.fujiyamakazan.zabuton.chabudai.common;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.app.ZabuApp;
import com.github.fujiyamakazan.zabuton.chabudai.apps.TaskSettings;

/**
 * CHABU-DAIのアプリケーションクラスです。
 * WicketApplicationを継承しています。
 * @author fujiyama
 */
public final class ChabuApp extends ZabuApp {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ChabuApp.class);

    public static final String urlJquery = "https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js";

    public static ChabuApp get() {
        return (ChabuApp) ZabuApp.get();
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return IndexPage.class;
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

    /** アプリケーションの設定情報です。 */
    private TaskSettings settings = new TaskSettings();

    /**
     * アプリケーションの設定情報を返します。
     * @return アプリケーションの設定情報
     */
    public TaskSettings getChabuSettings() {
        return settings;
    }

    /**
     * セッション情報を生成します。
     */
    @Override
    public Session newSession(Request request, Response response) {
        return new ChabuSession(request);
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return RuntimeConfigurationType.DEVELOPMENT;
        // TODO return RuntimeConfigurationType.DEPLOYMENT;
    }


}
