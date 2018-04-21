<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>用户登录</title>
</head>
<body>
<form action="login.do" method="post">
<p>登录名：<input type="text" name="id"></p>
<p>密码：<input type="password" name="pwd"></p>
<p>来自域：<input type="text" name="LOCAL_SERVICE" value="${LOCAL_SERVICE}" readonly></p>
<p><input type="submit" value="登录"></p>
</form>
</body>
</html>