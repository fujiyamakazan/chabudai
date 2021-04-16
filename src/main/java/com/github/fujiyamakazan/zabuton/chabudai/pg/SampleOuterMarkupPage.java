package com.github.fujiyamakazan.zabuton.chabudai.pg;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class SampleOuterMarkupPage {
//    @Override
    public Markup getAssociatedMarkup() {
        File file = new File("～.html");
        try {
            String html = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            return Markup.of(html);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
    public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {

        /*
         * マークアップだけがバージョンアップしてしまったときの回避策
         */

        if (tag instanceof ComponentTag) {
            String id = ((ComponentTag) tag).getId();
            WebComponent webComponent = new WebComponent(id) {

                private static final long serialVersionUID = 1L;

                @Override
                public IMarkupFragment getMarkup() {
                    return Markup.of("<span wicket:id='" + id + "'></span>");
                }

            };
            return webComponent.setVisible(false);
        }

        if (tag instanceof WicketTag) {

            WicketTag wtag = (WicketTag) tag;
            final String id = wtag.getId();
            WebMarkupContainer w = new WebMarkupContainer(id);
            w.setVisible(false);
            return w;

        }
        return null;
    }
}
