package Company;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myself.wypqwer.zhengqi_zhilian.CommonMethod;
import com.myself.wypqwer.zhengqi_zhilian.ComputerInformation;
import com.myself.wypqwer.zhengqi_zhilian.MyProgressDialog;
import com.myself.wypqwer.zhengqi_zhilian.R;

import RunnableModel.ComputerInformationRunnable;
import RunnableModel.ProblemSumInformation;
import RunnableModel.PublicOneListInterface;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CompanyComblemInformation extends AppCompatActivity implements PublicOneListInterface{
    private MyProgressDialog ProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companycombleminformation_layout);
        CommonMethod.setStatuColor(CompanyComblemInformation.this,R.color.white);
        init();
    }
    private void init(){
        String name = getIntent().getStringExtra("name");
        String id = getIntent().getStringExtra("id");
        //返回
        Button computer_Back = (Button)findViewById(R.id.CompanyProblemInformation_Back);
        computer_Back.setOnClickListener(new ComputerInformationListener());
        //负责人
        TextView CompanyProblemInformation_Person = (TextView)findViewById(R.id.CompanyProblemInformation_Person);
        CompanyProblemInformation_Person.setOnClickListener(new ComputerInformationListener());
        //提报类型
        TextView CompanyProblemInformation_Type= (TextView)findViewById(R.id.CompanyProblemInformation_Type);
        CompanyProblemInformation_Type.setOnClickListener(new ComputerInformationListener());
        //选择相片
        ImageView CompanyProblemInformation_photo = (ImageView)findViewById(R.id.CompanyProblemInformation_photo);
        CompanyProblemInformation_photo.setOnClickListener(new ComputerInformationListener());
        //确认提交
        ImageView CompanyProblemInformation_sureCommit = (ImageView)findViewById(R.id.CompanyProblemInformation_sureCommit);
        CompanyProblemInformation_sureCommit.setOnClickListener(new ComputerInformationListener());
        ProgressDialog = new MyProgressDialog(this,false,"加载中...");
        new ProblemSumInformation().getShopsData(this);;
    }

    @Override
    public void onGetDataSuccess(String succmessage) {
        cancelProgressDialog();
        Message msg =Message.obtain();
        msg.obj=succmessage;
        msg.what=0;
        handler.sendMessage(msg);
        Log.e("warn",succmessage);
    }

    @Override
    public void onGetDataError(String errmessage) {
        cancelProgressDialog();
        Message msg =Message.obtain();
        msg.obj=errmessage;
        msg.what=1;
        handler.sendMessage(msg);
        Log.e("warn",errmessage);
    }

    @Override
    public void onEmptyData(String Emptymessage) {
        cancelProgressDialog();
        Message msg =Message.obtain();
        msg.obj=Emptymessage;
        msg.what=2;
        handler.sendMessage(msg);
        Log.e("warn",Emptymessage);
    }

    private class ComputerInformationListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.CompanyProblemInformation_Back:finish();break;
                case R.id.CompanyProblemInformation_Type:break;
                case R.id.CompanyProblemInformation_Person:break;
                case R.id.CompanyProblemInformation_photo:break;
                case R.id.CompanyProblemInformation_sureCommit:break;
            }
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i= msg.what;
            if(i==0){
                String s0 = (String)msg.obj;
            }else if(i==1){
                String s1 = (String)msg.obj;
                Toast.makeText(CompanyComblemInformation.this,s1, Toast.LENGTH_SHORT).show();
            }else if(i==2){
                String s2 = (String)msg.obj;
                Toast.makeText(CompanyComblemInformation.this,s2, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void cancelProgressDialog(){
        if(ProgressDialog!=null){
            ProgressDialog.dismiss();
            ProgressDialog=null;
        }
    }
}
