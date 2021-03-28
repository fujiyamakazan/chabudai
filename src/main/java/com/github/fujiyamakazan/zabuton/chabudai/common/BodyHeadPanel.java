package com.github.fujiyamakazan.zabuton.chabudai.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BodyHeadPanel extends Panel {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(BodyHeadPanel.class);

    /**
     * コンストラクタ。
     * @param id id
     * @param title 画面タイトル
     */
    public BodyHeadPanel(String id, String title) {
        super(id);

        add(new BodyTitlePanel("bodyTitle",  title));

        IModel<Boolean> modelLocale = Model.of(ChabuSession.get().isKana());
        CheckBox lacaleSwitch = new CheckBox("localeSwitch", modelLocale);
        add(lacaleSwitch);

        lacaleSwitch.add(new OnChangeAjaxBehavior() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                ChabuSession.get().setKana(modelLocale.getObject());
                /* リロード */
                setResponsePage(getPage());
            }

        });
    }
}
