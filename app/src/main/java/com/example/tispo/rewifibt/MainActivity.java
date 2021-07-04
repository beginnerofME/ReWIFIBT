package com.example.tispo.rewifibt;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button Bluetooth_Btn;
    private Button WIFI_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //隱藏狀態列(時間/電量等等)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  //隱藏狀態列(時間/電量等等)
        setContentView(R.layout.activity_main);

        //指派WIFI_Btn來開啟視窗
        WIFI_Btn = (Button)findViewById(R.id.WIFI_Btn);
        //註冊按鈕事件
        WIFI_Btn.setOnClickListener(listener);

        //指派Bluetooth_Btn來開啟視窗
        Bluetooth_Btn = (Button)findViewById(R.id.Bluetooth_Btn);
        //註冊按鈕事件
        Bluetooth_Btn.setOnClickListener(listener2);

        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // TODO: The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.
                        } else {
                            // TODO: The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or
                            // other navigational controls.
                        }
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }





//編輯按鈕事件
    private  Button.OnClickListener listener = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            if(v.getId()==R.id.WIFI_Btn){
                //產生視窗物件
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("警告") //設定視窗標題
                        .setMessage("確定使用WIFI模式控制履帶車? ") //設定顯示的文字
                        .setNegativeButton("返回",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                            }
                        }) //當按下"返回"時，不發生任何事
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this , WifiModeIntoActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show(); //顯示對話視窗
            }

        }
    };
    //編輯按鈕事件
    private  Button.OnClickListener listener2 = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            if(v.getId()==R.id.Bluetooth_Btn){
                //產生視窗物件
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("警告") //設定視窗標題
                        .setMessage("確定使用藍芽模式控制履帶車? ") //設定顯示的文字
                        .setNegativeButton("返回",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                            }
                        }) //當按下"返回"時，不發生任何事
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this , BluetoothModeIntoActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show(); //顯示對話視窗
            }

        }
    };
}