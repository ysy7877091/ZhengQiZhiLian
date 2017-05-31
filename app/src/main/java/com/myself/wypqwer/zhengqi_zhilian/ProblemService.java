package com.myself.wypqwer.zhengqi_zhilian;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/20.
 */

public class ProblemService extends Service{

    public ProblemService(){

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public static class  ProblemReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_RECEIVED")){
                    Log.e("warn","cn.jpush.android.intent.NOTIFICATION_RECEIVED");
                }
        }
    }
}
