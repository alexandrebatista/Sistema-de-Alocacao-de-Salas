<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista - Professores</title>
</head>
<body>
<table border="1">
<tr><th>Nome</th><th>Email</th></tr>
<c:forEach items="${requestScope.resultado}" var="item">
   <tr><td>${item.getNome()}</td><td>${item.getEmail()}</td></tr>
</c:forEach>
</table><br><br>
<a href="index.html">Página Inicial</a>
</body>
</html>