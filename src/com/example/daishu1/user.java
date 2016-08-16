package com.example.daishu1;


import java.util.HashMap;
import java.util.Map;





import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daishu1.R;

import Utils.session_info;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class user extends Activity {
	private TextView hint;
	private ImageButton zhuxiao;
	private EditText loginuser;
	private EditText loginpwd;
	private Button loginbutton;
	private Button register;
	SharedPreferences sp ;
	Boolean islogin ;
	String name ;
	String sessionid ;
	RequestQueue queue;
	String url ="http://115.28.231.190:8080/daishu/LoginServlet";
//	String url ="http://10.2.52.207/daishu/LoginServlet";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("info", MODE_PRIVATE);
		queue = Volley.newRequestQueue(this);
	}
	@Override
	protected void onStart() {
		super.onStart();
		Intent intent= new Intent();
		islogin = sp.getBoolean("islogin", false);
		
		if(islogin){
			setContentView(R.layout.logined);
			zhuxiao = (ImageButton)findViewById(R.id.zhuxiao);
			hint = (TextView)findViewById(R.id.hint);
			zhuxiao.setOnClickListener(new mButtonListener());
			hint.setText(sp.getString("name", ""));
		}
		else{
			setContentView(R.layout.login);
			loginuser=(EditText)findViewById(R.id.loginuser);
			loginpwd=(EditText)findViewById(R.id.loginpwd);
			loginbutton=(Button)findViewById(R.id.loginbutton);
			register=(Button)findViewById(R.id.register);
			loginbutton.setOnClickListener(new ButtonListener());
			register.setOnClickListener(new ButtonListener());
		}
	}
	class mButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.zhuxiao:
				StringRequest stringrequest = new StringRequest(Request.Method.POST,url, 
						new Listener<String>() {
					@Override
					public void onResponse(String response) {
						if(response.replaceAll("\\s*", "").equals("zhuxiao_success")){
							Editor editor = sp.edit();
							editor.putBoolean("islogin", false);
							editor.putString("sessionid", "");
							editor.commit();
							onStart();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				}){
					@Override
					public Map<String, String> getHeaders()
							throws AuthFailureError {
						Map<String, String> headers = new HashMap<String, String>();
						headers.put("Cookie", "JSESSIONID="+sp.getString("sessionid", ""));
						return headers;
					}
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						Map<String, String> map = new HashMap<String, String>();
						map.put("method", "zhuxiao");
						return map;
					}
				};
				queue.add(stringrequest);
				break;
			default:
				break;
			}
		}
	}

	class ButtonListener implements OnClickListener{
		String rawcookies = null;
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.loginbutton:
				if(!loginuser.getText().toString().equals("")&&!loginpwd.getText().toString().equals("")){
					StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
				            new Response.Listener<String>() {
				    @Override
				    public void onResponse(String response) {
				    	if(response.replaceAll("\\s*", "").equals("denglu_success")){
				    		Log.d("tag", "success");
				    		Editor editor = sp.edit();
				    		editor.putBoolean("islogin", true);
				    		editor.putString("name", loginuser.getText().toString());
				    		if(sp.getString("sessionid", "").equals(""))
				    			editor.putString("sessionid",session_info.getSessionId(rawcookies));
				    		editor.commit();
				    		onStart();
				    	}
				    	else if (response.replaceAll("\\s*", "").equals("denglu_failed")) {
				    		Log.d("tag", "pwd_failed");
				    		Toast.makeText(user.this, "µ«¬Ω ß∞‹£¨√‹¬Î¥ÌŒÛ", Toast.LENGTH_SHORT).show();
				    	}
				    	else if (response.replaceAll("\\s*", "").equals("failed_zhuce")) {
				    		Log.d("tag", "zhuce_failed");
				    		Toast.makeText(user.this, "≤ª¥Ê‘⁄¥À’À∫≈£°", Toast.LENGTH_SHORT).show();
				    	}
				    }
				}, new Response.ErrorListener() {
				    @Override
				    public void onErrorResponse(VolleyError error) {
				    	Log.d("tag", "net_error");
				    }
				}){
					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String, String> map = new HashMap<String, String>();  
					    map.put("method", "denglu");  
					    map.put("user", loginuser.getText().toString());
					    map.put("pwd", loginpwd.getText().toString());
						return map;
					}
					@Override
					protected Response<String> parseNetworkResponse(
							NetworkResponse response) {
						Map<String, String> rsheaders = response.headers;
						if(sp.getString("sessionid", "").equals(""))
							rawcookies = rsheaders.get("Set-Cookie");
						return super.parseNetworkResponse(response);
					}
				};
				queue.add(stringRequest);
			}
				else
					Toast.makeText(user.this, "’À∫≈√‹¬Î≤ªø…Œ™ø’£°", Toast.LENGTH_SHORT).show();
				break;
			case R.id.register:
				Intent intent = new Intent();
				intent.setClass(user.this, zhuce.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	}
}