package com.myself.wypqwer.zhengqi_zhilian;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_Name;
import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_PassWord;
import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_SurePassWord;
import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_TelePhoneNum;
import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_sex;

//测试用
public class ZhuCe extends AppCompatActivity implements View.OnClickListener{
    private  EditText ZhuCe_Name;
    private  EditText ZhuCe_TelePhoneNum;
    private  EditText ZhuCe_sex;
    private  EditText ZhuCe_PassWord;
    private  EditText ZhuCe_SurePassWord;
    private ImageView registerSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);

        ZhuCe_Name = (EditText)findViewById(R.id.ZhuCe_Name);
        ZhuCe_TelePhoneNum = (EditText)findViewById(R.id.ZhuCe_TelePhoneNum);
        ZhuCe_sex = (EditText)findViewById(R.id.ZhuCe_sex);
        ZhuCe_PassWord = (EditText)findViewById(R.id.ZhuCe_PassWord);
        ZhuCe_SurePassWord = (EditText)findViewById(R.id.ZhuCe_SurePassWord);
        registerSubmit = (ImageView) findViewById(R.id.registerSubmit);
        registerSubmit.setOnClickListener(this);

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
            case R.id.registerSubmit:
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

                //ProgressDialog.dismiss();
                Toast.makeText(ZhuCe.this, "网络或服务器异常", Toast.LENGTH_SHORT).show();

            }else if (i == 1) {
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
                        Toast.makeText(ZhuCe.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (result1.equals("2")) {
                        Toast.makeText(ZhuCe.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    } else if (result1.equals("3")) {
                        Toast.makeText(ZhuCe.this, "系统错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ZhuCe.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }







        }
    };
}
