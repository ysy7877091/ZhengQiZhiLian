package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.myself.wypqwer.zhengqi_zhilian.R;

import java.util.List;

import JavaBeen.PublicBeen;

/**
 * Created by Administrator on 2017/6/1.
 */

/*
public class CompanyComblemAdapter extends BaseAdapter {
    private Context context;
    private List<PublicBeen> list;


    public CompanyComblemAdapter(Context context, List<PublicBeen> list) {
        this.list = list;
        this.context = context;
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
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.problemsumactivitylistviewadapeter_layout, null);

            holder.image = (NetworkImageView) convertView.findViewById(R.id.problem_photo);

            holder.ProblemSum_COMPANY = (TextView) convertView.findViewById(R.id.ProblemSum_COMPANY);

            holder.ProblemSum_person = (TextView) convertView.findViewById(R.id.ProblemSum_person);

            holder.ProblemSum_state = (TextView) convertView.findViewById(R.id.ProblemSum_state);

            holder.ProblemSum_time = (TextView) convertView.findViewById(R.id.ProblemSum_time);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.image.setTag(list.get(position).getIMAGESOURCE());
        holder.ProblemSum_COMPANY.setText(list.get(position).getCompanyName());
        holder.ProblemSum_person.setText(list.get(position).getPerson_Name());
        holder.ProblemSum_state.setText(list.get(position).getProject_state());
        holder.ProblemSum_time.setText(list.get(position).getProject_TIME());

        Log.e("warn",list.get(position).getIMAGESOURCE()+"+"+position);
        return convertView;
    }
    static class ViewHolder {
        TextView ProblemSum_COMPANY;
        TextView ProblemSum_person;
        TextView ProblemSum_state;
        TextView ProblemSum_time;
        NetworkImageView image;
    }
}
*/
