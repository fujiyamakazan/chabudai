package com.github.fujiyamakazan.zabuton.chabudai.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.util.value.IValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.chabudai.common.locale.LocaleModel;

public abstract class AbstractChabudaiPage  extends WebPage implements IComponentResolver {
    private static final long serialVersionUID = 1L;
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void onInitialize() {
        super.onInitialize();

        getSession().bind();

        add(new HeadPanel("head", getTitle()));
        add(new BodyHeadPanel("bodyHead", getTitle()));

    }

    protected abstract String getTitle();

    @Override
    public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {
        if (tag instanceof WicketTag) {
            WicketTag wtag = (WicketTag) tag;
            if (wtag.isMessageTag()) {
                IValueMap attributes = wtag.getAttributes();

                /*
                 * locale属性が指定されていれば、その値を多言語処理のモデルをもつラベルを返します。
                 */
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
