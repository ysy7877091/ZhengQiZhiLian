package RunnableModel;

import android.util.Log;

import com.myself.wypqwer.zhengqi_zhilian.Path;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import JavaBeen.PublicBeen;

/**
 * Created by Administrator on 2017/5/23.
 */

public class XiuGaiPWDRunnable implements PublicOneListMehtodInterface {
    private String pwd;
    PublicOneListInterface getData;
    private String user;
    private String oldPwd;
    public XiuGaiPWDRunnable( String pwd,String user,String oldPwd){
        this.pwd=pwd;
        this.user=user;
        this.oldPwd=oldPwd;
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
                    String methodName = "Update_Password";
                    // EndPoint
                    String endPoint = Path.get_faGai_Url();
                    // SOAP Action
                    String soapAction = "http://tempuri.org/Update_Password";
                    // 指定WebService的命名空间和调用的方法名
                    SoapObject rpc = new SoapObject(nameSpace, methodName);
                    //设置需调用WebService接口需要传入的参数CarNum

                    rpc.addProperty("loginName",user);
                    rpc.addProperty("OldPwd",oldPwd);
                    rpc.addProperty("NewPwd",pwd);
                    Log.e("warn",user);
                    Log.e("warn",oldPwd);
                    Log.e("warn",pwd);
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

                        SoapObject soapProvince = (SoapObject)envelope.bodyIn;

                        SoapObject soap1 = (SoapObject) soapProvince.getProperty("Update_PasswordResult");//获取整个数据
                        Log.e("warn",soapProvince.getProperty("Update_PasswordResult").toString());

                        Log.e("warn",soap1.getProperty("State").toString());

                        getData.onGetDataSuccess(soap1.getProperty("State").toString());
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
