package net.nanisl.zabuton.chabudai.webapp;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.Model;

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

                queue(new TextField<String>("accountId", Model.of("")));
                queue(new Label("keyFileMsg", Model.of("")));

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



                        for(FileUpload f: fileDropPanel.getFiles()) {
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
                queue(new TextField<String>("bookName", Model.of("")));
                queue(new TextField<String>("sheetName", Model.of("")));
                queue(new Button("import"));
            }
        });

    }

    @Override
    protected String getTitle() {
        return "タスク管理";
    }

}
