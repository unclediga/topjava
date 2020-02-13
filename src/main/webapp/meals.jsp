<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<section style="width: 900px; margin: auto">
    <table border="1" cellpadding="8" cellspacing="1">
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <jsp:useBean id="list" scope="request" type="java.util.List"/>
        <c:forEach var="row" items="${list}">
            <jsp:useBean id="row" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="background-color: ${row.excess ? "red" : "lightblue"} ">
                <td>
                    ${DateTimeUtil.formatHTMLDate(row.dateTime)}
                </td>
                <td>
                        ${row.description}
                </td>
                <td>
                        ${row.calories}
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
