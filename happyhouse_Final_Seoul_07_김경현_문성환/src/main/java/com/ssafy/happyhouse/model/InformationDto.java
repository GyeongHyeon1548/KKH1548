package com.ssafy.happyhouse.model;

public class InformationDto {

	String num;
	String subject;
	String content;
	String regtime;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "InformationDto [num=" + num + ", subject=" + subject + ", contents=" + content + ", regtime=" + regtime
				+ "]";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String contents) {
		this.content = contents;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regdate) {
		this.regtime = regdate;
	}

}
