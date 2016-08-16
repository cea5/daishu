package com.example.daishu1;

import java.lang.ref.WeakReference;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.daishu1.R;

import Utils.LvAdapter;
import Utils.MyListView;
import Utils.MyListView.OnRefreshListener;
import Utils.Submit_info;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class home extends Activity {
	SharedPreferences sp;
	private ImageView dot1;
	private ImageView dot2;
	private List<View> mviews = new ArrayList<View>();
	private MyListView lv;
	private ViewPager mViewPager ;
	private PagerAdapter mTabsAdapter ;
	public ImageHandler handler = new ImageHandler(new WeakReference<home>(this));
	private String result;
	private LinearLayout liner_view;
	LvAdapter adapter;
	RequestQueue queue;
	String url = "http://115.28.231.190:8080/daishu/SubmitServlet";
//	String url = "http://10.2.52.207/daishu/SubmitServlet";
	JsonObjectRequest jsonrequest;
	LinkedList<Submit_info> list;
	int length;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("test", "onCreate");
		setContentView(R.layout.home);
		sp = getSharedPreferences("info", MODE_PRIVATE);
		dot1 = (ImageView)findViewById(R.id.dot1);
		dot2 = (ImageView)findViewById(R.id.dot2);
		liner_view = (LinearLayout)findViewById(R.id.liner_view);
		mViewPager = new ViewPager(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm); 
		mViewPager.setLayoutParams(new LayoutParams(dm.widthPixels, 936*dm.widthPixels/2316+1));
		liner_view.addView(mViewPager);
		lv = (MyListView)findViewById(R.id.lv);
		queue = Volley.newRequestQueue(this);
		list = new LinkedList<Submit_info>();
//		list.add("更新中");
		jsonrequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					length = response.length();
					for (int j = 0; j < length; j++) {
						JSONObject json = response.getJSONObject(j+"");
						Submit_info submit_info = new Submit_info();
						submit_info.setName(json.getString("name"));
						submit_info.setSubmit_request(json.getString("request"));
						submit_info.setSubmit_address(json.getString("address"));
						submit_info.setSubmit_tel(json.getString("tel"));
						submit_info.setReward(json.getString("reward"));
						submit_info.setSubmit_time(json.getString("submit_time"));
						submit_info.setDelivery_time(json.getString("delivery_time"));	
						list.add(submit_info);
					}
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println(error.getStackTrace());
			}
		});
		queue.add(jsonrequest);
		adapter = new LvAdapter(list, this);
		lv.setAdapter(adapter);
		lv.setonRefreshListener(new OnRefreshListener(){
			@Override
			public void onRefresh() {
				new GetDataTask().execute();
			}
		});
		LayoutInflater lf = LayoutInflater.from(this);
		View one = lf.inflate(R.layout.one, null);
		View two = lf.inflate(R.layout.two, null);
		mviews.add(one);
		mviews.add(two);
		mTabsAdapter = new PagerAdapter() {
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mviews.get(position));
			}
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(mviews.get(position));
//	             //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
//	             ViewParent vp =view.getParent();
//	             if (vp!=null){
//	                 ViewGroup parent = (ViewGroup)vp;
//	                 parent.removeView(view);
//	             }
	             return mviews.get(position); 
			}
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			@Override
			public int getCount() {
				return mviews.size();
			}
		};
		mViewPager.setAdapter(mTabsAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				Log.d("debug","onPageSelectedposition:"+position);
				switch (position) {
				case 0:
					dot1.setImageResource(R.drawable.dot12);
					dot2.setImageResource(R.drawable.dot1);
					break;
				case 1:
					dot2.setImageResource(R.drawable.dot12);
					dot1.setImageResource(R.drawable.dot1);
					break;
				default:
					break;
				}
				if(position==1){
					Log.d("debug","onPageSelected:MSG_REFRESH");
					handler.sendEmptyMessageDelayed(ImageHandler.MSG_REFRESH, ImageHandler.MSG_DELAY);
				}
				else{
					Log.d("debug","onPageSelected:MSG_PAGE_CHANGED");
					handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, position, 0));
				}
			}
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}
			@Override
			public void onPageScrollStateChanged(int state) {
				switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                	Log.d("debug","SCROLL_STATE_DRAGGING:MSG_KEEP_SILENT");
                    handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                	Log.d("debug","SCROLL_STATE_IDLE:MSG_UPDATE_IMAGE");
                    handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                    break;
                default:
                    break;
                }
			}
		});
		mViewPager.setCurrentItem(0);
		dot1.setImageResource(R.drawable.dot12);
        //开始轮播效果
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_FIRST_UPDATE, ImageHandler.MSG_DELAY);
	}
	private static class ImageHandler extends Handler{
        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE  = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT   = 2;
        
        protected static final int MSG_FIRST_UPDATE  = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED  = 4;
        protected static final int MSG_REFRESH  = 5;
          
        //轮播间隔时间
        protected static final long MSG_DELAY = 3000;
          
        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<home> weakReference;
        private int currentItem = 0;
          
        protected ImageHandler(WeakReference<home> wk){
            weakReference = wk;
        }
          
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            home activity = weakReference.get();
            if (activity==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (activity.handler.hasMessages(MSG_UPDATE_IMAGE)){
                activity.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
            case MSG_UPDATE_IMAGE:
                currentItem++;
                activity.mViewPager.setCurrentItem(currentItem);
                //准备下次播放
                break;
            case MSG_KEEP_SILENT:
                //只要不发送消息就暂停了
                break;
            case MSG_PAGE_CHANGED:
                //记录当前的页号，避免播放的时候页面显示不正确。
                currentItem = msg.arg1;
                break;
            case MSG_FIRST_UPDATE:
            	currentItem++;
                activity.mViewPager.setCurrentItem(currentItem);
                break;
            case MSG_REFRESH:
            	currentItem=0;
                activity.mViewPager.setCurrentItem(currentItem);
                activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;   
            default:
                break;
            } 
        }
    }
	
	class GetDataTask extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			jsonrequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						list.clear();
						length = response.length();
						for (int j = 0; j < length; j++) {
							JSONObject json = response.getJSONObject(j+"");
							Submit_info submit_info = new Submit_info();
							submit_info.setName(json.getString("name"));
							submit_info.setSubmit_request(json.getString("request"));
							submit_info.setSubmit_address(json.getString("address"));
							submit_info.setSubmit_tel(json.getString("tel"));
							submit_info.setReward(json.getString("reward"));
							submit_info.setSubmit_time(json.getString("submit_time"));
							submit_info.setDelivery_time(json.getString("delivery_time"));	
							list.add(submit_info);
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
			lv.onRefreshComplete();
		}
	}
}

