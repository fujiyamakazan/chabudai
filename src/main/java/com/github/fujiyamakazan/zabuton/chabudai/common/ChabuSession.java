package com.github.fujiyamakazan.zabuton.chabudai.common;

import javax.servlet.http.Cookie;

import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

import com.github.fujiyamakazan.zabuton.app.ZabuSession;

/**
 * セッション情報を管理します。
 * @author fujiyama
 */
public class ChabuSession extends ZabuSession {

    private static final long serialVersionUID = 1L;

    /**
     * 言語の種類を定義します。
     */
    public enum Lang {
        GENERAL, KANA
    }

    private Lang lang = Lang.GENERAL;

    public static ChabuSession get() {
        Session session = Session.get();
        return (ChabuSession) session;
    }

    public ChabuSession(Request request) {
        super(request);
        readCookie();
    }

    /**
     * Cookieから設定値を初期化します。
     */
    private void readCookie() {
        WebRequest webRequest = (WebRequest) RequestCycle.get().getRequest();
        Cookie cookie = webRequest.getCookie("lang");
        if (cookie != null) {
            try {
                String value = cookie.getValue();
                setLang(Lang.valueOf(value));
            } catch (Exception e) {
                /* 処理なし */
            }
        }
    }

    /**
     * 言語を返します。
     * @return 言語
     */
    public Lang getLang() {
        return lang;
    }

    /**
     * 言語を設定します。Cookieも更新します。
     * @param lang 言語
     */
    public void setLang(Lang lang) {
        this.lang = lang;
        /*
         * Cookieに変更を登録
         */
        WebResponse response = (WebResponse) RequestCycle.get().getResponse();
        Cookie cookie = new Cookie("lang", lang.toString());
        cookie.setMaxAge(60 * 60 * 24 * 7); // 1週間
        response.addCookie(cookie);
    }

    public Boolean isKana() {
        return Lang.KANA.equals(this.lang);
    }

    /**
     * 「かな」のON/OFFより言語を設定します。
     * @param kana 「かな」がONのときにtrueを指定します。
     */
    public void setKana(Boolean kana) {
        if (kana) {
            setLang(Lang.KANA);
        } else {
            setLang(Lang.GENERAL);
        }
    }



}
