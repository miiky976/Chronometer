package com.leondechino.timekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnChrome;
    private boolean chrome = true;
    private TextView lblTime;
    private LinearLayout llyFlags;
    Thread cronom;
    Handler h = new Handler();
    private int minutes = 0, seconds = 0, mills = 0;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnChrome = (Button) findViewById(R.id.btnAction);
        lblTime = (TextView) findViewById(R.id.lblTime);
        llyFlags = (LinearLayout) findViewById(R.id.llyFlags);
        cronom = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (!chrome){
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){}
                        mills++;
                        if (mills==999){
                            seconds++;
                            mills=0;
                        }
                        if (seconds==59){
                            minutes++;
                            seconds=0;
                        }
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                lblTime.setText(formatedTime());
                            }
                        });
                    }
                }
            }
        });
        cronom.start();
    }
    public void ChromeButton(View view){
        if (chrome){
            btnChrome.setText(R.string.pause);
            chrome = false;
        } else {
            btnChrome.setText(R.string.start);
            chrome = true;
        }
    }
    public void FlagButton(View view){
        if(!chrome){
            TextView lblStep = new TextView(this);
            flag++;
            lblStep.setText(flag+" - "+formatedTime());
            lblStep.setTextSize(20);
            lblStep.setGravity(Gravity.CENTER);
            llyFlags.addView(lblStep);
        }
    }
    public void ResetButton(View view){
        chrome = true;
        llyFlags.removeAllViews();
        btnChrome.setText("Start");
        lblTime.setText(R.string.time);
        mills = 0;
        seconds = 0;
        minutes = 0;
        flag = 0;
    }
    private String formatedTime(){
        String m = "", s = "", mi = "";
        if (mills<10){
            m="00"+mills;
        } else if (mills < 100) {
            m="0"+mills;
        }else {
            m=""+mills;
        }
        if (seconds<10){
            s="0"+seconds;
        }else{
            s=""+seconds;
        }
        if (minutes<10){
            mi="0"+minutes;
        }else{
            mi=""+minutes;
        }
        return mi+":"+s+":"+m;
    }
}