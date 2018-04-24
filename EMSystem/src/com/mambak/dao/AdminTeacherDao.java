package com.mambak.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mambak.domain.Academy;
import com.mambak.domain.Teacher;
import com.mambak.utils.DataSourceUtils;

public class AdminTeacherDao {

	public int add(Teacher teacher) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into tb_teacher values(?,?,?,?,?)";
		return runner.update(sql,teacher.getT_no(),teacher.getT_name(),teacher.getT_entryyear(),teacher.getT_password(),teacher.getT_acno());
	}

	public List<Academy> getAcademyList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_academy";
		return runner.query(sql, new BeanListHandler<Academy>(Academy.class));
	}

	public List<Object[]> getEntryYear() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select distinct t_entryyear from tb_teacher";
		return runner.query(sql, new ArrayListHandler());
	}

	public int getTotalCount() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from tb_teacher";
		Long res = (Long) runner.query(sql,new ScalarHandler());
		return res.intValue();
	}

	public List<Teacher> findTeacherListForPageBean(int index, int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_teacher order by t_acno,t_no asc limit ?,?";
		return runner.query(sql, new BeanListHandler<Teacher>(Teacher.class),index,currentCount);
	}

	public String getAc_nameByAc_no(String t_acno) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select ac_name from tb_academy where ac_no=?";
		return (String) runner.query(sql, new ScalarHandler(),t_acno);
	}

	public Teacher queryTeacherByT_no(String t_no) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_teacher where t_no=?";
		return runner.query(sql, new BeanHandler<Teacher>(Teacher.class),t_no);
	}

	public List<Teacher> queryTeacherByTwo(String t_entryyear, String t_acno) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_teacher where t_entryyear=? and t_acno=?";
		return runner.query(sql, new BeanListHandler<Teacher>(Teacher.class),t_entryyear,t_acno);
	}

	public List<Teacher> queryTeacherByT_acno(String t_acno) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_teacher where t_acno=?";
		return runner.query(sql, new BeanListHandler<Teacher>(Teacher.class),t_acno);
	}

	public List<Teacher> queryTeacherByT_entryyear(String t_entryyear) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tb_teacher where t_entryyear=?";
		return runner.query(sql, new BeanListHandler<Teacher>(Teacher.class),t_entryyear);
	}

	public int delTeacherByT_no(String t_no) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from tb_teacher where t_no=?";
		return runner.update(sql, t_no);
	}

	public void delTeacher(String paramStr) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from tb_teacher where t_no in ("+paramStr+")";
		runner.update(sql);
	}

	public int update(Teacher teacher) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update tb_teacher set t_name=?,t_acno=?,t_password=? where t_no=?";
		return runner.update(sql, teacher.getT_name(),teacher.getT_acno(),teacher.getT_password(),teacher.getT_no());
	}

}
