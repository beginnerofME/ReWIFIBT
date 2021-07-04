package com.example.tispo.rewifibt;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class WifiModeIntoActivity extends AppCompatActivity{
    private ImageButton Vehical_No1_Btn;
    private WifiManager wifiManager;
    private TextView Wifi_Check_Enabled_TextView;
    private Button Wifi_Check_Enabled_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //隱藏狀態列(時間/電量等等)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //隱藏狀態列(時間/電量等等)
        setContentView(R.layout.activity_wifi_mode_into);

        //取得WifiManager
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);
        Wifi_Check_Enabled_TextView = (TextView)findViewById(R.id.Wifi_Check_Enabled_TextView);
        Wifi_Check_Enabled_Btn = (Button)findViewById(R.id.Wifi_Check_Enabled_Btn);


        if (!wifiManager.isWifiEnabled()) {
            //偵測到wifi未開啟
            Wifi_Check_Enabled_TextView.setText("偵測到Wifi未開啟,是否開啟?");
            Wifi_Check_Enabled_Btn.setEnabled(true);
            Wifi_Check_Enabled_Btn.setVisibility(View.VISIBLE);
        }
        else {
            //偵測到wifi已開啟
            Wifi_Check_Enabled_TextView.setText("Wifi已開啟");
            Wifi_Check_Enabled_Btn.setEnabled(false);
            Wifi_Check_Enabled_Btn.setVisibility(View.INVISIBLE);
        }
        //這個if/else還要加上如何"持續"偵測wifi是否開啟



        Wifi_Check_Enabled_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(true);
                }
            }
        });

        Vehical_No1_Btn = (ImageButton) findViewById(R.id.Vehical_No1_Btn);
        Vehical_No1_Btn.setOnClickListener(listener);

    }

    private ImageButton.OnClickListener listener = new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){

            if(v.getId()==R.id.Vehical_No1_Btn){
                //產生視窗物件
                new AlertDialog.Builder(WifiModeIntoActivity.this)
                        .setTitle("確認") //設定視窗標題
                        .setMessage("確定駕駛......此車輛? ") //設定顯示的文字
                        .setNegativeButton("返回",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                            }
                        }) //當按下"返回"時，不發生任何事
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Intent intent = new Intent();
                                intent.setClass(WifiModeIntoActivity.this ,V1WifiControlActivity.class);
                                startActivity(intent);


                            }
                        })
                        .show(); //顯示對話視窗
            }

        }

    };


}
