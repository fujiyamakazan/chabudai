package com.github.fujiyamakazan.zabuton.chabudai.pg.skeletonmaker;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.util.lang.Generics;

public class SkeletonListTemplate extends SkeletonTemplate {
    /* start */
    {
        add(new WebMarkupContainer("component_name") {
            private static final long serialVersionUID = 1L;

            class ListData implements Serializable {
                private static final long serialVersionUID = 1L;
            }

            @Override
            protected void onInitialize() {
                super.onInitialize();

                List<ListData> datas = Generics.newArrayList();
                datas.add(new ListData());
                datas.add(new ListData());
                datas.add(new ListData());

                add(new ListView<ListData>("list", datas) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void populateItem(ListItem<ListData> item) {
                        item.add(new WebMarkupContainer("itemContainer") {
                            private static final long serialVersionUID = 1L;
                            @Override
                            protected void onInitialize() {
                                super.onInitialize();
                                /* component_nameの子コンポーネント */
                            }
                        });
                    }
                });
            }
        }); // component_name ここまで
    }
    /* end */

}
