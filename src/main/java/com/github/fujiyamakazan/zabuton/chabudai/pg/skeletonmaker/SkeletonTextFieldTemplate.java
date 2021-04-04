package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.validator.EmailAddressValidator;

public class SkeletonTextFieldTemplate extends SkeletonTemplate {

    private boolean isView = false;

    /* start */
    {
        final Model<String> model_component_name = new Model<String>();
        add(new WebMarkupContainer("component_name") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onInitialize() {
                super.onInitialize();

                final String itemName = "item_name";

                TextField<String> input = new TextField<String>("input", model_component_name) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onInitialize() {
                        super.onInitialize();

                        /* 単項目チェックの定義 */
                        setRequired(true); // 入力必須とする
                        add(EmailAddressValidator.getInstance()); // Eメール形式チェックする
                    }

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setVisible(isView == false); // 閲覧モードでないときに表示
                    }

                    @Override
                    public void error(IValidationError error) {
                        Serializable msg = error.getErrorMessage(new IErrorMessageSource() {
                            @Override
                            public String getMessage(String key, Map<String, Object> vars) {

                                /* 単項目チェックのエラーメッセージを設定 */
                                if (StringUtils.equals(key, "Required")) {
                                    return itemName + "を入力してください。";
                                }
                                if (StringUtils.equals(key, "EmailAddressValidator")) {
                                    return itemName + "がメールアドレスの形式ではありません。";
                                }
                                return null;
                            }
                        });
                        error(msg);
                    }

                };
                add(input);
                resist(input);

                /*
                 * テキストフィールドに添えるラベル
                 * ・for属性により、クリックするとフォーカスが移動する
                 * ・単項目エラーがあるときに色が変わる。
                 */
                add(new Label("label", itemName) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onInitialize() {
                        super.onInitialize();
                        /* 入力コンポーネントにラベル付けをする */
                        input.setOutputMarkupId(true);
                        add(AttributeModifier.replace("for", input.getMarkupId()));
                    }

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        if (input != null) {
                            if (input.hasErrorMessage()) {
                                /* エラー時に色を変える */
                                add(AttributeModifier.replace("style", "color:red"));
                            } else {
                                add(AttributeModifier.remove("style"));
                            }
                        }
                    }
                });

                /*
                 * 閲覧モード用テキスト
                 */
                add(new Label("text", model_component_name) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setVisible(isView);
                    }

                });

                /* component_nameの子コンポーネント */
            }

        }); // component_name ここまで
    }
    /* end */




}
