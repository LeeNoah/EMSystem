package com.mambak.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.mambak.domain.Academy;
import com.mambak.domain.DisplayTeacher;
import com.mambak.domain.Teacher;
import com.mambak.service.AdminTeacherService;
import com.mambak.vo.PageBean;

@SuppressWarnings("serial")
public class AdminTeacherServlet extends BaseServlet {
	
	//跳转添加教师界面
	public void addTeacherUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AdminTeacherService service = new AdminTeacherService();
		List<Academy> ac_list = null;
		try {
			
			ac_list = service.getAcademyList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("ac_list", ac_list);
		request.getRequestDispatcher("admin/operation/add_tea.jsp").forward(request, response);
	}
	//查询所有教师信息，然后跳转到查询教师页面
	public void queryTeacherUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AdminTeacherService service = new AdminTeacherService();
		
		//从前台获取当前页数
		String currentPageStr = request.getParameter("currentPage");
		if(currentPageStr==null){
			currentPageStr="1";
		}
		//设置当前页数
		int currentPage = Integer.parseInt(currentPageStr);
		//认为每页显示10条
		int currentCount = 10;
		PageBean<DisplayTeacher> pageBean = null;
		
		try {
			pageBean = service.findPageBean(currentPage,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("isAll", true);
		
		request.getRequestDispatcher("admin/operation/query_tea.jsp").forward(request, response);
	}
	
	//ajax动态获取下拉列表的内容
	public void selectList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AdminTeacherService service = new AdminTeacherService();
		String select = request.getParameter("select");
		List<String> List = null;

		StringBuffer sb = new StringBuffer();
		sb.append("{");

		try {
			List = service.getList(select);

			if (List == null) {
				request.setAttribute("Info", "没有查到有关信息！");
			} else {
				for (int i = 0; i < List.size(); i++) {
					if (i != List.size() - 1) {
						sb.append("\"").append(select).append(i).append("\":\"")
								.append(List.get(i)).append("\",");
					} else {
						sb.append("\"").append(select).append(i).append("\":\"")
								.append(List.get(i)).append("\"}");
					}
				}
				response.getWriter().write(sb.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//添加教师
	public void addTeacher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Teacher teacher = new Teacher();
		int rows = 0;
		AdminTeacherService service = new AdminTeacherService();
		try {

			BeanUtils.populate(teacher, request.getParameterMap());
			
			rows = service.add(teacher);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(rows>0){
			//successful
			response.getWriter().write("添加信息成功！");
			response.sendRedirect(request.getContextPath()+"/adminTeacher?method=addTeacherUI");
		}else{
			request.setAttribute("info", "添加信息失败");
			request.getRequestDispatcher("admin/operation/add_tea.jsp").forward(request, response);
		}
	}
	
	//根据条件查询教师
	public void queryTeacher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		AdminTeacherService service = new AdminTeacherService();
		String t_no = request.getParameter("t_no");
		String t_entryyear = request.getParameter("t_entryyear");
		String t_acno = request.getParameter("t_acno");
		
		List<DisplayTeacher> tea_list = null;
		
		try {
			
			if(t_no!=null){
				tea_list = service.queryTeacherByT_no(t_no);
				
			}else{
				tea_list = service.queryTeacher(t_entryyear, t_acno);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(tea_list!=null){
			request.setAttribute("tea_list", tea_list);
			request.setAttribute("isAll", false);
			request.getRequestDispatcher("admin/operation/query_tea.jsp").forward(request, response);
			
		}else{
			request.setAttribute("Info", "没有查询到有关信息");
			request.getRequestDispatcher("admin/operation/query_tea.jsp").forward(request, response);
		}
	}
	
	//删除单个教师
	public void delTeacher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		AdminTeacherService service = new AdminTeacherService();
		String t_no = request.getParameter("t_no");
		try {
			service.delTeacherByT_no(t_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/adminTeacher?method=queryTeacherUI");
	}
	
	//删除多个教师
	public void delMulTeacher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		AdminTeacherService service = new AdminTeacherService();
		String[] t_noList = request.getParameterValues("t_no");
		
		try {
			service.delMulTeacher(t_noList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/adminTeacher?method=queryTeacherUI");
	}
	
	//跳转到更新教师信息界面
	public void updateTeacherUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		AdminTeacherService service = new AdminTeacherService();
		
		String t_no = request.getParameter("t_no");
		DisplayTeacher teacher = null;
		try {
			teacher = service.queryDsTeacherByT_no(t_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("teacher", teacher);
		request.getRequestDispatcher("admin/update/update_tea.jsp").forward(request, response);
	}
	
	//更新教师信息
	public void updateTeacher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		AdminTeacherService service = new AdminTeacherService();
		Teacher teacher = new Teacher();
		int rows=0;
		try {
			BeanUtils.populate(teacher, request.getParameterMap());
			rows = service.update(teacher);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(rows>0){
			response.sendRedirect(request.getContextPath()+"/adminTeacher?method=queryTeacherUI");
		}else{
			request.setAttribute("ErrorInfo", "更新失败");
		}
		
	}
}
