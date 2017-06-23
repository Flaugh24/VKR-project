<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Заявка <c:out value="${ticket.id}"/></title>
</head>
<body>

<c:url var="editUrl" value="/ticket/${ticket.id}/edit"/>
<c:url var="recordSheet" value="/ticket/${ticket.id}/recordSheet"/>
<c:url var="licenseAgreement" value="/ticket/${ticket.id}/licenseAgreement"/>

<header>
    <jsp:include page="header.jsp"/>
</header>
<main>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">№ заявки: <c:out value="${ticket.id}"/><br/></h3>
            </div>
            <div class="panel-body">
                Дата создания: <fmt:formatDate pattern="dd.MM.yyyy" value="${ticket.dateCreationStart}"/><br/>
                Статус: <c:out value="${ticket.status.name}"/><br/>
                Автор: <c:out value="${ticket.user.surname}"/> <c:out value="${ticket.user.firstName}"/> <c:out
                    value="${ticket.user.secondName}"/><br/>
                Телефон: <a href="tel:+${ticket.user.phoneNumber}"><c:out value="${ticket.user.phoneNumber}"/></a><br/>
                Email: <a href="mailto:${ticket.user.email}"><c:out value="${ticket.user.email}"/></a><br/>
                Специальность: <c:out value="${ticket.direction}"/> <c:out value="${ticket.directionCode}"/><br/>
                Заглавие работы: <c:out value="${ticket.title}"/><br/>
                Тип документа: <c:out value="${ticket.documentType.name}"/><br/>
                Тип использования: <c:out value="${ticket.typeOfUse.name}"/><br/>
            </div>
            <div class="panel-footer">
                <a href="${editUrl}" type="button" class="btn btn-default">Редактирвоать</a>
                <c:if test="${perm_check_tickets == true}">
                    <a href="${recordSheet}" type="button" class="btn btn-default">Регистрационный лист</a>
                    <a href="${licenseAgreement}" type="button" class="btn btn-default">Лицензионный договор</a>
                </c:if>
            </div>
        </div>
    </div>
</main>
</body>
</html>
