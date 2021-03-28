package com.github.fujiyamakazan.zabuton.chabudai.common.locale;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.wicket.Component;
import org.apache.wicket.validation.IErrorMessageSource;

import com.github.fujiyamakazan.zabuton.chabudai.common.ChabuSession;

public class LocaleMessageSource implements IErrorMessageSource {

    private String title;
    private Component component;

    public LocaleMessageSource(String title, Component component) {
        this.title = title;
        this.component = component;
    }

    @Override
    public String getMessage(String key, Map<String, Object> vars) {

        String str = component.getLocalizer().getString(key, component);

        str = str.replaceAll(Pattern.quote("'${label}' "), title);

        str = toLocale(str);

        return str;
    }

    /**
    * 言語が「かな」であれば かな変換 した文字列を返す。
    * @param string 文字列
    * @return かなに変換した文字列
    */
    private static String toLocale(String string) {
        if (ChabuSession.get().isKana()) {
            return KanaUtils.toKana(string);
        }
        return string;
    }
}