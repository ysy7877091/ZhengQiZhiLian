package Company;

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
import com.myself.wypqwer.zhengqi_zhilian.R;

import Manager.Manager_ProblemSum;
import sousuo.ClearEditText;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CompanyComblem extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companycomblem_layout);
        CommonMethod.setStatuColor(CompanyComblem.this,R.color.white);
        init();
    }
    private void init(){
        Button ProblemSum_back = (Button)findViewById(R.id.CompanyComblem_Back);
        ProblemSum_back.setOnClickListener(new ProblemSumActivityListener());

        Button add = (Button)findViewById(R.id.CompanyComblem_add);
        add.addTextChangedListener(new EditTextListener());

        RelativeLayout AllState = (RelativeLayout)findViewById(R.id.CompanyComblem_Allstate);
        AllState.setOnClickListener(new ProblemSumActivityListener());

        RelativeLayout AllPerson = (RelativeLayout)findViewById(R.id.CompanyComblem_AllPerson);
        AllPerson.setOnClickListener(new ProblemSumActivityListener());

        ListView mListView = (ListView)findViewById(R.id.CompanyComblem_mListView);
        mListView.setOnItemClickListener(new ListViewListener());
    }
    private class ProblemSumActivityListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.CompanyComblem_Back:
                    finish();
                    break;
                case R.id.CompanyComblem_add:break;
                case R.id.CompanyComblem_Allstate:break;
                case R.id.CompanyComblem_AllPerson:break;
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
