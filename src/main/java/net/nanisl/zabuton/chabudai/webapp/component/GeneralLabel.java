package net.nanisl.zabuton.chabudai.webapp.component;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;

public class GeneralLabel extends Label {

    private static final long serialVersionUID = 1L;

    private final GeneralTextField textField;

    /**
     * コンストラクタです。
     * @param id wicket:id
     * @param textField ラベルとして登録するテキストフィールド
     */
    public GeneralLabel(String id, GeneralTextField textField) {
        super(id);
        this.textField = textField;
        textField.setOutputMarkupId(true);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(AttributeModifier.replace("for", textField.getMarkupId()));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        if (textField.hasErrorMessage()) {
            /* エラーがあればclassを付与する */
            add(AttributeModifier.replace("class", "hasError"));
        }
    }
}
