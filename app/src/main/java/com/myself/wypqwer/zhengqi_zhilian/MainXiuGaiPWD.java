package com.myself.wypqwer.zhengqi_zhilian;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import RunnableModel.PublicOneListInterface;
import RunnableModel.XiuGaiPWDRunnable;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MainXiuGaiPWD extends AppCompatActivity implements PublicOneListInterface{
    private EditText ED_Username;
    private  EditText ED_Paqssword;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.xiugaipwd_layout);
        init();
    }
    private void init(){
        Button ManagerPWD_Back = (Button)findViewById(R.id.ManagerPWD_Back);
        ManagerPWD_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView SureCommit = (ImageView)findViewById(R.id.SureCommit);
        SureCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(!ED_Paqssword.getText().toString().equals(ED_Username.getText().toString())){
                            Toast.makeText(MainXiuGaiPWD.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(ED_Paqssword.getText().toString().equals("")||ED_Username.getText().toString().equals("")){
                            Toast.makeText(MainXiuGaiPWD.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        new XiuGaiPWDRunnable(ED_Username.getText().toString()).getShopsData(MainXiuGaiPWD.this);
            }
        });

        ED_Username = (EditText)findViewById(R.id.ED_Username);
        ED_Username.setOnFocusChangeListener(new ED_FocusChangeListener());
        ED_Paqssword = (EditText)findViewById(R.id.ED_Paqssword);
        ED_Paqssword.setOnFocusChangeListener(new ED_FocusChangeListener());
    }

    @Override
    public void onGetDataSuccess(String succmessage) {
        Message msg= Message.obtain();
        msg.obj=succmessage;
        msg.what=0;
        handler.sendMessage(msg);
    }

    @Override
    public void onGetDataError(String errmessage) {
        Message msg= Message.obtain();
        msg.obj=errmessage;
        msg.what=1;
        handler.sendMessage(msg);
    }

    @Override
    public void onEmptyData(String Emptymessage) {
        Message msg= Message.obtain();
        msg.obj=Emptymessage;
        msg.what=2;
        handler.sendMessage(msg);
    }

    private class ED_FocusChangeListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()){
                case R.id.ED_Username:
                    if(hasFocus){
                        ED_Username.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_focusbiankuang));
                    }else{
                        ED_Username.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_biankuang));
                    }
                    break;
                case R.id.ED_Paqssword:

                    if(hasFocus){
                        ED_Paqssword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_focusbiankuang));
                    }else{
                        ED_Paqssword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_biankuang));
                    }

                    break;
            }
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i= msg.what;
            if(i==0){

            }else if(i==1){

                Toast.makeText(MainXiuGaiPWD.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
            }else if(i==2){
                Toast.makeText(MainXiuGaiPWD.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainXiuGaiPWD.this, "修改密码失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
