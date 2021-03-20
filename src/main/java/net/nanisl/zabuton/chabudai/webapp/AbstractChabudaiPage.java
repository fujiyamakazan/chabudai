package net.nanisl.zabuton.chabudai.webapp;

import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.nanisl.zabuton.chabudai.webapp.panel.BodyTitlePanel;
import net.nanisl.zabuton.chabudai.webapp.panel.HeadPanel;

public abstract class AbstractChabudaiPage  extends WebPage {
    private static final long serialVersionUID = 1L;
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new HeadPanel("head", getTitle()));
        add(new BodyTitlePanel("bodyTitle", getTitle()));

    }

    protected abstract String getTitle();
}
