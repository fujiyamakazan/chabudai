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
        ROOT, 画面, ブロック, フォーム, リスト, ラベル,
        テキストフィールド, セレクトボックス, チェックボックス, リンク;

        /**
         * フォームコンポーネントか判定します。
         * @param type 種別
         * @return フォームコンポーネントならTrue
         */
        public static boolean isFormComponent(Type type) {
            return
                type.equals(Type.テキストフィールド)
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

    private String toSkeletonName() {
        switch (type) {
            case ブロック:
                return "SkeletonBlock";
            case フォーム:
                return "SkeletonForm";
            case リスト:
                return "SkeletonList";
            case ラベル:
                return "SkeletonLabel";
            case テキストフィールド:
                return "SkeletonTextField";
            case セレクトボックス:
                return "SkeletonSelectBox";
            case チェックボックス:
                return "SkeletonCheckBox";
            case リンク:
                return "SkeletonLink";
            default:
                throw new RuntimeException();
        }
    }

    private String name;

    private Type type;

    private Skeleton parent;

    private List<Skeleton> children = Generics.newArrayList();

    private String linkScreenName; // リンクコンポーネント用

    private String itemName; // テキストフィールドコンポーネント用

    /**
     * コンストラクタです。
     * @param name 名前
     * @param type 種類
     * @param parent 親コンポーネント
     */
    public Skeleton(String name, Skeleton parent, Type type) {
        this.name = name;
        this.type = type;
        this.parent = parent;

        /* 「種類」と「親」の妥当性検査 */
        try {
            check(name, type, parent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
         * 種類ごとの追加情報を取得
         */
        if (type.equals(Type.リンク)) {
            if (StringUtils.endsWith(name, "へのリンク")) {
                this.linkScreenName = SubstringUtils.left(name, "へのリンク");
            } else {
                throw new RuntimeException("リンク先が特定できません。");
            }
        }
        if (type.equals(Type.テキストフィールド)
                || type.equals(Type.チェックボックス)
                || type.equals(Type.セレクトボックス)
                ) {
            this.itemName = name;
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

    public boolean isRoot() {
        return type.equals(Type.ROOT);
    }

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


    public boolean hasAnyScreen() {
        if (parent != null) {
            return parent.hasAnyScreen();
        }
        return getChildren().isEmpty() == false;
    }

    private String getHtml(int indent) {
        StringBuilder childText = new StringBuilder();
        for (Skeleton child : children) {
            String childHtml = child.getHtml(indent + 1);
            if (childHtml.trim().isEmpty() == false) {
                childText.append(childHtml);
            }
        }
        String text = ResourceUtils.getAsUtf8Text(getTemplateHtml(), SkeletonTemplate.class);
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException(type + "のテンプレートが不正です。");
        }
        text = SubstringUtils.between(text, "<!-- start -->\r\n", "\t<!-- end -->");
        text = text.replaceAll("component_name", name);
        text = text.replaceAll("ComponentName", name);
        text = text.replaceAll("html_name", linkScreenName + "Page.html"); // 「リンク」用
        text = text.replaceAll(
            Pattern.quote("\t\t<div>" + name + "の子コンポーネント</div>\r\n"),
            paddingTabAllLines(childText.toString()));

        return text;
    }

    private String getJava(int indent) {
        StringBuilder childText = new StringBuilder();
        for (Skeleton child : children) {
            String childJava = child.getJava(indent + 1);
            if (childJava.trim().isEmpty() == false) {
                childText.append(childJava);
            }
        }

        String text = ResourceUtils.getAsUtf8Text(getTemplateJava(), SkeletonTemplate.class);
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException(type + "のテンプレートが不正です。");
        }
        text = SubstringUtils.between(text, "/* start */\r\n    {\r\n", "    }\r\n    /* end */");
        text = text.replaceAll("component_name", name);
        text = text.replaceAll("ComponentName", name);
        text = text.replaceAll("WebPage.class,", linkScreenName + "Page.class,"); // 「リンク」用
        text = text.replaceAll("item_name", itemName); // 「テキストフィールド」用
        text = text.replaceAll(Pattern.quote("@SuppressWarnings(\"unused\")"), "");
        if (type.equals(Type.リスト)) {
            text = text.replaceAll(
                    Pattern.quote("                                /* " + name + "の子コンポーネント */\r\n"), // インデントの量が他と異なる
                    paddinSpaceAllLinesForList(childText.toString()));

        } else {
            text = text.replaceAll(
                    Pattern.quote("                /* " + name + "の子コンポーネント */\r\n"),
                    paddinSpaceAllLines(childText.toString()));

        }
        return text;
    }

    private String paddinSpaceAllLines(String lines) {
        String newLines = "";
        for (String line : lines.split("\n")) {
            if (line.isEmpty() == false) {
                newLines += "        " + line + "\n";
            }
        }
        return newLines;
    }

    private String paddinSpaceAllLinesForList(String lines) {
        String newLines = "";
        for (String line : lines.split("\n")) {
            if (line.isEmpty() == false) {
                newLines += "                        " + line + "\n"; // インデントの量が他と異なる。[item.]を付与。
            }
        }
        return newLines;
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

    /**
     * 設定ファイルに書き出すテキストを生成します。
     * @return 設定テキスト
     */
    public String getSettingText() {
        return getSettingText(0);
    }
    /**
     * 設定ファイルに書き出すテキストを生成します。
     * @param indent インデント
     * @return 設定テキスト
     */
    private String getSettingText(int indent) {
        String space = StringUtils.repeat('\t', indent);
        StringBuffer sb = new StringBuffer();

        /* 自身のテキスト */
        sb.append(space + name + "[" + type + "]\n");

        /* 子階層のテキスト */
        for (Skeleton child : children) {
            sb.append(child.getSettingText(indent + 1));
        }
        return sb.toString();
    }

//    @Override
//    public String toString() {
//        return toString(0);
//    }

    private static final String PANEL_TEMPLATE_JAVA = "SkeletonPanelTemplate.java";
    private static final String PANEL_TEMPLATE_HTML = "SkeletonPanelTemplate.html";
    private static final String PAGE_TEMPLATE_JAVA = "SkeletonPageTemplate.java";
    private static final String PAGE_TEMPLATE_HTML = "SkeletonPageTemplate.html";

    protected final String getTemplateHtml() {
        String templateName = toSkeletonName() + "Template.html";
        log.debug(templateName);
        return templateName;
    }
    protected final String getTemplateJava() {
        String templateName = toSkeletonName() + "Template.java";
        log.debug(templateName);
        return templateName;
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



    /**
     * スケルトンを出力するフォルダを返します。
     * @return スケルトンを出力するフォルダ
     */
    public String getOutPath() {
        String pack = getPackage();
        String pathname = "src/main/java/" + pack.replaceAll(Pattern.quote("."), "/");
        File file = new File(pathname);
        return file.getAbsolutePath();
    }

    /**
     * ルートのパッケージを返します。
     * @return ルートのパッケージ
     */
    public String getPackage() {
        if (getParent() != null) {
            return parent.getPackage();
        }
        return name;
    }

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

    /**
     * 妥当性検査をします。
     * @throws Exception 妥当性検査でエラーを検知したとき
     */
    public void check() throws Exception {

        check(name, type, parent);

    }

    /**
     * 妥当性検査をします。
     * @throws Exception 妥当性検査でエラーを検知したとき
     */
    public static void check(String name, Skeleton.Type type, Skeleton parent) throws Exception {

        if (StringUtils.isBlank(name)) {
            throw new Exception("名前が指定されていません。");
        }

        if (type == null) {
            throw new Exception("種類が指定されていません。" + name);
        }

        if (type.equals(Type.ROOT) && parent != null) {
            throw new Exception("ROOTは親コンポーネントを指定できません。" + name);
        }
        if (parent == null) {
            if (type.equals(Type.ROOT) == false) {
                throw new Exception("親コンポーネントが指定されていません。" + name);
            }
        } else {
            if (type.equals(Type.ROOT)) {
                throw new Exception("ROOTは親コンポーネントを指定できません。" + name);
            }
            if (type.equals(Type.画面)) {
                if (parent.getType().equals(Type.ROOT) == false) {
                    throw new Exception("画面はRootの下に配置してください。" + name);
                }
            }
            if (Type.isContainer(parent.getType()) == false) {
                throw new Exception("親コンポーネントがコンテナではありません。" + name);
            }
        }
    }





}