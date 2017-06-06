package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.myself.wypqwer.zhengqi_zhilian.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import JavaBeen.PublicBeen;

/**
 * Created by Administrator on 2017/6/2.
 */

public class Manager_ProblemSumAdapter extends BaseAdapter implements SectionIndexer {
    private Context context;
    private List<PublicBeen> list;
    private ImageLoader imageLoader;
    private int i = 0;
    private RequestQueue queue;   //volley请求
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    public void updateListView(List<PublicBeen> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public Manager_ProblemSumAdapter(Context context, List<PublicBeen> list) {
        this.list = list;
        this.context = context;
       /* queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(queue, new BitmapCache());*/
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
            convertView = LayoutInflater.from(context).inflate(R.layout.manager_problemsumlistview_layout, null);

            //holder.image = (NetworkImageView) convertView.findViewById(R.id.Manager_photo);

            holder.ProblemSum_COMPANY = (TextView) convertView.findViewById(R.id.Manager_ProblemSum_COMPANY);

            holder.ProblemSum_time = (TextView) convertView.findViewById(R.id.Manager_ProblemSum_time);

            holder.ProblemSum_state = (TextView) convertView.findViewById(R.id.Manager_ProblemSum_state);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
      //  holder.image.setTag(list.get(position).getIMAGESOURCE());
        holder.ProblemSum_COMPANY.setText(list.get(position).getCompanyName());
        if(list.get(position).getProject_state().equals("0")){
            holder.ProblemSum_state.setText("未处理");
            holder.ProblemSum_state.setTextColor(context.getResources().getColor(R.color.red));
        }else if(list.get(position).getProject_state().equals("1")){
            holder.ProblemSum_state.setText("已处理");
        }else if(list.get(position).getProject_state().equals("2")){
            holder.ProblemSum_state.setText("驳回");
            holder.ProblemSum_state.setTextColor(context.getResources().getColor(R.color.blue));
        }
        // 毫秒转日期

        long now = Integer.parseInt(list.get(position).getProject_TIME());
        calendar.setTimeInMillis(now*1000);
        formatter.format(calendar.getTime());

        holder.ProblemSum_time.setText(formatter.format(calendar.getTime())+"");
       // loadImageByImageLoader(holder.image, list.get(position).getIMAGESOURCE());
        Log.e("warn",list.get(position).getIMAGESOURCE());

        return convertView;
    }

    static class ViewHolder {
        TextView ProblemSum_COMPANY;
        TextView ProblemSum_person;
        TextView ProblemSum_state;
        TextView ProblemSum_time;
        NetworkImageView image;
    }

    //加载图片
    private void loadImageByImageLoader(NetworkImageView image, String goodsUrl) {

        //创建ImageLoader对象，参数（加入请求队列，自定义缓存机制）

        image.setDefaultImageResId(R.mipmap.image);
        image.setErrorImageResId(R.mipmap.image);
        image.setImageUrl(goodsUrl, imageLoader);

    }
    //自定义图片缓存BitmapCache
    private class BitmapCache implements ImageLoader.ImageCache {

        //使用安卓提供的缓存机制
        private LruCache<String, Bitmap> mCache;

        //重写构造方法
        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;  //缓存大小为10兆
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };

        }

        @Override
        public Bitmap getBitmap(String s) {
            return mCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            mCache.put(s,bitmap);
        }
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
