package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;

public class SkeletonSelectBoxTemplate extends SkeletonTemplate {

    private boolean isView = false;

    /* start */
    {
        final Model<ChoiceItem> model_component_name = new Model<ChoiceItem>(); // TODO モデルに初期値を登録
        add(new WebMarkupContainer("component_name") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onInitialize() {
                super.onInitialize();

                final String itemName = "item_name";
                /* TODO 選択肢の構築 */
                List<ChoiceItem> choices = Generics.newArrayList();
                for (int i = 0; i < 3; i++) {
                    choices.add(new ChoiceItem(String.valueOf(i), "選択肢" + i));
                }
                //model_component_name.setObject(new ChoiceItem("1")); // TODO 初期選択

                /* セレクトボックス */
                DropDownChoice<ChoiceItem> input = new DropDownChoice<ChoiceItem>("input",
                        model_component_name,
                        choices,
                        new ChoiceRenderer<ChoiceItem>("name", "id")
                        ) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onInitialize() {
                        super.onInitialize();
                        setRequired(true); // TODO 選択必須でなければ除去
                    }
                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setVisible(isView == false); // 閲覧モードでないときに表示
                    }

                    @Override
                    protected String getNullValidDisplayValue() {
                        // TODO 自動生成されたメソッド・スタブ
                        return "abc";
                    }
                    @Override
                    protected String getNullKeyDisplayValue() {
                        return "選択してください"; // TODO
                    }
                    @Override
                    public void error(IValidationError error) {
                        Serializable msg = error.getErrorMessage(new IErrorMessageSource() {
                            @Override
                            public String getMessage(String key, Map<String, Object> vars) {
                                /* TODO 単項目チェックのエラーメッセージを設定 */
                                if (StringUtils.equals(key, "Required")) {
                                    return itemName + "を選択してください。";
                                }
                                return null;
                            }
                        });
                        error(msg);
                    }
                };
                add(input);
                resist(input); // formに登録

                /*
                 * 入力項目に添えるラベル
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
