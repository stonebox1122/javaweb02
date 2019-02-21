package com.stone.dao;

import java.util.List;

import com.stone.bean.Customer;

//根据具体业务定义方法 
public interface CustomerDAO {
	public List<Customer> getAll();
	
	public void save(Customer customer);
	
	public Customer get(Integer id);
	
	public void delete(Integer id);
	
	//返回name对应的记录数
	public long getCountWithName(String name);
	
	/**
	 * 返回满足查询条件的List
	 * @param cc：封装了查询条件
	 * @return
	 */
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc);
	
	public void update(Customer customer);
}