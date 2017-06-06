package com.myself.wypqwer.zhengqi_zhilian;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import RunnableModel.ComputerInformationRunnable;
import RunnableModel.PublicOneListInterface;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ComputerInformation extends AppCompatActivity implements PublicOneListInterface{
    private MyProgressDialog ProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computerinformation_layout);
        CommonMethod.setStatuColor(ComputerInformation.this,R.color.white);
        init();
    }
    private void init(){
        String name = getIntent().getStringExtra("name");
        String id = getIntent().getStringExtra("id");
        //返回
        Button computer_Back = (Button)findViewById(R.id.computerBack);
        computer_Back.setOnClickListener(new ComputerInformationListener());
        //更多
        Button computer_More = (Button)findViewById(R.id.computer_More);
        computer_More.setOnClickListener(new ComputerInformationListener());

        ProgressDialog = new MyProgressDialog(this,false,"加载中...");
        new ComputerInformationRunnable(id,name).getShopsData(this);
    }

    @Override
    public void onGetDataSuccess(String succmessage) {
        cancelProgressDialog();
        Message msg =Message.obtain();
        msg.obj=succmessage;
        msg.what=0;
        handler.sendMessage(msg);
        Log.e("warn",succmessage);
    }

    @Override
    public void onGetDataError(String errmessage) {
        cancelProgressDialog();
        Message msg =Message.obtain();
        msg.obj=errmessage;
        msg.what=1;
        handler.sendMessage(msg);
        Log.e("warn",errmessage);
    }

    @Override
    public void onEmptyData(String Emptymessage) {
        cancelProgressDialog();
        Message msg =Message.obtain();
        msg.obj=Emptymessage;
        msg.what=2;
        handler.sendMessage(msg);
        Log.e("warn",Emptymessage);
    }

    private class ComputerInformationListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.computerBack:finish();break;
                case R.id.computer_More:break;
            }
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i= msg.what;
            if(i==0){
                String s0 = (String)msg.obj;
                setViewShow(s0);
            }else if(i==1){
                String s1 = (String)msg.obj;
                Toast.makeText(ComputerInformation.this,s1, Toast.LENGTH_SHORT).show();
            }else if(i==2){
                String s2 = (String)msg.obj;
                Toast.makeText(ComputerInformation.this,s2, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void setViewShow(String result){
        if(result.contains("ProID")){
            int ProID_index = result.indexOf("ProID");
            String result1 = result.substring(ProID_index);
            int ProID_index1 = result1.indexOf("=");
            int ProID_index2 = result1.indexOf(";");
            String str = result1.substring(ProID_index1+1,ProID_index2);
            //企业信息代码
            TextView computer_ID = (TextView)findViewById(R.id.computer_ID);
            computer_ID.setText(str);
        }
        if(result.contains("PRONAME")){
            int ProID_index = result.indexOf("PRONAME");
            String result1 = result.substring(ProID_index);
            int ProID_index1 = result1.indexOf("=");
            int ProID_index2 = result1.indexOf(";");
            String str = result1.substring(ProID_index1+1,ProID_index2);
            //项目名称
            TextView computer_ProjectName = (TextView)findViewById(R.id.computer_ProjectName);
            computer_ProjectName.setText("       "+str);

        }
        if(result.contains("COMPANYPROFILE")){
            int ProID_index = result.indexOf("COMPANYPROFILE");
            String result1 = result.substring(ProID_index);
            int ProID_index1 = result1.indexOf("=");
            int ProID_index2 = result1.indexOf(";");
            String str = result1.substring(ProID_index1+1,ProID_index2);
            //企业简介
            TextView computer_Show = (TextView)findViewById(R.id.computer_Show);
            computer_Show.setText("        "+str);

        }
        if(result.contains("ADDRESS")){
            int ProID_index = result.indexOf("ADDRESS");
            String result1 = result.substring(ProID_index);
            int ProID_index1 = result1.indexOf("=");
            int ProID_index2 = result1.indexOf(";");
            String str = result1.substring(ProID_index1+1,ProID_index2);
            //建设地址
            TextView computer_Address= (TextView)findViewById(R.id.computer_Address);
            computer_Address.setText("       "+str);

        }
        if(result.contains("CONTENT")){
            int ProID_index = result.indexOf("CONTENT");
            String result1 = result.substring(ProID_index);
            int ProID_index1 = result1.indexOf("=");
            int ProID_index2 = result1.indexOf(";");
            String str = result1.substring(ProID_index1+1,ProID_index2);
            //建设内容
            TextView computer_Content= (TextView)findViewById(R.id.computer_Content);
            computer_Content.setText("        "+str);

        }
        if(result.contains("PROGRESS")){
            int ProID_index = result.indexOf("PROGRESS");
            String result1 = result.substring(ProID_index);
            int ProID_index1 = result1.indexOf("=");
            int ProID_index2 = result1.indexOf(";");
            String str = result1.substring(ProID_index1+1,ProID_index2);
            //建设进度
            TextView computer_Process = (TextView)findViewById(R.id.computer_Process);
            computer_Process .setText("       "+str);

        }
        if(result.contains("ATTEN")){
            int ProID_index = result.indexOf("ATTEN");
            String result1 = result.substring(ProID_index);
            int ProID_index1 = result1.indexOf("=");
            int ProID_index2 = result1.indexOf(";");
            String str = result1.substring(ProID_index1+1,ProID_index2);
            //联系人信息
            String str11="";
            if(result.contains("TEL")){
                int ProID_index3 = result.indexOf("TEL");
                String result2 = result.substring(ProID_index3);
                int ProID_index4 = result2.indexOf("=");
                int ProID_index5 = result2.indexOf(";");
                str11 = result2.substring(ProID_index4+1,ProID_index5);
            }
            TextView computer_LianXiRen= (TextView)findViewById(R.id.computer_LianXiRen);
            computer_LianXiRen.setText(str+" "+str11);

        }
    }
    private void cancelProgressDialog(){
        if(ProgressDialog!=null){
            ProgressDialog.dismiss();
            ProgressDialog=null;
        }
    }
}
