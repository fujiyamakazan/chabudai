package net.nanisl.zabuton.chabudai.webapp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import net.nanisl.zabuton.chabudai.ChabuSettings;
import net.nanisl.zabuton.chabudai.webapp.component.GeneralLabel;
import net.nanisl.zabuton.chabudai.webapp.component.GeneralTextField;
import net.nanisl.zabuton.wicketcomponent.FileDropPanel;

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

                ChabuSettings settings = ChabuSettings.get();

                /* アカウントID*/
                IModel<String> modelAccountId = LambdaModel.of(
                    settings::getGoogleAccountId,
                    settings::setGoogleAccountId);
                GeneralTextField textField = new GeneralTextField("accountId", modelAccountId, "アカウントID");
                add(textField);
                textField.setRequired(true); // 入力必須
                textField.add(EmailAddressValidator.getInstance()); // メール形式
                add(new GeneralLabel("labelAccountId", textField));

                /* キーファイル */
                Label labelKeyFileName = new Label("keyFileMsg", Model.of("ここにドロップ!!"));
                add(labelKeyFileName);
                labelKeyFileName.setOutputMarkupId(true);


                /*
                 * ファイルドロップ領域
                 */
                FileDropPanel fileDropPanel = new FileDropPanel("fileDrop") {
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

                        settings.save();

                        for (FileUpload f : fileDropPanel.getFiles()) {
                            String name = f.getClientFileName();
                            log.debug(name);
                            File dest = new File("data/" + name);
                            try {
                                FileUtils.copyInputStreamToFile(f.getInputStream(), dest);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }

                });
                queue(new TextField<String>("bookName",
                    LambdaModel.of(settings::getSpreadsheetName, settings::setSpreadsheetName)));
                queue(new TextField<String>("sheetName",
                    LambdaModel.of(settings::getTasksheetName, settings::setTasksheetName)));

                queue(new Button("import") {

                    /** serialVersionUID */
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onSubmit() {
                        super.onSubmit();
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
