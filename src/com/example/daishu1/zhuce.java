package com.example.daishu1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;






import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.example.daishu1.R;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class zhuce extends Activity {
	private EditText user ;
	private EditText pwd ;
	private EditText pwd2 ;
	private EditText mail ;
	private EditText tel ;
	private EditText identify_code ;
	private Button next ;
	private Button back ;
	public String method ="";
	private Spinner schoolspin;
    private Spinner academyspin;
    private Spinner yearspin;
    private Spinner sexspin;
    private String school="";
	private String academy="";
	private String year="";
	private String sex="";
	private TextView identify;
	private Boolean is_identified = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuce);
		SMSSDK.initSDK(this, "10a6b2def25e0", "3e48afa109073e63c2efb863a74b22c7");
		user=(EditText)findViewById(R.id.user);
		pwd=(EditText)findViewById(R.id.pwd);
		pwd2=(EditText)findViewById(R.id.pwd2);
		mail=(EditText)findViewById(R.id.mail);
		identify_code = (EditText) findViewById(R.id.identify_code);
		identify = (TextView) findViewById(R.id.identify);
		tel=(EditText)findViewById(R.id.tel);
		next=(Button)findViewById(R.id.next);
		back=(Button)findViewById(R.id.back);
		next.setOnClickListener(new ButtonListener());
		back.setOnClickListener(new ButtonListener());
		schoolspin = (Spinner)findViewById(R.id.schoolspin);
		schoolspin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				school = schoolspin.getSelectedItem().toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		academyspin = (Spinner)findViewById(R.id.academyspin);
		academyspin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				academy = academyspin.getSelectedItem().toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		yearspin = (Spinner)findViewById(R.id.yearspin);
		yearspin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				year = yearspin.getSelectedItem().toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		sexspin = (Spinner)findViewById(R.id.sexspin);
		sexspin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				sex = sexspin.getSelectedItem().toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		identify.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}
	public void send_identify(View view){
		EventHandler eventhandler = new EventHandler(){
			@Override
			public void afterEvent(int arg0, int arg1, Object arg2) {
				super.afterEvent(arg0, arg1, arg2);
				String result = "";
				if (arg1 == SMSSDK.RESULT_COMPLETE) {
	                //回调完成
	                if (arg0 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
	                //提交验证码成功
	                }else if (arg0 == SMSSDK.EVENT_GET_VERIFICATION_CODE){
	                //获取验证码成功
	                }else if (arg0 ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
	                //返回支持发送验证码的国家列表
	                } 
	              }else{                                                                 
	                 ((Throwable)arg2).printStackTrace(); 
	          }
				Message msg = new Message();
				msg.arg1 = arg0;
				msg.arg2 = arg1;
				msg.obj = arg2.getClass().getName();
				handler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eventhandler);
		SMSSDK.getVerificationCode("86", tel.getText().toString());
	}
	public void send_code(View view){
		SMSSDK.submitVerificationCode("86", tel.getText().toString().trim(), identify_code.getText().toString());
	}
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			String arg2 ="";
			String arg1 ="";
			if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {
                //回调完成
				arg2 = "RESULT_COMPLETE";
                if (msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                //提交验证码成功
                	arg1 = "EVENT_SUBMIT_VERIFICATION_CODE";
                	Toast.makeText(zhuce.this,"验证成功" , Toast.LENGTH_LONG).show();
                	is_identified = true;
                }else if (msg.arg1 == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                //获取验证码成功
                	arg1 = "EVENT_GET_VERIFICATION_CODE";
                	Toast.makeText(zhuce.this,"验证码已发送，请等待" , Toast.LENGTH_LONG).show();
                }else if (msg.arg1 ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                //返回支持发送验证码的国家列表
                	arg1 = "EVENT_GET_SUPPORTED_COUNTRIES";
                } 
			}
			System.out.println("arg0:"+arg1+"\narg1:"+arg2+"\narg2:"+msg.obj);
		}
	};
	@Override
	protected void onDestroy() {
		SMSSDK.unregisterAllEventHandler();
		super.onDestroy();
	}
	
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.next:
				if(user.getText().toString().replaceAll("\\s*", "")==""){
					Toast.makeText(zhuce.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if (pwd.getText().toString().replaceAll("\\s*", "")=="") {
					Toast.makeText(zhuce.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if (mail.getText().toString().replaceAll("\\s*", "")=="") {
					Toast.makeText(zhuce.this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if (tel.getText().toString().replaceAll("\\s*", "")=="") {
					Toast.makeText(zhuce.this, "手机不能为空！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if (pwd2.getText().toString().replaceAll("\\s*", "")=="") {
					Toast.makeText(zhuce.this, "请确认密码！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if(!pwd.getText().toString().equals(pwd2.getText().toString())){
					Toast.makeText(zhuce.this, "密码不一致！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if(school.equals("")){
					Toast.makeText(zhuce.this, "学校不能为空！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if(academy.equals("")){
					Toast.makeText(zhuce.this, "学院不能为空！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if(year.equals("")){
					Toast.makeText(zhuce.this, "入学年份不能为空！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if(sex.equals("")){
					Toast.makeText(zhuce.this, "性别不能为空！", Toast.LENGTH_SHORT).show();
					break;
				}
				else if(!is_identified){
					Toast.makeText(zhuce.this, "手机未验证", Toast.LENGTH_SHORT).show();
					break;
				}
				else{
					method="zhuce";
					NetworkThread thread = new NetworkThread();
					thread.start();
					break;
				}
			case R.id.back:
				zhuce.this.finish();
				break;	
			default:
				break;
			}
		}
	}
	public Handler handler1 = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(((String) msg.obj).replaceAll("\\s*", "").equals("zhuce_success")){
				Toast.makeText(zhuce.this, "注册成功！", Toast.LENGTH_SHORT).show();
				zhuce.this.finish();
			}
			else if (((String) msg.obj).replaceAll("\\s*", "").equals("zhuce_failed")) {
				Toast.makeText(zhuce.this, "用户已被注册！", Toast.LENGTH_SHORT).show();
			}
		}
	};
	class NetworkThread extends Thread{
		@Override
		public void run(){
			InputStream in = null;
			HttpURLConnection urlConnection=null;
			String line = null;
			String Xline= "";
			String strUTF8="";
			try {
				URL url = new URL("http://115.28.231.190:8080/daishu/LoginServlet");
//				URL url = new URL("http://10.2.52.207/daishu/LoginServlet");
				urlConnection = (HttpURLConnection) url.openConnection();
			    urlConnection.setDoOutput(true);
			    urlConnection.setDoInput(true);
			    urlConnection.setChunkedStreamingMode(0);
			    
		        StringBuffer params = new StringBuffer();
		        params.append("method=").append(method).append("&").
		        append("user").append("=").append(user.getText().toString()).append("&").
		        append("pwd").append("=").append(pwd.getText().toString()).append("&").
		        append("tel").append("=").append(tel.getText().toString()).append("&").
		        append("mail").append("=").append(mail.getText().toString()).append("&").
		        append("school").append("=").append(school).append("&").
		        append("academy").append("=").append(academy).append("&").
		        append("year").append("=").append(year).append("&").
		        append("sex").append("=").append(sex);
		        byte[] bt=params.toString().getBytes();
				urlConnection.getOutputStream().write(bt);
				
				in = new BufferedInputStream(urlConnection.getInputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				while((line = reader.readLine()) != null) {
				    Xline=Xline+"\n"+line;
				}
//				strUTF8 = URLDecoder.decode(Xline, "UTF-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("error");
				e.printStackTrace();
			} finally {
				urlConnection.disconnect();
			   }
			Message msg = new Message();
			msg.obj=Xline;
			handler1.sendMessage(msg);
		}
	}
}