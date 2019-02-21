package com.stone.dao.impl;

import java.util.List;

import com.stone.bean.Customer;
import com.stone.dao.CriteriaCustomer;
import com.stone.dao.CustomerDAO;
import com.stone.dao.DAO;

public class CustomerDAOImpl extends DAO<Customer> implements CustomerDAO {

	@Override
	public List<Customer> getAll() {
		String sql = "select id,name,address,phone from customers";
		return getForList(sql);
	}

	@Override
	public void save(Customer customer) {
		String sql = "insert into customers(name,address,phone) values(?,?,?)";
		update(sql, customer.getName(), customer.getAddress(), customer.getPhone());
		
	}

	@Override
	public Customer get(Integer id) {
		String sql = "select id,name,address,phone from customers where id=?";
		return get(sql, id);
	}

	@Override
	public void delete(Integer id) {
		String sql = "delete from customers where id=?";
		update(sql, id);		
		
	}

	@Override
	public long getCountWithName(String name) {
		String sql = "select count(id) from customers where name=?";
		return getForValue(sql, name);
	}

	@Override
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc) {
		String sql = "select id,name,address,phone from customers where name like ? and address like ? and phone like ?";
		//修改了CriteriaCustomer的getter方法：使其返回的字符串中有"%%".
		return getForList(sql, cc.getName(), cc.getAddress(), cc.getPhone());
	}

	@Override
	public void update(Customer customer) {
		String sql = "update customers set name=?,address=?,phone=? where id=?";
		update(sql, customer.getName(), customer.getAddress(), customer.getPhone(), customer.getId());
	}
}