package com.example.daishu1;



import com.example.daishu1.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TabHost tabhost = getTabHost();
		Intent homeintent = new Intent();
		Intent submitintent = new Intent();
		Intent shuointent = new Intent();
		Intent userintent = new Intent();

		View homeview = this.getLayoutInflater()
				.inflate(R.layout.hometab, null);
		homeintent.setClass(this, home.class);
		TabHost.TabSpec homespec = tabhost.newTabSpec("home")
				.setIndicator(homeview).setContent(homeintent);
		tabhost.addTab(homespec);

		View sbumitview = this.getLayoutInflater().inflate(R.layout.submittab,
				null);
		submitintent.setClass(this, submit.class);
		TabHost.TabSpec submitspec = tabhost.newTabSpec("submit")
				.setIndicator(sbumitview).setContent(submitintent);
		tabhost.addTab(submitspec);

		View shuoview = this.getLayoutInflater()
				.inflate(R.layout.shuotab, null);
		shuointent.setClass(this, shuo.class);
		TabHost.TabSpec shuospec = tabhost.newTabSpec("shuo")
				.setIndicator(shuoview).setContent(shuointent);
		tabhost.addTab(shuospec);

		View userview = this.getLayoutInflater()
				.inflate(R.layout.usertab, null);
		userintent.setClass(this, user.class);
		TabHost.TabSpec userspec = tabhost.newTabSpec("user")
				.setIndicator(userview).setContent(userintent);
		tabhost.addTab(userspec);

		tabhost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				LinearLayout content;

				if (tabId.equalsIgnoreCase("home")) {
					content = (LinearLayout) findViewById(R.id.hometab);
					content.setBackgroundColor(getResources().getColor(
							R.color.material_deep_teal_200));
					content = (LinearLayout) findViewById(R.id.submittab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
					content = (LinearLayout) findViewById(R.id.shuotab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
					content = (LinearLayout) findViewById(R.id.usertab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
				} else if (tabId.equalsIgnoreCase("submit")) {
					content = (LinearLayout) findViewById(R.id.submittab);
					content.setBackgroundColor(getResources().getColor(
							R.color.material_deep_teal_200));
					content = (LinearLayout) findViewById(R.id.hometab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
					content = (LinearLayout) findViewById(R.id.shuotab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
					content = (LinearLayout) findViewById(R.id.usertab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
				} else if (tabId.equalsIgnoreCase("shuo")) {
					content = (LinearLayout) findViewById(R.id.shuotab);
					content.setBackgroundColor(getResources().getColor(
							R.color.material_deep_teal_200));
					content = (LinearLayout) findViewById(R.id.hometab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
					content = (LinearLayout) findViewById(R.id.submittab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
					content = (LinearLayout) findViewById(R.id.usertab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
				} else if (tabId.equalsIgnoreCase("user")) {
					content = (LinearLayout) findViewById(R.id.usertab);
					content.setBackgroundColor(getResources().getColor(
							R.color.material_deep_teal_200));
					content = (LinearLayout) findViewById(R.id.hometab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
					content = (LinearLayout) findViewById(R.id.shuotab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
					content = (LinearLayout) findViewById(R.id.submittab);
					content.setBackgroundColor(getResources().getColor(
							R.color.switch_thumb_normal_material_light));
				}
			}
		});
	}
}
