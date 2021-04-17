package com.github.fujiyamakazan.zabuton.chabudai.pg;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;
import com.github.fujiyamakazan.zabuton.util.text.Utf8Text;

/**
 * @see Sample20210416TextMarkupPanel
 * @author fujiyama
 */
public class Sample20210416TextMarkupPage extends AbstractChabudaiPage {

    private static final long serialVersionUID = 1L;

    static {
        /* 共有フォルダにテキストファイルがあるものとする。 */
        Utf8Text text = new Utf8Text("data/share/Any.html");
        String html = "<html><body>"
            + "<wicket:panel>テキストの中に"
            + "<div wicket:id='dynamic'>ダイナミック</div>なコンポーネントを表示する"
            + "<div wicket:id='additional'>追加項目</div>"
            + "</wicket:panel></body></html>";
        text.write(html);
    }

    @Override
    protected String getTitle() {
        return "外部のHTMＬもマークアップ！";
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Sample20210416TextMarkupPanel("panel"));
    }

}
