package com.myself.wypqwer.zhengqi_zhilian;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_NameText;
import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_PassWordText;
import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_TelePhoneNumText;

public class ZhuCe extends AppCompatActivity implements View.OnClickListener{

    private  EditText ZhuCe_Name;
    private  EditText ZhuCe_TelePhoneNum;
    private  EditText ZhuCe_sex;
    private  EditText ZhuCe_PassWord;
    private  EditText ZhuCe_SurePassWord;
    private  TextView registerSubmit;
    private  MyProgressDialog ProgressDialog;
    private  ImageView iv_back;
    private TextView zhuCe_nameText;
    private TextView zhuCe_telePhoneNumText;
    private TextView zhuCe_sexText;
    private TextView zhuCe_passWordText;
    private TextView ZhuCe_SurePassWordText;
    private TextView zhuCe_sex;
    private String sex;
    private ImageView registerSubmit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhu_ce);
        CommonMethod.setStatuColor(ZhuCe.this,R.color.white);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        ZhuCe_Name = (EditText)findViewById(R.id.ZhuCe_Name);
        ZhuCe_TelePhoneNum = (EditText)findViewById(R.id.ZhuCe_TelePhoneNum);
        ZhuCe_sex = (EditText)findViewById(R.id.ZhuCe_sex);
        //zhuCe_sex = (TextView) findViewById(ZhuCe_sex);
       // zhuCe_sex.setOnClickListener(this);

        ZhuCe_PassWord = (EditText)findViewById(R.id.ZhuCe_PassWord);
        ZhuCe_SurePassWord = (EditText)findViewById(R.id.ZhuCe_SurePassWord);

        zhuCe_nameText = (TextView) findViewById(ZhuCe_NameText);
        zhuCe_telePhoneNumText = (TextView) findViewById(ZhuCe_TelePhoneNumText);
        zhuCe_sexText = (TextView) findViewById(R.id.ZhuCe_sexText);
        zhuCe_passWordText = (TextView) findViewById(ZhuCe_PassWordText);
        ZhuCe_SurePassWordText = (TextView) findViewById(R.id.ZhuCe_SurePassWordText);

        ZhuCe_Name.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());
        ZhuCe_TelePhoneNum.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());
        ZhuCe_sex.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());
        ZhuCe_PassWord.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());
        ZhuCe_SurePassWord.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());

       // registerSubmit = (TextView) findViewById(R.id.register_Submit);
        registerSubmit1 = (ImageView) findViewById(R.id.register_Submit);
        registerSubmit1.setOnClickListener(this);

    }
    private class ZhuCe_FocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.ZhuCe_Name:
                    if (hasFocus) {
                        zhuCe_nameText.setBackgroundResource(R.color.royalblue);
                    } else {
                        zhuCe_nameText.setBackgroundResource(R.color.tj11);
                    }
                    break;
                case R.id.ZhuCe_TelePhoneNum:
                    if (hasFocus) {
                        zhuCe_telePhoneNumText.setBackgroundResource(R.color.royalblue);
                    } else {
                        zhuCe_telePhoneNumText.setBackgroundResource(R.color.tj11);
                    }
                    break;
                case R.id.ZhuCe_sex:
                    if (hasFocus) {
                        zhuCe_sexText.setBackgroundResource(R.color.royalblue);
                    } else {
                        zhuCe_sexText.setBackgroundResource(R.color.tj11);
                    }
                    break;
                case R.id.ZhuCe_PassWord:
                    if (hasFocus) {
                        zhuCe_passWordText.setBackgroundResource(R.color.royalblue);
                    } else {
                        zhuCe_passWordText.setBackgroundResource(R.color.tj11);
                    }
                    break;
                case R.id.ZhuCe_SurePassWord:
                    if (hasFocus) {
                        ZhuCe_SurePassWordText.setBackgroundResource(R.color.royalblue);
                    } else {
                        ZhuCe_SurePassWordText.setBackgroundResource(R.color.tj11);
                    }
                    break;
            }
        }
    }

    private boolean YanZheng() {
        if (ZhuCe_Name.getText().toString().equals("") || ZhuCe_Name.getText().toString() == null) {
            Toast.makeText(ZhuCe.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ZhuCe_TelePhoneNum.getText().toString().equals("") || ZhuCe_TelePhoneNum.getText().toString() == null) {
            Toast.makeText(ZhuCe.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ZhuCe_PassWord.getText().toString().equals("") || ZhuCe_PassWord.getText().toString() == null) {
            Toast.makeText(ZhuCe.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!ZhuCe_PassWord.getText().toString().equals(ZhuCe_SurePassWord.getText().toString())) {
            Toast.makeText(ZhuCe.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ZhuCe_sex.getText().toString().equals("") || ZhuCe_sex.getText().toString() == null) {
            Toast.makeText(ZhuCe.this, "性别不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!ZhuCe_sex.getText().toString().equals("男") ||!ZhuCe_sex.getText().toString().equals("女") ) {
            Toast.makeText(ZhuCe.this, "请输入正确性别", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    Runnable networkZhuce = new Runnable() {
        SoapObject object;
        @Override
        public void run() {
            try {
                // 命名空间
                String nameSpace = "http://tempuri.org/";
                // 调用的方法名称
                String methodName = "Insert_LoginUser";
                // EndPoint
                String endPoint = Path.get_faGai_Url();
                // SOAP Action
                String soapAction = "http://tempuri.org/Insert_LoginUser";
                // 指定WebService的命名空间和调用的方法名
                SoapObject rpc = new SoapObject(nameSpace, methodName);
                rpc.addProperty("loginName",ZhuCe_Name.getText().toString());
                rpc.addProperty("loginPwd",ZhuCe_PassWord.getText().toString());
                rpc.addProperty("NAME",ZhuCe_SurePassWord.getText().toString());
                rpc.addProperty("SEX",ZhuCe_sex.getText().toString());
                rpc.addProperty("PhoneNum",ZhuCe_TelePhoneNum.getText().toString());

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
                    handlerInsert_LoginUser.sendMessage(msg);
                }
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = object.toString();
                    handlerInsert_LoginUser.sendMessage(msg);
            } catch (Exception e) {
                Message msg = Message.obtain();
                msg.what = 0;
                handlerInsert_LoginUser.sendMessage(msg);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.iv_back:
                finish();
                break;
//            case R.id.ZhuCe_sex:
//
//                arr = new String[]{"男", "女"};
//                new AlertDialog.Builder(ZhuCe.this).setTitle("请选择")
//                        .setSingleChoiceItems(arr, 0,
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        sex = arr[which];
//                                        zhuCe_sex.setText(arr[which]);
//                                        zhuCe_sex.setTextColor(getResources().getColor(R.color.black));
//                                        dialog.dismiss();
//                                    }
//                                }
//                        ).setNegativeButton("取消", null).show();
//                break;
            case R.id.register_Submit:

                boolean isFull = YanZheng();
                if (!isFull == true) {
                    return;
                }
                ProgressDialog = new MyProgressDialog(ZhuCe.this, false, "注册中...");
                new Thread(networkZhuce).start();

                break;
        }
    }

    Handler handlerInsert_LoginUser = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            if(i==0){

                ProgressDialog.dismiss();
                Toast.makeText(ZhuCe.this, "网络或服务器异常", Toast.LENGTH_SHORT).show();

            }else if (i == 1) {
                String str = (String) msg.obj;
                Log.e("warn", str);
                ProgressDialog.dismiss();
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
                        Toast.makeText(ZhuCe.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (result1.equals("2")) {
                        Toast.makeText(ZhuCe.this, "账号已存在", Toast.LENGTH_SHORT).show();
                    } else if (result1.equals("3")) {
                        Toast.makeText(ZhuCe.this, "系统错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ZhuCe.this, "注册失败，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}