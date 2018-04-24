package com.mambak.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.mambak.domain.DisplayStudent;
import com.mambak.domain.Manager;
import com.mambak.domain.Student;
import com.mambak.service.AdminStudentService;
import com.mambak.vo.PageBean;

@SuppressWarnings("serial")
public class AdminStudentServlet extends BaseServlet {
	//模块中的功能用方法进行区分
	
	//添加学生功能
	public void addStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 使用BeanUtils进行自动映射封装
		// BeanUtils工作原理：将map中的数据根据key值与实体的属性的对应关系封装
		Student student = new Student();
		int rows = 0;
		AdminStudentService service = new AdminStudentService();
		try {

			BeanUtils.populate(student, request.getParameterMap());
			
			rows = service.add(student);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(rows>0){
			//successful
			response.getWriter().write("添加信息成功！");
			response.sendRedirect("admin/operation/add_stu.jsp");
		}else{
			request.setAttribute("info", "添加信息失败");
			request.getRequestDispatcher("admin/operation/add_stu.jsp").forward(request, response);
		}
	}
	
	//删除多个学生功能
	public void delMulStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] s_noList = request.getParameterValues("s_no");

		AdminStudentService service = new AdminStudentService();

		try {

			service.delMulStudentByS_no(s_noList);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath()
				+ "/adminStudent?method=allStudent");
	}
	
	//删除单个学生功能
	public void delStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
String s_no = request.getParameter("s_no");
		
		AdminStudentService service = new AdminStudentService();
		try {
			service.delStudentByS_no(s_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath()+"/adminStudent?method=allStudent");
	}
	
	//管理员登录功能
	public void login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String m_id = request.getParameter("m_id");
		String m_password = request.getParameter("m_password");
		
		
		AdminStudentService service = new AdminStudentService();
		Manager manager=null;
		try {
			manager=service.adminlogin(m_id,m_password);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(manager==null){
			//failed
			request.setAttribute("Info", "用户名或密码错误");
			request.getRequestDispatcher("admin/login_admin.jsp").forward(request, response);
		}else{
			//successful
			session.setAttribute("manager", manager);
			response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
		}
	}
	
	//更新学生页面
	public void updateStudentUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String s_no = request.getParameter("s_no");
		String s_academyname = request.getParameter("s_academyName");
		String s_deptname = request.getParameter("s_deptName");
		
		
		String s_academyName=new String(s_academyname.getBytes("ISO8859-1"),"UTF-8"); 
		String s_deptName=new String(s_deptname.getBytes("ISO8859-1"),"UTF-8"); 
		
		AdminStudentService service = new AdminStudentService();
		Student student=null;
		try {
			student = service.getStudent(s_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(student!=null){
			request.setAttribute("student", student);
			request.setAttribute("s_academyName", s_academyName);
			request.setAttribute("s_deptName", s_deptName);
			request.getRequestDispatcher("admin/update/update_stu.jsp").forward(request, response);
		}else{
			
		}
	}
	
	//查询所有学生功能
	public void allStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String currentPageStr = request.getParameter("currentPage");
		if(currentPageStr==null){
			currentPageStr="1";
		}
		//设置当前页数
		int currentPage = Integer.parseInt(currentPageStr);
		//认为每页显示10条
		int currentCount = 10;
		AdminStudentService service = new AdminStudentService();
		PageBean<DisplayStudent> pageBean = null;
		try {
			pageBean = service.findPageBean(currentPage,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("isAll", true);
		request.setAttribute("pageBean", pageBean);
		request.getRequestDispatcher("admin/operation/query_stu.jsp").forward(request, response);
	}
	
	//更新学生信息
	public void updateStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Student student = new Student();
		int rows=0;
		try {
			
			BeanUtils.populate(student, request.getParameterMap());
			AdminStudentService service = new AdminStudentService();
			
			rows = service.update(student);
			
		
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		if(rows>0){
			response.sendRedirect(request.getContextPath()+"/adminStudent?method=allStudent");
		}else{
			request.setAttribute("ErrorInfo", "更新失败");
		}
	}
	
	
	//根据条件查询学生信息
	public void queryStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获取表单的内容
		String s_grade = request.getParameter("grade");
		String s_academy = request.getParameter("academy");
		String s_dept = request.getParameter("dept");
		String s_no = request.getParameter("s_no");
		
		AdminStudentService service = new AdminStudentService();
		List<DisplayStudent> stu_list = null;
		try {
			if (s_no == null) {

				stu_list = service.query(s_grade,s_academy, s_dept);

			} else {

				stu_list = service.queryS_no(s_no);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (stu_list == null) {
			request.setAttribute("Info", "没有查询到学生信息！");
			request.getRequestDispatcher("admin/operation/query_stu.jsp").forward(request, response);
		} else {
			request.setAttribute("isAll", false);
			request.setAttribute("stu_list", stu_list);
			request.getRequestDispatcher("admin/operation/query_stu.jsp").forward(
					request, response);
		}
	}
	
	//获取下拉列表内容
	public void selectList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String select = request.getParameter("select");
		String ac_no = request.getParameter("ac_no");
		
		AdminStudentService service = new AdminStudentService();

		List<String> List = null;

		StringBuffer sb = new StringBuffer();
		sb.append("{");

		try {
			List = service.getList(select,ac_no);

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
	
}
