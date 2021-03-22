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
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import net.nanisl.zabuton.chabudai.ChabuSettings;
import net.nanisl.zabuton.chabudai.webapp.panel.LabeledTextFieldPanel;
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

                String itemName = "アカウントＩＤ";
//                IModel<String> model = LambdaModel.of(settings::getGoogleAccountId, settings::setGoogleAccountId);
                add(new LabeledTextFieldPanel("accountId",itemName) {

                        @Override
                        protected void addSettings(TextField<String> textField) {
                            textField.setDefaultModel(LambdaModel.of(settings::getGoogleAccountId, settings::setGoogleAccountId));
                            textField.setRequired(true);
                            textField.add(EmailAddressValidator.getInstance());
                        }

                });

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
