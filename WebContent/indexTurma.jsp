<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Turma</title>
</head>
<body>
<form action="Scasa" method="post">
	<h2>Menu - Turma</h2><br>
			Para adicionar, digite o "ID", selecione o nome do "professor", o código da "disciplina", o número da "sala", e digite o "horário"(formato 24h). Turmas com mesmo ID não serão aceitas. O sistema impede o cadastro de turmas em que o professor ou a sala estejam ocupados.<br>
			Para remover, digite o "ID", selecione o nome do "professor", o código da "disciplina", o número da "sala", e digite o horário.<br>
			Para alterar, digite o "ID" da turma que se deseja alterar, selecione os novos nome do "professor", código da "disciplina", número da "sala", e digite o novo "horário".<br>
			Para consultar, digite o "ID".<br><br>
			<input type="hidden" name ="classe" value="CadastroTurma">
			ID:<input type="text" name="id"/> NOME - PROFESSOR: <select name="professor">
				<c:forEach items="${requestScope.resultado.get(0)}" var="item">
  					<option value="${item}">${item}</option>
  				</c:forEach>
			</select> 
			CÓDIGO - DISCIPLINA: <select name="disciplina">
				<c:forEach items="${requestScope.resultado.get(1)}" var="item">
  					<option value="${item}">${item}</option>
  				</c:forEach>
			</select> 
			NÚMERO - SALA: <select name="sala">
				<c:forEach items="${requestScope.resultado.get(2)}" var="item">
  					<option value="${item}">${item}</option>
  				</c:forEach>
  			</select> 
  			HORÁRIO: <input type="text" name="horario" maxlength="5" size="5"/>
			<input type="submit" value="Incluir" name="metodo"/>   <input type="submit" value="Remover" name="metodo"/>   <input type="submit" value="Alterar" name="metodo"/>	 <input type="submit" value="Consultar" name="metodo"/>   <input type="submit" value="Visualizar" name="metodo"/>
</form>	
<hr>
<a href="index.html">Página Inicial</a>
</body>
</html>