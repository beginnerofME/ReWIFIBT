package com.example.tispo.rewifibt;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.nio.InvalidMarkException;

public class V1WifiControlActivity extends AppCompatActivity {

    private SeekBar SeekBar_Left;
    private SeekBar SeekBar_Right;
    private TextView text1;
    private TextView text2;
    private Switch forward_Switch;
    private Switch backward_Switch;
    private Switch neutral_Switch;
    private ImageButton rotate_Btn_Unclock;
    private ImageButton rotate_Btn_clock;
    private WebView webView;
    private WebView webView2;
    private Switch light_Switch;
    private Switch engine_Switch;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //隱藏狀態列(時間/電量等等)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //隱藏狀態列(時間/電量等等)
        setContentView(R.layout.control_pad_wifi);

        SeekBar_Left = (SeekBar)findViewById(R.id.SeekBar_Left);
        SeekBar_Right = (SeekBar)findViewById(R.id.SeekBar_Right);
        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);
        forward_Switch = (Switch)findViewById(R.id.forward_Switch);
        backward_Switch = (Switch)findViewById(R.id.backward_Switch);
        neutral_Switch = (Switch)findViewById(R.id.neutral_Switch);
        rotate_Btn_clock = (ImageButton)findViewById(R.id.rotate_Btn_clock);
        rotate_Btn_Unclock = (ImageButton)findViewById(R.id.rotate_Btn_Unclock);
        light_Switch = (Switch)findViewById(R.id.Light_Switch);
        engine_Switch = (Switch)findViewById(R.id.Engine_Switch);


        webView = (WebView)findViewById(R.id.webView);
        webView2 = (WebView)findViewById(R.id.webView2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.43.95/gpio/Stall");


        neutral_Switch.setEnabled(false);
        forward_Switch.setEnabled(false);
        backward_Switch.setEnabled(false);
        rotate_Btn_Unclock.setEnabled(false);
        rotate_Btn_clock.setEnabled(false);

        neutral_Switch.setChecked(true);
        forward_Switch.setChecked(false);
        backward_Switch.setChecked(false);

        SeekBar_Left.setEnabled(false);
        SeekBar_Right.setEnabled(false);

        engine_Switch.setChecked(false);

        light_Switch.setChecked(false);
        light_Switch.setEnabled(false);

        SeekBar_Left.setVisibility(View.INVISIBLE);
        SeekBar_Right.setVisibility(View.INVISIBLE);
        rotate_Btn_clock.setVisibility(View.INVISIBLE);
        rotate_Btn_Unclock.setVisibility(View.INVISIBLE);


        engine_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    neutral_Switch.setEnabled(false);
                    neutral_Switch.setChecked(true);
                    SeekBar_Left.setEnabled(false);
                    SeekBar_Right.setEnabled(false);
                    SeekBar_Left.setVisibility(View.VISIBLE);
                    SeekBar_Right.setVisibility(View.VISIBLE);
                    rotate_Btn_clock.setVisibility(View.VISIBLE);
                    rotate_Btn_Unclock.setVisibility(View.VISIBLE);

                    forward_Switch.setEnabled(true);
                    backward_Switch.setEnabled(true);
                    rotate_Btn_clock.setEnabled(true);
                    rotate_Btn_Unclock.setEnabled(true);
                    text1.setText("左檔位:開啟");
                    text2.setText("右檔位:開啟");

                    light_Switch.setEnabled(true);
                    webView.loadUrl("http://192.168.43.95/gpio/NoMove");

                }
                else {
                    SeekBar_Left.setProgress(0);
                    SeekBar_Right.setProgress(0);
                    SeekBar_Left.setEnabled(false);
                    SeekBar_Right.setEnabled(false);
                    forward_Switch.setEnabled(false);
                    forward_Switch.setChecked(false);
                    backward_Switch.setEnabled(false);
                    backward_Switch.setChecked(false);
                    neutral_Switch.setEnabled(false);
                    rotate_Btn_Unclock.setEnabled(false);
                    rotate_Btn_clock.setEnabled(false);
                    text1.setText("左檔位:關閉");
                    text2.setText("右檔位:關閉");
                    SeekBar_Left.setVisibility(View.INVISIBLE);
                    SeekBar_Right.setVisibility(View.INVISIBLE);
                    rotate_Btn_clock.setVisibility(View.INVISIBLE);
                    rotate_Btn_Unclock.setVisibility(View.INVISIBLE);

                    webView.loadUrl("http://192.168.43.95/gpio/Stall");

                    light_Switch.setEnabled(false);

                }
            }
        });



        forward_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    webView.loadUrl("http://192.168.43.95/gpio/R0");
                    webView2.loadUrl("http://192.168.43.95/gpio/L0");
                    forward_Switch.setEnabled(false);
                    neutral_Switch.setEnabled(true);
                    neutral_Switch.setChecked(false);
                    backward_Switch.setEnabled(true);
                    backward_Switch.setChecked(false);
                    SeekBar_Left.setEnabled(true);
                    SeekBar_Right.setEnabled(true);
                    text1.setText("左檔位:0");
                    text2.setText("右檔位:0");

                    rotate_Btn_clock.setEnabled(true);
                    rotate_Btn_Unclock.setEnabled(true);

                    SeekBar_Left.setProgress(0);
                    SeekBar_Left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            text1.setText("左檔位:"+ Integer.toString(progress));
                            webView2.loadUrl("http://192.168.43.95/gpio/L" + Integer.toString(progress));
                            //傳送左邊、向前檔位的訊號
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            Log.e("----------","開始滑動!");
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            Log.e("----------","停止滑動!");
                        }
                    });
                    SeekBar_Right.setProgress(0);
                    SeekBar_Right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            text2.setText("右檔位:"+ Integer.toString(progress));
                            webView.loadUrl("http://192.168.43.95/gpio/R" + Integer.toString(progress));
                            //傳送右邊、向前檔位的訊號
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            Log.e("----------","開始滑動!");
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            Log.e("----------","停止滑動!");
                        }
                    });
                }
                else {
                    //webView.loadUrl("http://192.168.43.89/gpio/NoMove");
                }
            }
        });

        neutral_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    webView.loadUrl("http://192.168.43.95/gpio/R0");
                    webView2.loadUrl("http://192.168.43.95/gpio/L0");
                    forward_Switch.setEnabled(true);
                    forward_Switch.setChecked(false);
                    backward_Switch.setEnabled(true);
                    backward_Switch.setChecked(false);
                    neutral_Switch.setEnabled(false);

                    SeekBar_Left.setProgress(0);
                    SeekBar_Left.setEnabled(false);
                    text1.setText("左檔位:空檔");
                    SeekBar_Right.setProgress(0);
                    SeekBar_Right.setEnabled(false);
                    text2.setText("右檔位:空檔");

                    rotate_Btn_clock.setEnabled(true);
                    rotate_Btn_Unclock.setEnabled(true);

                    //傳送停止馬達的訊號(或者是0出力)
                    //傳送待機中的訊號
                }
                else {
                    //webView.loadUrl("http://192.168.43.89/gpio/NoMove");
                }
            }
        });


        backward_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    webView.loadUrl("http://192.168.43.95/gpio/R0");
                    webView2.loadUrl("http://192.168.43.95/gpio/L0");
                    forward_Switch.setChecked(false);
                    forward_Switch.setEnabled(true);
                    backward_Switch.setChecked(true);
                    backward_Switch.setEnabled(false);
                    neutral_Switch.setEnabled(true);
                    neutral_Switch.setChecked(false);
                    SeekBar_Left.setEnabled(true);
                    SeekBar_Right.setEnabled(true);
                    rotate_Btn_Unclock.setEnabled(true);
                    rotate_Btn_clock.setEnabled(true);
                    text1.setText("左檔位:0");
                    text2.setText("右檔位:0");


                    SeekBar_Left.setProgress(0);
                    SeekBar_Left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            text1.setText("左檔位:-"+ Integer.toString(progress));
                            webView2.loadUrl("http://192.168.43.95/gpio/L-" + Integer.toString(progress));
                            //傳送左邊、後退檔位的訊號
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            Log.e("----------","開始滑動!");
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            Log.e("----------","停止滑動!");
                        }
                    });
                    SeekBar_Right.setProgress(0);
                    SeekBar_Right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            text2.setText("右檔位:-"+ Integer.toString(progress));
                            webView.loadUrl("http://192.168.43.95/gpio/R-" + Integer.toString(progress));
                            //傳送右邊、後退檔位的訊號
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            Log.e("----------","開始滑動!");
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            Log.e("----------","停止滑動!");
                        }
                    });
                }
                else {
                    //webView.loadUrl("http://192.168.43.89/gpio/NoMove");
                }
            }
        });

        light_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    webView.loadUrl("http://192.168.43.95/gpio/LightOn");
                }
                else {
                    webView.loadUrl("http://192.168.43.95/gpio/LightOff");
                }
            }
        });



        rotate_Btn_clock.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        rotate_Btn_Unclock.setPressed(false);
                        rotate_Btn_Unclock.setEnabled(false);
                        SeekBar_Left.setEnabled(false);
                        SeekBar_Right.setEnabled(false);
                        webView.loadUrl("http://192.168.43.95/gpio/LFRB");
                        //傳送逆時針旋轉的訊號
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        if (neutral_Switch.isChecked()){
                            rotate_Btn_Unclock.setEnabled(true);
                            webView.loadUrl("http://192.168.43.95/gpio/NoMove");
                        }
                        else {
                            rotate_Btn_Unclock.setEnabled(true);
                            SeekBar_Left.setEnabled(true);
                            SeekBar_Right.setEnabled(true);
                            webView.loadUrl("http://192.168.43.95/gpio/NoMove");
                            //停止傳送逆時針旋轉的訊號
                            break;
                        }
                    }
                }
                return false;
            }
        });

        rotate_Btn_Unclock.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event2) {
                switch (event2.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        rotate_Btn_clock.setPressed(false);
                        rotate_Btn_clock.setEnabled(false);
                        SeekBar_Left.setEnabled(false);
                        SeekBar_Right.setEnabled(false);
                        webView.loadUrl("http://192.168.43.95/gpio/LBRF");
                        //傳送順時針旋轉的訊號
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        if (neutral_Switch.isChecked()){
                            rotate_Btn_clock.setEnabled(true);
                            webView.loadUrl("http://192.168.43.95/gpio/NoMove");
                        }
                        else {
                            rotate_Btn_clock.setEnabled(true);
                            SeekBar_Left.setEnabled(true);
                            SeekBar_Right.setEnabled(true);
                            webView.loadUrl("http://192.168.43.95/gpio/NoMove");
                            //停止傳送順時針旋轉的訊號
                            break;
                        }
                    }
                }
                return false;
            }
        });



    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(V1WifiControlActivity.this)
                    .setTitle("警告")
                    .setMessage("確定離開駕駛?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            webView.loadUrl("http://192.168.43.95/gpio/NoMove");
                            finish();
                        }
                    })
                    .show();
        }
        return true;
    }




}