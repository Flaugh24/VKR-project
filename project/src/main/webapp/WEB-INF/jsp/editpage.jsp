<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
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

    <link rel="stylesheet" href="<c:url value="/resources/css/liveSearch.css"/>"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/editPage.css"/> "/>

    <title>Заявка <c:out value="${ticketAttribute.id}"/></title>

</head>
<body>
<c:url var="uploadUrl" value="/ticket/${ticketAttribute.id}/fileupload"/>
<c:url var="deleteUrl" value="/ticket/${ticketAttribute.id}/filedelete"/>
<c:url var="saveUrl" value="/ticket/${ticketAttribute.id}/edit"/>
<c:url var="pdfUrl" value="/ticket/${ticketAttribute.id}/pdf"/>

<fmt:formatDate
        pattern="dd.MM.yyyy" value="${ticketAttribute.licenseDate}" var="licenseDate"/>


<c:choose>
    <c:when test="${disabledEdit && disabledCheck}">
        <c:set var="varclass" value="disabledEdit"/>
    </c:when>
</c:choose>

<div class="container-fluid">
    <header>
        <jsp:include page="header.jsp"/>
    </header>
    <div class="main">
        <div class="row">
            <div class="col-md-5" style="max-height: 100vh; overflow-y: auto; padding-bottom: 100px">
                <h2>Номер заявки <c:out value="${ticketAttribute.id}"/></h2>
                <c:if test="${disabledEdit == false || disabledCheck == false}">
                    <c:if test="${(ticketAttribute.filePdf == null || ticketAttribute.fileZip == null)&&
                                  (ticketAttribute.filePdfSecret == null || ticketAttribute.fileZipSecret == null)&&
                                  (ticketAttribute.filePdf == null || ticketAttribute.fileZipSecret == null)&&
                                  (ticketAttribute.filePdfSecret == null || ticketAttribute.fileZip == null)}">
                        <div>
                            <form method="POST" action="${uploadUrl}" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label>Загрузите файлы Вашей ВКР в формате PDF или ZIP<br/>
                                        <input name="uploadFile" id="uploadFile" type="file"/>
                                    </label>
                                </div>

                                <div class="form-group">
                                    <input type="checkbox" name="tradeSecret" value="${true}"/>Файл содержит комерческую
                                    тайну<br/>
                                    <input type="submit" name="submit" value="Загрузить"
                                           class="btn btn-default uploadButton" disabled/>
                                </div>
                            </form>
                        </div>
                    </c:if>
                    <div>
                        <c:if test="${ticketAttribute.filePdf != null }">
                            <a href="/vkr/ticket/${ticketAttribute.id}/file/delete?type=pdf" type="button"
                               class="btn btn-default">Удалить PDF</a>
                        </c:if>
                        <c:if test="${ticketAttribute.filePdfSecret != null }">
                            <a href="/vkr/ticket/${ticketAttribute.id}/file/delete?type=pdfSecret" type="button"
                               class="btn btn-default">Удалить Secret PDF</a>
                        </c:if>
                        <c:if test="${ticketAttribute.fileZip != null }">
                            <a href="/vkr/ticket/${ticketAttribute.id}/file/delete?type=zip" type="button"
                               class="btn btn-default">Удалить ZIP</a>
                        </c:if>
                        <c:if test="${ticketAttribute.fileZipSecret != null }">
                            <a href="/vkr/ticket/${ticketAttribute.id}/file/delete?type=zipSecret" type="button"
                               class="btn btn-default">Удалить Secret ZIP</a>
                        </c:if>
                    </div>
                </c:if>
                <form:form commandName="ticketAttribute" method="POST" id="ticketform" action="${saveUrl}">
                    <div>
                        <form:input path="id" cssStyle="display: none"/>
                        <form:input path="status.id" cssStyle="display: none"/>
                        <form:input path="filePdf" cssStyle="display: none"/>
                        <form:input path="curatorId" id="curatorId" cssStyle="display: none"/>
                    </div>
                    <div class="form-group">
                        <form:label path="licenseNumber">Номер лицензионного договора</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="licenseNumber"/>
                    </div>
                    <div class="form-group">
                        <form:label path="licenseDate">Дата лицензионного договора</form:label><br/>
                        <form:input type="text" cssClass="form-control ${varclass}" path="licenseDate"
                                    value="${licenseDate}"/>
                    </div>
                    <div class="form-group">
                        <form:label path="documentType.name">Тип документа</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="documentType.name" readonly="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="documentType.nameEng">Тип документа на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="documentType.nameEng" readonly="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="fullNameCurator">ФИО научного руководителя</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="fullNameCurator" id="fullNameCurator"
                                    maxlength="255"/>
                        <span class="help-block"><form:errors path="fullNameCurator"/></span>
                    </div>
                    <div class="form-group">
                        <form:label path="fullNameCuratorEng">ФИО научного руководителя на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="fullNameCuratorEng" maxlength="255"/>
                        <span class="help-block"><form:errors path="fullNameCuratorEng"/></span>
                    </div>
                    <div class="form-group">
                        <form:label path="degreeOfCurator">Ученая степень руководителя</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="degreeOfCurator" id="degree"
                                    maxlength="255"/>
                        <span class="help-block"><form:errors path="degreeOfCurator"/></span>
                    </div>
                    <div class="form-group">
                        <form:label
                                path="degreeOfCuratorEng">Ученая степень руководителя на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="degreeOfCuratorEng" maxlength="255"/>
                        <span class="help-block"><form:errors path="degreeOfCuratorEng"/></span>
                    </div>
                    <div class="form-group">
                        <form:label path="posOfCurator">Должность руководителя</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="posOfCurator" id="position"
                                    maxlength="255"/>
                        <span class="help-block"><form:errors path="posOfCurator"/></span>
                    </div>
                    <div class="form-group">
                        <form:label path="posOfCuratorEng">Должность руководителя на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="posOfCuratorEng" maxlength="255"/>
                        <span class="help-block"><form:errors path="posOfCuratorEng"/></span>
                    </div>
                    <div class="form-group">
                        <div class="form-group">
                            <form:label path="title">Заглавие работы</form:label>
                            <form:input cssClass="form-control ${varclass}" path="title" name="title" maxlength="255"
                                        aria-describedby="helpBlock"/>
                            <span id="helpBlock" class="help-block">
                                <form:errors path="title" cssClass="error"/>
                            </span>
                        </div>
                        <div class="form-group">
                            <form:label path="titleEng">Заглавие работы на английском языке</form:label>
                            <form:input cssClass="form-control ${varclass}" path="titleEng" maxlength="255"/>
                            <span class="help-block"><form:errors path="titleEng"/></span>
                        </div>
                        <div class="form-group">
                            <form:label path="placeOfPublic">Место публикации</form:label><br/>
                            <form:input cssClass="form-control ${varclass}" path="placeOfPublic" maxlength="255"/>
                            <span class="help-block"><form:errors path="placeOfPublic"/></span>
                        </div>
                        <div class="form-group">
                            <form:label path="placeOfPublicEng">Место публикации на английском</form:label><br/>
                            <form:input cssClass="form-control ${varclass}" path="placeOfPublicEng" maxlength="255"/>
                            <span class="help-block"><form:errors path="placeOfPublicEng"/></span>
                        </div>
                        <div class="form-group">
                            <form:label path="yearOfPublic">Год публикации</form:label><br/>
                            <form:input cssClass="form-control ${varclass}" path="yearOfPublic" readonly="true"
                                        maxlength="4"/>
                        </div>
                        <div class="form-group">
                            <form:label path="annotation">Аннотация</form:label>
                            <form:textarea path="annotation" rows="5" maxlength="1024"
                                           cssClass="form-control ${varclass}" cssStyle="max-width:100%"/>
                            <span class="help-block"><form:errors path="annotation"/></span>
                        </div>
                        <div class="form-group">
                            <form:label path="annotationEng">Аннотация на английском языке</form:label>
                            <form:textarea path="annotationEng" rows="5" maxlength="1024"
                                           cssClass="form-control ${varclass}" cssStyle="max-width:100%"/>
                            <span class="help-block"><form:errors path="annotationEng"/></span>
                        </div>
                        <div class="form-group">
                            <form:label path="keyWords">Ключевые слова</form:label>
                            <form:errors path="keyWords" cssClass="error"/>
                            <div class="row">
                                <c:forEach items="${ticketAttribute.keyWords}" var="word">
                                    <div class="col-xs-6">
                                        <form:input cssClass="form-control ${varclass}"
                                                    path="keyWords" value="${word}" maxlength="64"/>
                                    </div>
                                </c:forEach>
                            </div>
                            <span class="help-block"><form:errors path="keyWords"/></span>
                        </div>
                        <div class="form-group">
                            <form:label path="keyWordsEng">Ключевые слова на английском языке</form:label>
                            <form:errors path="keyWordsEng" cssClass="err"/>
                            <div class="row">
                                <c:forEach items="${ticketAttribute.keyWordsEng}" var="word">
                                    <div class="col-xs-6">
                                        <form:input cssClass="form-control ${varclass}"
                                                    path="keyWordsEng" value="${word}" maxlength="64"/>
                                    </div>
                                </c:forEach>
                            </div>
                            <span class="help-block"><form:errors path="keyWordsEng"/></span>
                        </div>
                        <form:label path="typeOfUse.id">Свободный доступ из сети интернет</form:label>
                        <form:select path="typeOfUse.id" cssClass="form-control ${varclass}" cssStyle="width: auto">
                            <c:forEach items="${typesOfUse}" var="typeOfUse">
                                <form:option value="${typeOfUse.id}"><c:out value="${typeOfUse.name}"/></form:option>>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <form:label path="direction">Направлениe подготовки</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="direction" readonly="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="directionCode">Код направления подготовки</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="directionCode" readonly="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="institute">Институт</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="institute" readonly="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="groupNum">Группа</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="groupNum" readonly="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="department">Кафедра</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="department" readonly="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="headOfDepartment">ФИО заведующего кафедрой</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="headOfDepartment" maxlength="255"/>
                        <span class="help-block"><form:errors path="headOfDepartment"/></span>
                    </div>
                    <c:if test="${disabledEdit == false}">
                        <div class="form-group">
                            <button name="button" type="submit" value="save" class="btn btn-default">Сохранить
                                изменения
                            </button>
                            <button name="button" type="submit" value="send" class="btn btn-default">Отправить на
                                проверку
                            </button>
                        </div>
                    </c:if>
                    <c:if test="${disabledCheck == false}">
                        <div class="form-group">
                            <button type="submit" name="button" value="save" class="btn btn-default">Сохранить
                                изменения
                            </button>
                            <button type="submit" name="button" value="ready" class="btn btn-default">Готова для
                                передачи в ИБК
                            </button>
                            <button type="submit" name="button" value="return" class="btn btn-default">Вернуть
                                студенту
                            </button>
                        </div>
                    </c:if>
                </form:form>
            </div>
            <div class="col-md-7" style="padding-top: 10px">
                <c:if test="${ticketAttribute.filePdf != null}">
                    <iframe src="${pdfUrl}" style="width: 100%; height: 100vh"/>
                </c:if>
            </div>
        </div>
    </div>
</div>


++
<script src="<c:url value="/resources/js/uploadFile.js"/> "></script>
<script src="<c:url value="/resources/js/libs/jquery.autocomplete.min.js"/> "></script>

<script>
    $(document).ready(
        function ($) {
            $('.disabledEdit').prop("disabled", true);
        }
    );

    $('#ticketform').keydown(function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            return false;
        }
    });


    $('#fullNameCurator').autocomplete({
        serviceUrl: '${pageContext.request.contextPath}/getEmployee', // Страница для обработки запросов автозаполнения
        paramName: "fullName",
        minChars: 3, // Минимальная длина запроса для срабатывания автозаполнения
        delimiter: ",", // Разделитель для нескольких запросов, символ или регулярное выражение
        maxHeight: 400, // Максимальная высота списка подсказок, в пикселях
        zIndex: 9999, // z-index списка
        deferRequestBy: 300, // Задержка запроса (мсек), на случай, если мы не хотим слать миллион запросов, пока пользователь печатает. Я обычно ставлю 300.
        transformResult: function (response) {
            console.log("12312312");
            return {

                suggestions: $.map($.parseJSON(response), function (item) {

                    return {
                        value: (item.surname + ' ' + item.firstName + ' ' + item.secondName + ' ' + item.position + ' ' + item.department),
                        position: item.position,
                        degree: item.degree,
                        username: item.username,
                        suraname: item.surname,
                        firstName: item.firstName,
                        secondName: item.secondName
                    };

                })
            };

        },
        onSelect: function (suggestion) {
            $('#position').val(suggestion.position);
            $('#degree').val(suggestion.degree);
            $('#curatorId').val(suggestion.username);
            $('#fullNameCurator').val(suggestion.suraname + ' ' + suggestion.firstName + ' ' + suggestion.secondName);
        }
    });

</script>
</body>
</html>