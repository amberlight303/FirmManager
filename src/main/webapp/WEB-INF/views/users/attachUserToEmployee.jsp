<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<firmManager:layout activeMenuItem="menu-admin" title="User: attach to an Employee"
                    contextPath="${contextPath}">
    <div class="content-inner">
        <div class="search-wrapper">
            <form:form modelAttribute="employee" action="${contextPath}/admin/users/${userId}/attach/find" method="get" class="form-horizontal"
                       id="search-owner-form">
                <div class="form-group">
                    <div class="control-group" id="name">
                        <label class="col-sm-2 control-label">Employee's last name</label>
                        <div class="col-sm-10">
                            <form:input class="form-control" path="lastName" size="30" maxlength="80"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Find Employees</button>
                    </div>
                </div>
            </form:form>
        </div>
        <h3 align="center">Attach to the user ${user.username} some Employee</h3>
        <div class="table-back">
        <table id="employees" class="table table-striped footable">
            <thead>
            <tr>
                <th>Employee's name</th>
                <th>Attached to user</th>
                <th>Attach</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${employees}" var="employee">
                <tr>
                    <td>
                        <c:out value="${employee.firstName} ${employee.lastName}"/>
                    </td>
                    <td>
                        <c:out value="${employee.user.username}"/>
                    </td>
                    <td>
                        <form:form modelAttribute="project" action="${contextPath}/admin/users/${userId}/attach/${employee.id}" method="post">
                            <button type="submit" class="btn btn-default">Attach</button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</firmManager:layout>