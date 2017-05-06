<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>


<firmManager:layout activeMenuItem="" title="User">
    <div class="content-inner">
        <h1 align="center">User Information</h1>
        <div class="table-back">
            <table class="table table-striped table-bordered footable">
                <tr>
                    <th>Name</th>
                    <td><b><c:out value="${user.firstName} ${user.lastName}"/></b></td>
                </tr>
                <tr>
                    <th>Username</th>
                    <td><c:out value="${user.username}"/></td>
                </tr>
                <tr>
                    <th>Employee related</th>
                    <c:choose>
                        <c:when test="${user.employee != null}">
                            <td><a href="/employees/${user.employee.id}">${user.employee.firstName}&nbsp;${user.employee.lastName}</a></td>
                        </c:when>
                        <c:otherwise>
                            <td>none</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </table>
        </div>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <form:form action="/admin/users/${user.id}/delete" method="post">
                <button type="submit" class="btn btn-default btn-center">Delete the user</button>
            </form:form>
        </sec:authorize>
    </div>
</firmManager:layout>

