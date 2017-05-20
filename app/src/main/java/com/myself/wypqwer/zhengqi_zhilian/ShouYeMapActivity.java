package com.myself.wypqwer.zhengqi_zhilian;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISLayerInfo;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.tasks.ags.query.Query;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ShouYeMapActivity extends AppCompatActivity {
    private MapView mMapView;
    private MyProgressDialog ProgressDialog;
    private ArcGISDynamicMapServiceLayer layer;
    private ArcGISLayerInfo layerInforZJ = null;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouyemap_layout);
        CommonMethod.setStatuColor(ShouYeMapActivity.this, R.color.white);
        init();
    }

    private void init() {
        Button MapMeau = (Button) findViewById(R.id.MapMeau);
        MapMeau.setOnClickListener(new ShouYeMapActivityListener());
        mMapView = (MapView) findViewById(R.id.mapView);

        Envelope ell = new Envelope(41502640.8973194, 4614039.69784388, 41531975.8104773, 4632772.5692145);//这里有4个坐标点，看似是一个矩形的4个顶点。
        Envelope el = new Envelope(41489475.648039 + 3000, 4629936.377077 + 15000, 41527078.378675 + 7000, 4595382.516492);//这里有4个坐标点，看似是一个矩形的4个顶点。
        mMapView.setExtent(ell);

        ProgressDialog = new MyProgressDialog(ShouYeMapActivity.this, false, "");

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
                        Log.e("warn", layerInforZJ.getLayers().length + "");
                        Log.e("warn", "图层总长度" + layer.getLayers().length);
                        for (int i = 0; i < layer.getLayers().length; i++) {
                            ArcGISLayerInfo layerInfor = layer.getLayers()[i];
                            if (layerInfor.getName().equals("泵站")) {
                                layerInfor.setVisible(false);
                            } else if (layerInfor.getName().equals("企业红线")) {
                                layerInfor.setVisible(false);
                            } else if (layerInfor.getName().equals("规划道路红线")) {
                                layerInfor.setVisible(false);
                            } else if (layerInfor.getName().equals("分区")) {
                                layerInfor.setVisible(false);
                            } else if (layerInfor.getName().equals("标注重点项目企业")) {
                                layerInfor.setVisible(false);
                            } else {
                                layerInfor.setVisible(true);
                            }
                            Log.e("GISActivity地图服务加载", "图层名称：" + layerInfor.getName() + "");
                        }
                    }
                }
                cancelProgressDialog();
            } else if (status == STATUS.INITIALIZATION_FAILED) {
                Toast.makeText(getApplicationContext(), "地图加载失败", Toast.LENGTH_SHORT).show();
                cancelProgressDialog();
            } else if (status == STATUS.LAYER_LOADING_FAILED) {
                Toast.makeText(getApplicationContext(), "图层加载失败", Toast.LENGTH_SHORT).show();
                cancelProgressDialog();
            }
        }
    }

    Point point;
    OnSingleTapListener mOnSingleTapListener = new OnSingleTapListener() {
        @Override
        public void onSingleTap(float x, float y) {

            progressDialog = new MyProgressDialog(ShouYeMapActivity.this, false, "");
            point = mMapView.toMapPoint(x, y);

            AsyncQueryTask(x, y);
        }
    };

    //点击地图 获取相应的区域
    private void AsyncQueryTask(float x, float y) {
        GraphicsLayer layer = GetGraphicLayer();
        if (layer != null && layer.isInitialized() && layer.isVisible()) {
            Graphic result = null;
            // 检索当前 光标点（手指按压位置）的附近的 graphic对象
            result = GetGraphicsFromLayer(x, y, layer);
            if (result != null) {
                // 获得附加特别的属性
                // 显示提示

                String name = getValue(result, "XMMC", "");//点击时获取的点击区域名称 RefName获取名称参数 DocPath获取参数
                String id = getValue(result, "XH", "");//点击时获取的点击区域名称 RefName获取名称参数 DocPath获取参数
                cancelprogressDialog();
                if (!id.equals("") && !name.equals("")) {
                    Intent intent = new Intent(ShouYeMapActivity.this, ComputerInformation.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "查询结果为空", Toast.LENGTH_SHORT).show();
                }
            } else {
                cancelprogressDialog();
            }
        } else {
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

    private class ShouYeMapActivityListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.MapMeau:
                    break;
            }
        }
    }

    Runnable getlayer = new Runnable() {
        @Override
        public void run() {

            String url = Path.get_MapUrl().concat("/0");
            // 查询所需的参数类
            Query query = new Query();
            query.setReturnGeometry(true);
            query.setOutFields(new String[]{"*"});
            query.setReturnGeometry(true);// 是否返回空间信息
            query.setWhere("1=1");// where条件
            com.esri.core.tasks.ags.query.QueryTask qTask = new com.esri.core.tasks.ags.query.QueryTask(url);// 查询任务类
            FeatureSet fs = null;
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
                for (int i = 0; i < grs.length; i++) {
                    AddNewGraphic(grs[i]);
                }
            } else {
                Toast.makeText(ShouYeMapActivity.this, "获取企业图层失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //添加企业覆盖物
    private void AddNewGraphic(Graphic grs) {
        Log.e("warn", grs.getAttributes().toString());
        GraphicsLayer graphicsLayer = GetGraphicLayer();//创建新图层对象

        if (graphicsLayer != null && graphicsLayer.isInitialized() && graphicsLayer.isVisible()) {
            // 转换坐标
            //Point pt = mMapView.toMapPoint(new Point(x, y));
            // 附加特别的属性
            //Map<String, Object> map = new HashMap<String, Object>();
            //map.put("tag", "" + (char) (m_Char++));
            // 创建 graphic对象
            CreateGraphic(graphicsLayer, grs);
            // 添加 Graphics 到图层
        }
    }

    private void CreateGraphic(GraphicsLayer graphicsLayer, Graphic grs) {

        Drawable image;

        image = getBaseContext().getResources().getDrawable(R.mipmap.mapcomputerdian);

        PictureMarkerSymbol symbol = new PictureMarkerSymbol(image);
        // 构建graphic
        // Graphic g = new Graphic(geometry, symbol);
        graphicsLayer.setRenderer(new SimpleRenderer(symbol));
        graphicsLayer.addGraphic(grs);//生成图层
    }

    private GraphicsLayer mGraphicsLayer = new GraphicsLayer();
    //覆盖物

    private GraphicsLayer GetGraphicLayer() {

        mMapView.addLayer(mGraphicsLayer);

        return mGraphicsLayer;
    }

    private void cancelProgressDialog() {
        if (ProgressDialog != null) {
            ProgressDialog.dismiss();
            ProgressDialog = null;
        }
    }

    private void cancelprogressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
