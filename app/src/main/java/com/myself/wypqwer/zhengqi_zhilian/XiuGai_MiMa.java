package com.myself.wypqwer.zhengqi_zhilian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class XiuGai_MiMa extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_queren;
    private Button xiuGai_back;
    private EditText xiugai_mima;
    private EditText xiugai_mimaSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai__mi_ma);
        CommonMethod.setStatuColor(this,R.color.z070);
        iv_queren = (ImageView) findViewById(R.id.iv_queren);
        iv_queren.setOnClickListener(this);
        xiuGai_back = (Button) findViewById(R.id.XiuGai_back);
        xiuGai_back.setOnClickListener(this);
        xiugai_mima = (EditText) findViewById(R.id.xiugai_mima);
        xiugai_mimaSure = (EditText) findViewById(R.id.xiugai_mimaSure);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.iv_queren:
                break;
            case R.id.XiuGai_back:
                finish();
                break;

        }

    }
}
