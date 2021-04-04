package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.util.text.Utf8Text;

/**
 * スケルトンを生成するファクトリです。
 * @author fujiyama
 */
public class SkeletonMaker implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(SkeletonMaker.class);

    /**
     * 単体テストをします。
     * @param args ignore
     */
    public static void main(String[] args) {

        File saveData = new File("data/SkeletonMakerPage.txt");
        saveData.getParentFile().mkdirs();
        Utf8Text settings = new Utf8Text(saveData);
        String setteingsText = settings.read();
        Skeleton skeleton = SkeletonMaker.createObject(setteingsText);

        File out = new File(skeleton.outPath());
        String outPath = out.getAbsolutePath();

        for (Skeleton screenObj : skeleton.getChildren()) {
            SkeletonScreen screen = (SkeletonScreen)screenObj;

            /* ファイル作成 */
            screen.makeFile(out, skeleton.getRootPackage());
        }
        log.info("スケルトンを作成しました。" + outPath);

    }

    /**
     * コンポーネントオブジェクトを作成します。
     * @param text 定義テキスト
     * @return コンポーネントオブジェクト
     */
    public static Skeleton createObject(String text) {

        List<String> list = Arrays.asList(text.split("\n"));
        Iterator<String> it = list.iterator();
        String name = it.next().trim();

        final Skeleton skeleton = new SkeletonRoot(name);

        createChildren(it, skeleton);

        return skeleton;
    }

    private static Skeleton createObjectSub(Skeleton parent, String text) {
        List<String> list = Arrays.asList(text.split("\n"));
        Iterator<String> it = list.iterator();
        String name = it.next().trim();

        final Skeleton skeleton;

        if (StringUtils.endsWith(name, "画面")) {
            skeleton = new SkeletonScreen(name, parent);

        } else if (StringUtils.endsWith(name, "フォーム")) {
            skeleton = new SkeletonForm(name, parent);

        } else if (StringUtils.endsWith(name, "リスト")) {
            skeleton = new SkeletonList(name, parent);

        } else if (StringUtils.endsWith(name, "テキストフィールド")) {
            skeleton = new SkeletonTextField(name, parent);

        } else if (StringUtils.endsWith(name, "ブロック")) {
            skeleton = new SkeletonBlock(name, parent);

        } else if (StringUtils.endsWith(name, "リンク")) {
            skeleton = new SkeletonLink(name, parent);

        } else if (StringUtils.endsWith(name, "ラベル")) {
            skeleton = new SkeletonLabel(name, parent);

        } else {
            throw new RuntimeException("スケルトンの種類が判定で決まません。使用できるのは次の接尾子です。"
                + Skeleton.Type.values());

        }

        createChildren(it, skeleton);

        return skeleton;
    }

    private static void createChildren(Iterator<String> it, final Skeleton parent) {
        StringBuilder buffer = new StringBuilder();
        while (it.hasNext()) {
            String line = it.next();
            line = line.substring(1);
            int indent = countIndent(line);
            if (line.isEmpty()) {
                continue;
            }
            if (indent == 0) {
                if (buffer.toString().isEmpty() == false) {
                    parent.addChild(createObjectSub(parent, buffer.toString()));
                    buffer = new StringBuilder();
                }
            }
            buffer.append(line + "\n");
        }
        if (buffer.toString().isEmpty() == false) {
            parent.addChild(createObjectSub(parent, buffer.toString()));
        }
    }

    private static int countIndent(String string) {
        int count = 0;
        for (char c : string.toCharArray()) {
            if (c == '\t') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}
