package com.example.daishu1;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daishu1.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class shuo_submit_activity extends Activity {
	private Button submit_button;
	private EditText shuo_content;
	private RequestQueue queue;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shuo_submit);
		queue = Volley.newRequestQueue(this);
		sp = getSharedPreferences("info", MODE_PRIVATE);
		shuo_content = (EditText) findViewById(R.id.shuo_content);
		submit_button = (Button) findViewById(R.id.submit_button);
		submit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(shuo_content.getText().toString().equals(""))
					Toast.makeText(shuo_submit_activity.this, "——输入内容不能为空——", Toast.LENGTH_SHORT).show();
				else{
					if(sp.getBoolean("islogin", false)){
						String url = "http://115.28.231.190:8080/daishu/ShuoServlet";
//						String url = "http://10.2.52.207/daishu/ShuoServlet";
						StringRequest srequest = new StringRequest(Method.POST, url, new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								if(response.replaceAll("\\s*", "").equals("submit_success")){
									Toast.makeText(shuo_submit_activity.this, "——发布说说成功——", Toast.LENGTH_SHORT).show();
									shuo_submit_activity.this.finish();
								}
								else if(response.replaceAll("\\s*", "").equals("submit_failed"))
									Toast.makeText(shuo_submit_activity.this, "——发布说说失败——", Toast.LENGTH_SHORT).show();
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
							}
						}){
							@Override
							protected Map<String, String> getParams()
									throws AuthFailureError {
								Map<String, String> map = new HashMap<String, String>();
								map.put("method", "shuo_submit");
								map.put("shuo_name", sp.getString("name", ""));
								map.put("shuo_content", shuo_content.getText().toString());
								return map;
							}
						};
						queue.add(srequest);
					}
					else
						Toast.makeText(shuo_submit_activity.this, "——请先登录——", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
}
