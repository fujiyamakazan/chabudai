package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class SkeletonLinkTemplate extends SkeletonTemplate {
    /* start */
    {
        PageParameters paramComponentName = new PageParameters();
        //component_name_param.set("no", "123");
        add(new BookmarkablePageLink<Void>("component_name", WebPage.class, paramComponentName)); // component_name ここまで
    }
    /* end */

}
