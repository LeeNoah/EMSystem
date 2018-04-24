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
	
	//��ת��ӽ�ʦ����
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
	//��ѯ���н�ʦ��Ϣ��Ȼ����ת����ѯ��ʦҳ��
	public void queryTeacherUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AdminTeacherService service = new AdminTeacherService();
		
		//��ǰ̨��ȡ��ǰҳ��
		String currentPageStr = request.getParameter("currentPage");
		if(currentPageStr==null){
			currentPageStr="1";
		}
		//���õ�ǰҳ��
		int currentPage = Integer.parseInt(currentPageStr);
		//��Ϊÿҳ��ʾ10��
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
	
	//ajax��̬��ȡ�����б������
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
	
	//��ӽ�ʦ
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
			response.getWriter().write("�����Ϣ�ɹ���");
			response.sendRedirect(request.getContextPath()+"/adminTeacher?method=addTeacherUI");
		}else{
			request.setAttribute("info", "�����Ϣʧ��");
			request.getRequestDispatcher("admin/operation/add_tea.jsp").forward(request, response);
		}
	}
	
	//����������ѯ��ʦ
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
			request.setAttribute("Info", "û�в�ѯ���й���Ϣ");
			request.getRequestDispatcher("admin/operation/query_tea.jsp").forward(request, response);
		}
	}
	
	//ɾ��������ʦ
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
	
	//ɾ�������ʦ
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
	
	//��ת�����½�ʦ��Ϣ����
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
	
	//���½�ʦ��Ϣ
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
			request.setAttribute("ErrorInfo", "����ʧ��");
		}
		
	}
}
