<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Список заявок</title>
</head>

<body style="margin: 20px 50px ">
<%--<c:url var="addUrl" value="/vkr/main/tickets/add" />--%>
<c:url var="addUrl" value="/tickets/uploadfiles"/>
<div class="container-fluid">
    <% int i =0;%>
    <a class="btn btn-default btn-lg" href="${addUrl}" role="button">Создать новую заявку</a>
    <h1>Текущие заявки</h1>

    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th rowspan="2" style="vertical-align: middle; text-align: center">№</th>
            <th rowspan="2" style="vertical-align: middle; text-align: center">№ Заявки</th>
            <th rowspan="2" style="vertical-align: middle; text-align: center">Номер группы</th>
            <th rowspan="2" style="vertical-align: middle; text-align: center">ФИО соискателя</th>
            <th rowspan="2" style="vertical-align: middle; text-align: center">Заглавие работы</th>
            <th rowspan="2" style="vertical-align: middle; text-align: center">Тип документа</th>
            <th rowspan="2" style="vertical-align: middle; text-align: center">Вид использования</th>
            <th colspan="2" style="vertical-align: middle; text-align: center">Лицензионный договор</th>
            <th rowspan="2" style="vertical-align: middle; text-align: center">Статус</th>
            <th rowspan="2" style="vertical-align: middle; text-align: center">Просмотреть</th>
        </tr>
        <tr>
            <th style="vertical-align: middle; text-align: center">Дата</th>
            <th style="vertical-align: middle; text-align: center">Номер</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tickets}" var="ticket">
            <%i++;%>
            <c:url var="checkUrl" value="/tickets/edit?id=${ticket.id}" />
            <tr>
                <td><%=i%></td>
                <td>
                    <c:out value="${ticket.id}" />
                </td>
                <td>#</td>
                <td>ФИО</td>
                <td>
                    <c:out value="${ticket.title}" />
                </td>
                <td>
                    <c:out value="${ticket.documentType.name}" />
                </td>
                <td>
                    <c:out value="${ticket.typeOfUse.typeOfUse}"/>
                </td>
                <td>###</td>
                <td>###</td>
                <td>
                    <c:out value="${ticket.status.statusName}" />
                </td>
                <td><a href="${checkUrl}">Просмотреть</a></td>

            </tr>

        </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty tickets}">
        No records found.
    </c:if>
</div>
</body>

</html>