package RunnableModel;

import android.os.Message;
import android.util.Log;

import com.myself.wypqwer.zhengqi_zhilian.Path;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ProblemSumInformation implements PublicOneListMehtodInterface{
    private PublicOneListInterface DataListener;
    @Override
    public void getShopsData(PublicOneListInterface getDataListener) {
        this.DataListener = getDataListener;
                new Thread(){
                    @Override
                    public void run() {
                        super.run();

                        /*try{
                            Log.e("warn","30");
                            // 命名空间
                            String nameSpace = "http://tempuri.org/";
                            // 调用的方法名称
                            String methodName = "Get_CarInfo";
                            // EndPoint
                            String endPoint = Path.get_faGai_Url();
                            // SOAP Action
                            String soapAction = "http://tempuri.org/Get_CarInfo";
                            // 指定WebService的命名空间和调用的方法名
                            SoapObject rpc = new SoapObject(nameSpace, methodName);
                            //设置需调用WebService接口需要传入的参数CarNum
                            rpc.addProperty("carNum",getIntent().getStringExtra("carNum").toString());
                            // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
                            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
                            envelope.dotNet = true;
                            envelope.setOutputSoapObject(rpc);

                            HttpTransportSE ht = new HttpTransportSE(endPoint,15000);
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
                            // 得到服务器传回的数据 数据时dataset类型的
                            int count1 = object.getPropertyCount();
                            Log.e("warn",String.valueOf(count1));
                            if(count1==0){
                                String msg ="无数据";
                                DataListener.onEmptyData(msg);
                            }
                            if(count1>0)
                            {
                                Log.e("warn","-----------------------------");
                                SoapObject soapProvince = (SoapObject) envelope.bodyIn;
                                Log.e("warn",soapProvince.getProperty("Get_CarInfoResult").toString()+":返回id");//dataset数据类型
                                String str = soapProvince.getProperty("Get_CarInfoResult").toString();

                                DataListener.onGetDataSuccess(str);
                            }
                        } catch (Exception e){
                            DataListener.onGetDataError("网络或服务器异常");
                        }*/


                    }
                }.start();
    }
}
