package RunnableModel;

import android.util.Log;

import com.myself.wypqwer.zhengqi_zhilian.Path;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Administrator on 2017/5/23.
 */

public class XiuGaiPWDRunnable implements PublicOneListMehtodInterface {
    private String pwd;
    PublicOneListInterface getData;
    public XiuGaiPWDRunnable( String pwd){
        this.pwd=pwd;
    }
    @Override
    public void getShopsData(PublicOneListInterface getDataListener) {
        this.getData=getDataListener;
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    // 命名空间
                    String nameSpace = "http://tempuri.org/";
                    // 调用的方法名称
                    String methodName = "Check_ProjectBrief";
                    // EndPoint
                    String endPoint = Path.get_faGai_Url();
                    // SOAP Action
                    String soapAction = "http://tempuri.org/Check_ProjectBrief";
                    // 指定WebService的命名空间和调用的方法名
                    SoapObject rpc = new SoapObject(nameSpace, methodName);
                    //设置需调用WebService接口需要传入的参数CarNum
                    if(!pwd.equals("")){
                        rpc.addProperty("ProID",pwd);
                    }
                    // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(rpc);
                    HttpTransportSE ht = new HttpTransportSE(endPoint,10000);
                    ht.debug=true;
                    Log.e("warn","50");
                    Object object=null;
                    (new MarshalBase64()).register(envelope);
                    try {
                        // 调用WebService
                        ht.call(soapAction,envelope);
                        object = envelope.getResponse();
                    } catch (Exception e) {
                        String msg1 = e.getMessage();
                        if (e instanceof java.net.SocketTimeoutException) {
                            msg1 = "连接服务器超时，请检查网络";
                            getData.onGetDataError(msg1);
                            return;
                        } else if (e instanceof java.net.UnknownHostException) {
                            msg1 = "未知服务器，请检查配置";
                            getData.onGetDataError(msg1);
                            return;
                        }

                    }
                    if(object!=null) {
                        getData.onGetDataSuccess(object.toString());
                    }else{
                        getData.onEmptyData("修改密码失败");
                    }
                } catch (Exception e){
                    getData.onGetDataError("网路或服务器异常");
                }
            }
        }.start();

    }
}
