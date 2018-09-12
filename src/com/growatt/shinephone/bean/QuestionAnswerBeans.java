package com.growatt.shinephone.bean;

public class QuestionAnswerBeans {
	private String id;
	private String questionId;
	private String userId;
	private String message;
	private String time;
	private String imageName;
	private String userName;
	public QuestionAnswerBeans(String id, String questionId, String userId,
			String message, String time, String imageName, String userName) {
		super();
		this.id = id;
		this.questionId = questionId;
		this.userId = userId;
		this.message = message;
		this.time = time;
		this.imageName = imageName;
		this.userName = userName;
	}
	public QuestionAnswerBeans() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
