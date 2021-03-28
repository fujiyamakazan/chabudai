package com.github.fujiyamakazan.zabuton.chabudai.pg;

import java.io.Serializable;

/**
 * サンプルプログラムの例示として使用するダミーです。
 * @author fujiyama
 */
public class DummyDao implements Serializable {

    private static final long serialVersionUID = 1L;

    public boolean check(String id, String pw) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    public boolean checkEmail(String id) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    /**
     * エラーメッセージを返却します。
     * @param level エラーレベル
     * @param code エラーコード
     * @param lang 言語
     * @return エラーメッセージ
     */
    public String getErrorMessage(String level, int code, String lang) {
        switch (code) {
            case 001:
                return "アカウントIDを入力してください.";
            case 002:
                return "アカウントIDの形式が不正です.";
            case 003:
                return "アカウントが不正です.";
            case 004:
                return "パスワードを入力してください.";
            default:
                throw new RuntimeException();
        }
    }
}
