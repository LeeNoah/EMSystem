package com.mambak.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mambak.domain.Academy;
import com.mambak.domain.Dept;
import com.mambak.domain.Manager;
import com.mambak.domain.Student;
import com.mambak.utils.DataSourceUtils;

public class AdminStudentDao {

	public int add(Student stu) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into tb_student values(?,?,?,?,?)";
		return runner.update(sql, stu.gets_no(), stu.gets_name(),
				stu.gets_password(), stu.gets_grade(), stu.gets_dept());
	}

	public Manager adminLogin(String m_id, String m_password)
			throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_manager where m_id=? and m_password=?";
		return runner.query(sql, new BeanHandler<Manager>(Manager.class), m_id,
				m_password);
	}

	public List<Student> query(String s_grade, String s_dept)
			throws SQLException {
		// 由专业加年级进行查询
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_student where s_grade=? and s_dept=?";
		return runner.query(sql, new BeanListHandler<Student>(Student.class),
				s_grade, s_dept);

	}

	public Student queryS_no(String s_no) throws SQLException {
		// 用学号进行查询
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_student where s_no=?";
		return (Student) runner.query(sql, new BeanHandler<Student>(
				Student.class), s_no);

	}

	public String queryAcademyName(String ac_no) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select ac_name from tb_academy where ac_no=?";
		return (String) runner.query(sql, new ScalarHandler(), ac_no);
	}

	public String queryDeptName(String s_dept) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select d_name from tb_dept where d_no=?";
		return (String) runner.query(sql, new ScalarHandler(), s_dept);
	}

	public List<Object[]> getGrade() throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select distinct s_grade from tb_student order by s_grade desc";
		List<Object[]> res = runner.query(sql, new ArrayListHandler());
		return res;
	}

	public List<Object[]> getDept() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select distinct s_dept from tb_student order by s_grade desc";
		List<Object[]> res = runner.query(sql, new ArrayListHandler());
		return res;
	}

	public List<Academy> getAcademy() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_academy";
		return runner.query(sql, new BeanListHandler<Academy>(Academy.class));

	}

	public List<Dept> getDeptByac_no(String ac_no) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_dept where d_acno=?";
		return runner.query(sql, new BeanListHandler<Dept>(Dept.class), ac_no);
	}

	public int update(Student student) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update tb_student set s_name=?,s_password=?,s_dept=? where s_no=?";
		return runner.update(sql, student.gets_name(), student.gets_password(),
				student.gets_dept(), student.gets_no());
	}

	public List<Student> findAllStudent() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_student";
		return runner.query(sql, new BeanListHandler<Student>(Student.class));
	}

	public String queryAc_no(String s_dept) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select d_acno from tb_dept where d_no=?";
		return (String) runner.query(sql, new ScalarHandler(),s_dept);
	}

	public int getTotalCount() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from tb_student";
		Long res = (Long)runner.query(sql, new ScalarHandler());
		return res.intValue();
	}

	public List<Student> findStudentListForPageBean(int index,int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_student order by s_dept,s_no asc limit ?,?";
		return runner.query(sql, new BeanListHandler<Student>(Student.class),index,currentCount);
		
	}

	public void delStudent(String s_no) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from tb_student where s_no=?";
		runner.update(sql, s_no);
	}

	public void delMulStudent(String paramStr) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from tb_student where s_no in ("+paramStr+")";
		runner.update(sql);
	}
}
