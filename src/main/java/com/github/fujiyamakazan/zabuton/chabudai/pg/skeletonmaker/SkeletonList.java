package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.util.regex.Pattern;

import com.github.fujiyamakazan.zabuton.util.resource.ResourceUtils;
import com.github.fujiyamakazan.zabuton.util.string.SubstringUtils;

public class SkeletonList extends Skeleton {
    private static final long serialVersionUID = 1L;

    public SkeletonList(String name, Skeleton parent) {
        super(name, parent, Type.リスト);
    }


    @Override
    protected String getJava(int indent) {
        StringBuilder childText = new StringBuilder();
        for (Skeleton child : children) {
            String childJava = child.getJava(indent + 1);
            if (childJava.trim().isEmpty() == false) {
                childText.append(childJava);
            }
        }

        String comonentText = ResourceUtils.getAsUtf8Text(getTemplateJava(), SkeletonTemplate.class);
        comonentText = SubstringUtils.between(comonentText, "/* start */\r\n    {\r\n", "    }\r\n    /* end */");
        comonentText = comonentText.replaceAll("component_name", name);
        comonentText = comonentText.replaceAll(
            Pattern.quote("                                /* " + name + "の子コンポーネント */\r\n"), // インデントの量が他と異なる
            paddinSpaceAllLines(childText.toString()));

        return comonentText;
    }

    @Override
    protected String paddinSpaceAllLines(String lines) {
        String newLines = "";
        for (String line : lines.split("\n")) {
            if (line.isEmpty() == false) {
                newLines += "                        " + line + "\n"; // インデントの量が他と異なる。[item.]を付与。
            }
        }
        return newLines;
    }
}
