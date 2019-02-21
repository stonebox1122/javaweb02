package com.stone.test;

import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.stone.bean.Customer;
import com.stone.dao.CriteriaCustomer;
import com.stone.dao.CustomerDAO;
import com.stone.dao.impl.CustomerDAOImpl;

public class CustomerDAOImplTest {
	
	private CustomerDAO customerDAO = new CustomerDAOImpl();
	
	@Test
	public void testGetForListWithCriteriaCustomer() {
		CriteriaCustomer cc = new CriteriaCustomer("t", null, null);
		List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(cc);
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}

	@Test
	public void testGetAll() {
		List<Customer> all = customerDAO.getAll();
		for (Customer customer : all) {
			System.out.println(customer);
		}
	}

	@Test
	public void testSave() {
		Customer customer = new Customer();
		customer.setName("stone");
		customer.setAddress("stone");
		customer.setPhone("13233332222");
		customerDAO.save(customer);
	}

	@Test
	public void testGetInteger() {
		Customer customer = customerDAO.get(3);
		System.out.println(customer);
	}

	@Test
	public void testDelete() {
		customerDAO.delete(3);
	}

	@Test
	public void testGetCountWithName() {
		long result = customerDAO.getCountWithName("stone");
		System.out.println(result);
	}
}