<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>
<body>
	<%
		request.getRequestDispatcher("page/loginPage.do").forward(request, response);
	%>
</body>
</html>