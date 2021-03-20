package net.nanisl.zabuton.chabudai.webapp.panel;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import net.nanisl.zabuton.chabudai.webapp.IndexPage;

public class BodyTitlePanel extends Panel {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ。
     * @param id id
     * @param title 画面タイトル
     */
    public BodyTitlePanel(String id, String title) {
        super(id);

        String str = "CHABU-DAI";
        if (StringUtils.isNotEmpty(title)) {
            str = title + " | " + str;
        }
        add(new Label("title", str));

        add(new BookmarkablePageLink<Void>("goIndex", IndexPage.class) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onInitialize() {
                super.onInitialize();
            }
        });
    }
}
