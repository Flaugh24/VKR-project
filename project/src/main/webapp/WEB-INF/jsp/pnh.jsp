<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"         pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

    <style>
        .search{
            position:relative;
        }

        .search_result{
            background: #FFF;
            border: 1px #ccc solid;
            width: 100px;
            border-radius: 4px;
            max-height:100px;
            overflow-y:scroll;
            display:none;
        }

        .search_result li{
            list-style: none;
            padding: 5px 10px;
            margin: 0 0 0 -40px;
            color: #0896D3;
            border-bottom: 1px #ccc solid;
            cursor: pointer;
            transition:0.3s;
        }

        .search_result li:hover{
            background: #F9FF00;
        }
    </style>

    <title>Title</title>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <script src="<c:url value="/resources/js/search.js"/> "></script>
</head>
<body>


<input type="text" name="referal" placeholder="Живой поиск" value="" class="who"  autocomplete="off">
<ul class="search_result"></ul>



</body>
</html>
