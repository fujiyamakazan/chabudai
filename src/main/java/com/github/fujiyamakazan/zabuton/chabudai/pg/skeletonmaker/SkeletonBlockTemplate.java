package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import org.apache.wicket.markup.html.WebMarkupContainer;

public class SkeletonBlockTemplate extends SkeletonTemplate {
    /* start */
    {
        add(new WebMarkupContainer("component_name") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onInitialize() {
                super.onInitialize();
                /* component_nameの子コンポーネント */
            }
        }); // component_name ここまで
    }
    /* end */

}
