package com.stone.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stone.bean.Customer;
import com.stone.dao.CriteriaCustomer;
import com.stone.dao.CustomerDAO;
import com.stone.dao.CustomerDAOFactory;
import com.stone.dao.impl.CustomerDAOImpl;

public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO = CustomerDAOFactory.getInstance().getCustomerDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	// protected void doPost(HttpServletRequest request, HttpServletResponse
	// response) throws ServletException, IOException {
	// String method = request.getParameter("method");
	// switch (method) {
	// case "insert": insert(request,response); break;
	// case "select": select(request,response); break;
	// case "delete": delete(request,response); break;
	// default:
	// break;
	// }
	// }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1.获取serletPath并去掉第一个字符"/"
		String servletPath = req.getServletPath().substring(1);
		// 2.去掉".do"后缀，得到请求的方法名
		String methodName = servletPath.substring(0, servletPath.length() - 3);
		try {
			// 3.利用反射创建对应的方法
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			// 4.利用反射调用对应的方法
			method.invoke(this, req, resp);
		} catch (Exception e) {
			// 若调用的方法不存在，响应一个error.jsp页面
			resp.sendRedirect("error.jsp");
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = 0;

		// try...catch的作用：防止idStr不能转为int类型，若不能转，则id=0，无法进行任何删除操作
		try {
			id = Integer.parseInt(idStr);
			customerDAO.delete(id);
		} catch (Exception e) {
		}
		response.sendRedirect("select.do");
	}

	private void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取模糊查询的请求参数
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		// 将请求参数封装为一个CriteriaCustomer对象
		CriteriaCustomer cc = new CriteriaCustomer(name, address, phone);

		// 1.调用CustomerDao的getForListWithCriteriaCustomer(cc)方法得到Customer的集合
		List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(cc);

		// 2.把Customers的集合放入request中
		request.setAttribute("customers", customers);

		// 3.转发页面到index.jsp（不能使用重定向）
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.获取表单参数：name，address，phone
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");

		// 1.1 检查name是否已经存在，调用CustomerDao的getCountWithName(String name)方法
		if (customerDAO.getCountWithName(name) > 0) {
			// 1.2 若存在，则通过转发的方式响应newcustomer.jsp页面：
			// 1.2.1 要求在newcustomer.jsp页面显示一个错误信息：用户名name已经被使用，请重新输入
			// 在request中放入一个属性message：用户名name已经被使用，请重新输入
			// 在页面上通过 request.getAttribute("message")的方式来显示
			request.setAttribute("message", "用户名" + name + "已经被使用，请重新输入");
			request.getRequestDispatcher("/newcustomer.jsp").forward(request, response);

			// 1.2.2 newcustomer.jsp表单值可以回显，通过value="<%= request.getParameter("name")
			// %>来进行回显
			// 1.2.3 结束方法 return
			return;
		}

		// 2.把表单参数封装为一个Customer对象
		Customer customer = new Customer(name, address, phone);

		// 3.调用CustomerDao的save(Customer customer)方法执行保存操作
		customerDAO.save(customer);

		// 4.重定向到success.jsp页面，使用重定向可以避免出现重新提交问题
		response.sendRedirect("success.jsp");
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.获取表单参数：id,name,address,phone,oldName
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String oldName = request.getParameter("oldName");

		// 2.验证name是否已经被占用
		// 2.1 比较name和oldName是否相同，若相同说明name不做修改，而是修改其他字段，若不同，饿说明会修改name
		if (!oldName.equals(name)) {
			// 2.2 若不相同，则调用CustomerDAO的getCountWithName(String name)查看name在数据库中是否存在
			if (customerDAO.getCountWithName(name) > 0) {
				// 2.3 若返回值大于0，则通过转发响应updatecustomer.jsp
				// 2.3.1 要求在updatecustomer.jsp页面显示一个错误信息：用户名name已经被使用，请重新输入
				// 在request中放入一个属性message：用户名name已经被使用，请重新输入
				// 在页面上通过 request.getAttribute("message")的方式来显示
				request.setAttribute("message", "用户名" + name + "已经被使用，请重新输入");
				// 2.3.2 updatecustomer.jsp表单值可以回显，
				// address，phone显示提交表单的新的值，而name显示oldName，而不是新提交的name
				// 2.3.3 结束方法 return
				request.getRequestDispatcher("/updatecustomer.jsp").forward(request, response);
				return;
			}
		}

		// 3.若验证通过，把表单参数封装为一个Customer对象
		Customer customer = new Customer(Integer.parseInt(id), name, address, phone);

		// 4.调用CustomerDao的update(Customer customer)方法执行更新操作
		customerDAO.update(customer);

		// 5.重定向到select.jsp页面，使用重定向可以避免出现重新提交问题
		response.sendRedirect("select.do");
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPath = "/error.jsp";
		// 1.获取请求参数id
		String idStr = request.getParameter("id");

		// 2.调用CustomerDAO的customerDAO。get(id)获取id对应的Customer对象
		try {
			Customer customer = customerDAO.get(Integer.parseInt(idStr));
			if (customer != null) {
				forwardPath = "/updatecustomer.jsp";
				// 3.将customer放入request中
				request.setAttribute("customer", customer);
			}
		} catch (Exception e) {
		}

		// 4.通过转发响应updatecustomer.jsp页面
		request.getRequestDispatcher(forwardPath).forward(request, response);
	}
}