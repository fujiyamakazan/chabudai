package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.io.Serializable;

import com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker.Skeleton.Type;

public class SkeletonValidator implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 全てのチェックを行います。
     * @return エラーが無ければnull
     */
    public static String check(Skeleton skeleton) {

        String name = skeleton.getName();
        Skeleton.Type type = skeleton.getType();
        Skeleton parent = skeleton.getParent();

        if (type.equals(Type.ROOT)) {
            if (parent != null) {
                throw new RuntimeException();
            }
        } else {
            if (name.endsWith(type.name()) == false) {
                return "名前「" + name + "」は不正です。～[" + type + "]にしてください。";
            }
            if (parent == null) {
                throw new RuntimeException();
            }
            if (type.equals(Type.画面)) {
                if (parent instanceof SkeletonRoot == false) {
                    throw new RuntimeException("画面はRoot直下のみ");
                }
            } else {
                if (parent instanceof SkeletonRoot) {
                    throw new RuntimeException("Root直下は画面のみ");
                }
            }
        }


        if (skeleton.underFrom() == false && Type.isFormComponent(type)) {
            return name + "はフォームコンポーネントですがフォームの配下にありません。";
        }

        switch (type) {

            case ROOT:
                if (skeleton instanceof SkeletonRoot == false) {
                    throw new RuntimeException("型不正");
                }
                break;
            case 画面:
                if (skeleton instanceof SkeletonScreen == false) {
                    throw new RuntimeException("型不正");
                }
                break;
            case ブロック:
                if (skeleton instanceof SkeletonBlock == false) {
                    throw new RuntimeException("型不正");
                }
                break;
            case フォーム:
                if (skeleton instanceof SkeletonForm == false) {
                    throw new RuntimeException("型不正");
                }
                break;
            case リスト:
                if (skeleton instanceof SkeletonList == false) {
                    throw new RuntimeException("型不正");
                }
                break;
            case ラベル:
                if (skeleton instanceof SkeletonLabel == false) {
                    throw new RuntimeException("型不正");
                }
                break;
            case テキストフィールド:
                if (skeleton instanceof SkeletonTextField == false) {
                    throw new RuntimeException("型不正");
                }
                break;
            case セレクトボックス:
                throw new RuntimeException("未実装");

            case チェックボックス:
                throw new RuntimeException("未実装");
            case リンク:
                if (skeleton instanceof SkeletonLink == false) {
                    throw new RuntimeException("型不正");
                }
                break;
            default:
                throw new RuntimeException();
        }

        return null;
    }
}
