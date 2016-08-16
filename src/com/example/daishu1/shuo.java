package com.example.daishu1;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.daishu1.R;

import Utils.LvAdapter;
import Utils.MyListView;
import Utils.ShuoAdapter;
import Utils.Shuo_info;
import Utils.Submit_info;
import Utils.MyListView.OnRefreshListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class shuo extends Activity {
	private MyListView shuo_list;
	private LvAdapter shuo_adapter;
	private ImageView shuo_submit_button;
	public JsonObjectRequest jsonrequest;
	public String url = "http://115.28.231.190:8080/daishu/ShuoServlet?method=shuo";
//	public String url = "http://10.2.52.207/daishu/ShuoServlet?method=shuo";
	RequestQueue queue;
	LinkedList<Shuo_info> list;
	ShuoAdapter adapter;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shuo);
		queue = Volley.newRequestQueue(this);
		sp = getSharedPreferences("info",MODE_PRIVATE);
		shuo_submit_button = (ImageView) findViewById(R.id.shuo_submit_button);
		shuo_submit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(shuo.this, shuo_submit_activity.class);
				startActivity(intent);
			}
		});
		shuo_list = (MyListView) findViewById(R.id.shuo_list);
		list = new LinkedList<Shuo_info>();
		jsonrequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					list.clear();
					int length = response.length();
					for (int j = 0; j < length; j++) {
						JSONObject json = response.getJSONObject(j+"");
						Shuo_info shuo_info = new Shuo_info();
						shuo_info.setId(json.getInt("shuo_id"));
						shuo_info.setName(json.getString("shuo_name"));
						shuo_info.setSchool(json.getString("school"));
						shuo_info.setContent(json.getString("shuo_content"));
						shuo_info.setSubmit_time(json.getString("shuo_time"));
						shuo_info.setComment_number(json.getInt("shuo_comment_number"));
						shuo_info.setVisit_number(json.getInt("shuo_visit_number"));
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
			}
		});
		queue.add(jsonrequest);
		adapter = new ShuoAdapter(list, this);
		shuo_list.setAdapter(adapter);
		shuo_list.setonRefreshListener(new OnRefreshListener(){
			@Override
			public void onRefresh() {
				new GetShuoTask().execute();
			}
		});
		shuo_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Toast.makeText(shuo.this, list.get((int)id).getId()+"", Toast.LENGTH_SHORT).show();
				Editor editor = sp.edit();
				editor.putInt("shuo_id", list.get((int)id).getId());
				editor.putString("shuo_name", list.get((int)id).getName());
				editor.putString("shuo_school", list.get((int)id).getSchool());
				editor.putString("shuo_content", list.get((int)id).getContent());
				editor.putString("shuo_submit_time", list.get((int)id).getSubmit_time());
				editor.commit();
				Intent intent = new Intent(shuo.this, CommentsActivity.class);
				startActivity(intent);
			}
		});
	}
	
	class GetShuoTask extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			jsonrequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						list.clear();
						int length = response.length();
						for (int j = 0; j < length; j++) {
							JSONObject json = response.getJSONObject(j+"");
							Shuo_info shuo_info = new Shuo_info();
							shuo_info.setName(json.getString("shuo_name"));
							shuo_info.setSchool(json.getString("school"));
							shuo_info.setContent(json.getString("shuo_content"));
							shuo_info.setSubmit_time(json.getString("shuo_time"));
							shuo_info.setComment_number(json.getInt("shuo_comment_number"));
							shuo_info.setVisit_number(json.getInt("shuo_visit_number"));
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
			shuo_list.onRefreshComplete();
		}
	}
}
