<%@ page import="org.barmaley.vkr.domain.Users" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:useBean id="principal" class="org.barmaley.vkr.controller.MainController"/>
<jsp:useBean id="permission" class="org.barmaley.vkr.tool.PermissionTool"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <title>Заявка</title>


</head>
<body>

<%
    Users user = principal.getPrincipal();
    boolean perm_add_fio_eng = permission.checkPermission("PERM_ADD_FIO_ENG");
    request.setAttribute("user", user);
    request.setAttribute("perm_add_fio_eng", perm_add_fio_eng);
%>

<c:url var="saveUrl" value="/user/profile"/>
<c:url var="home" value="/"/>
<div class="row">
    <div class="col-md-9">
        <a href="${home}">
            <h1>
                <c:out value="${user.surname}"/>
                <c:out value="${user.firstName}"/>
                <c:out value="${user.secondName}"/>
            </h1>
        </a>
    </div>
    <div class="col-md-3" style="padding-top: 20px;">
        <div class="btn-group" role="group" style="float: right">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                    aria-expanded="false">
                Профиль <span class="caret"></span>
            </button>
            <ul class="dropdown-menu pull-right">
                <li><a href="#myModal" data-toggle="modal">Настройки</a></li>
                <li><a href="#" onclick="document.forms['logoutForm'].submit()">Выйти</a></li>
            </ul>
            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Настройки профиля</h4>
                        </div>
                        <form:form commandName="user" action="${saveUrl}" method="post" id="profileform">
                            <div class="modal-body">
                                <form:label path="id" cssStyle="display: none"/>
                                <form:input path="id" cssStyle="display: none"/>
                                <div class="form-group">
                                    <form:label path="email">E-mail</form:label>
                                    <form:input path="email" cssClass="form-control" type="email" name="email"
                                                id="email" required="required"/>
                                </div>
                                <div class="form-group">
                                    <form:label path="phoneNumber">Номер телефона</form:label>
                                    <form:input path="phoneNumber" cssClass="form-control" type="tel" name="phone"
                                                id="phone" required="required"/>
                                </div>
                                <c:if test="${perm_add_fio_eng==true}">
                                    <form:label path="">ФИО на английском языке</form:label>
                                    <div class="form-group">
                                        <form:label path="firstNameEng"/>
                                        <form:input path="firstNameEng" cssClass="form-control" placeholder="Фамилия"
                                                    required="required"/>
                                    </div>
                                    <div class="form-group">
                                        <form:label path="surnameEng"/>
                                        <form:input path="surnameEng" cssClass="form-control" placeholder="Имя"
                                                    required="required"/>
                                    </div>
                                    <div class="form-group">
                                        <form:label path="secondNameEng"/>
                                        <form:input path="secondNameEng" cssClass="form-control" placeholder="Отчество"
                                                    required="required"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                                <input type="submit" class="btn btn-primary" value="Сохранить изменения"/>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



</body>
</html>
