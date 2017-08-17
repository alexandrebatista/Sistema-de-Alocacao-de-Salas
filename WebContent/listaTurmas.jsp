<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista - Turmas</title>
</head>
<body>
<table border="1">
<tr><th>Id - Turma</th><th>Nome - Professor</th><th>Código - Disciplina</th><th>Número - Sala</th><th>Horário</th></tr>
<c:forEach items="${requestScope.resultado}" var="item">
   <tr><td>${item.getId()}</td><td>${item.professor.getNome()}</td><td>${item.disciplina.getCodigo()}</td><td>${item.sala.getNumero()}</td><td>${item.getHorario()}</td></tr>
</c:forEach>
</table><br><br>
<a href="index.html">Página Inicial</a>
</body>
</html>