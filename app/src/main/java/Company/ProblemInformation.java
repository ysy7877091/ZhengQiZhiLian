package company;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myself.wypqwer.zhengqi_zhilian.CommonMethod;
import com.myself.wypqwer.zhengqi_zhilian.MyProgressDialog;
import com.myself.wypqwer.zhengqi_zhilian.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import JavaBeen.PublicBeen;
import RunnableModel.AddProblemRunnable;
import RunnableModel.Problem_FuzeRen_Runnable;
import RunnableModel.PublicInterface;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ProblemInformation extends AppCompatActivity implements PublicInterface{
    private MyProgressDialog ProgressDialog;
    private TextView ManagerProblemInformation_Manager;
    private MyProgressDialog ProgressDialog1;
    private Bitmap myBitmap = null;
    private ImageView take_photo;
    private MyProgressDialog ProgressDialog2;
    private  EditText ProblemInformation_reason;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managerprobleminformation_layout);
        CommonMethod.setStatuColor(ProblemInformation.this,R.color.white);
        init();
    }
    private void init(){
        getLogin();
        //管理局
        ManagerProblemInformation_Manager = (TextView)findViewById(R.id.ManagerProblemInformation_Manager);
        RelativeLayout guanliju = (RelativeLayout)findViewById(R.id.guanliju);
        guanliju.setOnClickListener(new ProblemInformationListener());
        //提报时间
        //TextView ProblemInformation_Time = (TextView)findViewById(R.id.ManagerProblemInformation_Time);

        /*//责任人
        RelativeLayout fuzeren = (RelativeLayout)findViewById(R.id.fuzeren);
        fuzeren.setOnClickListener(new ProblemInformationListener());
        TextView ProblemInformation_Person = (TextView)findViewById(R.id.ManagerProblemInformation_Person);*/


        //提报问题
        ProblemInformation_reason = (EditText)findViewById(R.id.ManagerProblemInformation_reason);
        //项目
        RelativeLayout xiangmu = (RelativeLayout)findViewById(R.id.xiangmu);
        xiangmu.setOnClickListener(new ProblemInformationListener());


       //照片
        LinearLayout COMPANY_PHOTO = (LinearLayout)findViewById(R.id.COMPANY_PHOTO);
        COMPANY_PHOTO.setOnClickListener(new ProblemInformationListener());
        take_photo = (ImageView)findViewById(R.id.take_photo);

        //提交
        TextView submit = (TextView)findViewById(R.id.Pro_Submit) ;
        submit.setOnClickListener(new ProblemInformationListener());

        Button ManagerProblemInformation_Back = (Button)findViewById(R.id.ManagerProblemInformation_Back);
        ManagerProblemInformation_Back.setOnClickListener(new ProblemInformationListener());
    }

    private List<PublicBeen> list;
    @Override
    public void onGetDataSuccess(List<PublicBeen> list) {
        this.list = list;
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
                if(ProgressDialog!=null) {
                    setManagerMethod();

                }
                if(ProgressDialog1!=null){

                }
                if(ProgressDialog2!=null){
                    if(list.size()>0){
                        if(list.get(0).getProject_state().equals("0")) {
                            Toast.makeText(ProblemInformation.this, "提交成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ProblemInformation.this, "提交失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }else{
                Toast.makeText(ProblemInformation.this,(String)msg.obj, Toast.LENGTH_SHORT).show();
            }
            cancelProgressDialog();
            cancelProgressDialog1();
            cancelProgressDialog2();
        }
    };

    private String bytes;
    private class ProblemInformationListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.guanliju:
                                       /* if(popupwindow1!=null){
                                            lps1.alpha =0.5f; //0.0-1.0
                                            getWindow().setAttributes(lps1);
                                            popupwindow1.showAsDropDown(ManagerProblemInformation_Manager,0,0);
                                        }else {
                                            ProgressDialog = new MyProgressDialog(ProblemInformation.this, false, "");
                                            new ManagerJuRunnable().getShopsData(ProblemInformation.this);
                                        }*/
                                        ProgressDialog = new MyProgressDialog(ProblemInformation.this, false, "");
                                        new Problem_FuzeRen_Runnable().getShopsData(ProblemInformation.this);
                                        break;
               /* case  R.id.fuzeren:
                                        ProgressDialog1= new MyProgressDialog(ProblemInformation.this, false, "");
                                        new Problem_FuzeRen_Runnable().getShopsData(ProblemInformation.this);
                                        break;*/
                //case  R.id.xiangmu:break;
                case  R.id.COMPANY_PHOTO:
                                        selectPhoto();
                                        break;
                case R.id.Pro_Submit:
                                        if (myBitmap != null) {
                                            bytes = Bitmap2Base64(myBitmap);
                                        }else{
                                            Toast.makeText(ProblemInformation.this, "请上传照片", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        TextView ProblemInformation_Order = (TextView)findViewById(R.id.ManagerProblemInformation_Order);
                                        if(ProblemInformation_Order.getText().toString()==null||ProblemInformation_Order.getText().toString().equals("")){
                                            Toast.makeText(ProblemInformation.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if(ManagerProblemInformation_Manager.getText().toString()==null||ManagerProblemInformation_Manager.getText().toString().equals("")){
                                            Toast.makeText(ProblemInformation.this, "管理局不能为空", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        /*TextView ProblemInformation_Person = (TextView)findViewById(R.id.ManagerProblemInformation_Person);
                                        if(ProblemInformation_Person.getText().toString()==null||ProblemInformation_Person.getText().toString().equals("")){
                                            Toast.makeText(ProblemInformation.this, "负责人不能为空", Toast.LENGTH_SHORT).show();
                                            return;
                                        }*/
                                        TextView ProblemInformation_Type = (TextView)findViewById(R.id.ManagerProblemInformation_Type);
                                        if(ProblemInformation_Type.getText().toString()==null||ProblemInformation_Type.getText().toString().equals("")){
                                            Toast.makeText(ProblemInformation.this, "项目不能为空", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        if(ProblemInformation_reason.getText().toString()==null||ProblemInformation_reason.getText().toString().equals("")){
                                            Toast.makeText(ProblemInformation.this, "问题不能为空", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        ProgressDialog2 = new MyProgressDialog(ProblemInformation.this,false,"提交中");
                                        new AddProblemRunnable(person_ID,Manager_arr_id[Manager_ID],Fuzeren_arr_id[Fuzeren_id],project_id,ProblemInformation_reason.getText().toString(),bytes).getShopsData(ProblemInformation.this);
                                        break;
                case R.id.ManagerProblemInformation_Back:
                                                            finish();
                                                            break;
            }
        }
    }
    //相册相机
    private String [] select={"相机","相册"};
    private void selectPhoto(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ProblemInformation.this);
        builder.setSingleChoiceItems(select, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(select[which].equals("相机")){
                    CameraMethod();
                    dialog.dismiss();
                }else if(select[which].equals("相册")){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private int REQUEST_CODE_CAPTURE_CAMEIA =0;
    private String sdPath;//sd卡路径
    private String picPath;//图片路径
    private String picPath1;//图片路径
    private void CameraMethod(){
        sdPath = Environment.getExternalStorageDirectory().getPath();
        picPath = sdPath + "/" + System.currentTimeMillis() + "temp.png";
        picPath1=System.currentTimeMillis() + "temp.png";
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            if(Build.VERSION.SDK_INT>=24) {//7.0以上调用相机
                File file = new File(getFilesDir(), "images");//getFilesDir()+"/images/"+picPath1图片地址  images必须和res/xml/file_piths中path对应
                File newFile = new File(file, picPath1);
                if (!newFile.getParentFile().exists()) newFile.getParentFile().mkdirs();
                Uri imageUri = FileProvider.getUriForFile(ProblemInformation.this, "com.jph.takephoto.fileprovider", newFile);//通过FileProvider创建一个content类型的Uri
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
                startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
            }else{//7.0以下调用相机（不包括7.0）
                Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                Uri uri = Uri.fromFile(new File(picPath));        //为拍摄的图片指定一个存储的路径
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
            }
            /*Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            Uri uri = Uri.fromFile(new File(picPath));        //为拍摄的图片指定一个存储的路径
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);*/
        }
        else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myBitmap!=null) {
            myBitmap.recycle();
        }
    }
    private byte[] mContent;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //相册
        if(requestCode == 1) {
            ContentResolver resolver = getContentResolver();
            if (data != null) {
                if (requestCode == 1) {
                    try {
                        // 获得图片的uri
                        Uri originalUri = data.getData();
                        // 将图片内容解析成字节数组
                        mContent = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));
                        // 将字节数组转换为ImageView可调用的Bitmap对象
                        if(myBitmap!=null){
                            myBitmap=null;
                        }
                        myBitmap = getPicFromBytes(mContent, null);
                        // //把得到的图片绑定在控件上显示
                        take_photo.setImageBitmap(myBitmap);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        //照相机
        if(requestCode== Activity.RESULT_CANCELED){//未拍照返回来
            //Toast.makeText(this, "11111111", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {//拍照之后返回来的

            FileInputStream fis = null;
            try {
                Log.e("sdPath2", picPath);
                //把图片转化为字节流
                if(Build.VERSION.SDK_INT >= 24){
                    fis = new FileInputStream(getFilesDir()+"/images/"+picPath1);
                }else{
                    fis = new FileInputStream(picPath);
                }
                if (myBitmap != null) {
                    myBitmap = null;
                }
                //把流转化图片
                myBitmap = BitmapFactory.decodeStream(fis);
                File file;
                //文件大小 picPath目标图片地址
                if (Build.VERSION.SDK_INT >= 24){
                    file = new File(getFilesDir()+"/images/"+picPath1);
                }else{
                    file = new File(picPath);
                }
                Log.e("warn", file.length() / 1024 + "kb");
                if (myBitmap != null) {
                    take_photo.setImageBitmap(myBitmap);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    /*
     * 将bitmap转换为base64字节数组
     */
    public String Bitmap2Base64(Bitmap bitmap) {
        try {
            // 先将bitmap转换为普通的字节数组
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50, out);
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            // 将普通字节数组转换为base64数组
            //byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String encode = Base64.encodeToString(buffer, Base64.DEFAULT);
            return encode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }
    //管理局点击事件
    //管理局
    String Manager_arr_name [] =null;
    String Manager_arr_id [] =null;
    String Fuzeren_arr_id [] =null;
    int Manager_ID = -1;
    int Fuzeren_id = -1;
    private void setManagerMethod(){
       /* Log.e("warn",list.size()+":长度");
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
        }*/

        if(list.size()>0){
            if(Manager_arr_name==null){
                Manager_arr_name = new String[list.size()];
            }
            if(Manager_arr_id==null){
                Manager_arr_id = new String[list.size()];
            }
            if(Fuzeren_arr_id==null){
                Fuzeren_arr_id= new String[list.size()];
            }
            for (int i = 0; i < list.size(); i++) {
                Manager_arr_name[i] = list.get(i).getManager_name()+"("+list.get(i).getPerson_Name()+")";
                Log.e("warn",list.get(i).getManager_name());
                Manager_arr_id[i] = list.get(i).getManager_id();
                Fuzeren_arr_id[i] = list.get(i).getPerson_ID();
            }
        }
        ManagerJupopWindow();
    }
    //管理局加载ProgressDialog
    private void cancelProgressDialog(){
        if(ProgressDialog!=null){
            ProgressDialog.dismiss();
            ProgressDialog=null;
        }
    }
    //管理局pop
    private PopupWindow popupwindow1;
    WindowManager.LayoutParams lps1;

    private void ManagerJupopWindow() {
        RelativeLayout CompanyComblem_Ju = (RelativeLayout)findViewById(R.id.CompanyComblem_Ju);
        lps1 = getWindow().getAttributes();
        lps1.alpha =0.5f; //0.0-1.0
        getWindow().setAttributes(lps1);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        View addview = LayoutInflater.from(this).inflate(R.layout.problemsumactivitymanagerjupop_layout, null);

        ListView MlistView = (ListView)addview.findViewById(R.id.MListView);
        MlistView.setOnItemClickListener(new ManagerJuListviewListener());
        MlistView.setAdapter(new ArrayAdapter<String>(this,R.layout.problemsumactivityarrayadapter_layout,Manager_arr_name));
        //BD_addinit(addview);
        popupwindow1 = new PopupWindow(addview,width/2, ViewGroup.LayoutParams.WRAP_CONTENT, true);//2,3参数为宽高
        popupwindow1.setTouchable(true);//popupWindow可触摸
        popupwindow1.setOutsideTouchable(true);//点击popupWindow以外区域消失
        popupwindow1.setFocusable(true);
        popupwindow1.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                Log.i("mengdd", "onTouch : ");
                return false;
            }
        });
        //设置popwindow消失的监听事件
        popupwindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lps1.alpha =1; //0.0-1.0
                getWindow().setAttributes(lps1);
            }
        });
        popupwindow1.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置好参数之后再show
        popupwindow1.showAsDropDown(ManagerProblemInformation_Manager,0,0);//在view(top)控件正下方，以view为参照点第二个参数为popwindow距离view的横向距离，
        //第三个参数为y轴即popwindow距离view的纵向距离
    }

    private void popupwindow1(){
        if(popupwindow1!=null){
            popupwindow1.dismiss();
        }
    }
    private class ManagerJuListviewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Manager_ID = position;
            Fuzeren_id = position;
            ManagerProblemInformation_Manager.setText( Manager_arr_name[position]);
            popupwindow1();
        }
    }
    //负责人
    String Manager_arr_person [] =null;
    String Manager_arr_personid [] =null;
    private void setPersonMethod(){
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

    }
    //管理局加载ProgressDialog
    private void cancelProgressDialog1(){
        if(ProgressDialog1!=null){
            ProgressDialog1.dismiss();
            ProgressDialog1=null;
        }
    }

//提交

private void cancelProgressDialog2(){
    if(ProgressDialog2!=null){
        ProgressDialog2.dismiss();
        ProgressDialog2=null;
    }
}
//获取登录信息
    private String person_ID="";
    private String person_name="";
    private String project_id="";
    private String project_name="";
    private void getLogin(){
        PublicBeen pb =(PublicBeen) getIntent().getSerializableExtra("information");
        person_ID =  pb.getLogin_ID();
        person_name = pb.getLogin_Name();
        Log.e("warn",person_name );
                //序号 (姓名)
        TextView ProblemInformation_Order = (TextView)findViewById(R.id.ManagerProblemInformation_Order);
        ProblemInformation_Order.setText(person_name);
        project_id = pb.getLogin_proID();
        Log.e("warn",project_id+"dasd");
        project_name =  pb.getLogin_proName();
        Log.e("warn",project_name);
        TextView ProblemInformation_Type = (TextView)findViewById(R.id.ManagerProblemInformation_Type);
        ProblemInformation_Type.setText(project_name);

    }


}
