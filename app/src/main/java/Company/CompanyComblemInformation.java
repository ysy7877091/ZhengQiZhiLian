package company;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.myself.wypqwer.zhengqi_zhilian.CommonMethod;
import com.myself.wypqwer.zhengqi_zhilian.MyProgressDialog;
import com.myself.wypqwer.zhengqi_zhilian.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import JavaBeen.PublicBeen;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CompanyComblemInformation extends AppCompatActivity {
    private MyProgressDialog ProgressDialog;
    private ImageLoader imageLoader;
    private int i = 0;
    private RequestQueue queue;   //volley请求
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companycombleminformation_layout);
        CommonMethod.setStatuColor(CompanyComblemInformation.this,R.color.white);
        queue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(queue, new BitmapCache());
        init();
    }
    private void init(){


        PublicBeen pb =(PublicBeen)getIntent().getSerializableExtra("xinxi") ;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        long now = Integer.parseInt(pb.getProject_TIME());
        calendar.setTimeInMillis(now*1000);
        formatter.format(calendar.getTime());

        if(pb==null){
            Toast.makeText(this, "获取信息失败", Toast.LENGTH_SHORT).show();
            return;
        }
        //返回
        Button computer_Back = (Button)findViewById(R.id.Company_ProblemInformation_Back);
        computer_Back.setOnClickListener(new ComputerInformationListener());
        //序号
        TextView order = (TextView)findViewById(R.id.Company_ProblemInformation_Order);
        order.setText(pb.getProject_id());
        //提报时间
        TextView CompanyProblemInformation_time= (TextView)findViewById(R.id.Company_ProblemInformation_Time);
        CompanyProblemInformation_time.setText(formatter.format(calendar.getTime()));
        //责任人
        TextView zerenren = (TextView)findViewById(R.id.Company_ProblemInformation_Person);
        zerenren.setText(pb.getManager_name());
        //提报类型
        TextView type = (TextView)findViewById(R.id.Company_ProblemInformation_Type);
        type.setText(pb.getManager_name());
        //问题
        TextView wenti = (TextView)findViewById(R.id.Company_ProblemInformation_reason);
        wenti.setText(pb.getContent());
        //图片
        NetworkImageView Company_tupian =(NetworkImageView)findViewById(R.id.Company_tupian);
        Company_tupian.setTag(pb.getIMAGESOURCE());
        loadImageByImageLoader(Company_tupian, pb.getIMAGESOURCE());
    }



    private class ComputerInformationListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Company_ProblemInformation_Back:finish();break;

            }
        }
    }
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
