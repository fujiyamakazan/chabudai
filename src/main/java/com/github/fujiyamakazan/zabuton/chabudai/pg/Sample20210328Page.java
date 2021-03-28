package com.github.fujiyamakazan.zabuton.chabudai.pg;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;

public class Sample20210328Page extends AbstractChabudaiPage {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(Sample20210328Page.class);

    private static final long serialVersionUID = 1L;

    private DummyDao accountService = new DummyDao();
    private DummyDao messageDao = new DummyDao();

    @Override
    protected void onInitialize() {
        super.onInitialize();

        /*
         * プロパティファイルでメッセージをカスタマイズするパターン
         */
        add(new Form<Void>("form1") {
            private static final long serialVersionUID = 1L;

            private Model<String> modelAccountId = Model.of("");
            private Model<String> modelPassword = Model.of("");

            @Override
            protected void onInitialize() {
                super.onInitialize();

                add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(this)));

                TextField<String> accountId = new TextField<String>("accountId", modelAccountId);
                add(accountId);
                accountId.setRequired(true); // 入力必須とする
                accountId.add(EmailAddressValidator.getInstance()); // Eメール形式チェックする

                add(new PasswordTextField("password", modelPassword));

            }

            @Override
            protected void onSubmit() {
                super.onSubmit();

                String id = modelAccountId.getObject();
                String pw = modelPassword.getObject();

                /* アカウントチェック */
                if (accountService.check(id, pw) == false) {
                    error("アカウントが不正です");
                    return;
                }
            }

        });

        /*
         * 動的なメッセージを使うパターン
         */
        add(new Form<Void>("form2") {
            private static final long serialVersionUID = 1L;

            private Model<String> modelAccountId = Model.of("");
            private Model<String> modelPassword = Model.of("");

            @Override
            protected void onInitialize() {
                super.onInitialize();

                add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(this)));

                TextField<String> accountId = new TextField<String>("accountId", modelAccountId) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void error(IValidationError error) {
                        Serializable msg = error.getErrorMessage(new IErrorMessageSource() {
                            @Override
                            public String getMessage(String key, Map<String, Object> vars) {
                                if (StringUtils.equals(key, "Required")) {
                                    return messageDao.getErrorMessage("ERR", 001, "ja");
                                }
                                if (StringUtils.equals(key, "EmailAddressValidator")) {
                                    return messageDao.getErrorMessage("ERR", 002, "ja");
                                }
                                return null;
                            }
                        });
                        error(msg);
                    }
                };
                add(accountId);
                accountId.setRequired(true); // 入力必須とする
                accountId.add(EmailAddressValidator.getInstance()); // Eメール形式チェックする

                add(new PasswordTextField("password", modelPassword) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void error(IValidationError error) {
                        Serializable msg = error.getErrorMessage(new IErrorMessageSource() {
                            @Override
                            public String getMessage(String key, Map<String, Object> vars) {
                                if (StringUtils.equals(key, "Required")) {
                                    return messageDao.getErrorMessage("ERR", 004, "ja");
                                }
                                return null;
                            }
                        });
                        error(msg);
                    }
                });

            }

            @Override
            protected void onSubmit() {
                super.onSubmit();

                String id = modelAccountId.getObject();
                String pw = modelPassword.getObject();

                /* アカウントチェック */
                if (accountService.check(id, pw) == false) {
                    error(messageDao.getErrorMessage("ERR", 003, "ja"));
                    return;
                }
            }

        });
    }

    @Override
    protected String getTitle() {
        return "テキストフィールドとバリデーションとメッセージと...";
    }

}
