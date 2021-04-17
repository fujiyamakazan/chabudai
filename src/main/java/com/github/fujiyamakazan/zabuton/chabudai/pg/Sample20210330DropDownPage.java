package com.github.fujiyamakazan.zabuton.chabudai.pg;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;
import com.github.fujiyamakazan.zabuton.chabudai.common.ChabuSession;
import com.github.fujiyamakazan.zabuton.chabudai.common.locale.KanaUtils;

/**
 * Wicketでセレクトボックスを実装するサンプルです。
 * 「選んでください」は org.apache.wicket.Application_ja.properties に書かれています。
 * Pageクラスのプロパティファイルやオーバーラードメソッドで書換えることができます。
 *
 * @author fujiyama
 */
public class Sample20210330DropDownPage extends AbstractChabudaiPage {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(Sample20210330DropDownPage.class);

    private static final long serialVersionUID = 1L;

    @Override
    protected String getTitle() {
        return "セレクトボックス/「選んでください」";
    }

    private class Choice implements Serializable {

        private static final long serialVersionUID = 1L;

        @SuppressWarnings("unused")
        private String id;
        @SuppressWarnings("unused")
        private String name;
        public Choice(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Form<Void>("form") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onInitialize() {
                super.onInitialize();
                add(new FeedbackPanel("feedback"));

                List<Choice> choices = Generics.newArrayList();
                choices.add(new Choice("Su", "日曜日"));
                choices.add(new Choice("Mo", "月曜日"));
                choices.add(new Choice("Tu", "火曜日"));
                choices.add(new Choice("We", "水曜日"));
                choices.add(new Choice("Th", "木曜日"));
                choices.add(new Choice("Fr", "金曜日"));
                choices.add(new Choice("Sa", "土曜日"));

                IModel<Choice> model1 = new Model<Choice>();
                IChoiceRenderer<Choice> renderer = new ChoiceRenderer<Choice>("name", "id");
                add(new DropDownChoice<Choice>("select1", model1, choices, renderer));

                IModel<Choice> model2 = new Model<Choice>();
                add(new DropDownChoice<Choice>("select2", model2, choices, renderer) {
                    private static final long serialVersionUID = 1L;
                    @Override
                    protected String getNullKeyDisplayValue() {
                        String str = "選択してください。";
                        /* 言語が「かな」であれば かな変換 した文字列を返す。 */
                        if (ChabuSession.get().isKana()) {
                            str = KanaUtils.toKana(str);
                        }
                        return str;
                    }
                });
            }
        });
    }
}
