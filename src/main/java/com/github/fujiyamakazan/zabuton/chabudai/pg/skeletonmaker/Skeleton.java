package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.lang.Generics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.util.resource.ResourceUtils;
import com.github.fujiyamakazan.zabuton.util.string.SubstringUtils;
import com.github.fujiyamakazan.zabuton.util.text.Utf8Text;

/**
 * ソースコードのスケルトンに紐づくオブジェクトです。
 *
 * @author fujiyama
 */
public class Skeleton implements Serializable {
    private static final long serialVersionUID = 1L;

    static final Logger log = LoggerFactory.getLogger(Skeleton.class);

    public enum Type {
        ROOT, 画面, ブロック, フォーム, リスト, ラベル, テキストフィールド, セレクトボックス, チェックボックス, リンク;

        /**
         * フォームコンポーネントか判定します。
         * @param type 種別
         * @return フォームコンポーネントならTrue
         */
        public static boolean isFormComponent(Type type) {
            return type.equals(Type.テキストフィールド)
                || type.equals(Type.セレクトボックス)
                || type.equals(Type.チェックボックス);
        }

        /**
         * コンテナか判定します。
         * @param type 種別
         * @return コンテナならTrue
         */
        public static boolean isContainer(Type type) {
            return type.equals(Type.ROOT)
                || type.equals(Type.画面)
                || type.equals(Type.ブロック)
                || type.equals(Type.フォーム)
                || type.equals(Type.リスト);
        }
    }

    protected String name;

    private Type type;

    private Skeleton parent;

    /**
     * コンストラクタです。
     * @param name 名前
     * @param type 種別
     * @param parent 親コンポーネント
     */
    public Skeleton(String name, Skeleton parent, Type type) {
        this.name = name;
        this.type = type;
        this.parent = parent;

        if (parent != null) {
            if (Type.isContainer(parent.getType()) == false) {
                throw new RuntimeException();
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    protected List<Skeleton> children = Generics.newArrayList();

    public List<Skeleton> getChildren() {
        return children;
    }

    public void addChild(Skeleton skeleton) {
        children.add(skeleton);
    }

    public void addChildFirst(Skeleton skeleton) {
        children.add(0, skeleton);
    }

    public void removeChild(Skeleton skeleton) {
        children.remove(skeleton);
    }

    public Skeleton getParent() {
        return parent;
    }

    /**
     * 「フォーム」の配下かどうかを判定します。
     * @return 配下ならTrue
     */
    public boolean underFrom() {
        if (parent != null) {
            if (parent.type.equals(Type.フォーム)) {
                return true;
            }
            return parent.underFrom(); // 再帰的に判定
        }
        return false;
    }

    public boolean isContainer() {
        return Type.isContainer(type);
    }

    protected String getHtml(int indent) {
        StringBuilder childText = new StringBuilder();
        for (Skeleton child : children) {
            String childHtml = child.getHtml(indent + 1);
            if (childHtml.trim().isEmpty() == false) {
                childText.append(childHtml);
            }
        }
        String comonentText = ResourceUtils.getAsUtf8Text(getTemplateHtml(), SkeletonTemplate.class);
        comonentText = SubstringUtils.between(comonentText, "<!-- start -->\r\n", "\t<!-- end -->");
        comonentText = comonentText.replaceAll("component_name", name);
        comonentText = comonentText.replaceAll("ComponentName", name);
        comonentText = comonentText.replaceAll(
            Pattern.quote("\t\t<div>" + name + "の子コンポーネント</div>\r\n"),
            paddingTabAllLines(childText.toString()));

        return comonentText;
    }

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
        comonentText = comonentText.replaceAll("ComponentName", name);
        comonentText = comonentText.replaceAll(
            Pattern.quote("                /* " + name + "の子コンポーネント */\r\n"),
            paddinSpaceAllLines(childText.toString()));

        return comonentText;
    }

    /**
     * インポート文を取得します。
     * @return インポート文（子階層の分も含む）
     */
    public List<String> getImport() {
        List<String> imports = Generics.newArrayList();
        for (Skeleton child : children) {
            imports.addAll(child.getImport());
        }

        String comonentText = ResourceUtils.getAsUtf8Text(getTemplateJava(), SkeletonTemplate.class);
        for (String line : comonentText.split("\n")) {
            if (line.startsWith("import ")) {
                imports.add(line);
            }
        }

        return imports;
    }

    protected final String getTemplateHtml() {
        return getClass().getSimpleName() + "Template.html";
    }

    protected final String getTemplateJava() {
        return getClass().getSimpleName() + "Template.java";
    }

    protected String paddingTabAllLines(String lines) {
        String newLines = "";
        for (String line : lines.split("\n")) {
            if (line.isEmpty() == false) {
                newLines += "\t" + line + "\n";
            }
        }
        return newLines;
    }

    protected String paddinSpaceAllLines(String lines) {
        String newLines = "";
        for (String line : lines.split("\n")) {
            if (line.isEmpty() == false) {
                newLines += "        " + line + "\n";
            }
        }
        return newLines;
    }

    /**
     * インデントを付与することができるtoStringです。
     * @param indent インデント
     * @return 文字列
     */
    public String toString(int indent) {
        String space = StringUtils.repeat('\t', indent);
        StringBuffer sb = new StringBuffer();
        sb.append(space + name + "\n");
        for (Skeleton child : children) {
            sb.append(child.toString(indent + 1));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(0);
    }

    /**
     * スケルトンを出力するフォルダを返します。
     * @return スケルトンを出力するフォルダ
     */
    public String outPath() {
        String pack = getRootPackage();
        String pathname = "src/main/java/" + pack.replaceAll(Pattern.quote("."), "/");
        File file = new File(pathname);
        return file.getAbsolutePath();
    }

    /**
     * ルートのパッケージを返します。
     * @return ルートのパッケージ
     */
    public String getRootPackage() {
        if (this instanceof SkeletonRoot == false) {
            return parent.outPath();
        }
        return name;
    }

    public boolean hasAnyScreen() {
        return this instanceof SkeletonRoot && children.isEmpty() == false;
    }


    private static final String PANEL_TEMPLATE_JAVA = "SkeletonPanelTemplate.java";
    private static final String PANEL_TEMPLATE_HTML = "SkeletonPanelTemplate.html";
    private static final String PAGE_TEMPLATE_JAVA = "SkeletonPageTemplate.java";
    private static final String PAGE_TEMPLATE_HTML = "SkeletonPageTemplate.html";


    /**
     * テキスト定義からファイルを生成します。
     * @param out 作成場所
     * @param pack パッケージ
     */
    public void makeFile(File out, String pack) {
        {
            String pageText = ResourceUtils.getAsUtf8Text(PAGE_TEMPLATE_HTML, SkeletonTemplate.class);
            pageText = pageText.replaceAll(Pattern.quote("${name}"), name);

            Utf8Text pageHtml = new Utf8Text(new File(out, name + "Page.html"));
            pageHtml.write(pageText);
        }

        {
            String text = ResourceUtils.getAsUtf8Text(PAGE_TEMPLATE_JAVA, SkeletonTemplate.class);
            text = text.replaceAll(Pattern.quote("Skeleton"), name);
            text = text.replaceAll(Pattern.quote("Template"), "");
            text = text.replaceAll(Pattern.quote(
                "package " + SkeletonTemplate.class.getPackageName()), "package " + pack);

            Utf8Text pageJava = new Utf8Text(new File(out, name + "Page.java"));
            pageJava.write(text);
        }

        {
            String text = ResourceUtils.getAsUtf8Text(PANEL_TEMPLATE_HTML, SkeletonTemplate.class);
            text = text.replaceAll(Pattern.quote("${name}"), name);
            StringBuilder childHtmlText = new StringBuilder();
            for (Skeleton child : children) {
                childHtmlText.append(child.getHtml(1));
            }
            text = text.replaceAll(
                Pattern.quote("\t<div>コンポーネント</div>"), childHtmlText.toString());

            Utf8Text panelHtml = new Utf8Text(new File(out, name + "Panel.html"));
            panelHtml.write(text);
        }
        {

            String text = ResourceUtils.getAsUtf8Text(PANEL_TEMPLATE_JAVA, SkeletonTemplate.class);
            text = text.replaceAll(Pattern.quote("Skeleton"), name);
            text = text.replaceAll(Pattern.quote("Template"), "");
            text = text.replaceAll(Pattern.quote(
                "package " + SkeletonTemplate.class.getPackageName()), "package " + pack);

            StringBuilder childJavaText = new StringBuilder();
            for (Skeleton child : children) {
                childJavaText.append(child.getJava(1));
            }
            text = text.replaceAll(
                Pattern.quote("        /* コンポーネント */"),
                childJavaText.toString());

            /* インポート文の補完 */
            StringBuilder sb = new StringBuilder();
            for (Skeleton child : children) {
                for (String importLine: child.getImport()) {
                    if (sb.indexOf(importLine) == -1) {
                        sb.append(importLine + "\n");
                    }
                }
            }
            int importIndex = text.indexOf("import ");
            StringBuilder sbPanelJavaText = new StringBuilder(text);
            sbPanelJavaText.insert(importIndex, sb.toString());
            text = sbPanelJavaText.toString();

            Utf8Text panelJava = new Utf8Text(new File(out, name + "Panel.java"));
            panelJava.write(text);
        }
    }



}