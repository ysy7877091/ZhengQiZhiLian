package com.myself.wypqwer.zhengqi_zhilian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import RunnableModel.Leader_ProblemSumActivityRunnable;
import RunnableModel.PublicInterface;
import sousuo.CharacterParser;
import sousuo.ClearEditText;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ProblemSumActivity extends AppCompatActivity implements PublicInterface{
    private RelativeLayout ZeRenRen;
    private RelativeLayout AllState;
    private RelativeLayout AllTime;
    private ListView mListView;
    private MyProgressDialog ProgressDialog;
    private TextView zerenren;
    private MyProgressDialog progressDialog;
    private MyProgressDialog state_progressDialog;
    private MyProgressDialog No_progressDialog;
    private MyProgressDialog Complete_progressDialog;
    private TextView Leader_allState;
    private TextView Leader_alltime;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problemsum_layout);
        CommonMethod.setStatuColor(ProblemSumActivity.this,R.color.white);
        init();
    }
    private void init(){
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        Button ProblemSum_back = (Button)findViewById(R.id.ProblemSum_back);
        ProblemSum_back.setOnClickListener(new ProblemSumActivityListener());

        ClearEditText sousuo = (ClearEditText)findViewById(R.id.ProblemSum_SouSuo);
        sousuo.addTextChangedListener(new EditTextListener());

        Button ProblemSum_Close = (Button)findViewById(R.id.ProblemSum_Close);
        ProblemSum_Close.setOnClickListener(new ProblemSumActivityListener());

        ZeRenRen =(RelativeLayout)findViewById(R.id.ZeRenRen);
        ZeRenRen.setOnClickListener(new ProblemSumActivityListener());
        zerenren= (TextView)findViewById(R.id.zerenren);

        AllState = (RelativeLayout)findViewById(R.id.AllState);
        AllState.setOnClickListener(new ProblemSumActivityListener());
        Leader_allState = (TextView)findViewById(R.id.Leader_allState);

        AllTime= (RelativeLayout)findViewById(R.id.AllTime);
        AllTime.setOnClickListener(new ProblemSumActivityListener());
        Leader_alltime =(TextView) findViewById(R.id.Leader_alltime);

        mListView = (ListView)findViewById(R.id.mListView);
        mListView.setOnItemClickListener(new ListViewListener());

        //new MainProblemSumActivity().getShopsData(this);
        ProgressDialog = new MyProgressDialog(this,false,"");
        new Leader_ProblemSumActivityRunnable("","").getShopsData(ProblemSumActivity.this);
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
                case R.id.ZeRenRen:
                                        if(popupwindow1!=null){
                                            lps1.alpha =0.5f; //0.0-1.0
                                            getWindow().setAttributes(lps1);
                                            popupwindow1.showAsDropDown(ZeRenRen,0, 10);
                                        }else{
                                            setManagerMethod();
                                        }
                                        break;
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
                case R.id.ProblemSum_allState:
                                                cancelpopupwindow();

                                                Leader_allState.setText(ProblemSum_allState.getText().toString());
                                                if(list!=null){
                                                    if(list.size()==list2.size()){
                                                        psa.notifyDataSetChanged();
                                                    }else {
                                                        list.clear();
                                                        Log.e("warn",list2.size()+"quanbu");
                                                        for (int i = 0; i < list2.size(); i++) {
                                                            list.add(list2.get(i));
                                                        }
                                                        psa.notifyDataSetChanged();
                                                    }
                                                }
                                                /*state_progressDialog = new MyProgressDialog(ProblemSumActivity.this,false,"");
                                                new AllStateRunnable().getShopsData(ProblemSumActivity.this);*/
                                                break;
                case R.id.ProblemSum_NoComplete:
                                                cancelpopupwindow();
                                                Leader_allState.setText(ProblemSum_NoComplete.getText().toString());
                                                if(list!=null){
                                                    list.clear();
                                                    for(int i=0;i<list2.size();i++){
                                                        if(list2.get(i).getProject_state().equals("0")){
                                                            list.add(list2.get(i));
                                                        }
                                                    }
                                                    psa.notifyDataSetChanged();
                                                }
                                                /*No_progressDialog = new MyProgressDialog(ProblemSumActivity.this,false,"");
                                                new AllStateRunnable().getShopsData(ProblemSumActivity.this);*/
                                                break;
                case R.id.ProblemSum_Complete:
                                                //全部状态中 已完成
                                                cancelpopupwindow();
                                                Leader_allState.setText(ProblemSum_Complete.getText().toString());

                                                if(list!=null){
                                                    list.clear();
                                                    for(int i=0;i<list2.size();i++){
                                                        if(list2.get(i).getProject_state().equals("1")){
                                                            list.add(list2.get(i));
                                                        }
                                                    }
                                                    psa.notifyDataSetChanged();
                                                }
                                                //cancelComplete_progressDialog();
                                                /*Complete_progressDialog = new MyProgressDialog(ProblemSumActivity.this,false,"");
                                                new AllStateRunnable().getShopsData(ProblemSumActivity.this);*/
                                                break;
                case R.id.ProblemSum_bohui:
                                                cancelpopupwindow();
                                                Leader_allState.setText(ProblemSum_bohui.getText().toString());
                                                if(list!=null){
                                                    list.clear();
                                                    Log.e("warn",list2.size()+"bohui");
                                                    Log.e("warn",list.size()+"bohui");
                                                    for(int i=0;i<list2.size();i++){
                                                        if(list2.get(i).getProject_state().equals("2")){
                                                            list.add(list2.get(i));
                                                        }
                                                    }
                                                    psa.notifyDataSetChanged();
                                                }


                                                break;
                //全部时间
                case R.id.Leader_ProblemSum_month:


                                                cancelpopupWindow();
                                                Leader_alltime.setText(Leader_ProblemSum_month.getText().toString());
                                                long newTime=System.currentTimeMillis()/1000;
                                                if(list!=null){
                                                    list.clear();
                                                    for(int i=0;i<list2.size();i++){
                                                        long oldTime = Integer.parseInt(list2.get(i).getProject_TIME());
                                                        if(newTime-oldTime<=30*24*60*60) {
                                                            Log.e("warn", newTime - oldTime - 30 * 24 * 60 * 60 + "");
                                                            Log.e("warn", list2.size() + "");
                                                            list.add(list2.get(i));
                                                        }
                                                    }
                                                    psa.notifyDataSetChanged();
                                                }

                                                break;
                case R.id.Leader_ProblemSum_year:

                                                cancelpopupWindow();
                                                Leader_alltime.setText(Leader_ProblemSum_year.getText().toString());
                                                long newTime1=System.currentTimeMillis()/1000;
                                                if(list!=null){
                                                    list.clear();
                                                    for(int i=0;i<list2.size();i++){
                                                        long oldTime = Integer.parseInt(list2.get(i).getProject_TIME());
                                                        if(newTime1-oldTime<=365*24*60*60) {
                                                            Log.e("warn", newTime1 - oldTime - 365 * 24 * 60 * 60 + "");
                                                            Log.e("warn", list2.size() + "");
                                                            list.add(list2.get(i));
                                                        }
                                                    }
                                                    psa.notifyDataSetChanged();
                                                }
                                               break;
                case R.id.Leader_ProblemSum_all:

                                            cancelpopupWindow();
                                            Leader_alltime.setText(Leader_ProblemSum_all.getText().toString());

                                            if(list!=null){
                                                if(list.size()==list2.size()){
                                                    psa.notifyDataSetChanged();
                                                }else {
                                                    list.clear();
                                                    Log.e("warn",list2.size()+"quanbu");
                                                    for (int i = 0; i < list2.size(); i++) {
                                                        list.add(list2.get(i));
                                                    }
                                                    psa.notifyDataSetChanged();
                                                }
                                            }
                                            break;
            }
        }
    }
    private class EditTextListener implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            filterData(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    //搜索
    private void filledData( PublicBeen sortModel){
        //汉字转换成拼音
        String pinyin = characterParser.getSelling(sortModel.getCompanyName());

        String sortString = pinyin.substring(0, 1).toUpperCase();

        // 正则表达式，判断首字母是否是英文字母
        if(sortString.matches("[A-Z]")){
            sortModel.setSortLetters(sortString.toUpperCase());
        }else{
            sortModel.setSortLetters("#");
        }

    }
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr){
        List<PublicBeen> filterDateList = new ArrayList<>();

        if(TextUtils.isEmpty(filterStr)){
            filterDateList =list;
        }else{
            filterDateList.clear();
            for(PublicBeen sortModel : list){
                String name = sortModel.getCompanyName();  //输入的内容转为大写
                if(name.indexOf(filterStr.toString().toUpperCase()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
                    filterDateList.add(sortModel);
                }
            }
        }

        // // 根据a-z进行排序��
        //Collections.sort(filterDateList, pinyinComparator);
        psa.updateListView(filterDateList);
    }
    private class ListViewListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ProblemSumActivity.this,ProblemInformation.class);
            intent.putExtra("xinxi",list.get(position));
            intent.putExtra("leader","0");
            startActivity(intent);
        }
    }
    //全部状态
    private PopupWindow popupwindow;
    WindowManager.LayoutParams lp;
    private TextView ProblemSum_allState;
    private TextView ProblemSum_NoComplete;
    private TextView ProblemSum_Complete;
    private TextView ProblemSum_bohui;
    private void BD_TimepopWindow() {
        lp = getWindow().getAttributes();
        lp.alpha =0.5f; //0.0-1.0
        getWindow().setAttributes(lp);

        View addview = LayoutInflater.from(this).inflate(R.layout.problemsumactivityallstatepop_layout, null);

        ProblemSum_allState= (TextView) addview.findViewById(R.id.ProblemSum_allState);
        ProblemSum_allState.setOnClickListener(new ProblemSumActivityListener());

        ProblemSum_NoComplete= (TextView) addview.findViewById(R.id.ProblemSum_NoComplete);
        ProblemSum_NoComplete.setOnClickListener(new ProblemSumActivityListener());

        ProblemSum_Complete= (TextView) addview.findViewById(R.id.ProblemSum_Complete);
        ProblemSum_Complete.setOnClickListener(new ProblemSumActivityListener());

        ProblemSum_bohui=(TextView)addview.findViewById(R.id.ProblemSum_bohui);
        ProblemSum_bohui.setOnClickListener(new ProblemSumActivityListener());
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
    private void cancelstate_progressDialog(){
        if(state_progressDialog!=null){
            state_progressDialog.dismiss();
            state_progressDialog=null;
        }
    }
    private void cancelno_progressDialog(){
        if(No_progressDialog!=null){
            No_progressDialog.dismiss();
            No_progressDialog=null;
        }
    }
    private void cancelComplete_progressDialog(){
        if(Complete_progressDialog!=null){
            Complete_progressDialog.dismiss();
            Complete_progressDialog=null;
        }
    }
    //全部时间pop

    private PopupWindow popupWindow;
    WindowManager.LayoutParams lps;
    private TextView Leader_ProblemSum_month;
    private TextView Leader_ProblemSum_year;
    private TextView Leader_ProblemSum_all;
    private void AllTimepopWindow() {
        lps = getWindow().getAttributes();
        lps.alpha =0.5f; //0.0-1.0
        getWindow().setAttributes(lps);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        View addview = LayoutInflater.from(this).inflate(R.layout.leadertime_layout, null);

        Leader_ProblemSum_month= (TextView) addview.findViewById(R.id.Leader_ProblemSum_month);
        Leader_ProblemSum_month.setOnClickListener(new ProblemSumActivityListener());

        Leader_ProblemSum_year= (TextView) addview.findViewById(R.id.Leader_ProblemSum_year);
        Leader_ProblemSum_year.setOnClickListener(new ProblemSumActivityListener());

        Leader_ProblemSum_all= (TextView)addview.findViewById(R.id.Leader_ProblemSum_all);
        Leader_ProblemSum_all.setOnClickListener(new ProblemSumActivityListener());
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
    //责任人pop
    private PopupWindow popupwindow1;
    WindowManager.LayoutParams lps1;

    private void ManagerJupopWindow() {
        lps1 = getWindow().getAttributes();
        lps1.alpha =0.5f; //0.0-1.0
        getWindow().setAttributes(lps1);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        View addview = LayoutInflater.from(this).inflate(R.layout.problemsumactivitymanagerjupop_layout, null);

        ListView MlistView = (ListView)addview.findViewById(R.id.MListView);
        MlistView.setOnItemClickListener(new ManagerJuListviewListener());
        MlistView.setAdapter(new ArrayAdapter<String>(this,R.layout.problemsumactivityarrayadapter_layout,Manager_arr_name));
        //BD_addinit(addview);
        popupwindow1 = new PopupWindow(addview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);//2,3参数为宽高
        popupwindow1.setTouchable(true);//popupWindow可触摸
        popupwindow1.setOutsideTouchable(true);//点击popupWindow以外区域消失
        popupwindow1.setFocusable(true);
        popupwindow1.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                Log.i("mengdd", "onTouch : ");
                return false;
            }
        });
        //设置popwindow消失的监听事件
        popupwindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lps1.alpha =1; //0.0-1.0
                getWindow().setAttributes(lps1);
            }
        });
        popupwindow1.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置好参数之后再show
        popupwindow1.showAsDropDown(ZeRenRen,0, 10);//在view(top)控件正下方，以view为参照点第二个参数为popwindow距离view的横向距离，
        //第三个参数为y轴即popwindow距离view的纵向距离
    }

    private void popupwindow1(){
        if(popupwindow1!=null){
            popupwindow1.dismiss();
        }
    }

    private List<PublicBeen> list2 = new ArrayList<>();//保存
    private ProblemSumActivityAdapter psa;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i =msg.what;
            if(i==0){
                //mListView.setAdapter(new ProblemSumActivityAdapter(ProblemSumActivity.this,list));
                if(ProgressDialog!=null){
                    for(int j=0;j<list.size();j++){
                        list2.add(list.get(j));
                        filledData(list.get(j));
                    }
                    psa= new ProblemSumActivityAdapter(ProblemSumActivity.this,list);
                    mListView.setAdapter(psa);
                }
                if(progressDialog!=null){

                }
                if(state_progressDialog!=null){

                }
                if(No_progressDialog!=null){

                }
                if(Complete_progressDialog!=null){

                }
            }else{
                Toast.makeText(ProblemSumActivity.this,(String)msg.obj, Toast.LENGTH_SHORT).show();
            }
            cancelProgressDialog();
            cancelprogressDialog();
            cancelstate_progressDialog();
            cancelComplete_progressDialog();
            cancelno_progressDialog();
        }
    };
    //管理局
    String Manager_arr_name [] =null;
    String Manager_arr_id [] =null;
    int Manager_ID = -1;
    private boolean isequal=false;
    private List<PublicBeen> list3 = new ArrayList<>();
    private void setManagerMethod(){
        Log.e("warn",list.size()+":长度");
        for(int i=0;i<list.size();i++){
            if(i>0){
                for(int j=0;j<i;j++){
                    if(list.get(i).getPerson_Name().equals(list.get(j).getPerson_Name())){
                        isequal = true;
                    }
                }
            }
            if(isequal==false){
                list3.add(list.get(i));
            }
            isequal=false;
        }
        if(Manager_arr_name==null){
            Manager_arr_name = new String[list3.size()];
            Manager_arr_id = new String[list3.size()];
            for(int i=0;i<list3.size();i++){
                Manager_arr_name[i] = list3.get(i).getPerson_Name();
                Manager_arr_id[i] = list3.get(i).getPerson_ID();
            }
        }
        ManagerJupopWindow();
    }
    //管理局加载ProgressDialog
    private void cancelProgressDialog(){
        if(ProgressDialog!=null){
            ProgressDialog.dismiss();
            ProgressDialog=null;
        }
    }
    private class ManagerJuListviewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Manager_ID = position;
                                        zerenren.setText(Manager_arr_name[position]);
                                        popupwindow1();
                                        /*progressDialog = new MyProgressDialog(ProblemSumActivity.this,false,"");
                                        new ProblemSumManagerJuRunnable(Manager_arr_id[Manager_ID]).getShopsData(ProblemSumActivity.this);*/
                                        list.clear();
                                        for(int i=0;i<list2.size();i++){
                                            if(list2.get(i).getPerson_Name().equals(Manager_arr_name[position])){
                                                list.add(list2.get(i));
                                            }
                                        }
                                        psa.notifyDataSetChanged();
        }
    }
    //根据管理局查看项目
    private void cancelprogressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }

}
