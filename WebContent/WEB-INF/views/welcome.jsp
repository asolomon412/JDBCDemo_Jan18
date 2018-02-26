<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
</head>
<body>
	Hi!
	<h1>This is the list before we use the forEach</h1>
	${message}
	
	<h3>Update Customer</h3>
	<form action="update" method="post">
	CustomerID: <input type="text" name="custID"> <br>
	Company Name: <input type="text" name="compName"> <br>
	ContactName: <input type="text" name="contName"> <br>
	<input type="submit" value="Update">
	</form>
	<h1>This is the list after we use the forEach</h1>
	<table border="1">
		<c:forEach items="${message}" var="list">
			<tr>
				<td><c:out value="${list}"></c:out></td>
			</tr>

		</c:forEach>

	</table>
</body>
</html>