package com.stone.dao;

import java.util.HashMap;
import java.util.Map;

import com.stone.dao.impl.CustomerDAOImpl;

public class CustomerDAOFactory {
	private static CustomerDAOFactory instance = new CustomerDAOFactory();
	private static String type = null;
	private Map<String, CustomerDAO> daos = new HashMap<>();
	private CustomerDAOFactory() {
		daos.put("jdbc", new CustomerDAOImpl());
	}
	public static CustomerDAOFactory getInstance() {
		return instance;
	}
	public void setType(String type) {
		this.type = type;
	}
	public CustomerDAO getCustomerDAO() {
		return daos.get(type);
	}
}
