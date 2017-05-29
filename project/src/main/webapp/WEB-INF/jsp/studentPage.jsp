<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/studentPage.css" />" />

    <title>Личный кабинет студента</title>

</head>
<body>
<c:url var="saveUrl" value="/user/profile" />
<c:set value="${user}" var="user" />
<div class="container-fluid">
    <header>
        <jsp:include page="header.jsp"/>
    </header>
    <main>
        <c:if test="${not empty educPrograms}">
            <h2>Создать заявку по направлению</h2><br>
            <div class="row">
                <c:forEach items="${educPrograms}" var="educProgram">
                    <c:url var="addTicket" value="/ticket/add" />
                    <div class="col-md-3" style="height: 150px">
                        <a href="#" onclick="document.getElementById('addticket${educProgram.id}').submit()">
                            <div class="addTicket">
                                Институт: <c:out value="${educProgram.institute}"/><br>
                                Квалификация: <c:out value="${educProgram.degree}"/><br>
                                Кафедра: <c:out value="${educProgram.department}"/><br>
                                Номер группы: <c:out value="${educProgram.groupNum}"/><br>
                                Направление: <c:out value="${educProgram.direction}" /><br>
                            </div>
                        </a>
                        <form method="post" id="addticket${educProgram.id}" action="${addTicket}" enctype="application/x-www-form-urlencoded">
                            <input name="userId" value="${user.id}" style="display: none"/>
                            <input name="educId" value="${educProgram.id}" style="display: none"/>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${not empty ticketsDTO}">
            <h2>Ваши заявки</h2>
            <table class="table table-striped table-bordered" style="text-align: center">
                <thead>
                <tr>
                    <td>№</td>
                    <td>Создана</td>
                    <td>Отправлена на проверку</td>
                    <td>Принята на проверку</td>
                    <td>Возвращена на доработку</td>
                    <td>Подготовлена к публикации</td>
                    <td>Принята в ИБК</td>
                    <td>Опубликована</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${ticketsDTO}" var="ticketDTO">
                    <c:url var="checkUrl" value="/ticket/edit?ticketId=${ticketDTO.id}" />
                    <a href="${checkUrl}">
                        <tr>
                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out value="${ticketDTO.id}"/></a></td>
                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out value="${ticketDTO.dateCreationStart}"/></a></td>
                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out value="${ticketDTO.dateCreationFinish}"/></a></td>
                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out value="${ticketDTO.dateCheckCoordinatorStart}" /></a></td>
                            <td><a href="${checkUrl}" class="editUrl" style="display: block">Date</a></td>
                            <td><a href="${checkUrl}" class="editUrl" style="display: block">Date</a></td>
                            <td><a href="${checkUrl}" class="editUrl" style="display: block">Date</a></td>
                            <td><a href="${checkUrl}" class="editUrl" style="display: block">Date</a></td>
                        </tr>
                    </a>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </main>
</div>
</body>
</html>
