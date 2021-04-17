package com.github.fujiyamakazan.zabuton.chabudai.pg;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.markup.resolver.WicketMessageResolver;
import org.apache.wicket.util.value.IValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;

/**
 * wicketのメッセージタグを２つ実装してみました。
 * プロパティファイルの値を表示する方法が一般的なものです。
 * 動的に決定した値を表示する方法も試しました。
 * ・・・が、画面クラスで実装するなら、Labelでいいじゃんって話しです。
 * もっと便利な使い方を模索中です。
 *
 * @author fujiyama
 */
public class Sample20210329MessageTagPage extends AbstractChabudaiPage

    implements IComponentResolver { // ← リゾルバを実装

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(Sample20210329MessageTagPage.class);

    private static final long serialVersionUID = 1L;

    @Override
    protected String getTitle() {
        return "Wicket Messageタグ or more";
    }

    private DummyDao messageDao = new DummyDao();

    @Override
    public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {

        if (tag instanceof WicketTag) {

            WicketTag wtag = (WicketTag) tag;

            if (wtag.isMessageTag()) {
                /* <wicket:message> のとき */

                IValueMap attributes = wtag.getAttributes();

                @SuppressWarnings("unused")
                String messageKey = attributes.getString(WicketMessageResolver.KEY_ATTRIBUTE);

                String ex = attributes.getString("ex");


                if (StringUtils.equals(ex, "ex")) {


                    final String id = wtag.getId();

                    /* 任意の文字列に変換する */
                    String message = messageDao.getMessage(DummyDao.MsgType.MSG, 001, "ja");

                    Label label = new Label(id, message);

                    return label;
                }
            }
        }
        return null;
    }
}
