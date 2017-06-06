package company;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import com.myself.wypqwer.zhengqi_zhilian.CommonMethod;
import com.myself.wypqwer.zhengqi_zhilian.MyProgressDialog;
import com.myself.wypqwer.zhengqi_zhilian.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProblemSumActivityAdapter;
import JavaBeen.PublicBeen;
import RunnableModel.AllStateRunnable;
import RunnableModel.Company_Pass_Manager_Check_Project;
import RunnableModel.ManagerJuRunnable;
import RunnableModel.PublicInterface;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CompanyComblem extends AppCompatActivity implements PublicInterface{
    private MyProgressDialog ProgressDialog;
    private TextView weiTuoJu;
    private MyProgressDialog state_progressDialog;
    private MyProgressDialog No_progressDialog;
    private MyProgressDialog Complete_progressDialog;
    private MyProgressDialog BoHui_progressDialog;
    private RelativeLayout state;
    private TextView stateChange;
    private MyProgressDialog manager_progressDialog;
    private ListView mListView;
    private List<PublicBeen> list2 = new ArrayList<>();
    private MyProgressDialog ProgressDialog3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companycomblem_layout);
        CommonMethod.setStatuColor(CompanyComblem.this,R.color.white);
        init();
    }
    private void init(){
        getLogin();
        Button ProblemSum_back = (Button)findViewById(R.id.CompanyComblem_Back);
        ProblemSum_back.setOnClickListener(new ProblemSumActivityListener());

        Button add = (Button)findViewById(R.id.CompanyComblem_add);
        add.setOnClickListener(new ProblemSumActivityListener());
        //管理局选择
        RelativeLayout CompanyComblem_Ju = (RelativeLayout)findViewById(R.id.CompanyComblem_Ju);
        CompanyComblem_Ju.setOnClickListener(new ProblemSumActivityListener());
        //状态选择
        state  =(RelativeLayout)findViewById(R.id.CompanyComblem_Allstate);
        state.setOnClickListener(new ProblemSumActivityListener());
        stateChange = (TextView)findViewById(R.id.stateChange);

        mListView = (ListView)findViewById(R.id.CompanyComblem_mListView);
        mListView.setOnItemClickListener(new ListViewListener());
        //委托局
        weiTuoJu = (TextView)findViewById(R.id.weiTuoJu);
        ProgressDialog3 = new MyProgressDialog(this,false,"");
        new Company_Pass_Manager_Check_Project("",project_id).getShopsData(CompanyComblem.this);
    }
    private  List<PublicBeen> list;

    @Override
    public void onGetDataSuccess(List<PublicBeen> list) {
        this.list=list;
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
                case R.id.CompanyComblem_Back:
                                                finish();
                                                break;
                case R.id.CompanyComblem_add:
                                                    Intent intent = new Intent(CompanyComblem.this,ProblemInformation.class);
                                                    intent.putExtra("information",getIntent().getSerializableExtra("information"));
                                                    startActivity(intent);
                                                    break;
                case R.id.CompanyComblem_Allstate:          if(popupwindow!=null){
                                                                                lp.alpha =0.5f; //0.0-1.0
                                                                                getWindow().setAttributes(lp);
                                                                                popupwindow.showAsDropDown(state,0,10);
                                                                            }else{
                                                                                    AllStateWindow();
                                                                                }

                                                                         break;
                case R.id.CompanyComblem_Ju:
                                                    //责任人
                                                    /*ProgressDialog = new MyProgressDialog(CompanyComblem.this,false,"");
                                                    new ManagerJuRunnable().getShopsData(CompanyComblem.this);*/
                                                    if(popupwindow1!=null){
                                                        RelativeLayout CompanyComblem_Ju = (RelativeLayout)findViewById(R.id.CompanyComblem_Ju);
                                                        lps1.alpha =0.5f; //0.0-1.0
                                                        getWindow().setAttributes(lps1);
                                                        popupwindow1.showAsDropDown(CompanyComblem_Ju,0, 10);
                                                    }else{
                                                        setManagerMethod();
                                                    }

                                                    break;
                case R.id.ProblemSum_bohui:
                                                    //全部状态中 已驳回
                                                    cancelpopupwindow();
                                                    stateChange.setText(ProblemSum_bohui.getText().toString());
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
                                                    stateChange.setText(ProblemSum_allState.getText().toString());
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
                                                    stateChange.setText(ProblemSum_NoComplete.getText().toString());
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
                                                stateChange.setText(ProblemSum_Complete.getText().toString());

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
            }
        }
    }

    private class ListViewListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(CompanyComblem.this,CompanyComblemInformation.class);
                        intent.putExtra("xinxi",list.get(position));
                        startActivity(intent);
        }
    }
    //管理局
    String Manager_arr_name [] =null;
    String Manager_arr_id [] =null;
    int Manager_ID = -1;
    private List<PublicBeen> list3 = new ArrayList<>();
    private boolean isequal=false;
    private void setManagerMethod(){

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
        }
        for(int i=0;i<list3.size();i++){
            Manager_arr_name[i] = list3.get(i).getPerson_Name();
            Manager_arr_id[i] = list3.get(i).getPerson_ID();
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
    //管理局pop
    private PopupWindow popupwindow1;
    WindowManager.LayoutParams lps1;

    private void ManagerJupopWindow() {
        RelativeLayout CompanyComblem_Ju = (RelativeLayout)findViewById(R.id.CompanyComblem_Ju);
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
        popupwindow1 = new PopupWindow(addview,width/2,ViewGroup.LayoutParams.WRAP_CONTENT, true);//2,3参数为宽高
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
        popupwindow1.showAsDropDown(CompanyComblem_Ju,0, 10);//在view(top)控件正下方，以view为参照点第二个参数为popwindow距离view的横向距离，
        //第三个参数为y轴即popwindow距离view的纵向距离
    }

    private void popupwindow1(){
        if(popupwindow1!=null){
            popupwindow1.dismiss();
        }
    }
    private class ManagerJuListviewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Manager_ID = position;
            weiTuoJu.setText(Manager_arr_name[position]);
            popupwindow1();
            list.clear();
            for(int i=0;i<list2.size();i++){
                if(list2.get(i).getPerson_Name().equals(Manager_arr_name[position])){
                    list.add(list2.get(i));
                }
            }
            psa.notifyDataSetChanged();
        }
    }
    private ProblemSumActivityAdapter psa;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            if (i == 0) {

                            if(ProgressDialog3!=null){
                                for(int j=0;j<list.size();j++){
                                    list2.add(list.get(j));
                                }
                                psa= new ProblemSumActivityAdapter(CompanyComblem.this,list);
                                mListView.setAdapter(psa);
                            }
                            if(manager_progressDialog!=null){
                                /*psa= new ProblemSumActivityAdapter(CompanyComblem.this,list);
                                mListView.setAdapter(psa);*/
                            }
            } else {
                Toast.makeText(CompanyComblem.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            }
            cancelProgressDialog();
            cancelmanager_progressDialog();
            cancelProgressDialog3();
        }
    };

    //全部状态
    private PopupWindow popupwindow;
    WindowManager.LayoutParams lp;
    private TextView ProblemSum_NoComplete;
    private TextView ProblemSum_allState;
    private TextView ProblemSum_Complete;
    private TextView ProblemSum_bohui;
    private void AllStateWindow() {
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
        popupwindow.showAsDropDown(state,0, 10);//在view(top)控件正下方，以view为参照点第二个参数为popwindow距离view的横向距离，
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
    private void cancelBoHui_progressDialog(){
        if(BoHui_progressDialog!=null){
            BoHui_progressDialog.dismiss();
            BoHui_progressDialog=null;
        }
    }
    private void cancelmanager_progressDialog(){
        if(manager_progressDialog!=null){
            manager_progressDialog.dismiss();
            manager_progressDialog=null;
        }
    }
    private void cancelProgressDialog3(){
        if(ProgressDialog3!=null){
            ProgressDialog3.dismiss();
            ProgressDialog3=null;
        }
    }
    private String project_id;
    private void getLogin(){
        PublicBeen pb =(PublicBeen)getIntent().getSerializableExtra("information");

                    project_id = pb.getLogin_proID();
                    Log.e("warn",project_id);

    }
}
