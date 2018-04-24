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
	//ģ���еĹ����÷�����������
	
	//���ѧ������
	public void addStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ʹ��BeanUtils�����Զ�ӳ���װ
		// BeanUtils����ԭ����map�е����ݸ���keyֵ��ʵ������ԵĶ�Ӧ��ϵ��װ
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
			response.getWriter().write("�����Ϣ�ɹ���");
			response.sendRedirect("admin/operation/add_stu.jsp");
		}else{
			request.setAttribute("info", "�����Ϣʧ��");
			request.getRequestDispatcher("admin/operation/add_stu.jsp").forward(request, response);
		}
	}
	
	//ɾ�����ѧ������
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
	
	//ɾ������ѧ������
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
	
	//����Ա��¼����
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		if(manager==null){
			//failed
			request.setAttribute("Info", "�û������������");
			request.getRequestDispatcher("admin/login_admin.jsp").forward(request, response);
		}else{
			//successful
			session.setAttribute("manager", manager);
			response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
		}
	}
	
	//����ѧ��ҳ��
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
	
	//��ѯ����ѧ������
	public void allStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String currentPageStr = request.getParameter("currentPage");
		if(currentPageStr==null){
			currentPageStr="1";
		}
		//���õ�ǰҳ��
		int currentPage = Integer.parseInt(currentPageStr);
		//��Ϊÿҳ��ʾ10��
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
	
	//����ѧ����Ϣ
	public void updateStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Student student = new Student();
		int rows=0;
		try {
			
			BeanUtils.populate(student, request.getParameterMap());
			AdminStudentService service = new AdminStudentService();
			
			rows = service.update(student);
			
		
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		if(rows>0){
			response.sendRedirect(request.getContextPath()+"/adminStudent?method=allStudent");
		}else{
			request.setAttribute("ErrorInfo", "����ʧ��");
		}
	}
	
	
	//����������ѯѧ����Ϣ
	public void queryStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ��ȡ��������
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
			request.setAttribute("Info", "û�в�ѯ��ѧ����Ϣ��");
			request.getRequestDispatcher("admin/operation/query_stu.jsp").forward(request, response);
		} else {
			request.setAttribute("isAll", false);
			request.setAttribute("stu_list", stu_list);
			request.getRequestDispatcher("admin/operation/query_stu.jsp").forward(
					request, response);
		}
	}
	
	//��ȡ�����б�����
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
				request.setAttribute("Info", "û�в鵽�й���Ϣ��");
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
