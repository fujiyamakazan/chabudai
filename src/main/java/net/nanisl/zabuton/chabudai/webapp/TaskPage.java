package net.nanisl.zabuton.chabudai.webapp;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
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

                IModel<String> modelAccountId = LambdaModel.of(
                    settings::getGoogleAccountId,
                    settings::setGoogleAccountId);

                GeneralTextField textField = new GeneralTextField("accountId", modelAccountId, "アカウントID");
                add(textField);
                textField.setRequired(true); // 入力必須とする
                textField.add(EmailAddressValidator.getInstance()); // メール形式とする
                add(new GeneralLabel("labelAccountId", textField));



                queue(new Label("keyFileMsg",
                    LambdaModel.of(settings::getGoogleDriveKeyFileName, settings::setGoogleDriveKeyFileName)));

                /*
                 * ファイルドロップ領域
                 */
                FileDropPanel fileDropPanel = new FileDropPanel("fileDrop");
                queue(fileDropPanel);

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
