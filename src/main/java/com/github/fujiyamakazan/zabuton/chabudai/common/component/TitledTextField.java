package com.github.fujiyamakazan.zabuton.chabudai.common.component;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 * 項目名付きのテキストフィールドです。
 * @author fujiyama
 */
public class TitledTextField extends TextField<String> {

    private static final long serialVersionUID = 1L;
    private String title;

    public TitledTextField(String id, IModel<String> model, String title) {
        super(id, model);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
