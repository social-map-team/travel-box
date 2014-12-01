package com.socialmap.yy.travelbox;

/**
 * Created by gxyzw_000 on 2014/11/29.
 */


    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import android.app.Activity;
    import android.os.Bundle;
    import android.widget.ListView;

    public class historyActivity extends Activity {

        private ListView listView;
        List<String> data ;
        private TimelineAdapter timelineAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

            listView = (ListView) this.findViewById(R.id.listview);
            //listView.setDividerHeight(0);
            timelineAdapter = new TimelineAdapter(this, getData());
            listView.setAdapter(timelineAdapter);

        }

        private List<Map<String, Object>> getData() {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", "这是第1行测试数据");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("title", "这是第2行测试数据");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("title", "这是第3行测试数据");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("title", "这是第4行测试数据");
            list.add(map);
            return list;
        }

    }


