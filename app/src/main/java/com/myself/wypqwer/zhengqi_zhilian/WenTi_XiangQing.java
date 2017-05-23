package com.myself.wypqwer.zhengqi_zhilian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WenTi_XiangQing extends AppCompatActivity implements View.OnClickListener{

    private Button xiangQing_back;
    private TextView wenti_id;
    private TextView wenti_time;
    private TextView wenti_zeRenRen;
    private TextView wenti_type;
    private TextView wenti_zhiNeng;
    private ImageView iv_image_neiRong;
    private ImageView xiangQing_sanJiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_ti__xiang_qing);


        xiangQing_back = (Button) findViewById(R.id.XiangQing_back);
        xiangQing_back.setOnClickListener(this);
        wenti_id = (TextView) findViewById(R.id.wenti_ID);
        wenti_time = (TextView) findViewById(R.id.wenti_Time);
        wenti_zeRenRen = (TextView) findViewById(R.id.wenti_zeRenRen);
        wenti_type = (TextView) findViewById(R.id.wenti_Type);
        wenti_zhiNeng = (TextView) findViewById(R.id.wenti_zhiNeng);
        iv_image_neiRong = (ImageView) findViewById(R.id.iv_Image_NeiRong);
        xiangQing_sanJiao = (ImageView) findViewById(R.id.XiangQing_sanJiao);
        xiangQing_sanJiao.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.XiangQing_back:
                finish();
                break;
            case R.id.XiangQing_sanJiao:
                break;


        }
    }
}
