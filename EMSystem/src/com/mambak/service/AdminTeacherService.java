package com.mambak.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mambak.dao.AdminTeacherDao;
import com.mambak.domain.Academy;
import com.mambak.domain.DisplayTeacher;
import com.mambak.domain.Teacher;
import com.mambak.vo.PageBean;

public class AdminTeacherService {

	public int add(Teacher teacher) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		return dao.add(teacher);
	}


	public List<String> getEntryYearList() throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		List<String> res = new ArrayList<String>();
		List<Object[]> list = dao.getEntryYear();
		for (Object[] obj : list) {
			for (Object object : obj) {
				String s = String.valueOf(object);
				res.add(s);
			}
		}
		return res;
	}

	public List<String> getAcademyListStr() throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		List<String> res = new ArrayList<String>();
		List<Academy> list = dao.getAcademyList();
		for (Academy academy : list) {
			StringBuffer sb = new StringBuffer();
			sb.append(academy.getAc_no()).append("_").append(academy.getAc_name());
			res.add(sb.toString());
		}
		return res;
	}


	public PageBean<DisplayTeacher> findPageBean(int currentPage,
			int currentCount) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();

		PageBean<DisplayTeacher> pageBean = new PageBean<DisplayTeacher>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		int totalCount = dao.getTotalCount();
		pageBean.setTotalCount(totalCount);
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);
		// 5、每页显示的数据private List<T> displayList = new ArrayList<T>();
		int index = (currentPage - 1) * currentCount;
		List<Teacher> list = dao
				.findTeacherListForPageBean(index, currentCount);
		List<DisplayTeacher> tea_list = new ArrayList<DisplayTeacher>();
		for (Teacher teacher : list) {
			DisplayTeacher ds = new DisplayTeacher();
			ds.setTeacher(teacher);
			ds.setAc_name(dao.getAc_nameByAc_no(teacher.getT_acno()));
			tea_list.add(ds);
		}
		pageBean.setDisplayList(tea_list);
		return pageBean;
	}


	public List<DisplayTeacher> queryTeacherByT_no(String t_no) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		List<DisplayTeacher> res = null;
		Teacher teacher = dao.queryTeacherByT_no(t_no);
		if(teacher!=null){
			res = new ArrayList<DisplayTeacher>();
			DisplayTeacher ds = new DisplayTeacher();
			ds.setTeacher(teacher);
			ds.setAc_name(dao.getAc_nameByAc_no(teacher.getT_acno()));
			res.add(ds);
		}
		return res;
	}

	//根据条件查询教师信息
	public List<DisplayTeacher> queryTeacher(String t_entryyear, String t_acno) throws SQLException {
		//两个条件可选可不选
		if(t_entryyear!=null && t_acno!=null){
			return queryTeacherByTwo(t_entryyear,t_acno);
		}else{
			if(t_entryyear==null && t_acno!=null){
				return queryTeacherByT_acno(t_acno);
			}else{
				return queryTeacherByT_entryyear(t_entryyear);
			}
		}
	}


	private List<DisplayTeacher> queryTeacherByT_entryyear(String t_entryyear) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		List<DisplayTeacher> res = new ArrayList<DisplayTeacher>();
		List<Teacher> list = new ArrayList<Teacher>();
		list = dao.queryTeacherByT_entryyear(t_entryyear);
		for (Teacher teacher : list) {
			DisplayTeacher ds = new DisplayTeacher();
			ds.setTeacher(teacher);
			ds.setAc_name(dao.getAc_nameByAc_no(teacher.getT_acno()));
			res.add(ds);
		}
		return res;
	}


	private List<DisplayTeacher> queryTeacherByT_acno(String t_acno) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		List<DisplayTeacher> res = new ArrayList<DisplayTeacher>();
		List<Teacher> list = new ArrayList<Teacher>();
		list = dao.queryTeacherByT_acno(t_acno);
		for (Teacher teacher : list) {
			DisplayTeacher ds = new DisplayTeacher();
			ds.setTeacher(teacher);
			ds.setAc_name(dao.getAc_nameByAc_no(teacher.getT_acno()));
			res.add(ds);
		}
		return res;
	}


	private List<DisplayTeacher> queryTeacherByTwo(String t_entryyear,
			String t_acno) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		List<DisplayTeacher> res = new ArrayList<DisplayTeacher>();
		List<Teacher> list = new ArrayList<Teacher>();
		list = dao.queryTeacherByTwo(t_entryyear, t_acno);
		for (Teacher teacher : list) {
			DisplayTeacher ds = new DisplayTeacher();
			ds.setTeacher(teacher);
			ds.setAc_name(dao.getAc_nameByAc_no(teacher.getT_acno()));
			res.add(ds);
		}
		return res;
	}


	public java.util.List<String> getList(String select) throws SQLException {
		if("academy".equals(select)){
			return getAcademyListStr();
		}else{
			return getEntryYearList();
		}
	}


	public List<Academy> getAcademyList() throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		List<Academy> list = dao.getAcademyList();
		return list;
	}


	public void delTeacherByT_no(String t_no) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		dao.delTeacherByT_no(t_no);
	}


	public void delMulTeacher(String[] t_noList) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		StringBuffer params = new StringBuffer();
		for (String string : t_noList) {
			String str = string.substring(1, string.length() - 2);
			params.append("'").append(str).append("',");
		}
		String paramStr = params.substring(0, params.length() - 1);
		dao.delTeacher(paramStr);
	}


	public DisplayTeacher queryDsTeacherByT_no(String t_no) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		DisplayTeacher ds = new DisplayTeacher();
		Teacher teacher = dao.queryTeacherByT_no(t_no);
		ds.setTeacher(teacher);
		ds.setAc_name(dao.getAc_nameByAc_no(teacher.getT_acno()));
		return ds;
	}


	public int update(Teacher teacher) throws SQLException {
		AdminTeacherDao dao = new AdminTeacherDao();
		return dao.update(teacher);
	}

}
