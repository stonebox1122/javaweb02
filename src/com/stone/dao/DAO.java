package com.stone.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.stone.utils.JdbcUtils;

/**
 * 封装了基本的CRUD方法，以供子类继承使用 当前DAO直接在方法中获取数据库连接 整个DAO采取DBUtils解决方案
 * 
 * @param <T>：当前DAO处理的实体类的类型
 */
public class DAO<T> {
	private QueryRunner queryRunner = new QueryRunner();
	private Class<T> clazz;

	public DAO() {
		Type superclass = getClass().getGenericSuperclass();
		if (superclass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) superclass;
			Type[] typeArguments = parameterizedType.getActualTypeArguments();
			if (typeArguments != null && typeArguments.length > 0) {
				if (typeArguments[0] instanceof Class) {
					clazz = (Class<T>) typeArguments[0];
				}
			}
		}
	}

	/**
	 * 返回某一个字段的值，例如返回某一条记录的customerName，或者返回数据库表中有多少条记录等
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public <E> E getForValue(String sql, Object... args) {
		Connection connection = null;

		try {
			connection = JdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new ScalarHandler<>(), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}
		
		return null;
	}

	/**
	 * 返回T对应的List
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<T> getForList(String sql, Object... args) {
		Connection connection = null;

		try {
			connection = JdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}
		
		return null;
	}

	/**
	 * 返回对应的T的一个实体类对象
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(String sql, Object... args) {
		Connection connection = null;

		try {
			connection = JdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}
		
		return null;
	}

	/**
	 * 该方法封装了insert，delete，update操作
	 * 
	 * @param sql：SQL语句
	 * @param args：填充SQL语句的占位符
	 */
	public void update(String sql, Object... args) {
		Connection connection = null;

		try {
			connection = JdbcUtils.getConnection();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}
	}
}