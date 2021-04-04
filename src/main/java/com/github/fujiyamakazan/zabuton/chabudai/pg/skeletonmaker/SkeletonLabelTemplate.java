package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class SkeletonLabelTemplate extends SkeletonTemplate {
    /* start */
    {
        add(new Label("component_name", Model.of("component_name")));
    }
    /* end */
}
