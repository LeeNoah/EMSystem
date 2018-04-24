package com.mambak.domain;

public class Course {
	private String c_no;
	private String c_name;
	private String c_tno;
	private double c_credit;
	private String c_type;
	private String c_period;

	public Course() {
		super();
	}


	public String getc_no() {
		return c_no;
	}

	public void setc_no(String c_no) {
		this.c_no = c_no;
	}

	public String getc_name() {
		return c_name;
	}

	public void setc_name(String c_name) {
		this.c_name = c_name;
	}


	public String getc_tno() {
		return c_tno;
	}

	public void setc_tno(String c_tno) {
		this.c_tno = c_tno;
	}

	public double getc_credit() {
		return c_credit;
	}

	public void setc_credit(double c_credit) {
		this.c_credit = c_credit;
	}

	public String getc_type() {
		return c_type;
	}

	public void setc_type(String c_type) {
		this.c_type = c_type;
	}

	public String getc_period() {
		return c_period;
	}

	public void setc_period(String c_period) {
		this.c_period = c_period;
	}

}
