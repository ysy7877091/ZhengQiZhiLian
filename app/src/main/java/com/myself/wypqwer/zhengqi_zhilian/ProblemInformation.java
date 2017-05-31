package com.myself.wypqwer.zhengqi_zhilian;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import RunnableModel.ProblemSumInformation;
import RunnableModel.PublicOneListInterface;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ProblemInformation extends AppCompatActivity implements PublicOneListInterface{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.probleminformation_layout);
        CommonMethod.setStatuColor(ProblemInformation.this,R.color.white);
        init();
        new ProblemSumInformation().getShopsData(this);
    }
    private void init(){
        //序号
        TextView ProblemInformation_Order = (TextView)findViewById(R.id.ProblemInformation_Order);
        //提报时间
        TextView ProblemInformation_Time = (TextView)findViewById(R.id.ProblemInformation_Time);
        //责任人
        TextView ProblemInformation_Person = (TextView)findViewById(R.id.ProblemInformation_Person);
        //提报问题
        TextView ProblemInformation_reason = (TextView)findViewById(R.id.ProblemInformation_reason);
        //提报类型
        TextView ProblemInformation_Type = (TextView)findViewById(R.id.ProblemInformation_Type);
    }

    @Override
    public void onGetDataSuccess(String succmessage) {
        Message msg =Message.obtain();
        msg.what=0;
        msg.obj=succmessage;
        handler.sendMessage(msg);
    }

    @Override
    public void onGetDataError(String errmessage) {
        Message msg =Message.obtain();
        msg.what=1;
        msg.obj=errmessage;
        handler.sendMessage(msg);
    }

    @Override
    public void onEmptyData(String Emptymessage) {
        Message msg =Message.obtain();
        msg.what=2;
        msg.obj=Emptymessage;
        handler.sendMessage(msg);
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i =msg.what;
            if(i==0){

            }else{
                Toast.makeText(ProblemInformation.this,(String)msg.obj, Toast.LENGTH_SHORT).show();
            }
        }
    };
}