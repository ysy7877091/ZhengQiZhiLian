package com.myself.wypqwer.zhengqi_zhilian;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import JavaBeen.PublicBeen;
import RunnableModel.Manager_Problem_ChuLiRunnable;
import RunnableModel.PublicInterface;
import company.CompanyComblemInformation;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ProblemInformation extends AppCompatActivity implements PublicInterface{
    private MyProgressDialog progressDialog;
    private ImageLoader imageLoader;
    private int i = 0;
    private RequestQueue queue;   //volley请求
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.probleminformation_layout);
        CommonMethod.setStatuColor(ProblemInformation.this,R.color.white);
        queue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(queue, new BitmapCache());
        init();
       // new ProblemSumInformation().getShopsData(ProblemInformation.this);
    }
    private PublicBeen pb;
    private void init(){
        pb =(PublicBeen) getIntent().getSerializableExtra("xinxi");

        if(getIntent().getStringExtra("leader")!=null){
            LinearLayout ll_regitANDagree =(LinearLayout)findViewById(R.id.ll_regitANDagree);
            ll_regitANDagree.setVisibility(View.GONE);
        }

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        long now = Integer.parseInt(pb.getProject_TIME());
        calendar.setTimeInMillis(now*1000);
        formatter.format(calendar.getTime());

        //序号
        TextView ProblemInformation_Order = (TextView)findViewById(R.id.ProblemInformation_Order);
        ProblemInformation_Order.setText(pb.getProject_id());
        //提报时间
        TextView ProblemInformation_Time = (TextView)findViewById(R.id.ProblemInformation_Time);
        ProblemInformation_Time.setText(formatter.format(calendar.getTime()));
        //责任人
        TextView ProblemInformation_Person = (TextView)findViewById(R.id.ProblemInformation_Person);
        ProblemInformation_Person.setText(pb.getLogin_Name());
        //提报问题
        TextView ProblemInformation_reason = (TextView)findViewById(R.id.ProblemInformation_reason);
        ProblemInformation_reason.setText(pb.getContent());
        //提报类型
        TextView ProblemInformation_Type = (TextView)findViewById(R.id.ProblemInformation_Type);
        ProblemInformation_Type.setText(pb.getManager_name());
        //图片
        NetworkImageView Manager_tupian = ( NetworkImageView)findViewById(R.id.Manager_tupian);
        Manager_tupian.setTag(pb.getIMAGESOURCE());
        loadImageByImageLoader(Manager_tupian, pb.getIMAGESOURCE());

        Button bohui = (Button)findViewById(R.id.bohui);
        bohui.setOnClickListener(new ProblemInformationListener());
        Button chuli = (Button)findViewById(R.id.chuli);
        chuli.setOnClickListener(new ProblemInformationListener());
        Button Manager_ProblemInformation_Back =(Button)findViewById(R.id.Manager_ProblemInformation_Back);
        Manager_ProblemInformation_Back.setOnClickListener(new ProblemInformationListener());
    }
    private class ProblemInformationListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bohui:
                                progressDialog = new MyProgressDialog(ProblemInformation.this,false,"");
                                new Manager_Problem_ChuLiRunnable(pb.getProject_id(),"2").getShopsData(ProblemInformation.this);
                                break;
                case R.id.chuli:
                                progressDialog = new MyProgressDialog(ProblemInformation.this,false,"");
                                new Manager_Problem_ChuLiRunnable(pb.getProject_id(),"1").getShopsData(ProblemInformation.this);
                                break;
                case R.id.Manager_ProblemInformation_Back:
                                                                finish();
                                                                break;
            }
        }
    }
    private List<PublicBeen> list;
    @Override
    public void onGetDataSuccess(List<PublicBeen> list) {
        this.list=list;
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
                if(list!=null&&list.size()>0){
                    if(list.get(0).getProject_state().equals("0")){
                        Toast.makeText(ProblemInformation.this, "提交成功", Toast.LENGTH_SHORT).show();
                        Intent  intent1 =new Intent();
                        ProblemInformation.this.setResult(1,intent1);
                        finish();
                    }else{
                        Toast.makeText(ProblemInformation.this, "提交失败", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ProblemInformation.this, "提交失败", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(ProblemInformation.this,(String)msg.obj, Toast.LENGTH_SHORT).show();
            }
            if(progressDialog!=null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    };
    //加载图片
    private void loadImageByImageLoader(NetworkImageView image, String goodsUrl) {

        //创建ImageLoader对象，参数（加入请求队列，自定义缓存机制）

        image.setDefaultImageResId(R.mipmap.image);
        image.setErrorImageResId(R.mipmap.image);
        image.setImageUrl(goodsUrl, imageLoader);

    }
    //自定义图片缓存BitmapCache
    private class BitmapCache implements ImageLoader.ImageCache {

        //使用安卓提供的缓存机制
        private LruCache<String, Bitmap> mCache;

        //重写构造方法
        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;  //缓存大小为10兆
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };

        }

        @Override
        public Bitmap getBitmap(String s) {
            return mCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            mCache.put(s,bitmap);
        }
    }

}
