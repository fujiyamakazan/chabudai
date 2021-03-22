package net.nanisl.zabuton.chabudai.webapp;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import net.nanisl.zabuton.chabudai.ChabuSettings;
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

                add(new WebMarkupContainer("accountCnt") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onInitialize() {
                        super.onInitialize();

                        TextField<String> accountId = new TextField<String>("accountId",
                            LambdaModel.of(settings::getGoogleAccountId, settings::setGoogleAccountId)) {

                            private static final long serialVersionUID = 1L;


                            // TODO プロパティファイルを使わずに任意のラベルを設定する

//                            @Override
//                            public void validate() {
//                                super.validate();
//
//                                Iterator<FeedbackMessage> iterator = getFeedbackMessages().iterator();
//                                List<FeedbackMessage> news = Generics.newArrayList();
//                                while(iterator.hasNext()) {
//                                    FeedbackMessage fm = iterator.next();
//                                    log.debug("message:" + fm.toString());
//                                    news.add(fm);
//                                }
//                                getFeedbackMessages().clear();
//                                for (FeedbackMessage f: news) {
//                                    Serializable message = f.getMessage() + "++++";
//                                    getFeedbackMessages().add(new FeedbackMessage(f.getReporter(),message,f.getLevel()));
//                                }
//                            }

                            @Override
                            public void error(IValidationError error) {
//                                super.error(error);
                                //Serializable message = error.getErrorMessage(source);
                                Serializable message = error.getErrorMessage(new IErrorMessageSource() {

                                    @Override
                                    public String getMessage(String key, Map<String, Object> vars) {
                                        //return key + vars.entrySet();
//                                        return key;
                                        return getLocalizer().getString(key, null);
                                    }

                                });

                                if (message == null) {
                                    throw new RuntimeException();
                                }
                                error(message);
                            }

                        };
                        add(accountId);

                        accountId.setRequired(true);
                        accountId.add(new EmailAddressValidator() {

                            private static final long serialVersionUID = 1L;

                            @Override
                            protected IValidationError decorate(IValidationError error,
                                IValidatable<String> validatable) {
                                return super.decorate(error, validatable);
                            }

                        });

                        accountId.setOutputMarkupId(true);

                        Label label = new Label("accountIdLabel", getString("accountId")) {

                            private static final long serialVersionUID = 1L;

                            @Override
                            protected void onInitialize() {
                                super.onInitialize();
                                add(AttributeModifier.replace("for", accountId.getMarkupId()));
                            }

                            @Override
                            protected void onConfigure() {
                                super.onConfigure();
                                if (accountId.hasErrorMessage()) {
                                    add(AttributeModifier.replace("class", "hasError"));
                                }
                            }

                        };
                        add(label);

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
