<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<firmManager:layout activeMenuItem="menu-admin" title="Users" contextPath="${contextPath}">
    <div class="content-inner">
        <div class="search-wrapper">
            <form:form modelAttribute="user" action="${contextPath}/admin/users/find" method="get" class="form-horizontal"
                       id="search-owner-form">
                <div class="form-group">
                    <div class="control-group" id="name">
                        <label class="col-sm-2 control-label">Last name </label>
                        <div class="col-sm-10">
                            <form:input class="form-control" path="lastName" size="30" maxlength="80"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="control-group" id="projectObjective.name">
                        <label class="col-sm-2 control-label">Username </label>
                        <div class="col-sm-10">
                            <form:input class="form-control" path="username" size="30" maxlength="80"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                        <button type="submit" class="btn btn-default submit-search">Find Users</button>
                </div>
            </form:form>
        </div>
        <br>
        <a href="${contextPath}/admin/registration" class="btn btn-default btn-center">Registration</a>
        <br>
        <div class="table-back">
            <table id="employees" class="table table-striped footable">
                <thead>
                    <tr>
                        <th class="extend-table"><span class="glyphicon glyphicon-plus"></span></th>
                        <th>Username</th>
                        <th>Name</th>
                        <th>Attached to Employee</th>
                        <th data-title="Attach to Employee" data-breakpoints="xs sm md">Attach to the Employee</th>
                        <th data-title="Delete" data-breakpoints="xs sm md">Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td class="extend-table">
                            </td>
                            <td>
                                <a href="${contextPath}/users/${user.id}"><c:out value="${user.username}"/></a>
                            </td>
                                <td>
                                    <c:out value="${user.firstName} ${user.lastName}"/>
                                </td>
                            <td>
                                <c:if test="${user.employee != null}">
                                    <a href="${contextPath}/employees/${user.employee.id}">
                                        <c:out value="${user.employee.firstName} ${user.employee.lastName}"/>
                                    </a>
                                </c:if>
                            </td>
                            <td>
                                <a class="btn btn-default attach-btn" href="/admin/users/${user.id}/attach">Attach</a>
                            </td>
                            <td>
                                <form:form action="${contextPath}/admin/users/${user.id}/delete" method="post">
                                    <button type="submit" class="btn btn-default delete-btn">Delete</button>
                                </form:form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</firmManager:layout>