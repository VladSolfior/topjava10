<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <table>
        <thead>
            <tr>
                <td>Description</td>
                <td>Calories</td>
                <td>Date</td>
            </tr>
        </thead>
        
        <c:if test="${ !empty list}">
            <c:forEach var="elem" items="${list}">
                <tbody>
                <tr>
                    <c:choose>
                        <c:when test="${elem.exceed}">
                            <td style="color: red" >${elem.description}</td>
                            <td style="color: red" >${elem.calories}</td>
                            <td style="color: red" >${elem.dateTime}</td>
                        </c:when>
                        <c:when test="${!elem.exceed}">
                            <td style="color: forestgreen" >${elem.description}</td>
                            <td style="color: forestgreen" >${elem.calories}</td>
                            <td style="color: forestgreen" >${elem.dateTime}</td>
                        </c:when>

                    </c:choose>


                </tr>
                </tbody>
            </c:forEach>

        </c:if>
        

    </table>

</body>
</html>
