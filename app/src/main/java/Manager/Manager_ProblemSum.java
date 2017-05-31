package Manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.myself.wypqwer.zhengqi_zhilian.CommonMethod;
import com.myself.wypqwer.zhengqi_zhilian.ProblemInformation;
import com.myself.wypqwer.zhengqi_zhilian.ProblemSumActivity;
import com.myself.wypqwer.zhengqi_zhilian.R;

import sousuo.ClearEditText;

/**
 * Created by Administrator on 2017/5/19.
 */

public class Manager_ProblemSum extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_problemsum_layout);
        CommonMethod.setStatuColor(Manager_ProblemSum.this,R.color.white);
        init();
    }
    private void init(){
        Button ProblemSum_back = (Button)findViewById(R.id.Manager_ProblemSum_back);
        ProblemSum_back.setOnClickListener(new ProblemSumActivityListener());
        ClearEditText sousuo = (ClearEditText)findViewById(R.id.Manager_ProblemSum_SouSuo);
        sousuo.addTextChangedListener(new EditTextListener());

        RelativeLayout AllState = (RelativeLayout)findViewById(R.id.Manager_AllState);
        AllState.setOnClickListener(new ProblemSumActivityListener());

        RelativeLayout Manager_AllTime = (RelativeLayout)findViewById(R.id.Manager_AllTime);
        Manager_AllTime.setOnClickListener(new ProblemSumActivityListener());

        ListView mListView = (ListView)findViewById(R.id.Manager_mListView);
        mListView.setOnItemClickListener(new ListViewListener());
    }
    private class ProblemSumActivityListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Manager_ProblemSum_back:
                    finish();
                    break;
                case R.id.Manager_AllState:break;
                case R.id.Manager_AllTime:break;
            }
        }
    }
    private class EditTextListener implements TextWatcher {

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

        }
    }
}
