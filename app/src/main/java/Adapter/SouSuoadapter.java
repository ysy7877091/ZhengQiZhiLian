package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.myself.wypqwer.zhengqi_zhilian.R;

import java.util.List;

import JavaBeen.PublicBeen;

/**
 * Created by Administrator on 2017/5/19.
 */

public class SouSuoadapter extends BaseAdapter implements SectionIndexer {
    private List<PublicBeen> list;
    private Context context;
    public SouSuoadapter(List<PublicBeen> list, Context context){
        this.context=context;
        this.list = list;
    }
    public void updateListView(List<PublicBeen> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView= inflater.inflate(R.layout.mapsousuoarrayadapter_layout,null);
            holder.name = (TextView) convertView.findViewById(R.id.COMPANY_NAME);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.name.setText(list.get(position).getCompanyName());
        return convertView;
    }
    static class ViewHolder{
       private TextView name;
    }
    // 搜索

    @Override
    public Object[] getSections() {
        return new Object[0];
    }
    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }
    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }
    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
}
