package com.ssafy.happyhouse.model;

public class MemberDto {

	private String userName;
	private String userId;
	private String userPwd;
	private String address;
	private String phone;
	private String grade;
	private String comments;
	private String iscertify;

	public String getIscertify() {
		return iscertify;
	}

	public void setIscertify(String iscertify) {
		this.iscertify = iscertify;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	@Override
	public String toString() {
		return "MemberDto [userName=" + userName + ", userId=" + userId + ", userPwd=" + userPwd + ", address="
				+ address + ", phone=" + phone + ", grade=" + grade + ", comments=" + comments + "]";
	}

}
