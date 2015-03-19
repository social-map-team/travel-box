package com.socialmap.yy.travelbox;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.socialmap.yy.travelbox.chat.activity.MainActivity;
import com.socialmap.yy.travelbox.chat.service.XXService;
import com.socialmap.yy.travelbox.chat.view.CustomDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingTabActivity extends Activity implements OnItemClickListener{
    
    private ListView ListView = null;
    
    private List<Map<String,String>> listData = null;
    private SimpleAdapter adapter = null;
   private Button exitbutton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_setting);
        
       ListView = (ListView)findViewById(R.id.setting_list);
        setListData();

        exitbutton=(Button)findViewById(R.id.exit_app);

        adapter = new SimpleAdapter(getApplicationContext(), listData, R.layout.main_tab_setting_list_item , new String[]{"text"}, new int[]{R.id.setting_list_item_text});
        ListView.setAdapter(adapter);
        ListView.setOnItemClickListener(this);






    }
    


   private void setListData(){
        listData = new ArrayList<Map<String, String>>();

        Map<String,String> map = new HashMap<String, String>();
        map.put("text", "帐号管理");
        listData.add(map);

        map = new HashMap<String, String>();
        map.put("text", "消息管理");
        listData.add(map);
        
        map = new HashMap<String, String>();
        map.put("text", "隐私管理");
        listData.add(map);

        map = new HashMap<String, String>();
        map.put("text", "通用设置");
        listData.add(map);

        map = new HashMap<String, String>();
        map.put("text", "SOS管理");
        listData.add(map);

        map = new HashMap<String, String>();
        map.put("text", "关于我们");
        listData.add(map);

        map = new HashMap<String, String>();
        map.put("text", "退出登录");
        listData.add(map);




    }

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		switch (position) {
		case 0:
			Toast.makeText(SettingTabActivity.this, "帐号管理",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Setting1Activity.class));
            break;
		case 1:
			Toast.makeText(SettingTabActivity.this, "消息管理", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Setting2Activity.class));
            break;
		case 2:
			Toast.makeText(SettingTabActivity.this, "隐私管理", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Setting3Activity.class));
            break;
        case 3:
            Toast.makeText(SettingTabActivity.this, "通用设置", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Setting4Activity.class));
            break;
        case 4:
             Toast.makeText(SettingTabActivity.this,"SOS管理",Toast.LENGTH_SHORT).show();
             startActivity(new Intent(this,Setting5Activity.class));
            break;
        case 5:
            Toast.makeText(SettingTabActivity.this, "关于我们", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Setting6Activity.class));
            break;
        case 6:
            Toast.makeText(SettingTabActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
            logoutDialog();
            break;





		default:
			break;
		}
	}


    public void logoutDialog() {
        new CustomDialog.Builder(this)
                .setTitle(this.getString(R.string.open_switch_account))
                .setMessage(
                        this.getString(
                                R.string.open_switch_account_msg))
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                XXService service = MainActivity.mXxService;

                                if (service != null) {
                                    service.logout();// 注销
                                }
                                dialog.dismiss();
                                startActivity(new Intent(SettingTabActivity.this,
                                        LoginActivity.class));
                                SettingTabActivity.this.finish();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
    }





}