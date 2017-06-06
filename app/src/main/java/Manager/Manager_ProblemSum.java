package manager;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.myself.wypqwer.zhengqi_zhilian.CommonMethod;
import com.myself.wypqwer.zhengqi_zhilian.MyProgressDialog;
import com.myself.wypqwer.zhengqi_zhilian.ProblemInformation;
import com.myself.wypqwer.zhengqi_zhilian.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.Manager_ProblemSumAdapter;
import JavaBeen.PublicBeen;
import RunnableModel.Manager_ProblemSumRunnable;
import RunnableModel.PublicInterface;
import sousuo.CharacterParser;
import sousuo.ClearEditText;

/**
 * Created by Administrator on 2017/5/19.
 */

public class Manager_ProblemSum extends AppCompatActivity implements PublicInterface {
    private TextView allState;
    private MyProgressDialog ProgressDialog;
    private  ListView mListView;
    private RelativeLayout Manager_AllTime;
    private TextView alltime;
    private MyProgressDialog ProgressDialog1;
    private MyProgressDialog ProgressDialog2;
    private MyProgressDialog ProgressDialog3;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_problemsum_layout);
        CommonMethod.setStatuColor(Manager_ProblemSum.this,R.color.white);
        init();
    }
    private void init(){
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        Button ProblemSum_back = (Button)findViewById(R.id.Manager_ProblemSum_back);
        ProblemSum_back.setOnClickListener(new ProblemSumActivityListener());
        ClearEditText sousuo = (ClearEditText)findViewById(R.id.Manager_ProblemSum_SouSuo);
        sousuo.addTextChangedListener(new EditTextListener());

        RelativeLayout AllState = (RelativeLayout)findViewById(R.id.Manager_AllState);
        AllState.setOnClickListener(new ProblemSumActivityListener());
        allState = (TextView)findViewById(R.id.allState);

        Manager_AllTime = (RelativeLayout)findViewById(R.id.Manager_AllTime);
        Manager_AllTime.setOnClickListener(new ProblemSumActivityListener());
        alltime = (TextView)findViewById(R.id.alltime);

        mListView = (ListView)findViewById(R.id.Manager_mListView);
        mListView.setOnItemClickListener(new ListViewListener());


        PublicBeen pb = (PublicBeen)getIntent().getSerializableExtra("information");
        ProgressDialog = new MyProgressDialog(Manager_ProblemSum.this,false,"");
        new Manager_ProblemSumRunnable(pb.getManager_id(),"").getShopsData(this);
    }
    private  List<PublicBeen> list;
    private  List<PublicBeen> list2=new ArrayList<>();
    @Override
    public void onGetDataSuccess(List<PublicBeen> list) {
        if(this.list!=null){
            this.list=null;
        }
        this.list=list;
        if(list2.size()>0) {
            list2.clear();
        }
        Log.e("warn",list.size()+"list");
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
                case R.id.Manager_ProblemSum_back:
                                                        finish();
                                                        break;
                case R.id.Manager_AllState:
                                                if(popupwindow!=null){
                                                    lp.alpha =0.5f; //0.0-1.0
                                                    getWindow().setAttributes(lp);
                                                    popupwindow.showAsDropDown(AllState,0,10);
                                                }else{
                                                    AllStateWindow();
                                                }
                                                break;
                case R.id.Manager_AllTime:
                                                if(popupWindow!=null){
                                                    lps.alpha =0.5f; //0.0-1.0
                                                    getWindow().setAttributes(lps);
                                                    popupWindow.showAsDropDown(Manager_AllTime,0,10);
                                                }else{
                                                    AllTimepopWindow();
                                                }
                                                break;
                case R.id.ProblemSum_bohui:
                                                //全部状态中 已驳回
                                                cancelpopupwindow();
                                                allState.setText(ProblemSum_bohui.getText().toString());
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
                                                }else{
                                                                                     /*BoHui_progressDialog = new MyProgressDialog(CompanyComblem.this,false,"");
                                                                                     new Company_Pass_Manager_Check_Project(Manager_arr_id[Manager_ID], project_id ).getShopsData(CompanyComblem.this);*/
                                                }
                                                break;
                case R.id.ProblemSum_allState:
                                                //全部状态中 全部状态
                                                cancelpopupwindow();
                                                allState .setText(ProblemSum_allState.getText().toString());
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
                                                }else{
                                                                                   /* state_progressDialog = new MyProgressDialog(CompanyComblem.this,false,"");
                                                                                    new AllStateRunnable().getShopsData(CompanyComblem.this);*/
                                                }
                                                break;
                case R.id.ProblemSum_NoComplete:
                                                //全部状态中 未完成
                                                cancelpopupwindow();
                                                allState .setText(ProblemSum_NoComplete.getText().toString());
                                                if(list!=null){
                                                    list.clear();
                                                    for(int i=0;i<list2.size();i++){
                                                        if(list2.get(i).getProject_state().equals("0")){
                                                            list.add(list2.get(i));
                                                        }
                                                    }
                                                    psa.notifyDataSetChanged();
                                                }else{
                                                                                    /*No_progressDialog = new MyProgressDialog(CompanyComblem.this,false,"");
                                                                                    new AllStateRunnable().getShopsData(CompanyComblem.this);*/
                                                }
                                                break;
                case R.id.ProblemSum_Complete:
                                                //全部状态中 已完成
                                                cancelpopupwindow();
                                                allState .setText(ProblemSum_Complete.getText().toString());

                                                if(list!=null){
                                                    list.clear();
                                                    for(int i=0;i<list2.size();i++){
                                                        if(list2.get(i).getProject_state().equals("1")){
                                                            list.add(list2.get(i));
                                                        }
                                                    }
                                                    psa.notifyDataSetChanged();
                                                }else{
                                                                               /* Complete_progressDialog = new MyProgressDialog(CompanyComblem.this,false,"");
                                                                                new AllStateRunnable().getShopsData(CompanyComblem.this);*/
                                                }
                                                break;
                case R.id.ProblemSum_month:
                                            cancelpopupWindow();
                                            alltime.setText(ProblemSum_month.getText().toString());
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
                                            }else{
                                                                                                       /* Complete_progressDialog = new MyProgressDialog(CompanyComblem.this,false,"");
                                                                                                        new AllStateRunnable().getShopsData(CompanyComblem.this);*/
                                            }

                                           /* PublicBeen pb = (PublicBeen)getIntent().getSerializableExtra("information");
                                            ProgressDialog1 = new MyProgressDialog(Manager_ProblemSum.this,false,"");
                                            new Manager_ProblemSumRunnable(pb.getManager_id(),"").getShopsData(Manager_ProblemSum.this);*/
                                            break;
                case R.id.ProblemSum_year:
                                            cancelpopupWindow();
                                            alltime.setText(ProblemSum_year.getText().toString());
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
                                                }else{
                                                                                                                                   /* Complete_progressDialog = new MyProgressDialog(CompanyComblem.this,false,"");
                                                                                                                                    new AllStateRunnable().getShopsData(CompanyComblem.this);*/
                                                }
                                            /*PublicBeen pb1 = (PublicBeen)getIntent().getSerializableExtra("information");
                                            ProgressDialog2 = new MyProgressDialog(Manager_ProblemSum.this,false,"");
                                            new Manager_ProblemSumRunnable(pb1.getManager_id(),"").getShopsData(Manager_ProblemSum.this);*/
                                            break;
                case R.id.ProblemSum_all:

                                            cancelpopupWindow();
                                            alltime.setText(ProblemSum_all.getText().toString());

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
                                            }else{
                                                                                                           /* state_progressDialog = new MyProgressDialog(CompanyComblem.this,false,"");
                                                                                                            new AllStateRunnable().getShopsData(CompanyComblem.this);*/
                                            }
                                            /*PublicBeen pb2 = (PublicBeen)getIntent().getSerializableExtra("information");
                                            ProgressDialog3 = new MyProgressDialog(Manager_ProblemSum.this,false,"");
                                            new Manager_ProblemSumRunnable(pb2.getManager_id(),"").getShopsData(Manager_ProblemSum.this);*/
                                            break;
            }
        }
    }
    private class EditTextListener implements TextWatcher {

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
            Intent intent = new Intent(Manager_ProblemSum.this, ProblemInformation.class);
            intent.putExtra("xinxi",list.get(position));
            startActivityForResult(intent,0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("warn","sadasd");
        if(resultCode==1) {
            PublicBeen pb = (PublicBeen) getIntent().getSerializableExtra("information");
            ProgressDialog = new MyProgressDialog(Manager_ProblemSum.this, false, "");
            new Manager_ProblemSumRunnable(pb.getManager_id(), "").getShopsData(this);
        }
    }

    //全部状态
    private PopupWindow popupwindow;
    WindowManager.LayoutParams lp;
    private TextView ProblemSum_NoComplete;
    private TextView ProblemSum_allState;
    private TextView ProblemSum_Complete;
    private TextView ProblemSum_bohui;
    RelativeLayout AllState;
    private void AllStateWindow() {
        AllState = (RelativeLayout)findViewById(R.id.Manager_AllState);
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

        ProblemSum_bohui= (TextView) addview.findViewById(R.id.ProblemSum_bohui);
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
    Manager_ProblemSumAdapter psa;
   /* private List<PublicBeen> list_month = new ArrayList<>();
    private List<PublicBeen> list_year = new ArrayList<>();*/
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            if (i == 0) {

                if(ProgressDialog!=null){
                    for(int j=0;j<list.size();j++){
                        list2.add(list.get(j));
                        filledData(list.get(j));
                    }
                    psa= new Manager_ProblemSumAdapter(Manager_ProblemSum.this,list);
                    mListView.setAdapter(psa);
                }

                /*if(ProgressDialog1!=null){
                    for(int j=0;j<list.size();j++){
                        list_month.add(list.get(j));
                    }
                    list.clear();
                    long newTime=System.currentTimeMillis()/1000;
                    for(int k=0;k<list_month.size();k++){
                        long oldTime = Integer.parseInt(list_month.get(k).getProject_TIME());
                        if(newTime-oldTime<=30*24*60*60){
                            Log.e("warn",newTime-oldTime-30*24*60*60+"");
                            Log.e("warn",list_month.size()+"");
                            list.add(list_month.get(k));
                        }
                    }
                    psa.notifyDataSetChanged();
                }
                if(ProgressDialog2!=null){
                    for(int j=0;j<list.size();j++){
                        list_month.add(list.get(j));
                    }
                    list.clear();
                    long newTime=System.currentTimeMillis()/1000;
                    for(int k=0;k<list_month.size();k++){
                        long oldTime = Integer.parseInt(list_month.get(k).getProject_TIME());
                        if(newTime-oldTime<=365*24*60*60){
                            list.add(list_month.get(k));
                        }
                    }
                    psa.notifyDataSetChanged();
                }
                if(ProgressDialog3!=null){
                    for(int j=0;j<list.size();j++){
                        list_month.add(list.get(j));
                    }
                    psa.notifyDataSetChanged();
                }*/
            } else {
                Toast.makeText(Manager_ProblemSum.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            }
            cancelProgressDialog();
            cancelProgressDialog1();
            cancelProgressDialog2();
            cancelProgressDialog3();
        }
    };
    private void cancelProgressDialog(){
        if(ProgressDialog!=null){
            ProgressDialog.dismiss();
            ProgressDialog=null;
        }
    }
    private void cancelProgressDialog1(){
        if(ProgressDialog1!=null){
            ProgressDialog1.dismiss();
            ProgressDialog1=null;
        }
    }
    private void cancelProgressDialog2(){
        if(ProgressDialog2!=null){
            ProgressDialog2.dismiss();
            ProgressDialog2=null;
        }
    }
    private void cancelProgressDialog3(){
        if(ProgressDialog3!=null){
            ProgressDialog3.dismiss();
            ProgressDialog3=null;
        }
    }
    //全部时间
    //全部时间pop

    private PopupWindow popupWindow;
    WindowManager.LayoutParams lps;
    private TextView ProblemSum_month;
    private TextView ProblemSum_year;
    private TextView ProblemSum_all;
    private void AllTimepopWindow() {
        Manager_AllTime = (RelativeLayout)findViewById(R.id.Manager_AllTime);
        lps = getWindow().getAttributes();
        lps.alpha =0.5f; //0.0-1.0
        getWindow().setAttributes(lps);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        View addview = LayoutInflater.from(this).inflate(R.layout.problemsumactivityalltimepop_layout, null);

        ProblemSum_month= (TextView) addview.findViewById(R.id.ProblemSum_month);
        ProblemSum_month.setOnClickListener(new ProblemSumActivityListener());

        ProblemSum_year= (TextView) addview.findViewById(R.id.ProblemSum_year);
        ProblemSum_year.setOnClickListener(new ProblemSumActivityListener());

         ProblemSum_all= (TextView) addview.findViewById(R.id.ProblemSum_all);
        ProblemSum_all.setOnClickListener(new ProblemSumActivityListener());
        //BD_addinit(addview);
        popupWindow = new PopupWindow(addview, width/2, ViewGroup.LayoutParams.WRAP_CONTENT, true);//2,3参数为宽高
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
        popupWindow.showAsDropDown(Manager_AllTime,0,10);//在view(top)控件正下方，以view为参照点第二个参数为popwindow距离view的横向距离，
        //第三个参数为y轴即popwindow距离view的纵向距离
    }
    private void cancelpopupWindow(){
        if(popupWindow!=null){
            popupWindow.dismiss();
        }
    }
}
