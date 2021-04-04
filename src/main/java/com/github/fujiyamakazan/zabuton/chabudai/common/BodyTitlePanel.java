package com.github.fujiyamakazan.zabuton.chabudai.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.github.fujiyamakazan.zabuton.chabudai.common.locale.LocaleLabel;

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
        add(new LocaleLabel("title", str));

        add(new BookmarkablePageLink<Void>("goIndex", IndexPage.class) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onInitialize() {
                super.onInitialize();
            }
        });

        add(new Link<Void>("reload") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                /* リロード */
                setResponsePage(getPage());
            }
        });

    }
}
