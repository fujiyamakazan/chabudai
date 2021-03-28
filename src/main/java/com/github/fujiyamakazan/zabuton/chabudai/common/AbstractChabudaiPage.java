package com.github.fujiyamakazan.zabuton.chabudai.common;

import org.apache.wicket.markup.html.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractChabudaiPage  extends WebPage {
    private static final long serialVersionUID = 1L;
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void onInitialize() {
        super.onInitialize();

        getSession().bind();

        add(new HeadPanel("head", getTitle()));
        add(new BodyHeadPanel("bodyHead", getTitle()));

    }

    protected abstract String getTitle();

}
