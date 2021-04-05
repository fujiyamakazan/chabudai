package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.lang.Generics;

public class SkeletonFormTemplate extends SkeletonTemplate {
    /* start */
    {
        add(new Form<Void>("component_name") {
            private static final long serialVersionUID = 1L;

            /* 閲覧モード用フラグ */
            private boolean isView = false;

            @SuppressWarnings("unused")
            class ChoiceItem implements Serializable{
                private static final long serialVersionUID = 1L;

                private final String id;
                private final String name;

                public ChoiceItem(String id) {
                    this.id = id;
                    this.name = id;
                }
                public ChoiceItem(String id,String name) {
                    this.id = id;
                    this.name = name;
                }
                @Override
                public String toString() {
                    return name;
                }
            }

            @Override
            protected void onInitialize() {
                super.onInitialize();

                add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(this)));

                /* component_nameの子コンポーネント */

                /*
                 * 入力ボタン
                 */
                add(new Button("input") {
                    private static final long serialVersionUID = 1L;
                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setEnabled(isView == false);
                    }

                    @Override
                    public void onSubmit() {
                        super.onSubmit();

                        /*
                         * TODO 単項目ではチェックできないエラーのチェックをします。
                         * エラーを検知したら、errorメソッドにメッセージを入れます。
                         */
                        // String str1 = modelXXX.getValue();
                        // String str2 = modelXXX.getValue();
                        // ・・・
                        // error(エラーメッセージ)
                        // return; // ここで終了

                        /*
                         * 閲覧モードへ変更
                         *  もし、閲覧モード（確認画面）が不要であればそのまま
                         *  主処理を実行ししてもよいです。
                         */
                        isView = true;

                        info("入力内容を確認して「確定」ボタンを押してください。");
                    }
                });
                /*
                 * 確定 TODO 閲覧モード（確認画面）が不要であれば削除
                 */
                add(new Button("commit") {
                    private static final long serialVersionUID = 1L;
                    @Override
                    protected void onInitialize() {
                        super.onInitialize();
                    }

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setEnabled(isView);
                    }

                    @Override
                    public void onSubmit() {
                        super.onSubmit();

                        /*
                         * TODO エラーチェック
                         * データの状態が変わっているかもしれません。「入力ボタン」の時と
                         * 同様のチェックを行います。チェック処理はメソッドなどで共通化することを推奨します。
                         */

                        // error(エラーメッセージ)
                        // return; // ここで終了

                        /* TODO DB処理などを行い、結果をフィードバックします。 */
                        error("処理が失敗しました");
                        success("処理が終了しました");
                    }
                });

                /*
                 * 変更
                 */
                add(new Button("edit") {
                    private static final long serialVersionUID = 1L;
                    @Override
                    protected void onInitialize() {
                        super.onInitialize();
                        setDefaultFormProcessing(false); // バリデーションの結果を無視
                    }

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setEnabled(isView);
                    }

                    @Override
                    public void onSubmit() {
                        super.onSubmit();
                        isView = false;
                    }
                });

                /*
                 * リセットボタン TODO 不要なら削除
                 * 　画面を表示した時点の値に戻す
                 */
                add(new Button("reset") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onInitialize() {
                        super.onInitialize();
                        setDefaultFormProcessing(false); // バリデーションの結果を無視
                    }

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setEnabled(isView == false);
                    }

                    @Override
                    public void onSubmit() {
                        super.onSubmit();
                        for (FormComponent<?> formComponent: formComponents) {
                            formComponent.clearInput();
                        }
                    }
                });

                /*
                 * クリアボタン
                 * 　入力値を削除する
                 */
                add(new Button("clear") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onInitialize() {
                        super.onInitialize();
                        setDefaultFormProcessing(false); // バリデーションの結果を無視
                    }

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setEnabled(isView == false);
                    }

                    @Override
                    public void onSubmit() {
                        super.onSubmit();
                        for (FormComponent<?> formComponent: formComponents) {
                            formComponent.setDefaultModelObject(null);
                        }
                    }
                });

            }

            private List<FormComponent<?>> formComponents = Generics.newArrayList();

            @SuppressWarnings("unused")
            private void resist(FormComponent<?> input) {
                formComponents.add(input);
            }
        }); // component_name ここまで
    }
    /* end */

}
