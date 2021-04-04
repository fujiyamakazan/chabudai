package com.github.fujiyamakazan.zabuton.chabudai.pg;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;

public class PgIndexPage extends AbstractChabudaiPage {
    private static final long serialVersionUID = 1L;



    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new BookmarkablePageLink<Void>("SkeletonMakerPage", SkeletonMakerPage.class));

        add(new BookmarkablePageLink<Void>("Sample20210328Page", Sample20210328Page.class));
        add(new BookmarkablePageLink<Void>("Sample20210329Page", Sample20210329Page.class));
        add(new BookmarkablePageLink<Void>("Sample20210330Page", Sample20210330Page.class));
    }



    @Override
    protected String getTitle() {
        return "プログラミング";
    }

}
