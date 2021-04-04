package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

/**
 * スケルトンメーカーの画面スケルトンです。
 * @author fujiyama
 *
 */
public class SkeletonScreen extends Skeleton {

    private static final long serialVersionUID = 1L;

    /**
     * 画面のスケルトンをインスタンス化します。
     * @param name 名前
     * @param root 上位スケルトン
     */
    public SkeletonScreen(String name, Skeleton root) {
        super(name, root, Type.画面);
        if (root instanceof SkeletonRoot == false) {
            throw new RuntimeException("不正です。画面はRootの下に配置してください。");
        }
    }

}
