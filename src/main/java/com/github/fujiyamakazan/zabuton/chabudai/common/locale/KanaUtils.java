package com.github.fujiyamakazan.zabuton.chabudai.common.locale;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

public class KanaUtils {

    /**
     * 文字列をかなに変換します。
     *
     * @param string 元の文字列
     * @return かなに変換された文字列
     */
    public static String toKana(String string) {
        try {
            Tokenizer tokenizer = new Tokenizer();
            List<Token> tokens = tokenizer.tokenize(string);
            StringBuilder sb = new StringBuilder();
            for (Token t : tokens) {
                String reading = t.getReading();
                if (StringUtils.equals(reading, "*")) {
                    sb.append(t.getSurface());
                } else {
                    sb.append(toHiragana(reading));
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return string;
        }
    }

    private static String toHiragana(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (0x30A1 <= c && c <= 0x30F3) {
                c -= 0x0060;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
