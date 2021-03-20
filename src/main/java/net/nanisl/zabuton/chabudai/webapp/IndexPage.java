package net.nanisl.zabuton.chabudai.webapp;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class IndexPage extends AbstractChabudaiPage {

    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new BookmarkablePageLink<Void>("goTask", TaskPage.class));
    }

    @Override
    protected String getTitle() {
        return null; // トップページはタイトルなし
    }
}
