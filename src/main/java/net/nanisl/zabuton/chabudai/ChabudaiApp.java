package net.nanisl.zabuton.chabudai;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;

import net.nanisl.zabuton.app.ZabuApp;
import net.nanisl.zabuton.chabudai.webapp.IndexPage;

public final class ChabudaiApp extends ZabuApp {

    @Override
    protected void init() {
        super.init();

        /*
         * CSPを無効化。
         * <licket:link>タグ内でのjavascritpが制限されるため。
         * https://wicket.apache.org/news/2020/07/15/wicket-9-released.html
         */
        getCspSettings().blocking().disabled();
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
