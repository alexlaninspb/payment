<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Input</title>
</head>
<body>
<h2>Input data</h2>

<%--@elvariable id="home" type=""--%>
<form:form action="submit" method="post" modelAttribute="home">
    <table>
        <tr>
            <td><form:label path="paymentType">paymentType: </form:label></td>
            <td><form:input path="paymentType" id="paymentType" size="10"/></td>
        </tr>
        <tr>
            <td><form:label path="amount">amount: </form:label></td>
            <td><form:input path="amount" id="amount" size="10"/></td>
        </tr>
        <tr>
            <td><form:label path="currency">currency: </form:label></td>
            <td><form:input path="currency" id="currency" maxlength="3" size="10"/></td>
        </tr>
        <tr>
            <td><form:button>Submit</form:button></td>
        </tr>
    </table>
</form:form>
</body>
</html>
