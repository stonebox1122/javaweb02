package com.stone.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.stone.dao.CustomerDAOFactory;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		CustomerDAOFactory.getInstance().setType("jdbc");
		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/classes/switch.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
			String type = properties.getProperty("type");
			CustomerDAOFactory.getInstance().setType(type);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}