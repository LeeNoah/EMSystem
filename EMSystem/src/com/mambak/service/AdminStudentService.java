package com.mambak.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mambak.dao.AdminStudentDao;
import com.mambak.domain.Academy;
import com.mambak.domain.Dept;
import com.mambak.domain.DisplayStudent;
import com.mambak.domain.Manager;
import com.mambak.domain.Student;
import com.mambak.vo.PageBean;

public class AdminStudentService {

	public int add(Student student) throws SQLException {

		AdminStudentDao dao = new AdminStudentDao();
		return dao.add(student);

	}

	public Manager adminlogin(String m_id, String m_password)
			throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		return dao.adminLogin(m_id, m_password);
	}

	public List<DisplayStudent> query(String s_grade, String s_academy,
			String s_dept) throws SQLException {
		List<Student> list = null;
		List<DisplayStudent> stu_list = new ArrayList<DisplayStudent>();
		AdminStudentDao dao = new AdminStudentDao();
		list = dao.query(s_grade, s_dept);
		for (Student student : list) {
			DisplayStudent ds = new DisplayStudent();
			ds.setStudent(student);
			ds.setAc_name(queryAcademyName(s_academy));
			ds.setD_name(queryDeptName(s_dept));
			stu_list.add(ds);
		}

		return stu_list;
	}

	public String queryDeptName(String s_dept) throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		return dao.queryDeptName(s_dept);
	}

	public String queryAcademyName(String ac_no) throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		return dao.queryAcademyName(ac_no);
	}

	public List<DisplayStudent> queryS_no(String s_no) throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		List<DisplayStudent> stu_list = new ArrayList<DisplayStudent>();
		Student student = dao.queryS_no(s_no);
		DisplayStudent ds = new DisplayStudent();
		if (student != null) {
			ds.setStudent(student);
			ds.setD_name(dao.queryDeptName(student.gets_dept()));
			String ac_no = dao.queryAc_no(student.gets_dept());
			ds.setAc_name(dao.queryAcademyName(ac_no));
			stu_list.add(ds);
		}
		return stu_list;
	}

	public Student getStudent(String s_no) throws SQLException {

		AdminStudentDao dao = new AdminStudentDao();
		return dao.queryS_no(s_no);
	}

	public java.util.List<String> getList(String select, String ac_no)
			throws SQLException {
		if ("grade".equals(select)) {
			return getGrade();
		} else if ("academy".equals(select)) {
			return getAcademy();
		} else {// 查询系别
			if (ac_no == null) {// 查询学生页面的功能
				return getDept();
			} else {// 添加学生页面的功能
				return getDeptByac_no(ac_no);
			}
		}
	}

	private List<String> getDeptByac_no(String ac_no) throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		List<String> res = new ArrayList<String>();
		List<Dept> list = dao.getDeptByac_no(ac_no);
		for (Dept dept : list) {
			String str = dept.getD_no() + "_" + dept.getD_name();
			res.add(str);
		}
		return res;
	}

	private List<String> getAcademy() throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		List<String> res = new ArrayList<String>();
		List<Academy> list = dao.getAcademy();
		for (Academy academy : list) {
			String str = academy.getAc_no() + "_" + academy.getAc_name();
			res.add(str);
		}

		return res;
	}

	public List<String> getGrade() throws SQLException {

		AdminStudentDao dao = new AdminStudentDao();
		List<String> res = new ArrayList<String>();
		List<Object[]> list = dao.getGrade();
		for (Object[] obj : list) {
			for (Object object : obj) {
				String s = String.valueOf(object);
				res.add(s);
			}
		}

		return res;
	}

	public List<String> getDept() throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		List<String> res = new ArrayList<String>();
		List<Object[]> list = dao.getDept();
		for (Object[] obj : list) {
			for (Object object : obj) {
				String s = String.valueOf(object);
				res.add(s);
			}
		}

		return res;
	}

	public int update(Student student) throws SQLException {

		AdminStudentDao dao = new AdminStudentDao();
		return dao.update(student);
	}

	// 查询所有学生
	public List<DisplayStudent> findAllStudent() throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		List<DisplayStudent> stu_list = new ArrayList<DisplayStudent>();
		List<Student> list = dao.findAllStudent();
		for (Student student : list) {
			DisplayStudent ds = new DisplayStudent();
			ds.setStudent(student);
			ds.setD_name(dao.queryDeptName(student.gets_dept()));
			String ac_no = dao.queryAc_no(student.gets_dept());
			ds.setAc_name(dao.queryAcademyName(ac_no));
			stu_list.add(ds);
		}
		return stu_list;
	}

	// 分页操作
	public PageBean<DisplayStudent> findPageBean(int currentPage,
			int currentCount) throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();

		// 目的：封装一个PageBean并返回
		PageBean<DisplayStudent> pageBean = new PageBean<DisplayStudent>();
		// 1、当前页private int currentPage;
		pageBean.setCurrentPage(currentPage);
		// 2、当前页显示的条数private int currentCount;
		pageBean.setCurrentCount(currentCount);
		// 3、总条数private int totalCount;
		int totalCount = dao.getTotalCount();
		pageBean.setTotalCount(totalCount);
		// 4、总页数private int totalPage;
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);
		// 5、每页显示的数据private List<T> displayList = new ArrayList<T>();
		int index = (currentPage - 1) * currentCount;
		List<Student> list = dao
				.findStudentListForPageBean(index, currentCount);
		List<DisplayStudent> stu_list = new ArrayList<DisplayStudent>();
		for (Student student : list) {
			DisplayStudent ds = new DisplayStudent();
			ds.setStudent(student);
			ds.setD_name(dao.queryDeptName(student.gets_dept()));
			String ac_no = dao.queryAc_no(student.gets_dept());
			ds.setAc_name(dao.queryAcademyName(ac_no));
			stu_list.add(ds);
		}
		pageBean.setDisplayList(stu_list);
		return pageBean;
	}

	// 根据学号删除条目
	public void delStudentByS_no(String s_no) throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		dao.delStudent(s_no);
	}

	public void delMulStudentByS_no(String[] s_noList) throws SQLException {
		AdminStudentDao dao = new AdminStudentDao();
		StringBuffer params = new StringBuffer();
		for (String string : s_noList) {
			String str = string.substring(1, string.length() - 2);
			params.append("'").append(str).append("',");
		}
		String paramStr = params.substring(0, params.length() - 1);
		dao.delMulStudent(paramStr);
	}
}
