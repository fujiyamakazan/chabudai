package net.nanisl.zabuton.chabudai.webapp.component;

import java.io.Serializable;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;

public class GeneralTextField extends TextField<String> {

    private static final long serialVersionUID = 1L;
    private String title;

    public GeneralTextField(String id, IModel<String> model, String title) {
        super(id, model);
        this.title = title;
    }

    @Override
    public void error(IValidationError error) {
        Serializable msg = error.getErrorMessage(new IErrorMessageSource() {
            @Override
            public String getMessage(String key, Map<String, Object> vars) {

                /* TODO ラベル以外を変更するならLocalizerを使用しない  */
                String str = getLocalizer().getString(key, GeneralTextField.this);

                /* ラベルを変更する */
                str = str.replaceAll(Pattern.quote("'${label}' "), title);

                return str;
            }
        });
        error(msg);
    }
}
