package com.github.fujiyamakazan.zabuton.chabudai.pg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.chabudai.common.AbstractChabudaiPage;

/**
 *
 *
 * @author fujiyama
 *
 */
public class Sample20210330Page extends AbstractChabudaiPage {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(Sample20210330Page.class);

    private static final long serialVersionUID = 1L;

    @Override
    protected String getTitle() {
        return "セレクトボックス/「選んでください」";
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();



//        Map<String, String> choices = new LinkedHashMap<String, String>(); // 登録順を維持するMap
//        List<Pair<String, String>> pairs = Generics.newArrayList();
//        pairs.add(new Pair<String, String>() {
//
//            @Override
//            public String setValue(String value) {
//                // TODO 自動生成されたメソッド・スタブ
//                return null;
//            }
//
//            @Override
//            public String getRight() {
//                // TODO 自動生成されたメソッド・スタブ
//                return null;
//            }
//
//            @Override
//            public String getLeft() {
//                // TODO 自動生成されたメソッド・スタブ
//                return null;
//            }
//        });
//
//        choices.put("Su", "日曜日");
//        choices.put("Mo", "月曜日");
//        choices.put("Tu", "火曜日");
//        choices.put("We", "水曜日");
//        choices.put("Th", "木曜日");
//        choices.put("Fr", "金曜日");
//        choices.put("Sa", "土曜日");
//
//        add(new DropDownChoice<Map.Entry<String, String>>(
//            "select1", new Model<Map.Entry<String, String>>(), choices.entrySet(), new IChoiceRenderer<Map.Entry<String, String>>() {
//
//            @Override
//            public Object getDisplayValue(String object) {
//                // TODO 自動生成されたメソッド・スタブ
//                return null;
//            }
//
//            @Override
//            public String getIdValue(String object, int index) {
//                // TODO 自動生成されたメソッド・スタブ
//                return null;
//            }
//
//            @Override
//            public String getObject(String id, IModel<? extends List<? extends String>> choices) {
//                // TODO 自動生成されたメソッド・スタブ
//                return null;
//            }
//            このメソッドを実装して返さなくてはならなくなってる！
//
//        }));


    }




}
