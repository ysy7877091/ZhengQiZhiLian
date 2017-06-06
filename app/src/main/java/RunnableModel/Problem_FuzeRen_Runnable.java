package RunnableModel;

import android.util.Log;

import com.myself.wypqwer.zhengqi_zhilian.Path;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import JavaBeen.PublicBeen;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Problem_FuzeRen_Runnable implements PublicMethodInterface{
    private PublicInterface DataListener;
    private List<PublicBeen> list =new ArrayList<>();
    @Override
    public void getShopsData(PublicInterface getDataListener) {
        this.DataListener=getDataListener;
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    // 命名空间
                    String nameSpace = "http://tempuri.org/";
                    // 调用的方法名称
                    String methodName = "GetList";
                    // EndPoint
                    String endPoint = Path.get_faGai_Url();
                    // SOAP Action
                    String soapAction = "http://tempuri.org/GetList";
                    // 指定WebService的命名空间和调用的方法名
                    SoapObject rpc = new SoapObject(nameSpace, methodName);
                    //设置需调用WebService接口需要传入的参数CarNum
                    //rpc.addProperty("CarNum","");
                    // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(rpc);

                    HttpTransportSE ht = new HttpTransportSE(endPoint,20000);
                    ht.debug=true;
                    Log.e("warn","50");
                    try {
                        // 调用WebService
                        ht.call(soapAction, envelope);
                    } catch (Exception e) {
                        String msg1 = e.getMessage();
                        if (e instanceof java.net.SocketTimeoutException) {
                            msg1 = "连接服务器超时，请检查网络";
                            DataListener.onGetDataError(msg1);
                            return;
                        } else if (e instanceof java.net.UnknownHostException) {
                            msg1 = "未知服务器，请检查配置";
                            DataListener.onGetDataError(msg1);
                            return;
                        }

                    }

                    SoapObject object;
                    // 开始调用远程方法
                    Log.e("warn","60");

                    object = (SoapObject) envelope.getResponse();
                    Log.e("warn","64");
                    // 得到服务器传回的数据 返回的数据时集合 每一个count是一个及集合的对象
                    int count1 = object.getPropertyCount();
                    Log.e("warn",String.valueOf(count1));
                    if(count1==0){
                        DataListener.onEmptyData("无管理局");
                        return;
                    }
                    if(count1>0) {

                       // for (int i = 0; i < count1; i++) {
                            Log.e("warn", "-----------------------------");

                            SoapObject soapProvince = (SoapObject)envelope.bodyIn;

                            SoapObject soap1 = (SoapObject) soapProvince.getProperty("GetListResult");//获取整个数据

                           // Log.e("warn",soapProvince.getProperty("GetListResult").toString());//

                        for(int i=0;i<soap1.getPropertyCount();i++){//soap1.getPropertyCount() 获取有多少条数据
                            PublicBeen pb = new PublicBeen();
                            SoapObject soap3=(SoapObject) soap1.getProperty(i);//取出每个数据信息(子信息)
                           // Log.e("warn",soap3.toString());
                            //每条信息中单个的标签值
                            if(soap3.getProperty("ID").toString().equals("anyType{}")){
                                pb.setPerson_ID("");
                            }else{
                                pb.setPerson_ID(soap3.getProperty("ID").toString());
                            }
                            if(soap3.getProperty("Name").toString().equals("anyType{}")){
                                pb.setPerson_Name("");
                            }else{
                                pb.setPerson_Name(soap3.getProperty("Name").toString());
                            }
                            Log.e("warn",soap3.getProperty("Power").toString());//每条信息中单个的标签值


                            if(soap3.getProperty("Power").toString().equals("1")){
                                SoapObject soap4=(SoapObject)soap3.getProperty(5);//）获取每条子数据中的整个子数据 （集合中的集合
                                for(int j=0;j<soap4.getPropertyCount();j++) {//整个子数据有多少条子数据soap4.getPropertyCount()
                                    SoapObject soap5 = (SoapObject) soap4.getProperty(j);//获取每条子数据
                                    Log.e("warn", soap5.getProperty("GLJNAME").toString());//获取没条子数据中单个标签的值
                                    Log.e("warn", soap5.getProperty("ID").toString());//获取没条子数据中单个标签的值
                                    pb.setManager_id(soap5.getProperty("ID").toString());
                                    pb.setManager_name(soap5.getProperty("GLJNAME").toString());
                                    list.add(pb);
                                }

                            }
                        }
                        DataListener.onGetDataSuccess(list);
                    }
                } catch (Exception e){
                    DataListener.onGetDataError("网路或服务器异常");
                }
            }
        }.start();
    }
}
