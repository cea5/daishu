package Utils;

import java.util.List;



import com.example.daishu1.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
//把所有的去掉，重新一句一句测试，就好了？？？。。。
public class ShuoAdapter extends BaseAdapter{
	private List<Shuo_info> list;  
    private Context context;  
    public ShuoAdapter(List<Shuo_info> list, Context context) {  
        this.list = list;  
        this.context = context;  
    }
	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = (RelativeLayout) LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.shuo_view, null);
		TextView list_name = (TextView) convertView.findViewById(R.id.list_name);
		list_name.setText(list.get(position).getName());
		TextView list_school = (TextView) convertView.findViewById(R.id.list_school);
		list_school.setText(list.get(position).getSchool());
		TextView list_shuo = (TextView) convertView.findViewById(R.id.list_shuo);
		list_shuo.setText(list.get(position).getContent());
		TextView list_comment = (TextView) convertView.findViewById(R.id.list_comment);
		list_comment.setText("评论"+list.get(position).getComment_number()+"次");
		TextView list_visit = (TextView) convertView.findViewById(R.id.list_visit);
		list_visit.setText("游览"+list.get(position).getVisit_number()+"次");
		TextView shuo_submit_time = (TextView) convertView.findViewById(R.id.shuo_submit_time);
		shuo_submit_time.setText(list.get(position).getSubmit_time());
		return convertView;
	}
}
