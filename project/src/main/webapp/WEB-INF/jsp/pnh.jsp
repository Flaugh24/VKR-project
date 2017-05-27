<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"         pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>

</head>
<body>

<c:url var="uploadUrl" value="/path-to" />

<input id="id" value="123213">
<button id="btn-save" class="active" value="save">sadfsadfasdf</button>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

<script src="<c:url value="/resources/js/main.js"/>"></script>

</body>
</html>
