package com.myself.wypqwer.zhengqi_zhilian;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISLayerInfo;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.tasks.ags.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import JavaBeen.PublicBeen;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ShouYeMapActivity extends AppCompatActivity{
    private MapView mMapView;
    private MyProgressDialog ProgressDialog;
    private ArcGISDynamicMapServiceLayer layer;
    private ArcGISLayerInfo layerInforZJ = null;
    private MyProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouyemap_layout);
        CommonMethod.setStatuColor(ShouYeMapActivity.this,R.color.white);
        init();
    }
    private Envelope e;
    private void init(){
        setAlais();//设置别名
        StartService();//开启服务
        //右上角菜单
        Button MapMeau = (Button)findViewById(R.id.MapMeau);
        MapMeau.setOnClickListener(new ShouYeMapActivityListener());
        //搜索
        TextView Map_SouSuo = (TextView)findViewById(R.id.Map_SouSuo);
        Map_SouSuo.setOnClickListener(new ShouYeMapActivityListener());
        //地图
        mMapView = (MapView)findViewById(R.id.mapView);

        e = new Envelope(41502640.8973194, 4614039.69784388,41531975.8104773,4632772.5692145);//这里有4个坐标点，看似是一个矩形的4个顶点。
        //Envelope el = new Envelope(41489475.648039+3000,4629936.377077+15000,41527078.378675+7000,4595382.516492);//这里有4个坐标点，看似是一个矩形的4个顶点。
        mMapView.setExtent(e);

        ProgressDialog = new MyProgressDialog(ShouYeMapActivity.this,false,"");

        layer = new ArcGISDynamicMapServiceLayer(Path.get_MapUrl());
        layer.refresh();//刷新地图
        mMapView.addLayer(layer);
        mMapView.setOnSingleTapListener(mOnSingleTapListener);//单击地图上的泵站
        mMapView.setOnStatusChangedListener(new mMapViewChangListener());
        new Thread(getlayer).start();
    }
    private class mMapViewChangListener implements OnStatusChangedListener {//OnStatusChangedListener接口用于监听MapView或Layer（图层）状态变化的监听器

        @Override
        public void onStatusChanged(Object o, STATUS status) {
            if (status == STATUS.INITIALIZED) {
            } else if (status == STATUS.LAYER_LOADED) {
                if (layer != null) {
                    if (layer.getLayers() != null) {

                        layerInforZJ = layer.getLayers()[0];
                        layerInforZJ.getName();
                        Log.e("warn",layerInforZJ.getLayers().length+"");
                        /* List<String> list1=null;
                        for(int i=0;i<list.size();i++){
                           Legend l= list.get(i);
                                   list1=l.getValues();

                        }
                        for(int j=0;j<list1.size();j++){
                            Log.e("warn","list1:"+list1.get(j));
                        }*/


                        Log.e("warn","图层总长度"+layer.getLayers().length);
                        for (int i = 0; i < layer.getLayers().length; i++) {
                            ArcGISLayerInfo layerInfor = layer.getLayers()[i];
                            if(layerInfor.getName().equals("泵站")){
                                layerInfor.setVisible(false);
                            }else if(layerInfor.getName().equals("企业红线")){
                                layerInfor.setVisible(false);
                            }else if(layerInfor.getName().equals("规划道路红线")){
                                layerInfor.setVisible(false);
                            }else if(layerInfor.getName().equals("分区")){
                                layerInfor.setVisible(false);
                            }else if(layerInfor.getName().equals("标注重点项目企业")){
                                layerInfor.setVisible(false);
                            }else{
                                layerInfor.setVisible(true);
                            }
                            Log.e("GISActivity地图服务加载", "图层名称：" + layerInfor.getName() + "");
                        }
                    }
                }
                //rlLoadView.setVisibility(View.INVISIBLE);
                cancelProgressDialog();
                //gifLoadGis.showCover();
                //Toast.makeText(GISActivity.this, "地图加载成功", Toast.LENGTH_SHORT).show();
            } else if (status == STATUS.INITIALIZATION_FAILED) {
                Toast.makeText(getApplicationContext(), "地图加载失败", Toast.LENGTH_SHORT).show();
                /*rlLoadView.setVisibility(View.INVISIBLE);
                gifLoadGis.showCover();*/
                cancelProgressDialog();
            } else if (status == STATUS.LAYER_LOADING_FAILED) {
                Toast.makeText(getApplicationContext(), "图层加载失败", Toast.LENGTH_SHORT).show();
                /*rlLoadView.setVisibility(View.INVISIBLE);
                gifLoadGis.showCover();*/
                cancelProgressDialog();
            }
        }
    }
    //地图点击事件
    Point point;
    OnSingleTapListener mOnSingleTapListener = new OnSingleTapListener() {
        @Override
        public void onSingleTap(float x, float y) {

               progressDialog = new MyProgressDialog(ShouYeMapActivity.this, false,"");
                point = mMapView.toMapPoint(x, y);
                /*AsyncQueryTask ayncQuery = new AsyncQueryTask();
                ayncQuery.execute(x, y);*/
            AsyncQueryTask(x,y);
        }
    };

   //点击地图 获取相应的区域
    private void AsyncQueryTask(float x, float y){
        GraphicsLayer layer = GetGraphicLayer();
        if (layer != null && layer.isInitialized() && layer.isVisible()) {
            Graphic result = null;
            // 检索当前 光标点（手指按压位置）的附近的 graphic对象
            result = GetGraphicsFromLayer(x, y, layer);
            if (result != null) {
                // 获得附加特别的属性
                // 显示提示

                String name = getValue(result, "XMMC", "");//点击时获取的点击区域名称 RefName获取名称参数 DocPath获取参数
                String id = getValue(result,"XH", "");//点击时获取的点击区域名称 RefName获取名称参数 DocPath获取参数
                cancelprogressDialog();
                if(!id.equals("")&&!name.equals("")){
                    Intent intent = new Intent(ShouYeMapActivity.this,ComputerInformation.class);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "查询结果为空", Toast.LENGTH_SHORT).show();
                }
            }else{
                cancelprogressDialog();
            }
        }else{
            cancelprogressDialog();
        }
    }
    /*
    * 从一个图层里里 查找获得 Graphics对象. x,y是屏幕坐标,layer
    * 是GraphicsLayer目标图层（要查找的）。相差的距离是50像素。
    */
    private Graphic GetGraphicsFromLayer(double xScreen, double yScreen,
                                         GraphicsLayer layer) {
        Graphic result = null;
        try {
            int[] idsArr = layer.getGraphicIDs();
            double x = xScreen;
            double y = yScreen;
            for (int i = 0; i < idsArr.length; i++) {
                Graphic gpVar = layer.getGraphic(idsArr[i]);
                if (gpVar != null) {
                    Point pointVar = (Point) gpVar.getGeometry();
                    pointVar = mMapView.toScreenPoint(pointVar);
                    double x1 = pointVar.getX();
                    double y1 = pointVar.getY();
                    if (Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1)) < 100) {
                        result = gpVar;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    String getValue(Graphic graphic, String key, String defaultVal) {
        Object obj = graphic.getAttributeValue(key);

        if (obj == null)
            return defaultVal;
        else
            return obj.toString();
    }
    //监听事件
    private class ShouYeMapActivityListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.MapMeau:
                                    MeauPopWindows();
                                    break;
                case R.id.MapPop_Pwd:
                                            Intent intent2 = new Intent(ShouYeMapActivity.this,MainXiuGaiPWD.class);
                                            intent2.putExtra("user",getIntent().getStringExtra("alais"));
                                            intent2.putExtra("pwd",getIntent().getStringExtra("pwd"));
                                            startActivity(intent2);
                                            //Toast.makeText(ShouYeMapActivity.this, "系统维护，该功能不久将开启", Toast.LENGTH_SHORT).show();
                                            closePopwindow();
                                            break;
                case R.id.MapPop_SumProblem:
                                                Intent intent1 = new Intent(ShouYeMapActivity.this,ProblemSumActivity.class);
                                                startActivity(intent1);
                                                closePopwindow();
                                                break;
                case R.id.Map_SouSuo:
                                        if(sb.toString().contains(",")) {
                                            Intent intent = new Intent(ShouYeMapActivity.this, MapSouSuoActivity.class);
                                            intent.putExtra("company", sb.toString());
                                            startActivityForResult(intent, 0);
                                            if( GetgraphicLayer()!=null){//名称图层
                                                GetgraphicLayer().removeAll();
                                                mMapView.setExtent(e);
                                            }
                                        }
                                        break;
            }
        }
    }
    //查询公司位置并放大和显示公司名称
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            String name =data.getStringExtra("NAME");
            Log.e("warn",name);
            for(int i=0;i<list.size();i++){
                if(name.equals(list.get(i).getCompanyName())){
                    //放大区域
                    Envelope el = new Envelope(list.get(i).getPoint().getX()-100,list.get(i).getPoint().getY(),list.get(i).getPoint().getX()+100,list.get(i).getPoint().getY());
                    Log.e("warn",list.get(i).getPoint().toString());

                    //将文字转为图片添加到 图层上去
                    Drawable image=createMapBitMap(name);//将文字转为图片
                    //将文字图片添加到覆盖物symbol上
                    PictureMarkerSymbol symbol = new PictureMarkerSymbol(image);
                    //设置覆盖物偏离point（指定坐标点的距离）的距离
                    symbol.setOffsetX(0);
                    symbol.setOffsetY(-30);
                    //将覆盖物添加到小图层上
                    Graphic g = new Graphic(list.get(i).getPoint(), symbol,0);
                    GraphicsLayer graphicsLayer=GetgraphicLayer();
                    graphicsLayer.addGraphic(g);


                    mMapView.setExtent(el);
                }
            }
        }
    }
    private GraphicsLayer mgraphicsLayer= new GraphicsLayer();;//覆盖物
    private GraphicsLayer GetgraphicLayer() {

        mMapView.addLayer(mgraphicsLayer);

        return mgraphicsLayer;
    }

    /**中文标注乱码所以将文字转为图片
     * 文字转换BitMap
     * @param text
     * @return
     */
    public static Drawable createMapBitMap(String text) {

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(20);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        float textLength = paint.measureText(text);

        int width = (int) textLength + 10;
        int height = 40;

        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas cv = new Canvas(newb);
        cv.drawColor(Color.parseColor("#00000000"));

        cv.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));

        cv.drawText(text, width / 2, 20, paint);

        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储

        return new BitmapDrawable(newb);
    }









    private Dialog popupWindow ;
    private void MeauPopWindows() {
        //获取顶部标题栏的高度
        RelativeLayout top = (RelativeLayout) findViewById(R.id.top_rl);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        popupWindow  = new Dialog(this);
        popupWindow .requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        // setContentView可以设置为一个View也可以简单地指定资源ID
        // LayoutInflater
        // li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        // View v=li.inflate(R.layout.dialog_layout, null);
        // dialog.setContentView(v);
        View addview = LayoutInflater.from(this).inflate(R.layout.shouyepop_layout, null);
        LinearLayout meau_LL = (LinearLayout)addview.findViewById(R.id.top_ll);
        //设置总布局大小
        LinearLayout.LayoutParams  linearParams =(LinearLayout.LayoutParams) meau_LL.getLayoutParams();
        linearParams.height=height/5;
        meau_LL.setLayoutParams(linearParams);


        popupWindow .setContentView(addview);
        addinit(addview);

        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = popupWindow .getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.CENTER);
        //dialog位置
        lp.x = -1000; // 新位置X坐标
        lp.y =-((height- height/5)/2-top.getHeight()); // 新位置Y坐标
        //dialog大小
        lp.width = width/3; // 宽度
        //lp.height = height/2+200; // 高度
        lp.height = height/5;

        dialogWindow.setAttributes(lp);
        popupWindow .show();
    }
    private void closePopwindow() {
        popupWindow.dismiss();
        if(popupWindow!=null){ popupWindow = null;}
    }

    private void addinit(View view) {
        TextView MapPop_ShouYe = (TextView)view.findViewById(R.id.MapPop_Pwd);
        MapPop_ShouYe.setOnClickListener(new ShouYeMapActivityListener());
        TextView MapPop_SumProblem = (TextView)view.findViewById(R.id.MapPop_SumProblem);
        MapPop_SumProblem.setOnClickListener(new ShouYeMapActivityListener());
        /*TextView MapPop_XiuGaiPWD = (TextView)view.findViewById(R.id.MapPop_XiuGaiPWD);
        MapPop_XiuGaiPWD.setOnClickListener(new ShouYeMapActivityListener());*/
    }

    Runnable getlayer = new Runnable() {
        @Override
        public void run() {

            String url = Path.get_MapUrl().concat("/0");
            // 查询所需的参数类
            Query query = new Query();


            query.setReturnGeometry(true);
            query.setOutFields(new String[] {"*"});
            //String whereClause = queryParams[1];
               /* SpatialReference sr = SpatialReference.create(2365);// 建立一个空间参考
                // WKID_WGS84_WEB_MERCATOR_AUXILIARY_SPHERE（102100）
                query.setGeometry(new Envelope(41502640.8973194, 4614039.69784388,
                        41531975.8104773, 4632772.5692145));// 设置查询空间范围
                query.setOutSpatialReference(sr);// 设置查询输出的坐标系*/
            query.setReturnGeometry(true);// 是否返回空间信息
            query.setWhere("1=1");// where条件

            com.esri.core.tasks.ags.query.QueryTask qTask = new com.esri.core.tasks.ags.query.QueryTask(url);// 查询任务类
            FeatureSet fs = null;
            Log.i(null, "doInBackground is running !");
            try {
                fs = qTask.execute(query);// 执行查询，返回查询结果
            } catch (Exception e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                msg.obj = fs;
                msg.setData(bundle);
                handler.sendMessage(msg);

            }
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            msg.obj = fs;
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FeatureSet fs = (FeatureSet) msg.obj;
            if (fs != null) {
                Graphic[] grs = fs.getGraphics();
                Log.e("warn", grs.length + "adsds");
                for(int i=0;i<grs.length;i++){
                    AddNewGraphic(grs[i]);
                }

            }else{
                Toast.makeText(ShouYeMapActivity.this, "获取企业图层失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private StringBuffer sb = new StringBuffer();
    private List<PublicBeen> list =new ArrayList<>();
    //添加企业覆盖物
    private void AddNewGraphic(Graphic grs) {
        BaoCun(grs);

        sb.append(grs.getAttributeValue("XMMC")+",");
        GraphicsLayer graphicsLayer = GetGraphicLayer();//创建新图层对象

        if (graphicsLayer  != null &&graphicsLayer .isInitialized() && graphicsLayer .isVisible()) {
            // 转换坐标
            //Point pt = mMapView.toMapPoint(new Point(x, y));

            // 附加特别的属性
            //Map<String, Object> map = new HashMap<String, Object>();
            //map.put("tag", "" + (char) (m_Char++));
            // 创建 graphic对象
            CreateGraphic(graphicsLayer,grs);
            // 添加 Graphics 到图层
        }
    }
    private void CreateGraphic(GraphicsLayer graphicsLayer,Graphic grs) {

        Drawable image;

            image = getBaseContext()
                    .getResources().getDrawable(R.mipmap.mapcomputerdian);

        PictureMarkerSymbol symbol = new PictureMarkerSymbol(image);
        // 构建graphic
        // Graphic g = new Graphic(geometry, symbol);
        graphicsLayer.setRenderer(new SimpleRenderer(symbol));
        graphicsLayer.addGraphic(grs);//生成图层
    }
    private GraphicsLayer mGraphicsLayer= new GraphicsLayer();;//覆盖物
    private GraphicsLayer GetGraphicLayer() {

        mMapView.addLayer(mGraphicsLayer);

        return mGraphicsLayer;
    }
    private void cancelProgressDialog(){
        if(ProgressDialog!=null){
            ProgressDialog.dismiss();
            ProgressDialog=null;
        }
    }
    //获取地图企业的位置坐标和企业名称
    private void BaoCun(Graphic grs){
        Point p =(Point)grs.getGeometry();

        PublicBeen pb =new PublicBeen();
        if(p!=null&&grs.getAttributeValue("XMMC").toString()!=null){
            pb.setPoint(p);
            pb.setCompanyName(grs.getAttributeValue("XMMC").toString());
            list.add(pb);
        }
    }
    private void cancelprogressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }


//设置别名
private void setAlais(){

    SharedPreferences sp =getSharedPreferences("bieming",0);
    String value=sp.getString("key","0");
    Log.e("warn",value+":");
    String user=sp.getString("user","0");//保存的用户名
    String alias = getIntent().getStringExtra("alais");//新获取的用户名（别名）
    if(value.equals("1")&&alias.equals(user)) {
        return;
    }else {
        if (TextUtils.isEmpty(alias)) {
            Log.e("warn", "别名设置失败");
            return;
        }
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }
}
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    Log.e("warn","别名设置成功");
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    SharedPreferences sp = getSharedPreferences("bieming",0);
                    SharedPreferences.Editor edit=sp.edit();
                    edit.putString("user",alias);
                    edit.putString("key","1");
                    edit.commit();
                    break;
                case 6002:

                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:

            }
            //ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:

                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:

            }
        }
    };
    //开启服务
    private void StartService(){
        Intent intent = new Intent(ShouYeMapActivity.this,ProblemService.class);
        startService(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("是否退出应用");
            //builder.setTitle("是否退出应用");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
