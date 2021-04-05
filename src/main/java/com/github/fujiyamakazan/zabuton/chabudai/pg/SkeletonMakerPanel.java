package com.github.fujiyamakazan.zabuton.chabudai.pg;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;

import com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker.Skeleton;
import com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker.Skeleton.Type;

public class SkeletonMakerPanel extends Panel {

    private static final long serialVersionUID = 1L;

    private final Skeleton skeleton;

    /**
     * コンストラクタ。
     * @param id wicket:id
     * @param skeleton スケルトンオブジェクト
     */
    public SkeletonMakerPanel(String id, Skeleton skeleton) {
        super(id);
        this.skeleton = skeleton;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Form<Void>("form") {
            private static final long serialVersionUID = 1L;

            private Model<String> modelName;
            private Model<Skeleton.Type> modelKind;

            @Override
            protected void onInitialize() {
                super.onInitialize();

                /*
                 * フィードバック
                 */
                add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(this)));

                /*
                 * 名前
                 */
                modelName = new Model<String>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public String getObject() {
                        return skeleton.getName();
                    }

                    @Override
                    public void setObject(String object) {
                        skeleton.setName(object);
                    }
                };
                TextField<String> inputName = new TextField<String>("name", modelName);
                add(inputName);

                /*
                 * 種類選択
                 */
                modelKind = new Model<Skeleton.Type>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Skeleton.Type getObject() {
                        return skeleton.getType();
                    }

                    @Override
                    public void setObject(Skeleton.Type object) {
                        skeleton.setType(object);
                    }
                };
                DropDownChoice<Skeleton.Type> selectKind = new DropDownChoice<Skeleton.Type>("kind") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onInitialize() {
                        super.onInitialize();

                        setModel(modelKind);

                        if (skeleton.getType().equals(Type.ROOT) || skeleton.getType().equals(Type.画面)) {
                            /*
                             * スケルトン「ROOT」「画面」 は変更不可
                             */
                            setEnabled(false);
                            setChoices(Arrays.asList(Skeleton.Type.values()));

                        } else {
                            /*
                             * その他のスケルトンは「ROOT」「画面」 への変更は不可
                             */
                            List<Skeleton.Type> choices = Generics.newArrayList();
                            for (Skeleton.Type type: Skeleton.Type.values()) {
                                if (type.equals(Type.ROOT) == false && type.equals(Type.画面) == false) {

                                    if (Type.isFormComponent(type) && skeleton.underFrom() == false) {
                                        /* フォームコンポーネントのスケルトンは、上位に「フォーム」が無ければ追加不可 */

                                    } else {
                                        choices.add(type);
                                    }
                                }
                            }
                            setChoices(choices);
                        }

                        setChoiceRenderer(new IChoiceRenderer<Skeleton.Type>() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public Object getDisplayValue(Skeleton.Type object) {
                                return object.name();
                            }

                            @Override
                            public String getIdValue(Skeleton.Type object, int index) {
                                return object.name();
                            }

                            @Override
                            public Skeleton.Type getObject(
                                String id,
                                IModel<? extends List<? extends Skeleton.Type>> choices) {
                                return Skeleton.Type.valueOf(id);
                            }
                        });


                    }
                };
                add(selectKind);

                /*
                 * 更新ボタン
                 */
                add(new Button("edit") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onSubmit() {
                        super.onSubmit();
                    }
                });

                /*
                 * 削除ボタン
                 */
                add(new Button("remove") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setEnabled(skeleton.isRoot());
                    }

                    @Override
                    public void onSubmit() {
                        super.onSubmit();
                        skeleton.getParent().removeChild(skeleton);
                    }
                });

                /*
                 * [+]ボタン 上に追加
                 */
                add(new Button("upper") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();

                        boolean visible = skeleton.getChildren().isEmpty() == false; // 空の時は非表示
                        if (visible) {
                            /* コンテナ以外は利用不可 */
                            if (skeleton.isContainer() == false) {
                                visible = false;
                            }
                        }
                        setVisible(visible);
                    }

                    @Override
                    public void onSubmit() {
                        super.onSubmit();
                        Skeleton newChild = createNew();
                        skeleton.addChildFirst(newChild);
                    }
                });

                /*
                 * [+]ボタン 下に追加
                 */
                add(new Button("under") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        boolean visible = true;
                        if (visible) {
                            /* コンテナ以外は利用不可 */
                            if (skeleton.isContainer() == false) {
                                visible = false;
                            }
                        }
                        setVisible(visible);
                    }

                    @Override
                    public void onSubmit() {
                        super.onSubmit();
                        Skeleton newChild = createNew();
                        skeleton.addChild(newChild);
                    }
                });

                IModel<List<Skeleton>> model = new LoadableDetachableModel<List<Skeleton>>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected List<Skeleton> load() {
                        return skeleton.getChildren();
                    }
                };
                ListView<Skeleton> listView = new ListView<Skeleton>("lv", model) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void populateItem(ListItem<Skeleton> item) {
                        Skeleton child = item.getModelObject();
                        item.add(new SkeletonMakerPanel("child", child));
                    }
                };
                add(listView);

            }

            private Skeleton createNew() {
                final Skeleton newChild;
                if (skeleton.getType().equals(Type.ROOT)) {
                    newChild = new Skeleton("New", skeleton, Type.画面);
                } else {
                    newChild = new Skeleton("New", skeleton, Type.ブロック);
                }
                return newChild;
            }
        });
    }


}
