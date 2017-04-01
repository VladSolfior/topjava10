<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>

<style>
    .exceeded {color: red;}
    .normal {color: forestgreen;}

</style>

<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table border="1">
    <thead>
    <tr>
        <td>Description</td>
        <td>Calories</td>
        <td>Date</td>
    </tr>
    </thead>

    <c:if test="${ !empty list}">
        <c:forEach var="meal" items="${list}">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tbody>
            <tr class="${meal.exceed ? "exceeded": "normal"}">
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><%=TimeUtil.toString(meal.getDateTime())%></td>
            </tr>
            </tbody>
        </c:forEach>

    </c:if>


</table>

</body>
</html>
