package com.myself.wypqwer.zhengqi_zhilian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class WenTi_List extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_listBack;
    private TextView tv_guanBi;
    private ImageView iv_find;
    private ImageView iv_huisanjiao1;
    private ImageView iv_huisanjiao2;
    private ImageView iv_huisanjiao3;
    private ListView lv_wentiList;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private LinearLayout ll_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_ti__list);


        iv_listBack = (ImageView) findViewById(R.id.iv_listBack);
        iv_listBack.setOnClickListener(this);
        tv_guanBi = (TextView) findViewById(R.id.tv_guanBi);
        tv_guanBi.setOnClickListener(this);
        iv_find = (ImageView) findViewById(R.id.iv_find);
        iv_find.setOnClickListener(this);
        ll_1 = (LinearLayout) findViewById(R.id.ll_1);
        ll_2 = (LinearLayout) findViewById(R.id.ll_2);
        ll_3 = (LinearLayout) findViewById(R.id.ll_3);
        //iv_huisanjiao1 = (ImageView) findViewById(R.id.iv_huisanjiao1);
        ll_1.setOnClickListener(this);
       // iv_huisanjiao2 = (ImageView) findViewById(R.id.iv_huisanjiao2);
        ll_2.setOnClickListener(this);
       // iv_huisanjiao3 = (ImageView) findViewById(R.id.iv_huisanjiao3);
        ll_3.setOnClickListener(this);
        lv_wentiList = (ListView) findViewById(R.id.lv_wentiList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_listBack:
                finish();
                break;
            case R.id.tv_guanBi:
                finish();
                break;
            case R.id.ll_1:
                break;
            case R.id.ll_2:
                break;
            case R.id.ll_3:
                break;


        }
    }
}
