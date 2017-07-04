<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/liveSearch.css"/>"/>

    <title>Администратор</title>
</head>
<body>
<c:url value="/admin/groups" var="changeGroups"/>
<div class="container-fluid">
    <header>
        <jsp:include page="header.jsp"/>
    </header>
    <main>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation"><a href="#statistic" aria-controls="statistic" role="tab"
                                       data-toggle="tab">Статистика</a></li>
            <li role="presentation"><a href="#admin_panel" aria-controls="admin_panel" role="tab"
                                       data-toggle="tab">Администрирование</a></li>
        </ul>
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane" id="statistic">
                Статистика
            </div>
            <div role="tabpanel" class="tab-pane" id="admin_panel">
                <div class="row">
                    <div class="col-md-6">
                        <h3>Назначение прав</h3>
                        <a href="${contextPath}/admin/permissions/1" class="btn btn-default">Студенты</a>
                        <a href="${contextPath}/admin/permissions/2" class="btn btn-default">Координаторы</a>
                        <a href="${contextPath}/admin/permissions/3" class="btn btn-default">Библиографы</a>
                    </div>
                    <div class="col-md-6">
                        <h3>Назначение групп</h3>
                        <div class="form-group">
                            <form action="${changeGroups}" method="post" enctype="application/x-www-form-urlencoded">
                                <input id="fullNameCoordinator" class="form-control" required>
                                <br/>
                                <input id="coordinatorId" name="coordinatorId" required style="display: none">
                                <button class="btn btn-default">
                                    Выбрать
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
<script src="<c:url value="/resources/js/tab.js"/>"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<c:url value="/resources/js/libs/jquery.autocomplete.min.js"/> "></script>
<script>
    $('#fullNameCoordinator').autocomplete({
        serviceUrl: '${pageContext.request.contextPath}/getEmployee', // Страница для обработки запросов автозаполнения
        paramName: "fullName",
        minChars: 3, // Минимальная длина запроса для срабатывания автозаполнения
        delimiter: ",", // Разделитель для нескольких запросов, символ или регулярное выражение
        maxHeight: 400, // Максимальная высота списка подсказок, в пикселях
        width: 690,
        zIndex: 9999, // z-index списка
        deferRequestBy: 300, // Задержка запроса (мсек), на случай, если мы не хотим слать миллион запросов, пока пользователь печатает. Я обычно ставлю 300.
        transformResult: function (response) {
            return {

                suggestions: $.map($.parseJSON(response), function (item) {

                    return {
                        value: (item.surname + ' ' + item.firstName + ' ' + item.secondName + ' ' + item.position + ' ' + item.department),
                        username: item.username,
                        suraname: item.surname,
                        firstName: item.firstName,
                        secondName: item.secondName
                    };

                })
            };

        },
        onSelect: function (suggestion) {
            $('#coordinatorId').val(suggestion.username);
            $('#fullNameCoordinator').val(suggestion.suraname + ' ' + suggestion.firstName + ' ' + suggestion.secondName);
        }
    });
</script>
</body>
</html>
