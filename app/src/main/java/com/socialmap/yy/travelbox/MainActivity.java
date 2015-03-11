package com.socialmap.yy.travelbox;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.socialmap.yy.travelbox.arclibrary.ArcMenu;
import com.socialmap.yy.travelbox.fragment.SOSDialogFragment;
import com.socialmap.yy.travelbox.service.AccountService;


public class MainActivity extends Activity {
    MapView mMapView = null;
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    private BaiduMap mBaiduMap;
    public TextView mLocationResult,logMsg;
    boolean isFirstLoc = true;// 是否是第一次定位
    private boolean isRequest = false;//手动触发定位请求
    BitmapDescriptor mCurrentMarker;
    // Service
    private AccountService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (AccountService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private static final int[] ITEM_DRAWABLES = { R.drawable.composer_thought, R.drawable.composer_camera,
             R.drawable.composer_with,R.drawable.composer_sleep,R.drawable.composer_place };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //百度地图
        mLocationClient = new LocationClient(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mCurrentMarker = null;
        mBaiduMap.setMyLocationEnabled(true);

        //定位SDK
        mMyLocationListener = new MyLocationListener();
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener( mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        //option.setOpenGps(true);// 开启GPS
        option.setCoorType("bd09ll"); // 编码有三种,gcj02  bd09   bd0911
        option.setScanSpan(2000);//这个是设置定位间隔时间，单位ms
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        //手动定位
        /**  selfloc = (ImageButton)findViewById(R.id.locationself);
         View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
        if (view.equals(selfloc)) {

        requestLocation();}
        }
        };
         selfloc.setOnClickListener(onClickListener);**/  //TODO 方法一

//TODO 方法二
        ((ImageButton) findViewById(R.id.locationself)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requestLocation();
            }
        });


        ImageButton sos = (ImageButton) findViewById(R.id.sos);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);



        ArcMenu arcMenu2 = (ArcMenu) findViewById(R.id.arc_menu_2);

        initArcMenu(arcMenu2, ITEM_DRAWABLES);
        sos.setOnClickListener(new Button.OnClickListener(){//创建监听
            public void onClick(View v) {
                        SOSDialogFragment sos = new SOSDialogFragment();
                        sos.show(getFragmentManager(), "SOSDialog");
                    }
                });
                //绑定账户服务
        bindService(new Intent("com.socialmap.yy.travelbox.ACCOUNT_SERVICE"),
                conn,
                Service.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

// 定位监听
public class MyLocationListener implements BDLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {


        if (location == null || mMapView == null)
            return;
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())

                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        if (isFirstLoc|| isRequest) {
            isFirstLoc = false;
            isRequest = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
        }
        //Receive Location
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
        sb.append("\nradius : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation){
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\ndirection : ");
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append(location.getDirection());
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());

            sb.append("\noperationers : ");
            sb.append(location.getOperators());
        }
        //logMsg(sb.toString());  //TODO 这就是把经纬度传出来的代码
        Log.i("BaiduLocationApiDem", sb.toString());
    }

    //TODO 这个就是接收经纬度的。对应的是189行
    /**public void logMsg(String str) {
     try {
     if (mLocationResult != null)
     mLocationResult.setText(str);  //TODO locationresult就是定位结果，log里面也能查到。这里用的是TEXTVIEW显示，而我们需要的是服务器
     } catch (Exception e) {
     e.printStackTrace();
     }
     }**/


    public void onReceivePoi(BDLocation poiLocation) {
    }

}
    /**
     * 手动请求定位的方法
     */
    public void requestLocation() {
        isRequest = true;

        if(mLocationClient != null && mLocationClient.isStarted()){

            mLocationClient.requestLocation();
        }else{
            Log.d("log", "locClient is null or not started");
        }
    }
    //主界面中菜单点击事件响应
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_my_schedule:
                startActivity(new Intent(this, ScheduleActivity.class));
                break;
            case R.id.action_nearby:
                startActivity(new Intent(this, NearbyActivity.class));
                break;
            case R.id.action_account:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
          /*  case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_sos:
                //show sos dialog
                SOSDialogFragment sos = new SOSDialogFragment();
                sos.show(getFragmentManager(), "SOSDialog");
                break;
            case R.id.action_feedback:
                startActivity(new Intent(this, ComplainActivity.class));
                break;  */
            case R.id.action_message:
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case R.id.action_allteam:
                startActivity(new Intent(this, AllTeamActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }




//TODO SOS

    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    choose(position);
                   // Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();








                }
            });
        }
    }


   public void choose(int position) {
       switch (position) {
           case 0:
               startActivity(new Intent(this, MessageActivity.class));
               break;
           case 1:
               startActivity(new Intent(this, ScheduleActivity.class));
               break;
           case 2:
               startActivity(new Intent(this, ProfileActivity.class));
               break;
           case 3:
               startActivity(new Intent(this, AllTeamActivity.class));
               break;
           case 4:
               startActivity(new Intent(this, NearbyActivity.class));
               break;


       }


   }




}
