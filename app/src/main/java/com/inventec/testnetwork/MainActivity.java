package com.inventec.testnetwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //网络状态广播
    private NetWorkReceiver netWorkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.STATE_CHANGE");
        filter.addAction("android.Net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        netWorkReceiver  = new NetWorkReceiver ();
        //注册广播接收
        registerReceiver(netWorkReceiver,filter);
    }

    public class NetWorkReceiver extends BroadcastReceiver {

        public int NET_ETHERNET = 1;
        public int NET_WIFI = 2;
        public int NET_NOCONNECT = 0;

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(
                    ConnectivityManager.CONNECTIVITY_ACTION)
                    || action.equals(
                    "android.net.conn.CONNECTIVITY_CHANGE")) {

                switch (isNetworkAvailable(context)) {
                    case 1:
                        Toast.makeText(context, "-----------networktest---------以太网连接", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(context, "-----------networktest---------无线网", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "-----------networktest---------无网络", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }

        private int isNetworkAvailable(Context context) {
            ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ethNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (ethNetInfo != null && ethNetInfo.isConnected()) {
                return NET_ETHERNET;
            } else if (wifiNetInfo != null && wifiNetInfo.isConnected()) {
                return NET_WIFI;
            } else {
                return NET_NOCONNECT;
            }
        }
    }
}
