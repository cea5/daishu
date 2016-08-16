package Utils;

import android.media.Image;

public class Shuo_info {
	private int id;
	private String name;
	private String school;
	private String content;
	private String submit_time;
	private int comment_number;
	private int visit_number;
	private Image head;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}
	public int getComment_number() {
		return comment_number;
	}
	public void setComment_number(int comment_number) {
		this.comment_number = comment_number;
	}
	public int getVisit_number() {
		return visit_number;
	}
	public void setVisit_number(int visit_number) {
		this.visit_number = visit_number;
	}
	public Image getHead() {
		return head;
	}
	public void setHead(Image head) {
		this.head = head;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
