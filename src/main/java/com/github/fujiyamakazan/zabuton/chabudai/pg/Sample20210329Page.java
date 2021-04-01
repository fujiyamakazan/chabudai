package com.github.fujiyamakazan.zabuton.chabudai.pg;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.MarkupElement;
import org.apache.wicket.markup.MarkupFactory;
import org.apache.wicket.markup.MarkupResourceStream;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.AbstractMarkupSourcingStrategy;
import org.apache.wicket.markup.html.panel.IMarkupSourcingStrategy;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.markup.resolver.WicketMessageResolver;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.value.IValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;

/**
 *
 *
 * @author fujiyama
 *
 */
public class Sample20210329Page extends AbstractChabudaiPage

    implements IComponentResolver { // ← リゾルバを実装

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(Sample20210329Page.class);

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

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new WebMarkupContainer("abc") {
            /** serialVersionUID */
            private static final long serialVersionUID = 1L;




            @Override
            protected void onInitialize() {
                super.onInitialize();

                add(new Label("in", "IN-STR"));

            }

            @Override
            public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {



                IResourceStream mrs = new IResourceStream() {

                    @Override
                    public Instant lastModifiedTime() {
                        // TODO 自動生成されたメソッド・スタブ
                        return null;
                    }

                    @Override
                    public void setVariation(String variation) {
                        // TODO 自動生成されたメソッド・スタブ

                    }

                    @Override
                    public void setStyle(String style) {
                        // TODO 自動生成されたメソッド・スタブ

                    }

                    @Override
                    public void setLocale(Locale locale) {
                        // TODO 自動生成されたメソッド・スタブ

                    }

                    @Override
                    public Bytes length() {
                        // TODO 自動生成されたメソッド・スタブ
                        return null;
                    }

                    @Override
                    public String getVariation() {
                        // TODO 自動生成されたメソッド・スタブ
                        return null;
                    }

                    @Override
                    public String getStyle() {
                        // TODO 自動生成されたメソッド・スタブ
                        return null;
                    }

                    @Override
                    public Locale getLocale() {
                        // TODO 自動生成されたメソッド・スタブ
                        return null;
                    }

                    @Override
                    public InputStream getInputStream() throws ResourceStreamNotFoundException {

                        return IOUtils.toInputStream("<span wicket:id=\"in\"></span>");
                    }

                    @Override
                    public String getContentType() {
                        // TODO 自動生成されたメソッド・スタブ
                        return null;
                    }

                    @Override
                    public void close() throws IOException {
                        // TODO 自動生成されたメソッド・スタブ

                    }
                };
                MarkupResourceStream markupResourceStream = new MarkupResourceStream(mrs);

                Markup markup;
                try {

                    markup = MarkupFactory.get().newMarkupParser(markupResourceStream).parse();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ResourceStreamNotFoundException e) {
                    throw new RuntimeException(e);
                }

                //new Markup(new MarkupResourceStream(new IResourceStream));


                MarkupStream ms = new MarkupStream(markup);

                //super.onComponentTagBody(markupStream, openTag);
                super.onComponentTagBody(ms, openTag);
            }

            @Override
            public IMarkupFragment getMarkup(Component child) {
                return super.getMarkup(child);
            }

            @Override
            protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
                return new AbstractMarkupSourcingStrategy() {

                    @Override
                    public IMarkupFragment getMarkup(MarkupContainer container, Component child) {
                        return new IMarkupFragment() {

                            @Override
                            public Iterator<MarkupElement> iterator() {
                                // TODO 自動生成されたメソッド・スタブ
                                return null;
                            }

                            @Override
                            public MarkupElement get(int index) {
                                // TODO 自動生成されたメソッド・スタブ
                                return null;
                            }

                            @Override
                            public MarkupResourceStream getMarkupResourceStream() {

                                IResourceStream mrs = new IResourceStream() {

                                    @Override
                                    public Instant lastModifiedTime() {
                                        // TODO 自動生成されたメソッド・スタブ
                                        return null;
                                    }

                                    @Override
                                    public void setVariation(String variation) {
                                        // TODO 自動生成されたメソッド・スタブ

                                    }

                                    @Override
                                    public void setStyle(String style) {
                                        // TODO 自動生成されたメソッド・スタブ

                                    }

                                    @Override
                                    public void setLocale(Locale locale) {
                                        // TODO 自動生成されたメソッド・スタブ

                                    }

                                    @Override
                                    public Bytes length() {
                                        // TODO 自動生成されたメソッド・スタブ
                                        return null;
                                    }

                                    @Override
                                    public String getVariation() {
                                        // TODO 自動生成されたメソッド・スタブ
                                        return null;
                                    }

                                    @Override
                                    public String getStyle() {
                                        // TODO 自動生成されたメソッド・スタブ
                                        return null;
                                    }

                                    @Override
                                    public Locale getLocale() {
                                        // TODO 自動生成されたメソッド・スタブ
                                        return null;
                                    }

                                    @Override
                                    public InputStream getInputStream() throws ResourceStreamNotFoundException {

                                        return IOUtils.toInputStream("<span wicket:id=\"in\"></span>");
                                    }

                                    @Override
                                    public String getContentType() {
                                        // TODO 自動生成されたメソッド・スタブ
                                        return null;
                                    }

                                    @Override
                                    public void close() throws IOException {
                                        // TODO 自動生成されたメソッド・スタブ

                                    }
                                };
                                MarkupResourceStream markupResourceStream = new MarkupResourceStream(mrs);
                                return markupResourceStream;
                            }

                            @Override
                            public int size() {
                                // TODO 自動生成されたメソッド・スタブ
                                return 0;
                            }

                            @Override
                            public IMarkupFragment find(String wicketId) {
                                // TODO 自動生成されたメソッド・スタブ
                                return null;
                            }

                            @Override
                            public String toString(boolean markupOnly) {
                                // TODO 自動生成されたメソッド・スタブ
                                return null;
                            }

                        };
                    }

                };
            }




        });

    }

    /*
     *          カスタムwicketタグ「locale」を解決するリゾルバを追加
        getPageSettings().getComponentResolvers().add(0, new LocaleMessageResolver());
     */



}
