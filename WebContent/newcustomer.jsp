<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4><font color="red"><%= request.getAttribute("message") == null ? "" : request.getAttribute("message") %></font></h4>
	<form action="insert.do" method="post">
		<table>
			<tr>
				<td>CustomerName:</td>
				<td><input type="text" name="name" value="<%= request.getParameter("name") == null ? "" : request.getParameter("name") %>"></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input type="text" name="address" value="<%= request.getParameter("address") == null ? "" : request.getParameter("address") %>"></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><input type="text" name="phone" value="<%= request.getParameter("phone") == null ? "" : request.getParameter("phone") %>"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Insert"></td>
			</tr>
		</table>
	</form>
</body>
</html>