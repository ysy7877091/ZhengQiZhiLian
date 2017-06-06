package com.myself.wypqwer.zhengqi_zhilian;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private MyProgressDialog ProgressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiugaipwd_layout);
        Log.e("warn",getIntent().getStringExtra("pwd"));
        init();
    }
    private void init(){
        if(getIntent().getStringExtra("user")==null||getIntent().getStringExtra("pwd")==null){
            Toast.makeText(this, "未获取到有效信息", Toast.LENGTH_SHORT).show();
            return;
        }

        Button ManagerPWD_Back = (Button)findViewById(R.id.ManagerPWD_Back);
        ED_Username = (EditText)findViewById(R.id.ED_Username);
        ED_Username.setOnFocusChangeListener(new ED_FocusChangeListener());
        ED_Paqssword = (EditText)findViewById(R.id.ED_Paqssword);
        ED_Paqssword.setOnFocusChangeListener(new ED_FocusChangeListener());
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
                        ProgressDialog = new MyProgressDialog(MainXiuGaiPWD.this,false,"");
                        new XiuGaiPWDRunnable(ED_Username.getText().toString(),getIntent().getStringExtra("user"),getIntent().getStringExtra("pwd")).getShopsData(MainXiuGaiPWD.this);
            }
        });

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
            cancelProgressDialog();
            if(i==0){
                String result = (String)msg.obj;
                if(result.equals("0")){
                    Toast.makeText(MainXiuGaiPWD.this,"修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(result.equals("2")){
                    Toast.makeText(MainXiuGaiPWD.this,"旧密码与新密码对不上", Toast.LENGTH_SHORT).show();
                }else if(result.equals("3")){
                    Toast.makeText(MainXiuGaiPWD.this,"系统异常，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            }else if(i==1){
                Toast.makeText(MainXiuGaiPWD.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
            }else if(i==2){
                Toast.makeText(MainXiuGaiPWD.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainXiuGaiPWD.this, "修改密码失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void cancelProgressDialog(){
        if(ProgressDialog!=null){
            ProgressDialog.dismiss();
            ProgressDialog=null;
        }
    }
}
