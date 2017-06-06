package RunnableModel;

import java.util.ArrayList;
import java.util.List;

import JavaBeen.PublicBeen;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MainProblemSumActivity implements PublicMethodInterface{
    PublicInterface DataListener;
    List<PublicBeen> list = new ArrayList<>();
    @Override
    public void getShopsData(PublicInterface getDataListener) {
        this.DataListener=getDataListener;
        new Thread(){
            @Override
            public void run() {
                super.run();

                /*try{
                    Log.e("warn","30");
                    // 命名空间
                    String nameSpace = "http://tempuri.org/";
                    // 调用的方法名称
                    String methodName = "Get_CarNoPic_List";
                    // EndPoint
                    String endPoint =Path.get_ZanShibeidouPath();
                    // SOAP Action
                    String soapAction = "http://tempuri.org/Get_CarNoPic_List";
                    // 指定WebService的命名空间和调用的方法名
                    SoapObject rpc = new SoapObject(nameSpace, methodName);
                    //设置需调用WebService接口需要传入的参数CarNum
                    //rpc.addProperty("CarNum","");
                    // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(rpc);

                    HttpTransportSE ht = new HttpTransportSE(endPoint,10000);
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
                        DataListener.onEmptyData("无问题记录");
                        return;
                    }
                    if(count1>0)
                    {

                        for (int i = 0; i < count1; i++) {
                            Log.e("warn","-----------------------------");

                            SoapObject soapProvince = (SoapObject)object.getProperty(i);

                            Log.e("warn",soapProvince.getProperty("NUM").toString()+":");


                            Log.e("warn",soapProvince.getProperty("CARNUM").toString()+":");

                            //lieBiao.setD_NUM(soapProvince.getProperty("D_NUM").toString());

                            Log.e("warn",soapProvince.getProperty("CARTYPE").toString()+":");

                            //lieBiao.setD_OFFNUM(soapProvince.getProperty("D_OFFNUM").toString());

                            Log.e("warn",soapProvince.getProperty("PERID").toString()+":");

                            //lieBiao.setD_ONLINE(soapProvince.getProperty("D_ONLINE").toString());

                            Log.e("warn",soapProvince.getProperty("NAME").toString()+":");

                            //lieBiao.setD_CARNUMBER(soapProvince.getProperty("D_CARNUMBER").toString());

                            Log.e("warn",soapProvince.getProperty("TELNUMBER").toString()+":");

                            //lieBiao.setN_NUM(soapProvince.getProperty("N_NUM").toString());;

                       *//* Log.e("warn",soapProvince.getProperty("CARIMG").toString()+":");
                        sb.append(soapProvince.getProperty("CARIMG").toString()+",");
                        //lieBiao.setN_WD(soapProvince.getProperty("N_WD").toString());*//*
                        }
                        if(list.size()>0) {
                            DataListener.onGetDataSuccess(list);
                        }else{
                            DataListener.onEmptyData("无问题记录");
                        }
                    }
                } catch (Exception e){

                    DataListener.onGetDataError("网络或服务器异常");
                }*/

            }
        }.start();

    }
}
