package Utils;

import android.media.Image;

public class Submit_info {
	
	private String name;
	private String school;
	private Image head;
	private String submit_time;
	private String submit_address;
	private String submit_tel;
	private String delivery_time;
	private String submit_request;
	private int state;
	private String reward;
	private int Request_Submit = 0;
	private int Request_Taken = 1;
	private int Request_Done = 2;
	private int Request_Invalid = 3;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public Image getHead() {
		return head;
	}
	public void setHead(Image head) {
		this.head = head;
	}
	public String getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}
	public String getSubmit_request() {
		return submit_request;
	}
	public void setSubmit_request(String submit_request) {
		this.submit_request = submit_request;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getSubmit_address() {
		return submit_address;
	}
	public void setSubmit_address(String submit_address) {
		this.submit_address = submit_address;
	}
	public String getSubmit_tel() {
		return submit_tel;
	}
	public void setSubmit_tel(String submit_tel) {
		this.submit_tel = submit_tel;
	}
	public String getDelivery_time() {
		return delivery_time;
	}
	public void setDelivery_time(String delivery_time) {
		this.delivery_time = delivery_time;
	}
	
}
