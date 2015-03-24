package com.socialmap.yy.travelbox.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.socialmap.yy.travelbox.AboutActivity;
import com.socialmap.yy.travelbox.ComplainActivity;
import com.socialmap.yy.travelbox.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Setting6Activity  extends Activity implements AdapterView.OnItemClickListener {


    private android.widget.ListView ListView = null;

    private List<Map<String, String>> listData = null;
    private SimpleAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_setting);

        ListView = (android.widget.ListView) findViewById(R.id.setting_list);
        setListData();

        adapter = new SimpleAdapter(getApplicationContext(), listData, R.layout.main_tab_setting_list_item, new String[]{"text"}, new int[]{R.id.setting_list_item_text});
        ListView.setAdapter(adapter);
        ListView.setOnItemClickListener(this);
    }


    private void setListData() {
        listData = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "产品简介");
        listData.add(map);

        map = new HashMap<String, String>();
        map.put("text", "检查更新");
        listData.add(map);


        map = new HashMap<String, String>();
        map.put("text", "意见反馈");
        listData.add(map);


    }


    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "暂无可用最新", Toast.LENGTH_LONG).show();
                break;
            case 2:
                startActivity(new Intent(this, ComplainActivity.class));
                break;


        }
    }


}
