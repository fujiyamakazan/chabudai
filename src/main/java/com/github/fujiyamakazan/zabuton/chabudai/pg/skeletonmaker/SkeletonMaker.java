package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker.Skeleton.Type;
import com.github.fujiyamakazan.zabuton.util.string.SubstringUtils;
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

        File out = new File(skeleton.getOutPath());
        String outPath = out.getAbsolutePath();

        for (Skeleton screen : skeleton.getChildren()) {
            /* ファイル作成 */
            screen.makeFile(out, skeleton.getPackage());
        }
        log.info("スケルトンを作成しました。" + outPath);

    }

    /**
     * コンポーネントオブジェクトを作成します。
     * @param text 定義テキスト
     * @return コンポーネントオブジェクト
     */
    public static Skeleton createObject(String text) {
        return createObject(text, null);
    }

    private static Skeleton createObject(String text, Skeleton parent) {

        List<String> list = Arrays.asList(text.split("\n"));
        Iterator<String> it = list.iterator();
        String line = it.next().trim();

        if (line.contains("[") == false && line.endsWith("]")) {
            throw new RuntimeException("名前[種類] の形式になっていません。");
        }

        String name = SubstringUtils.left(line, "[");
        Skeleton.Type type = Type.valueOf(SubstringUtils.between(line, "[", "]"));

        /* 1行目から自身のオブジェクトを生成します。*/
        final Skeleton skeleton = new Skeleton(name, parent, type);

        /* 2行目以降から子階層のオブジェクトを生成します。*/
        StringBuilder buffer = new StringBuilder();
        while (it.hasNext()) {
            line = it.next();
            line = line.substring(1); // インデントを1つ解除
            int indent = countIndent(line);
            if (line.isEmpty()) {
                continue;
            }
            if (indent == 0) {
                if (buffer.toString().isEmpty() == false) {
                    Skeleton child = createObject(buffer.toString(), skeleton); // 再帰処理
                    skeleton.addChild(child);
                    buffer = new StringBuilder();
                }
            }
            buffer.append(line + "\n");
        }
        if (buffer.toString().isEmpty() == false) {
            Skeleton child = createObject(buffer.toString(), skeleton); // 再帰処理
            skeleton.addChild(child);
        }

        return skeleton;
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
