package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import com.github.fujiyamakazan.zabuton.util.string.SubstringUtils;

public class SkeletonLink extends Skeleton {
    private static final long serialVersionUID = 1L;

    private String linkScreenName;

    public SkeletonLink(String name, Skeleton parent) {
        super(name, parent, Type.リンク);
        this.linkScreenName = SubstringUtils.left(name, "へのリンク");
    }

    @Override
    protected String getHtml(int indent) {
        String html = super.getHtml(indent);
        html = html.replaceAll("html_name", linkScreenName + "Page.html");
        return html;
    }

    @Override
    protected String getJava(int indent) {
        String java = super.getJava(indent);
        java = java.replaceAll("WebPage.class,", linkScreenName + "Page.class,");
        return java;
    }
}
