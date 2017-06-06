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
 * Created by Administrator on 2017/6/2.
 */

public class Manager_Problem_ChuLiRunnable implements PublicMethodInterface {
    private PublicInterface DataListener;
    private List<PublicBeen> list =new ArrayList<>();
    private String managerid;
    private String state;
    public Manager_Problem_ChuLiRunnable(String managerid,String state){
        this.managerid=managerid;
        this.state=state;
    }

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
                    String methodName = "Update_ProblemState";
                    // EndPoint
                    String endPoint = Path.get_faGai_Url();
                    // SOAP Action
                    String soapAction = "http://tempuri.org/Update_ProblemState";
                    // 指定WebService的命名空间和调用的方法名
                    SoapObject rpc = new SoapObject(nameSpace, methodName);
                    //设置需调用WebService接口需要传入的参数CarNum

                        rpc.addProperty("ID",managerid);

                        rpc.addProperty("state", state);

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
                        DataListener.onEmptyData("无数据");
                        return;
                    }
                    if(envelope.getResponse()!=null) {

                        SoapObject soapProvince = (SoapObject)envelope.bodyIn;

                        SoapObject soap1 = (SoapObject) soapProvince.getProperty("Update_ProblemStateResult");//获取整个数据
                        Log.e("warn",soapProvince.getProperty("Update_ProblemStateResult").toString());

                        for(int i=0;i<soap1.getPropertyCount();i++){
                            PublicBeen pb =new PublicBeen();
                          //  SoapObject soap2 = (SoapObject)soap1.getProperty(i);//获取每条子数据
                            //Log.e("warn",soap2.toString());
                            //地址将饭斜杠替换成正斜杠 要不然加载不出来图片
                            if(!soap1.getProperty("STATE").toString().equals("anyType{}")){
                                pb.setProject_state(soap1.getProperty("STATE").toString());
                            }else {
                                pb.setCompanyName("");
                            }
                           /* if(soap2.getProperty("FUNAME").toString().equals("anyType{}")){
                                pb.setPerson_Name("");
                            }else{
                                pb.setPerson_Name(soap2.getProperty("FUNAME").toString());
                            }
                            pb.setProject_id(soap2.getProperty("QUEID").toString());
                            pb.setManager_id(soap2.getProperty("GUANID").toString());
                            pb.setManager_name(soap2.getProperty("GLJNAME").toString());
                            pb.setContent(soap2.getProperty("PROBLEMCONTENT").toString());
                            pb.setProject_state(soap2.getProperty("STATE").toString());
                            pb.setProject_TIME(soap2.getProperty("TIME").toString());
                            pb.setIMAGESOURCE("http://beidoujieshou.sytxmap.com:6500"+soap2.getProperty("IMAGESOURCE").toString());*/
                            list.add(pb);
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
