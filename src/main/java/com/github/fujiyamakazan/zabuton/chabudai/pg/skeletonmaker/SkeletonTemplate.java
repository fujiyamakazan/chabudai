package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;

public class SkeletonTemplate {
    protected void add(WebMarkupContainer webMarkupContainer) {
    }

    protected void add(Label label) {
    }

    protected void resist(FormComponent<?> input) {
    }
    protected class ChoiceItem implements Serializable{
        private static final long serialVersionUID = 1L;

        @SuppressWarnings("unused")
        private final String id;
        private final String name;

        public ChoiceItem(String id) {
            this.id = id;
            this.name = id;
        }
        public ChoiceItem(String id,String name) {
            this.id = id;
            this.name = name;
        }
        @Override
        public String toString() {
            return name;
        }

    }
}
