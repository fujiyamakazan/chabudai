package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.Model;

public class SkeletonCheckBoxTemplate extends SkeletonTemplate {

    private boolean isView = false;

    /* start */
    {
        final Model<Boolean> model_component_name = new Model<Boolean>(); // TODO モデルに初期値を登録
        add(new WebMarkupContainer("component_name") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onInitialize() {
                super.onInitialize();

                final String itemName = "item_name";
                CheckBox input = new CheckBox("input", model_component_name) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setVisible(isView == false); // 閲覧モードでないときに表示
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
                });
                /*
                 * 閲覧モード用テキスト
                 */
                Model<String> modelForText = new Model<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return model_component_name.getObject() ? "はい" : "いいえ";
                    }

                };
                add(new Label("text", modelForText ) {
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
