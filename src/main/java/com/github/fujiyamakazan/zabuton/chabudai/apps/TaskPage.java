package com.github.fujiyamakazan.zabuton.chabudai.apps;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;
import com.github.fujiyamakazan.zabuton.chabudai.common.component.TitledTextField;
import com.github.fujiyamakazan.zabuton.chabudai.common.locale.LocaleLabel;
import com.github.fujiyamakazan.zabuton.chabudai.common.locale.LocaleMessageSource;
import com.github.fujiyamakazan.zabuton.wicketcomponent.FileDropPanel;

public class TaskPage extends AbstractChabudaiPage {
    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        queue(new Form<Void>("form") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onInitialize() {
                super.onInitialize();

                add(new FeedbackPanel("feedbak"));

                TaskSettings settings = TaskSettings.get();

                /* アカウントID*/
                IModel<String> modelAccountId = LambdaModel.of(
                    settings::getGoogleAccountId,
                    settings::setGoogleAccountId);

                TitledTextField textField = new TitledTextField("accountId", modelAccountId, "アカウントID") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void error(IValidationError error) {
                        Serializable msg = error.getErrorMessage(new LocaleMessageSource(getTitle(), this));
                        error(msg);
                    }
                };

                add(textField);
                textField.setRequired(true); // 入力必須
                textField.add(EmailAddressValidator.getInstance()); // メール形式
                add(new LocaleLabel("labelAccountId", textField));

                /* キーファイル */
                Label labelKeyFileName = new Label("keyFileMsg", Model.of("ここにドロップ!!"));
                add(labelKeyFileName);
                labelKeyFileName.setOutputMarkupId(true);

                /*
                 * ファイルドロップ領域
                 */
                FileDropPanel fileDropPanel = new FileDropPanel("fileDrop", true) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void afterFileUpload(AjaxRequestTarget target, List<FileUpload> files) {
                        labelKeyFileName.setDefaultModelObject("");
                        if (files.isEmpty() == false) {
                            labelKeyFileName.setDefaultModelObject(files.get(0).getClientFileName());
                        }
                        target.add(labelKeyFileName);
                    }
                };
                queue(fileDropPanel);

                /*
                 * 設定保存ボタン
                 */
                queue(new Button("saveSettings") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onSubmit() {
                        super.onSubmit();

                        /* ファイル処理 */
                        FileUpload keyFile = fileDropPanel.getFileSingle();
                        if (keyFile != null) {
                            File dest = new File("data/" + keyFile.getClientFileName());
                            try {
                                FileUtils.copyInputStreamToFile(keyFile.getInputStream(), dest);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            settings.setGoogleDriveKeyFileName(keyFile.getClientFileName());
                        }
                        /* 設定保存 */
                        settings.save();
                    }
                });
                queue(new TextField<String>("bookName",
                    LambdaModel.of(settings::getSpreadsheetName, settings::setSpreadsheetName)));
                queue(new TextField<String>("sheetName",
                    LambdaModel.of(settings::getTasksheetName, settings::setTasksheetName)));

                /*
                 * TODO セレクトボックスの仮実装
                 */
                List<String> choices = Arrays.asList(new String[] { "abc", "def" });
                IChoiceRenderer<String> renderer = new IChoiceRenderer<String>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getDisplayValue(String object) {
                        return object;
                    }

                    @Override
                    public String getIdValue(String object, int index) {
                        return object;
                    }

                    @Override
                    public String getObject(String id, IModel<? extends List<? extends String>> choices) {
                        return id;
                    }
                };
                queue(new DropDownChoice<String>("selectSheet", Model.of(""), choices, renderer) {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected String getNullKeyDisplayValue() {
                        return "なし";
                    }

                });

                queue(new Button("import") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onSubmit() {
                        super.onSubmit();
                        @SuppressWarnings("unused")
                        SpreadSheet ss = new SpreadSheet(settings);
                    }

                });
            }
        });

    }

    @Override
    protected String getTitle() {
        return "タスク管理";
    }

}
