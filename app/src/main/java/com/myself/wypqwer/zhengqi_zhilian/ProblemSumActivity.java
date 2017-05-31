package com.myself.wypqwer.zhengqi_zhilian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProblemSumActivityAdapter;
import JavaBeen.PublicBeen;
import RunnableModel.PublicInterface;
import sousuo.ClearEditText;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ProblemSumActivity extends AppCompatActivity implements PublicInterface{
    private RelativeLayout ZeRenRen;
    private RelativeLayout AllState;
    private RelativeLayout AllTime;
    private ListView mListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problemsum_layout);
        CommonMethod.setStatuColor(ProblemSumActivity.this,R.color.white);
        init();
    }
    private void init(){
        Button ProblemSum_back = (Button)findViewById(R.id.ProblemSum_back);
        ProblemSum_back.setOnClickListener(new ProblemSumActivityListener());
        ClearEditText sousuo = (ClearEditText)findViewById(R.id.ProblemSum_SouSuo);
        sousuo.addTextChangedListener(new EditTextListener());
        Button ProblemSum_Close = (Button)findViewById(R.id.ProblemSum_Close);
        ProblemSum_Close.setOnClickListener(new ProblemSumActivityListener());

        ZeRenRen =(RelativeLayout)findViewById(R.id.ZeRenRen);
        ZeRenRen.setOnClickListener(new ProblemSumActivityListener());

        AllState = (RelativeLayout)findViewById(R.id.AllState);
        AllState.setOnClickListener(new ProblemSumActivityListener());

        AllTime= (RelativeLayout)findViewById(R.id.AllTime);
        AllTime.setOnClickListener(new ProblemSumActivityListener());

        mListView = (ListView)findViewById(R.id.mListView);
        mListView.setOnItemClickListener(new ListViewListener());

        //new MainProblemSumActivity().getShopsData(this);
    }
    private  List<PublicBeen> list = new ArrayList<>();
    @Override
    public void onGetDataSuccess(List<PublicBeen> list) {
        this.list=list;
        Message msg =Message.obtain();
        msg.what=0;
        handler.sendMessage(msg);
    }

    @Override
    public void onGetDataError(String errmessage) {
        Message msg =Message.obtain();
        msg.what=1;
        msg.obj=errmessage;
        handler.sendMessage(msg);
    }

    @Override
    public void onEmptyData(String Emptymessage) {
        Message msg =Message.obtain();
        msg.what=2;
        msg.obj=Emptymessage;
        handler.sendMessage(msg);
    }

    private class ProblemSumActivityListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ProblemSum_back:
                                                finish();
                                                break;
                case R.id.ProblemSum_Close:break;
                case R.id.ZeRenRen:break;
                case R.id.AllState:    if(popupwindow!=null){
                                                    lp = getWindow().getAttributes();
                                                    lp.alpha =0.5f; //0.0-1.0
                                                    getWindow().setAttributes(lp);

                                                popupwindow.showAsDropDown(AllState,0,10);//显示
                                            }else{
                                                BD_TimepopWindow();
                                            }
                                        break;
                case R.id.AllTime:
                                    if(popupWindow!=null){
                                        lps = getWindow().getAttributes();
                                        lps.alpha =0.5f; //0.0-1.0
                                        getWindow().setAttributes(lps);

                                        popupWindow.showAsDropDown(AllTime,0,10);//显示
                                    }else{
                                        AllTimepopWindow();
                                    }
                                    break;
                //全部状态
                case R.id.ProblemSum_allState:cancelpopupwindow();break;
                case R.id.ProblemSum_NoComplete:cancelpopupwindow();break;
                case R.id.ProblemSum_Complete:cancelpopupwindow();break;
                //全部时间
                case R.id.ProblemSum_month:cancelpopupWindow();break;
                case R.id.ProblemSum_year:cancelpopupWindow();break;
            }
        }
    }
    private class EditTextListener implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    private class ListViewListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ProblemSumActivity.this,ProblemInformation.class);
            startActivity(intent);
        }
    }
    private PopupWindow popupwindow;
    WindowManager.LayoutParams lp;
    private void BD_TimepopWindow() {
        lp = getWindow().getAttributes();
        lp.alpha =0.5f; //0.0-1.0
        getWindow().setAttributes(lp);

        View addview = LayoutInflater.from(this).inflate(R.layout.problemsumactivityallstatepop_layout, null);

        TextView ProblemSum_allState= (TextView) addview.findViewById(R.id.ProblemSum_allState);
        ProblemSum_allState.setOnClickListener(new ProblemSumActivityListener());

        TextView ProblemSum_NoComplete= (TextView) addview.findViewById(R.id.ProblemSum_NoComplete);
        ProblemSum_NoComplete.setOnClickListener(new ProblemSumActivityListener());

        TextView ProblemSum_Complete= (TextView) addview.findViewById(R.id.ProblemSum_Complete);
        ProblemSum_Complete.setOnClickListener(new ProblemSumActivityListener());

        //BD_addinit(addview);
        popupwindow = new PopupWindow(addview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);//2,3参数为宽高
        popupwindow.setTouchable(true);//popupWindow可触摸
        popupwindow.setOutsideTouchable(true);//点击popupWindow以外区域消失
        popupwindow.setFocusable(true);
        popupwindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                Log.i("mengdd", "onTouch : ");
                return false;
            }
        });
        //设置popwindow消失的监听事件
        popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha =1; //0.0-1.0
                getWindow().setAttributes(lp);
            }
        });
        popupwindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置好参数之后再show
        popupwindow.showAsDropDown(AllState,0, 10);//在view(top)控件正下方，以view为参照点第二个参数为popwindow距离view的横向距离，
        //第三个参数为y轴即popwindow距离view的纵向距离
    }
    private void cancelpopupwindow(){
        if(popupwindow!=null){
            popupwindow.dismiss();
        }
    }
    //全部时间pop

    private PopupWindow popupWindow;
    WindowManager.LayoutParams lps;
    private void AllTimepopWindow() {
        lps = getWindow().getAttributes();
        lps.alpha =0.5f; //0.0-1.0
        getWindow().setAttributes(lp);

        View addview = LayoutInflater.from(this).inflate(R.layout.problemsumactivityalltimepop_layout, null);

        TextView ProblemSum_month= (TextView) addview.findViewById(R.id.ProblemSum_month);
        ProblemSum_month.setOnClickListener(new ProblemSumActivityListener());

        TextView ProblemSum_year= (TextView) addview.findViewById(R.id.ProblemSum_year);
        ProblemSum_year.setOnClickListener(new ProblemSumActivityListener());

        //BD_addinit(addview);
        popupWindow = new PopupWindow(addview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);//2,3参数为宽高
        popupWindow.setTouchable(true);//popupWindow可触摸
        popupWindow.setOutsideTouchable(true);//点击popupWindow以外区域消失
        popupWindow.setFocusable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                Log.i("mengdd", "onTouch : ");
                return false;
            }
        });
        //设置popwindow消失的监听事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lps.alpha =1; //0.0-1.0
                getWindow().setAttributes(lps);
            }
        });
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置好参数之后再show
        popupWindow.showAsDropDown(AllTime,0,10);//在view(top)控件正下方，以view为参照点第二个参数为popwindow距离view的横向距离，
        //第三个参数为y轴即popwindow距离view的纵向距离
    }
    private void cancelpopupWindow(){
        if(popupWindow!=null){
            popupWindow.dismiss();
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i =msg.what;
            if(i==0){
                //mListView.setAdapter(new ProblemSumActivityAdapter(ProblemSumActivity.this,list));
            }else{
                Toast.makeText(ProblemSumActivity.this,(String)msg.obj, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
