package com.github.fujiyamakazan.zabuton.chabudai.pg;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.lang.Generics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;
import com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker.Skeleton;
import com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker.SkeletonMaker;
import com.github.fujiyamakazan.zabuton.util.text.Utf8Text;

/**
 * スケルトンメーカーを使ってスケルトンを作成します。
 * @author fujiyama
 */
public class SkeletonMakerPage extends AbstractChabudaiPage {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(SkeletonMakerPage.class);

    @Override
    protected String getTitle() {
        return "スケルトンメーカー";
    }

    private Skeleton skeleton;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        File saveData = new File("data/SkeletonMakerPage.txt");
        Utf8Text settings = new Utf8Text(saveData);
        if (saveData.exists() == false) {
            saveData.getParentFile().mkdirs();
            settings.write("com.example.sample[ROOT]");
        }
        String setteingsText = settings.read();
        skeleton = SkeletonMaker.createObject(setteingsText);

        add(new Form<Void>("formMake") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();

                if (StringUtils.startsWith(skeleton.getPackage(), "com.example.sample")) {
                    info("スケルトンを作成するパッケージを決めてください。");

                } else {
                    if (skeleton.hasAnyScreen() == false) {
                        info("[+]ボタンで画面を追加してください。");
                    } else {
                        info("[+]ボタンでコンポーネントを追加してください。");
                    }
                }
            }

            @Override
            protected void onInitialize() {
                super.onInitialize();

                add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(this)));

                SkeletonMakerPanel skeletonMakerPanel = new SkeletonMakerPanel("skeleton", skeleton);
                add(skeletonMakerPanel);

                /*
                 * 一時保存
                 */
                add(new Button("save") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onSubmit() {
                        super.onSubmit();

                        try {

                            skeleton.check();

                        } catch (Exception e) {

                            error(e.getMessage());

                        }

                        if (hasError()) {
                            info("エラーがあるので中止しました。");
                            return;
                        }

                        /* 設定保存 */
                        settings.write(skeleton.getSettingText());

                        log.info("保存しました。" + settings.getFile().getAbsolutePath());
                        success("保存しました。" + settings.getFile().getAbsolutePath());
                    }
                });

                /*
                 * 作成
                 */
                add(new Button("make") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setEnabled(skeleton.hasAnyScreen()); // 画面が1つ以上あるときに作成
                    }

                    @Override
                    public void onSubmit() {
                        super.onSubmit();

                        try {

                            skeleton.check();

                        } catch (Exception e) {

                            error(e.getMessage());

                        }

                        if (hasError()) {
                            info("エラーがあるので中止しました。");
                            return;
                        }

                        /* 設定保存 */
                        settings.write(skeleton.getSettingText());

                        /*
                         * スケルトン作成
                         */
                        File out = new File(skeleton.getOutPath());
                        String outPath = out.getAbsolutePath();

                        if (out.exists() == false) {
                            error("ファイルを書出すフォルダが見つかりません。" + outPath);
                            log.error("ファイルを書出すフォルダが見つかりません。" + outPath);
                            return;
                        }
                        if (out.isDirectory() == false) {
                            error("ファイルを書出す場所がフォルダではありません。" + outPath);
                            log.error("ファイルを書出す場所がフォルダではありません。" + outPath);
                            return;
                        }
                        if (out.listFiles().length > 0) {
                            error("ファイルを書出す場所にすでに何かファイルがあります。" + outPath);
                            log.error("ファイルを書出す場所にすでに何かファイルがあります。" + outPath);
                            return;
                        }

                        /* ファイル作成 */
                        for (Skeleton screen : skeleton.getChildren()) {
                            screen.makeFile(out, skeleton.getPackage());
                        }
                        success("スケルトンを作成しました。" + outPath);
                        log.info("スケルトンを作成しました。" + outPath);

                        String names = "";
                        for (Skeleton screen : skeleton.getChildren()) {
                            names += screen.getName() + "Page,";
                        }
                        String message = "スケルトンを作成しました。\n";
                        message += names;
                        log.info(message);
                        success(message);
                    }
                });
            }
        });

        add(new Form<Void>("formList") {

            private static final long serialVersionUID = 1L;

            private IModel<? extends List<File>> modelList = new LoadableDetachableModel<List<File>>() {
                private static final long serialVersionUID = 1L;

                @Override
                protected List<File> load() {
                    List<File> pages = Generics.newArrayList();
                    File dir = new File(skeleton.getOutPath());
                    if (dir.exists() == false) {
                        return Generics.newArrayList();
                    }
                    for (File f: dir.listFiles()) {
                        if (f.getName().endsWith("Page.java")) {
                            pages.add(f);
                        }
                    }
                    return pages;
                }
            };

            @Override
            protected void onInitialize() {

                super.onInitialize();

                add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(this)));


                add(new ListView<File>("list", modelList) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void populateItem(ListItem<File> item) {

                        File pageJava = item.getModelObject();
                        String pageName = pageJava.getName().replaceAll(Pattern.quote(".java"), "");
                        String setName = pageJava.getAbsolutePath().replaceAll(Pattern.quote("Page.java"), "");

                        String date = new SimpleDateFormat("MM/dd HH:mm").format(new Date(pageJava.lastModified()));
                        item.add(new Label("date", date));
                        item.add(new WebMarkupContainer("link") {
                            private static final long serialVersionUID = 1L;

                            @Override
                            protected void onInitialize() {
                                super.onInitialize();
                                add(new Label("name", pageName));

                                String href = skeleton.getPackage() + "." +  pageName;
                                add(AttributeModifier.replace("href", href));
                            }
                        });
                        item.add(new Button("delete") {

                            private static final long serialVersionUID = 1L;

                            @Override
                            public void onSubmit() {
                                super.onSubmit();

                                /* PanelとPageを削除 */
                                List<File> files = Generics.newArrayList();
                                files.add(new File(setName + "Page.java"));
                                files.add(new File(setName + "Panel.java"));
                                files.add(new File(setName + "Page.html"));
                                files.add(new File(setName + "Panel.html"));

                                for (File f: files) {
                                    if (f.delete()) {
                                        success(f.getName() +  "を削除しました。");
                                    } else {
                                        error(f.getName() +  "の削除に失敗しました。");
                                    }
                                }
                            }
                        });
                    }
                });
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                if (modelList.getObject().isEmpty()) {
                    info("作成済みのスケルトンはありません。");
                }
            }

        });
    }
}
