package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import org.apache.wicket.markup.html.WebPage;

public class SkeletonPageTemplate extends WebPage {
    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new SkeletonPanelTemplate("mainPanel"));
    }

}
