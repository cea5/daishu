package com.example.daishu1;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.daishu1.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Test extends Activity {
	private TextView tv;
	ArrayList<HashMap<String,Object>> map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		tv = (TextView) findViewById(R.id.tv);
		SMSSDK.initSDK(this, "10a6b2def25e0", "3e48afa109073e63c2efb863a74b22c7");
		EventHandler eventhandler = new EventHandler(){
			@Override
			public void afterEvent(int arg0, int arg1, Object arg2) {
				super.afterEvent(arg0, arg1, arg2);
				map = (ArrayList<HashMap<String, Object>>) arg2;
				tv.setText("arg0:"+arg0+"arg1:"+arg1+"arg0:"+"arg2:"+map.toString());
				SMSSDK.unregisterAllEventHandler();
			}
			@Override
			public void beforeEvent(int arg0, Object arg1) {
				super.beforeEvent(arg0, arg1);
			}
			@Override
			public void onRegister() {
				super.onRegister();
			}
			@Override
			public void onUnregister() {
				super.onUnregister();
			}
		};
		SMSSDK.registerEventHandler(eventhandler);
		SMSSDK.getSupportedCountries();
	}
	

}
