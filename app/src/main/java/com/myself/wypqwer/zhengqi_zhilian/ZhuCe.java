package com.myself.wypqwer.zhengqi_zhilian;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

import JavaBeen.PublicBeen;
import RunnableModel.ManagerJuRunnable;
import RunnableModel.ProjectRunnale;
import RunnableModel.PublicInterface;

import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_NameText;
import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_PassWordText;
import static com.myself.wypqwer.zhengqi_zhilian.R.id.ZhuCe_TelePhoneNumText;

public class ZhuCe extends AppCompatActivity implements View.OnClickListener , PublicInterface{

    private  EditText ZhuCe_Name;
    private  EditText ZhuCe_TelePhoneNum;
    private  EditText ZhuCe_sex;
    private  EditText ZhuCe_PassWord;
    private  EditText ZhuCe_SurePassWord;
    private  TextView registerSubmit;
    private  MyProgressDialog ProgressDialog;
    private  Button iv_back;
    private TextView zhuCe_nameText;
    private TextView zhuCe_telePhoneNumText;
    private TextView zhuCe_sexText;
    private TextView zhuCe_passWordText;
    private TextView ZhuCe_SurePassWordText;
    private TextView zhuCe_sex;
    private String sex;
    private ImageView registerSubmit1;
    private TextView ZhuCe_Power;
    private TextView ZhuCe_ZhuCe_Power_sexText;
    private TextView ZhuCe_Power_Type;
    private TextView ZhuCe_ZhuCe_Power_Type_sexText;
    private MyProgressDialog progressDialog;
    private MyProgressDialog progressDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhu_ce);
        CommonMethod.setStatuColor(ZhuCe.this,R.color.white);

        iv_back = (Button) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        ZhuCe_Name = (EditText)findViewById(R.id.ZhuCe_Name);
        ZhuCe_TelePhoneNum = (EditText)findViewById(R.id.ZhuCe_TelePhoneNum);
        ZhuCe_sex = (EditText)findViewById(R.id.ZhuCe_sex);
        //zhuCe_sex = (TextView) findViewById(ZhuCe_sex);
       // zhuCe_sex.setOnClickListener(this);

        ZhuCe_PassWord = (EditText)findViewById(R.id.ZhuCe_PassWord);
        ZhuCe_SurePassWord = (EditText)findViewById(R.id.ZhuCe_SurePassWord);
        ZhuCe_Power = (TextView)findViewById(R.id.ZhuCe_Power);
        zhuCe_nameText = (TextView) findViewById(ZhuCe_NameText);
        zhuCe_telePhoneNumText = (TextView) findViewById(ZhuCe_TelePhoneNumText);
        zhuCe_sexText = (TextView) findViewById(R.id.ZhuCe_sexText);
        zhuCe_passWordText = (TextView) findViewById(ZhuCe_PassWordText);
        ZhuCe_SurePassWordText = (TextView) findViewById(R.id.ZhuCe_SurePassWordText);

        ZhuCe_ZhuCe_Power_sexText = (TextView)findViewById(R.id.ZhuCe_ZhuCe_Power_sexText);
        ZhuCe_Power_Type = (TextView)findViewById(R.id.ZhuCe_Power_Type);
        ZhuCe_ZhuCe_Power_Type_sexText=(TextView)findViewById(R.id.ZhuCe_ZhuCe_Power_Type_sexText);


        ZhuCe_Name.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());
        ZhuCe_Power.setOnClickListener(new ProblemSumActivityListener());
        ZhuCe_Power_Type.setOnClickListener(new ProblemSumActivityListener());
        ZhuCe_TelePhoneNum.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());
        ZhuCe_sex.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());
        ZhuCe_PassWord.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());
        ZhuCe_SurePassWord.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());

        ZhuCe_Limit = (RelativeLayout)findViewById(R.id.ZhuCe_Limit);
        ZhuCe_Power_Type.setOnFocusChangeListener(new ZhuCe_FocusChangeListener());

       // registerSubmit = (TextView) findViewById(R.id.register_Submit);
        registerSubmit1 = (ImageView) findViewById(R.id.register_Submit);
        registerSubmit1.setOnClickListener(this);

    }
    private  List<PublicBeen> list;
    @Override
    public void onGetDataSuccess(List<PublicBeen> list) {

        this.list= list;
        Message msg =Message.obtain();
        msg.what=0;
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
                if(progressDialog!=null){
                    setManagerMethod();
                }
                if(progressDialog1!=null){
                    setProjectMethod();
                }
            }else{
                Toast.makeText(ZhuCe.this,(String)msg.obj, Toast.LENGTH_SHORT).show();
            }
            cancelprogressdialog();
            cancelprogressdialog1();
        }
    };
    String Manager_arr_name [] =null;
    String Manager_arr_id [] =null;
    int Manager_id=-1;
    AlertDialog.Builder BUILDER;
    private void setManagerMethod(){
        Log.e("warn",list.size()+":长度");
        if(Manager_arr_name==null){
            Manager_arr_name = new String[list.size()];
        }
        if(Manager_arr_id==null){
            Manager_arr_id = new String[list.size()];
        }

            for (int i = 0; i < list.size(); i++) {
                Manager_arr_name[i] = list.get(i).getManager_name();
                Log.e("warn",list.get(i).getManager_name());
                Manager_arr_id[i] = list.get(i).getManager_id();
            }

        if(BUILDER!=null){
            BUILDER.show();
        }else{
            Log.e("warn","0202");
            BUILDER = new AlertDialog.Builder(this);
            BUILDER.setTitle("请选择");
            BUILDER.setSingleChoiceItems(Manager_arr_name, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ZhuCe_Power_Type.setText(Manager_arr_name[which]);
                    Manager_id = which;
                    dialog.dismiss();
                }
            });
            BUILDER.show();
        }

    }
    String Project_arr_name [] =null;
    String Project_arr_id [] =null;
    int Project_id=-1;
    AlertDialog.Builder Project_BUILDER;
    private void setProjectMethod(){
        if(Project_arr_name==null){
            Project_arr_name = new String[list.size()];
            if(Project_arr_id==null){
                Project_arr_id = new String[list.size()];
            }
            for (int i = 0; i < list.size(); i++) {
                Project_arr_name[i] = list.get(i).getManager_name();
                Project_arr_id[i] = list.get(i).getManager_id();
            }
        }



        if(Project_BUILDER!=null){
            Project_BUILDER.show();
        }else{
            Project_BUILDER = new AlertDialog.Builder(this);
            Project_BUILDER.setTitle("请选择");
            Project_BUILDER.setSingleChoiceItems(Project_arr_name, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ZhuCe_Power_Type.setText( Project_arr_name[which]);
                    Project_id = which;
                    dialog.dismiss();
                }
            });
            Project_BUILDER.show();
        }

    }
    private class ZhuCe_FocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.ZhuCe_Name:
                    if (hasFocus) {
                        zhuCe_nameText.setBackgroundDrawable(getResources().getDrawable(R.color.royalblue));
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
    private PopupWindow popupwindow;
    WindowManager.LayoutParams lp;
    TextView ProblemSum_leader;
    TextView ProblemSum_Manager;
    TextView ProblemSum_Company;
    RelativeLayout AllState;
    private void BD_TimepopWindow() {
        lp = getWindow().getAttributes();
        lp.alpha =0.5f; //0.0-1.0
        getWindow().setAttributes(lp);

        AllState = (RelativeLayout)findViewById(R.id.power);
        View addview = LayoutInflater.from(this).inflate(R.layout.zhucepop_layout, null);

        ProblemSum_leader= (TextView) addview.findViewById(R.id.ProblemSum_leader);
        ProblemSum_leader.setOnClickListener(new ProblemSumActivityListener());

        ProblemSum_Manager= (TextView) addview.findViewById(R.id.ProblemSum_Manager);
        ProblemSum_Manager.setOnClickListener(new ProblemSumActivityListener());

        ProblemSum_Company= (TextView) addview.findViewById(R.id.ProblemSum_Company);
        ProblemSum_Company.setOnClickListener(new ProblemSumActivityListener());

        //BD_addinit(addview);
        popupwindow = new PopupWindow(addview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);//2,3参数为宽高
        popupwindow.setTouchable(true);//popupWindow可触摸
        popupwindow.setOutsideTouchable(true);//点击popupWindow以外区域消失
        popupwindow.setFocusable(true);
        popupwindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                Log.i("mengdd", "onTouch : ");
                return false;
            }
        });
        //设置popwindow消失的监听事件
        popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha =1; //0.0-1.0
                getWindow().setAttributes(lp);
            }
        });
        popupwindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置好参数之后再show
        popupwindow.showAsDropDown(AllState,50, 0);//在view(top)控件正下方，以view为参照点第二个参数为popwindow距离view的横向距离，
        //第三个参数为y轴即popwindow距离view的纵向距离
    }
    private void cancelpopupwindow(){
        if(popupwindow!=null){
            popupwindow.dismiss();
        }
    }
    private void cancelprogressdialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }
    private void cancelprogressdialog1(){
        if(progressDialog1!=null){
            progressDialog1.dismiss();
            progressDialog1=null;
        }
    }
    RelativeLayout ZhuCe_Limit;
    private class ProblemSumActivityListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
                switch (v.getId()){
                    case R.id.ProblemSum_leader:

                                                    ZhuCe_Power.setText(ProblemSum_leader.getText().toString());
                                                    ZhuCe_Limit.setVisibility(View.GONE);
                                                    cancelpopupwindow();
                                                    break;
                    case R.id.ProblemSum_Manager:

                                                    ZhuCe_Power.setText(ProblemSum_Manager.getText().toString());
                                                    ZhuCe_Limit.setVisibility(View.VISIBLE);
                                                    cancelpopupwindow();
                                                    ZhuCe_Power_Type.setText("");
                                                    break;
                    case R.id.ProblemSum_Company:
                                                    ZhuCe_Power.setText(ProblemSum_Company.getText().toString());
                                                    ZhuCe_Limit.setVisibility(View.VISIBLE);
                                                    cancelpopupwindow();
                                                    break;
                    case R.id.ZhuCe_Power:
                            if(popupwindow!=null){
                                if(lp!=null){
                                    lp.alpha =0.5f; //0.0-1.0
                                    getWindow().setAttributes(lp);
                                }else{
                                    lp = getWindow().getAttributes();
                                    lp.alpha =0.5f; //0.0-1.0
                                    getWindow().setAttributes(lp);
                                }
                                popupwindow.showAsDropDown(AllState,0, 10);//在view(top)控件正下方，以view为参照点第二个参数为popwindow距离view的横向距离，
                            }else{
                                BD_TimepopWindow();
                            }
                         break;
                    case R.id.ZhuCe_Power_Type:
                                                if(ZhuCe_Power.getText().toString().equals(ProblemSum_Manager.getText().toString())){
                                                    if(Manager_arr_name==null){
                                                        progressDialog = new MyProgressDialog(ZhuCe.this,false,"");
                                                        new ManagerJuRunnable().getShopsData(ZhuCe.this);
                                                    }else{
                                                       setManagerMethod();
                                                    }
                                                }else if(ZhuCe_Power.getText().toString().equals(ProblemSum_Company.getText().toString())){
                                                    if(Project_arr_name==null) {
                                                        progressDialog1 = new MyProgressDialog(ZhuCe.this, false, "");
                                                        new ProjectRunnale().getShopsData(ZhuCe.this);
                                                    }else{
                                                        setProjectMethod();
                                                    }
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
        if (ZhuCe_sex.getText().toString().equals("") || ZhuCe_sex.getText().toString() == null) {
            Toast.makeText(ZhuCe.this, "性别不能为空", Toast.LENGTH_SHORT).show();
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

        if (!ZhuCe_sex.getText().toString().equals("女")&& !ZhuCe_sex.getText().toString().equals("男")) {
            Toast.makeText(ZhuCe.this, "请输入正确性别", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ZhuCe_Power.getText().toString().equals("")) {
            Toast.makeText(ZhuCe.this, "请选择权限", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ZhuCe_Power.getText().toString().equals(ProblemSum_Manager.getText().toString())) {
            if(ZhuCe_Power_Type.getText().toString().equals("")){
                Toast.makeText(ZhuCe.this, "请选择所属管理局", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (ZhuCe_Power.getText().toString().equals(ProblemSum_Company.getText().toString())) {
            if(ZhuCe_Power_Type.getText().toString().equals("")){
                Toast.makeText(ZhuCe.this, "请选择项目", Toast.LENGTH_SHORT).show();
                return false;
            }
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
                String methodName = "Add_LoginUser";
                // EndPoint
                String endPoint = Path.get_faGai_Url();
                // SOAP Action
                String soapAction = "http://tempuri.org/Add_LoginUser";
                // 指定WebService的命名空间和调用的方法名
                SoapObject rpc = new SoapObject(nameSpace, methodName);
                rpc.addProperty("loginName",ZhuCe_TelePhoneNum.getText().toString());
                rpc.addProperty("loginPwd",ZhuCe_PassWord.getText().toString());
                rpc.addProperty("NAME",ZhuCe_Name.getText().toString());
                rpc.addProperty("SEX",ZhuCe_sex.getText().toString());
                rpc.addProperty("PhoneNum",ZhuCe_TelePhoneNum.getText().toString());

                if(ZhuCe_Power.getText().toString().equals(ProblemSum_leader.getText().toString())){
                    rpc.addProperty("power","0");
                }
                else if(ZhuCe_Power.getText().toString().equals(ProblemSum_Manager.getText().toString())){
                    rpc.addProperty("power","1");
                    rpc.addProperty("gljID",Manager_arr_id[Manager_id]);
                }else if(ZhuCe_Power.getText().toString().equals(ProblemSum_Company.getText().toString())){
                    rpc.addProperty("power","2");
                    rpc.addProperty("proID",Project_arr_id[Project_id]);
                    Log.e("warn","222222");
                }

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
                    Log.e("warn", "51");
                    object = (SoapObject) envelope.getResponse();
                    Log.e("warn", "52");
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
    private void cancelProgressDialog(){
        if(ProgressDialog!=null){
            ProgressDialog.dismiss();
            ProgressDialog=null;
        }
    }
    Handler handlerInsert_LoginUser = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            if(i==0){

                cancelProgressDialog();
                Toast.makeText(ZhuCe.this, "网络或服务器异常", Toast.LENGTH_SHORT).show();

            }else if (i == 1) {
                cancelProgressDialog();
                String str = (String) msg.obj;
                Log.e("warn", "sada:"+str);
                cancelProgressDialog();
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
                        Toast.makeText(ZhuCe.this, "注册失败，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ZhuCe.this, "注册失败，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

}