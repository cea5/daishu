package Utils;

import java.util.List;  



import com.example.daishu1.R;

import android.content.Context;  
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.RelativeLayout;
import android.widget.TextView;  
  
public class LvAdapter extends BaseAdapter {  
    private List<Submit_info> list;  
    private Context context;  
    public LvAdapter(List<Submit_info> list, Context context) {  
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
		convertView = (RelativeLayout) LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.submit_list_view, null);
		TextView list_name = (TextView) convertView.findViewById(R.id.list_name);
		list_name.setText(list.get(position).getName());
		TextView list_request = (TextView) convertView.findViewById(R.id.list_request);
		list_request.setText(list.get(position).getSubmit_request());
		TextView list_submit_time = (TextView) convertView.findViewById(R.id.list_submit_time);
		list_submit_time.setText(list.get(position).getSubmit_time());
		TextView list_reward = (TextView) convertView.findViewById(R.id.list_reward);
		list_reward.setText("׬"+list.get(position).getReward()+"Ԫ");
		return convertView;
    }  
}  