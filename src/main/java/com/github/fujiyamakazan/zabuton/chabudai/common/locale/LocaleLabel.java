package com.github.fujiyamakazan.zabuton.chabudai.common.locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;

import com.github.fujiyamakazan.zabuton.chabudai.common.component.TitledTextField;

public class LocaleLabel extends Label {

    private static final long serialVersionUID = 1L;
    private TitledTextField textField;

    public LocaleLabel(String id, String str) {
        super(id, new LocaleModel(str));
    }

    public LocaleLabel(String id, TitledTextField textField) {
        super(id, new LocaleModel(textField.getTitle()));
        this.textField = textField;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        if (textField != null) {
            add(AttributeModifier.replace("for", textField.getMarkupId()));
        }

    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        if (textField != null) {
            if (textField.hasErrorMessage()) {
                /* エラーがあればclassを付与する */
                add(AttributeModifier.replace("class", "hasError"));
            }
        }

    }



}
