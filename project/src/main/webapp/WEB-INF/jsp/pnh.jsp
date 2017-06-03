<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

    <style>
        .search {
            position: relative;
        }

        .search_result {
            background: #FFF;
            border: 1px #ccc solid;
            width: 100px;
            border-radius: 4px;
            max-height: 100px;
            overflow-y: scroll;
            display: none;
        }

        .search_result li {
            list-style: none;
            padding: 5px 10px;
            margin: 0 0 0 -40px;
            color: #0896D3;
            border-bottom: 1px #ccc solid;
            cursor: pointer;
            transition: 0.3s;
        }

        .search_result li:hover {
            background: #F9FF00;
        }
    </style>

    <title>Title</title>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <script src="<c:url value="/resources/js/libs/jquery.autocomplete.min.js"/> "></script>
</head>
<body>

<div>
    <input type="text" id="w-input-search" value="">
    <span>
        <button id="w-button-search" type="button">Search</button>
    </span>
</div>

<script>
    $(document).ready(function () {

        $('#w-input-search').autocomplete({
            serviceUrl: '${pageContext.request.contextPath}/getTags',
            paramName: "fio",
            delimiter: ",",
            transformResult: function (response) {

                return {

                    suggestions: $.map($.parseJSON(response), function (item) {

                        return {value: item.fio, data: item.id};
                    })

                };

            }

        });


    });
</script>


</body>
</html>
