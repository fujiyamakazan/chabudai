package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import com.github.fujiyamakazan.zabuton.util.string.SubstringUtils;

public class SkeletonTextField extends Skeleton {
    private static final long serialVersionUID = 1L;

    private String itemName;

    public SkeletonTextField(String name, Skeleton parent) {
        super(name, parent, Type.テキストフィールド);
        this.itemName = SubstringUtils.left(name, "テキストフィールド");
    }

    @Override
    protected String getHtml(int indent) {
        String html = super.getHtml(indent);
        html = html.replaceAll("item_name", itemName);
        return html;
    }

    @Override
    protected String getJava(int indent) {
        String java = super.getJava(indent);
        java = java.replaceAll("item_name", itemName);
        return java;
    }
}
