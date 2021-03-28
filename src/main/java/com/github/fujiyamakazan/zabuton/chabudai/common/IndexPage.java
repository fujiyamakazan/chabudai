package com.github.fujiyamakazan.zabuton.chabudai.common;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import com.github.fujiyamakazan.zabuton.chabudai.apps.TaskPage;
import com.github.fujiyamakazan.zabuton.chabudai.pg.PgIndexPage;

public class IndexPage extends AbstractChabudaiPage {

    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new BookmarkablePageLink<Void>("goTask", TaskPage.class));
        add(new BookmarkablePageLink<Void>("goPg", PgIndexPage.class));
    }

    @Override
    protected String getTitle() {
        return null; // トップページはタイトルなし
    }
}
