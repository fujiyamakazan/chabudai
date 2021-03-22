package net.nanisl.zabuton.chabudai.webapp.panel;

import java.io.Serializable;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;

public abstract class LabeledTextFieldPanel extends Panel {

    private static final long serialVersionUID = 1L;

    final private String itemName;

    public LabeledTextFieldPanel(String id, String itemName) {
        super(id);
        this.itemName = itemName;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> textField = new TextField<String>("input") {

            private static final long serialVersionUID = 1L;

            @Override
            public void error(IValidationError error) {
                TextField<String> textField = this;
                Serializable msg = error.getErrorMessage(new IErrorMessageSource() {
                    @Override
                    public String getMessage(String key, Map<String, Object> vars) {

                        /* TODO ラベル以外を変更するならLocalizerを使用しない  */
                        String str = getLocalizer().getString(key, textField);

                        /* ラベルを変更する */
                        str = str.replaceAll(Pattern.quote("'${label}' "), itemName);

                        return str;
                    }
                });
                error(msg);
            }
        };
        add(textField);

        addSettings(textField);



        textField.setOutputMarkupId(true);


        Label label = new Label("label", itemName) {

            private static final long serialVersionUID = 1L;

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

        };
        add(label);
    }

    protected abstract void addSettings(TextField<String> textField);
}
