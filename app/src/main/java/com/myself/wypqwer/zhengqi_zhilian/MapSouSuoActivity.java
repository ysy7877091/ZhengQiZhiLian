package com.myself.wypqwer.zhengqi_zhilian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.SouSuoadapter;
import JavaBeen.PublicBeen;
import sousuo.CharacterParser;
import sousuo.ClearEditText;

/**
 * Created by Administrator on 2017/5/19.
 */

public class MapSouSuoActivity extends AppCompatActivity {
    private List<PublicBeen> list= new ArrayList<>();
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapsousuo_layout);
        CommonMethod.setStatuColor(MapSouSuoActivity.this, R.color.white);
        init();
    }
    private void init() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        ClearEditText Map_SouSuo = (ClearEditText) findViewById(R.id.Map_SouSuoActivity);
        Map_SouSuo.addTextChangedListener(new ClearEditTextListener());



        /*Button SouSuoButton = (Button)findViewById(R.id.SouSuoButton);
        SouSuoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        CompanyMethod();
    }
    String arr []=null;
    SouSuoadapter ss;
    private void CompanyMethod(){
        String company =getIntent().getStringExtra("company");
        arr  = company.split(",");
        for(int i=0;i<arr.length;i++){
            if(arr!=null&&arr.length>0){
                if(!arr[i].equals("")&&arr[i]!=null){
                    PublicBeen pb = new PublicBeen();
                    pb.setCompanyName(arr[i]);
                    filledData(pb);
                    list.add(pb);
                }
            }
        }
        ListView  listView = (ListView)findViewById(R.id.SouSuo_lv);
        ss = new SouSuoadapter(list,MapSouSuoActivity.this);
        listView.setAdapter(ss);
        listView.setOnItemClickListener(new ListViewListener());
    }
    private class ListViewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.putExtra("NAME",list.get(position).getCompanyName());
            MapSouSuoActivity.this.setResult(1,intent);
            MapSouSuoActivity.this.finish();
        }
    }
    private class ClearEditTextListener implements TextWatcher{

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
        ss.updateListView(filterDateList);
    }
}
