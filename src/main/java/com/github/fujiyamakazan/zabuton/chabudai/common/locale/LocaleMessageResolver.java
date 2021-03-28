package com.github.fujiyamakazan.zabuton.chabudai.common.locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.util.value.IValueMap;

/**
 * カスタムwicketタグ「locale」を解決するリゾルバです。
 * @author fujiyama
 */
public class LocaleMessageResolver implements IComponentResolver {

    private static final long serialVersionUID = 1L;

    @Override
    public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {

        if (tag instanceof WicketTag) {
            WicketTag wtag = (WicketTag) tag;
            if (wtag.isMessageTag()) {
                IValueMap attributes = wtag.getAttributes();
                String locale = attributes.getString("locale");
                if (StringUtils.isNotEmpty(locale)) {
                    final String id = wtag.getId();

                    Label label = new Label(id, new LocaleModel(locale));
                    label.setRenderBodyOnly(container.getApplication()
                        .getMarkupSettings()
                        .getStripWicketTags());
                    return label;
                }
            }
        }
        return null;
    }

}
