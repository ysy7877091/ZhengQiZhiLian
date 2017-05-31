package com.myself.wypqwer.zhengqi_zhilian;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    private  EditText ED_Username;
    private  EditText ED_Paqssword;
    private MyProgressDialog ProgressDialog;
    private TextView dengLu_miMaText;
    private TextView dengLu_zhangHaoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CommonMethod.setStatuColor(MainActivity.this,R.color.white);
        init();

    }

    private void init() {

        ED_Username = (EditText)findViewById(R.id.username);

        ED_Username.setOnFocusChangeListener(new ED_FocusChangeListener());
        ED_Paqssword = (EditText)findViewById(R.id.password);
        ED_Paqssword.setOnFocusChangeListener(new ED_FocusChangeListener());

        dengLu_miMaText = (TextView) findViewById(R.id.dengLu_miMaText);
        dengLu_zhangHaoText = (TextView) findViewById(R.id.dengLu_zhangHaoText);
        //登录
        ImageView login = (ImageView)findViewById(R.id.login);
        login.setOnClickListener(new MainActivityListener());
        //注册
        TextView zhuCe = (TextView) findViewById(R.id.zhuCe);
        zhuCe.setOnClickListener(new MainActivityListener());
        chechVersion1();
        UserN();
    }
    private class ED_FocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.username:
                    if (hasFocus) {
                        dengLu_zhangHaoText.setBackgroundResource(R.color.royalblue);
                    } else {
                        dengLu_zhangHaoText.setBackgroundResource(R.color.tj11);
                    }
                    break;
                case R.id.password:

                    if (hasFocus) {
                        dengLu_miMaText.setBackgroundResource(R.color.royalblue);
                    } else {
                        dengLu_miMaText.setBackgroundResource(R.color.tj11);
                    }
                    break;
            }
        }
    }
    private class MainActivityListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.login:
                    if (ED_Username.getText().toString().equals("") || ED_Username.getText().toString() == null) {
                        Toast.makeText(MainActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ED_Paqssword.getText().toString().equals("") || ED_Paqssword.getText().toString() == null) {
                        Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ProgressDialog = new MyProgressDialog(MainActivity.this, false, "登陆中...");
                     new Thread(networkLogin).start();
                    break;

                case R.id.zhuCe:
                    Intent Register = new Intent(MainActivity.this, ZhuCe.class);
                    startActivity(Register);
                    break;
            }
        }
    }

    Runnable networkLogin = new Runnable() {
        SoapObject object;
        @Override
        public void run() {
            try {
                // 命名空间
                String nameSpace = "http://tempuri.org/";
                // 调用的方法名称
                String methodName = "Get_Login";
                // EndPoint
                String endPoint = Path.get_faGai_Url();
                // SOAP Action
                String soapAction = "http://tempuri.org/Get_Login";
                // 指定WebService的命名空间和调用的方法名
                SoapObject rpc = new SoapObject(nameSpace, methodName);

                rpc.addProperty("loginName",ED_Username.getText().toString());
                rpc.addProperty("loginPwd",ED_Paqssword.getText().toString());

                // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(rpc);
                HttpTransportSE ht = new HttpTransportSE(endPoint,20000);
                ht.debug = true;
                Log.e("warn", "50");
                (new MarshalBase64()).register(envelope);
                try {
                    // 调用WebService
                    ht.call(soapAction, envelope);
                    object = (SoapObject) envelope.getResponse();
                } catch (Exception e) {
                    Message msg = Message.obtain();
                    msg.what = 0;
                    handlerLogin.sendMessage(msg);
                }
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = object.toString();
                    handlerLogin.sendMessage(msg);
            } catch (Exception e) {
                Message msg = Message.obtain();
                msg.what = 0;
                handlerLogin.sendMessage(msg);
            }
        }
    };

    Handler handlerLogin = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            if(i==0){

                ProgressDialog.dismiss();
                Toast.makeText(MainActivity.this, "网络或服务器异常", Toast.LENGTH_SHORT).show();

            }else if (i == 2) {
                ProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"无数据",Toast.LENGTH_SHORT).show();

            } else if (i == 1) {
                ProgressDialog.dismiss();
                String str = (String) msg.obj;
                Log.e("warn", str);

                if (str.contains("State")) {
                    int index = str.indexOf("State");
                    Log.e("warn", String.valueOf(index));
                    String result = str.substring(index + 1);
                    Log.e("warn",result);
                    int index1 = result.indexOf("=");
                    Log.e("warn", String.valueOf(index1));
                    int index2 = result.indexOf(";");
                    Log.e("warn", String.valueOf(index2));
                    String result1 = result.substring(index1 + 1, index2);
                    Log.e("warn", result1);
                    if (result1.equals("0")) {
                        SharedPreferences sp = getSharedPreferences("logininformation",0);
                        SharedPreferences.Editor edit=sp.edit();
                        edit.putString("use",ED_Username.getText().toString().trim());
                        edit.putString("pwd",ED_Paqssword.getText().toString().trim());
                        edit.commit();
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ShouYeMapActivity.class);
                        intent.putExtra("alais",ED_Username.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    } else if (result1.equals("2")) {
                        Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    //读写sd卡权限
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE ,
            Manifest.permission.READ_PHONE_STATE};

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    //检查sd卡和读取手机emei申请权限
    private void chechVersion1(){
        if(Build.VERSION.SDK_INT>=23){
            //检查手机是否有该权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkCallPhonePermission!= PackageManager.PERMISSION_GRANTED){//当没有该权限时
                //弹出对话框申请该权限   数组里装的是要申请的权限 id = 0 索引0 申请权限在数组中的位置 返回的数组结果也在数组索引0中
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                return;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //返回的数组 结果的位置就是申请该权限 申请的权限位置 即索引0
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){//已授权
            Toast.makeText(getApplicationContext(),"授权成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"权限被拒绝,有可能导致应用内部错误", Toast.LENGTH_SHORT).show();
        }
        //sd卡权限
        if(grantResults[REQUEST_EXTERNAL_STORAGE]==PackageManager.PERMISSION_GRANTED){//已授权
            Toast.makeText(getApplicationContext(),"授权成功", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(),"权限被拒绝,有可能导致应用内部错误", Toast.LENGTH_SHORT).show();
        }

    }
    //获取已保存的用户名
    private void UserN(){
        SharedPreferences sp =getSharedPreferences("logininformation",0);
        String user=sp.getString("use","0");
        String pwd=sp.getString("pwd","0");
        if(!user.equals("0")&&!pwd.equals("0")){
            ED_Username.setText(user);
            ED_Paqssword.setText(pwd);
        }
    }


}