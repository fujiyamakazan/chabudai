package com.github.fujiyamakazan.zabuton.chabudai.pg;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;

public class PgIndexPage extends AbstractChabudaiPage {
    private static final long serialVersionUID = 1L;



    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new BookmarkablePageLink<Void>("SkeletonMakerPage", SkeletonMakerPage.class));

        add(new BookmarkablePageLink<Void>("Sample20210328TextFieldPage", Sample20210328TextFieldPage.class));
        add(new BookmarkablePageLink<Void>("Sample20210329MessgeTagPage", Sample20210329MessageTagPage.class));
        add(new BookmarkablePageLink<Void>("Sample20210330DropDownPage", Sample20210330DropDownPage.class));
        add(new BookmarkablePageLink<Void>("Sample20210416TextMarkupPage", Sample20210416TextMarkupPage.class));
    }



    @Override
    protected String getTitle() {
        return "プログラミング";
    }

}
