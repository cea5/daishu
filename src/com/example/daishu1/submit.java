package com.example.daishu1;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daishu1.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class submit extends Activity {
	
	private Button next = null;
	private EditText request =null;
	private EditText address =null;
	private EditText tel =null;
	private EditText reward =null;
	private Spinner time_spinner;
	SharedPreferences sp;
	RequestQueue queue;
	String delivery_time;
	String url = "http://115.28.231.190:8080/daishu/SubmitServlet";
//	String url = "http://10.2.52.207/daishu/SubmitServlet";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit);
		
		next = (Button)findViewById(R.id.next);
		request = (EditText)findViewById(R.id.request);
		address = (EditText)findViewById(R.id.address);
		tel = (EditText)findViewById(R.id.tel);
		reward = (EditText)findViewById(R.id.reward);
		sp = getSharedPreferences("info",MODE_PRIVATE);
		queue = Volley.newRequestQueue(this);
		next.setOnClickListener(new ButtonListener());
		time_spinner = (Spinner) findViewById(R.id.delivery_time);
		time_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				delivery_time = time_spinner.getSelectedItem().toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	class ButtonListener implements OnClickListener{

		@SuppressLint("ShowToast")
		@Override
		public void onClick(View v) {
			if(request.getText().toString().equals("")||address.getText().toString().equals("")
					||tel.getText().toString().equals("")||reward.getText().toString().equals("")){
				Toast t = Toast.makeText(submit.this, "——输入内容不能为空——", Toast.LENGTH_SHORT);
				t.show();
				}
			else{
				if(sp.getBoolean("islogin", false)){
					StringRequest stringrequest = new StringRequest(Request.Method.POST, url, 
							new Listener<String>() {
								@Override
								public void onResponse(String response) {
									if(response.replaceAll("\\s*", "").equals("submit_success")){
										Toast t = Toast.makeText(submit.this, "——发布任务成功——", Toast.LENGTH_SHORT);
										t.show();
										request.setText("");
										address.setText("");
										tel.setText("");
										reward.setText("");
									}
									else{
										Toast t = Toast.makeText(submit.this, "——未知错误——", Toast.LENGTH_SHORT);
										t.show();
										request.setText("");
										address.setText("");
										tel.setText("");
										reward.setText("");
									}
								}
					}, new ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							
						}
					}){
						@Override
						protected Map<String, String> getParams()
								throws AuthFailureError {
							Map<String, String> params = new HashMap<String, String>();
							params.put("method", "submit");
							params.put("name", sp.getString("name", ""));
							params.put("request", request.getText().toString());
							params.put("address", address.getText().toString());
							params.put("tel", tel.getText().toString());
							params.put("reward", reward.getText().toString());
							params.put("delivery_time", delivery_time);
							return params;
						}
						@Override
						public Map<String, String> getHeaders()
								throws AuthFailureError {
							Map<String, String> headers = new HashMap<String, String>();
							headers.put("Cookie", "JSESSIONID="+sp.getString("sessionid", ""));
							return headers;
						}
					};
					queue.add(stringrequest);
				}
				else{
					Toast t = Toast.makeText(submit.this, "——请先登录——", Toast.LENGTH_SHORT);  
					t.show();
				}
			}
		}
	} 
}
