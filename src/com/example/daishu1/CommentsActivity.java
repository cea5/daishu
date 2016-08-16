package com.example.daishu1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daishu1.R;

import Utils.MyListView;
import Utils.MyListView.OnRefreshListener;
import Utils.ShuoAdapter;
import Utils.Shuo_info;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommentsActivity extends Activity {
	private MyListView listview;
	private MyAdapter adapter;
	private LinkedList<Shuo_info> list;
	SharedPreferences sp;
	private TextView comment_click;
	private EditText comment_text;
	private RequestQueue queue;
	String url ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_activity);
		queue = Volley.newRequestQueue(this);
		sp = getSharedPreferences("info", MODE_PRIVATE);
		url = "http://115.28.231.190:8080/daishu/ShuoServlet?method=comment&comment_to_id="+sp.getInt("shuo_id", 0);
//		url = "http://10.2.52.207/daishu/ShuoServlet?method=comment&comment_to_id="+sp.getInt("shuo_id", 0);
		listview = (MyListView) findViewById(R.id.shuo_list);
		comment_click = (TextView) findViewById(R.id.comment_click);
		comment_text = (EditText) findViewById(R.id.comment_text);
		list = new LinkedList<Shuo_info>();
		Shuo_info shuoinfo = new Shuo_info();
		shuoinfo.setName(sp.getString("shuo_name", ""));
		shuoinfo.setSchool(sp.getString("shuo_school", ""));
		shuoinfo.setContent(sp.getString("shuo_content", ""));
		shuoinfo.setSubmit_time(sp.getString("shuo_submit_time", ""));
		list.add(shuoinfo);
		list.add(shuoinfo);
		adapter = new MyAdapter(list);
		listview.setAdapter(adapter);
		JsonObjectRequest jrequest = new JsonObjectRequest(url, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					int length = response.length();
					for (int j = 0; j < length; j++) {
						JSONObject json = response.getJSONObject(j+"");
						Shuo_info shuo_info = new Shuo_info();
						shuo_info.setName(json.getString("comment_name"));
						shuo_info.setContent(json.getString("comment_content"));
						shuo_info.setSchool(json.getString("school"));
						shuo_info.setSubmit_time(json.getString("comment_time"));
						list.add(shuo_info);
					}
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
		queue.add(jrequest);
		listview.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new GetCommentTask().execute();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
					
			}
		});
		
		comment_click.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "http://10.2.52.207/daishu/ShuoServlet";
				StringRequest request = new StringRequest(Method.POST, url, new Listener<String>() {
					@Override
					public void onResponse(String response) {
						if(response.replaceAll("\\s*", "").equals("comment_success"))
							Toast.makeText(CommentsActivity.this, "——发布评论成功——", Toast.LENGTH_SHORT).show();
						else if(response.replaceAll("\\s*", "").equals("comment_UnKnownError"))
							Toast.makeText(CommentsActivity.this, "——发布评论失败——", Toast.LENGTH_SHORT).show();
						comment_text.setText("");
					}
				} , new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				}){
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						Map<String, String> map = new HashMap<String, String>();
						map.put("method", "comment_submit");
						map.put("comment_name", sp.getString("name", ""));
						map.put("comment", comment_text.getText().toString());
						map.put("comment_to_id", sp.getInt("shuo_id", 0)+"");
						return map;
					}
				};
				queue.add(request);
			}
		});
	}
	private class MyAdapter extends BaseAdapter{
		private List<Shuo_info> list;  
	    public MyAdapter(List<Shuo_info> list) {  
	        this.list = list;
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
			if(position!=1){
				convertView = (RelativeLayout) LayoutInflater.from(CommentsActivity.this).inflate(R.layout.shuo_details_listview, null);
				TextView list_name = (TextView) convertView.findViewById(R.id.list_name);
				list_name.setText(list.get(position).getName());
				TextView list_school = (TextView) convertView.findViewById(R.id.list_school);
				list_school.setText(list.get(position).getSchool());
				TextView list_shuo = (TextView) convertView.findViewById(R.id.list_shuo);
				list_shuo.setText(list.get(position).getContent());
				TextView shuo_submit_time = (TextView) convertView.findViewById(R.id.shuo_submit_time);
				shuo_submit_time.setText(list.get(position).getSubmit_time());
			}
			else{
				TextView tv = new TextView(CommentsActivity.this);
				tv.setText("以下为评论:");
				tv.setTextColor(Color.rgb(0, 0, 0));
				tv.setTextSize(20);
				convertView = tv;
			}
			return convertView;
		}
	}

	class GetCommentTask extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			JsonObjectRequest jsonrequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					for(int i = 2;i<list.size();i++){
						list.remove(i);
						i--;
					}
					try {
						int length = response.length();
						for (int j = 0; j < length; j++) {
							JSONObject json = response.getJSONObject(j+"");
							Shuo_info shuo_info = new Shuo_info();
							shuo_info.setName(json.getString("comment_name"));
							shuo_info.setContent(json.getString("comment_content"));
							shuo_info.setSchool(json.getString("school"));
							shuo_info.setSubmit_time(json.getString("comment_time"));
							list.add(shuo_info);
						}
						adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.d("test", error.getMessage());
				}
			});
			queue.add(jsonrequest);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			listview.onRefreshComplete();
		}
	}
}
