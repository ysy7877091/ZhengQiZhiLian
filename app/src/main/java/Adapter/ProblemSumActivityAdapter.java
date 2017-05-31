package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myself.wypqwer.zhengqi_zhilian.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ProblemSumActivityAdapter extends BaseAdapter {
    private Context context;
    private List list;
    public ProblemSumActivityAdapter(Context context, List list){

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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.problemsumactivitylistviewadapeter_layout, null);


            holder.ProblemSum_COMPANY = (TextView) convertView.findViewById(R.id.ProblemSum_COMPANY);

            holder.ProblemSum_person= (TextView)convertView.findViewById(R.id.ProblemSum_person);

            holder.ProblemSum_state=(TextView)convertView.findViewById(R.id.ProblemSum_state);

            holder.ProblemSum_time=(TextView)convertView.findViewById(R.id.ProblemSum_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /*holder.ProblemSum_COMPANY.setText(list.get(position).getCarNum());
        holder.ProblemSum_person.setText(list.get(position).getCarNum());
        holder.ProblemSum_state.setText(list.get(position).getName());
        holder.ProblemSum_time.setText(list.get(position).getName());*/


        return convertView;
    }
    static class ViewHolder{
        TextView ProblemSum_COMPANY;
        TextView ProblemSum_person;
        TextView ProblemSum_state;
        TextView ProblemSum_time;
    }
}
