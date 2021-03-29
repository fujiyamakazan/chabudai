package com.github.fujiyamakazan.zabuton.chabudai.common.locale;

import org.apache.wicket.model.Model;

import com.github.fujiyamakazan.zabuton.chabudai.common.ChabuSession;

/**
 * 多言語処理を行うモデルです。
 * @author fujiyama
 */
public class LocaleModel extends Model<String> {
    private final String str;
    private static final long serialVersionUID = 1L;

    public LocaleModel(String str) {
        this.str = str;
    }

    @Override
    public String getObject() {

        /* 言語が「かな」であれば かな変換 した文字列を返す。 */
        if (ChabuSession.get().isKana()) {
            return KanaUtils.toKana(str);
        }
        return str;
    }

}