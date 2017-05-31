package Manager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.myself.wypqwer.zhengqi_zhilian.R;

/**
 * Created by Administrator on 2017/5/22.
 */

public class XiuGaiPwd extends AppCompatActivity {
    private  EditText ED_Username;
    private  EditText ED_Paqssword;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.xiugaipwd_layout);
        init();
    }
    private void init(){
        Button ManagerPWD_Back = (Button)findViewById(R.id.ManagerPWD_Back);
        ManagerPWD_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView SureCommit = (ImageView)findViewById(R.id.SureCommit);
        SureCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ED_Username = (EditText)findViewById(R.id.ED_Username);
        ED_Username.setOnFocusChangeListener(new ED_FocusChangeListener());
        ED_Paqssword = (EditText)findViewById(R.id.ED_Paqssword);
        ED_Paqssword.setOnFocusChangeListener(new ED_FocusChangeListener());
    }
    private class ED_FocusChangeListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()){
                case R.id.ED_Username:
                    if(hasFocus){
                        ED_Username.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_focusbiankuang));
                    }else{
                        ED_Username.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_biankuang));
                    }
                    break;
                case R.id.ED_Paqssword:

                    if(hasFocus){
                        ED_Paqssword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_focusbiankuang));
                    }else{
                        ED_Paqssword.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_biankuang));
                    }

                    break;
            }
        }
    }

}
