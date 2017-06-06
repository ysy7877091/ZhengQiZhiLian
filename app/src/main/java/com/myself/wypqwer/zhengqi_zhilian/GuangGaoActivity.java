package com.myself.wypqwer.zhengqi_zhilian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/6/6.
 */

public class GuangGaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guanggao_layout);
        init();

    }
    private void init(){
        Button button =(Button)findViewById(R.id.in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(r);//停止5s之后执行线程
                Intent intent = new Intent(GuangGaoActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        handler.postDelayed(r, 5000);   //5秒之后执行线程
    }
    Handler handler = new Handler();

   Runnable r= new Runnable(){
        public void run() {
            //显示dialog
            Intent intent = new Intent(GuangGaoActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
