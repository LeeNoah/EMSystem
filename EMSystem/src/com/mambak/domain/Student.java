package com.mambak.domain;

public class Student {
	private String s_no;
	private String s_name;
	private String s_password;
	private String s_grade;
	private String s_dept;

	public Student() {
		super();
	}

	public String gets_no() {
		return s_no;
	}

	public void sets_no(String s_no) {
		this.s_no = s_no;
	}

	public String gets_name() {
		return s_name;
	}

	public void sets_name(String s_name) {
		this.s_name = s_name;
	}

	public String gets_password() {
		return s_password;
	}

	public void sets_password(String s_password) {
		this.s_password = s_password;
	}

	public String gets_grade() {
		return s_grade;
	}

	public void sets_grade(String s_grade) {
		this.s_grade = s_grade;
	}

	public String gets_dept() {
		return s_dept;
	}

	public void sets_dept(String s_dept) {
		this.s_dept = s_dept;
	}
}
