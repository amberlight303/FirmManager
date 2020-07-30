<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<firmManager:layout activeMenuItem="menu-employees" title="Employee"
                    contextPath="${contextPath}">
    <div class="content-inner ${employee.fired == true ? 'empl-fired-bg' : ''}">
        <h2 align="center">Employee's information</h2>
        <c:if test="${employee.fired}">
            <h1 align="center">FIRED</h1>
        </c:if>
        <c:choose>
            <c:when test="${emptyPhotoName != null}">
                <img src="${contextPath}/resources/images/noPhoto.png" class="empl-photo">
            </c:when>
            <c:otherwise>
                <div class="photo-wrapper">
                    <img src="${contextPath}/uploads/${photoName}" class="empl-photo">
                    <a class="btn btn-default btn-center"
                       href="${contextPath}/employees/${employee.id}/download-photo">
                        Download Image</a>
                </div>
            </c:otherwise>
        </c:choose>
        <div class="table-back">
            <table class="table table-striped footable">
                <tr>
                    <th>Name</th>
                    <td>
                        <b><c:out value="${employee.firstName} ${employee.lastName}"/></b>
                    </td>
                </tr>
                <tr>
                    <th>Position</th>
                    <td><c:out value="${employee.workingPosition.name}"/></td>
                </tr>
                <tr>
                    <th>Experience</th>
                    <td>
                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${employee.experience}"/> <c:out value=" years"/>                    </td>
                </tr>
                <tr>
                    <th>Age</th>
                    <td><c:out value="${employee.age} years"/></td>
                </tr>
                <tr>
                    <th>Gender</th>
                    <td><c:out value="${employee.gender.name}"/></td>
                </tr>
                <tr>
                    <th>Birth Date</th>
                    <td><fmt:formatDate value="${employee.birthDate}" pattern="dd-MM-yyyy"/></td>
                </tr>
                <tr>
                    <th>City</th>
                    <td><c:out value="${employee.city}"/></td>
                </tr>
                <tr>
                    <th>Country</th>
                    <td><c:out value="${employee.country}"/></td>
                </tr>
                <tr>
                    <th>Address</th>
                    <td><c:out value="${employee.address}"/></td>
                </tr>
                <tr>
                    <th>Telephone</th>
                    <td><c:out value="${employee.telephone}"/></td>
                </tr>
                <tr>
                    <th>E-mail</th>
                    <td><c:out value="${employee.email}"/></td>
                </tr>
                <tr>
                    <th>Hire Date</th>
                    <td><fmt:formatDate value="${employee.hireDate}" pattern="dd-MM-yyyy"/></td>
                </tr>
            </table>
        </div>
        <c:if test="${employee.user != null}">
            <spring:url value="${contextPath}/users/{userId}" var="userDetailsUrl">
                <spring:param name="userId" value="${employee.user.id}"/>
            </spring:url>
            <a href="${userDetailsUrl}" class="btn btn-default btn-center">View User Related</a>
        </c:if>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <spring:url value="${contextPath}/admin/employees/{employeeId}/edit" var="editUrl">
                <spring:param name="employeeId" value="${employee.id}"/>
            </spring:url>
            <a href="${editUrl}" class="btn btn-default btn-center">Edit Employee</a>
            <spring:url value="${contextPath}/admin/employees/{employeeId}/delete" var="deleteUrl">
                <spring:param name="employeeId" value="${employee.id}"/>
            </spring:url>
            <form:form method="post" action="${deleteUrl}">
                <button class="btn btn-default btn-center full-width" type="submit">Delete Employee</button>
            </form:form>

            <spring:url value="${contextPath}/admin/employees/{employeeId}/fire" var="fireUrl">
                <spring:param name="employeeId" value="${employee.id}"/>
            </spring:url>
            <form:form method="post" action="${fireUrl}">
                <button class="btn btn-default btn-center full-width" type="submit">Set Fired</button>
            </form:form>
            <spring:url value="${contextPath}/admin/employees/{employeeId}/projects/attach" var="attachUrl">
                <spring:param name="employeeId" value="${employee.id}"/>
            </spring:url>
            <a href="${attachUrl}" class="btn btn-default btn-center">Attach Project</a>
        </sec:authorize>
        <h2 align="center">Projects attached to the employee</h2>
        <div class="table-back">
            <table class="table table-striped footable">
                <thead>
                <tr>
                    <th class="extend-table">
                        <span class="glyphicon glyphicon-plus"></span>
                    </th>
                    <th>Name</th>
                    <th data-title="Status" data-breakpoints="xs sm md">Status</th>
                    <th>Objective</th>
                    <th data-title="Start Date" data-breakpoints="xs sm md">Start Date</th>
                    <th data-title="End Date" data-breakpoints="xs sm md">End Date</th>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <th>Detach</th>
                    </sec:authorize>
                </tr>
                </thead>
                <tbody>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <c:forEach var="project" items="${employee.projects}">
                        <c:choose>
                            <c:when test="${project.projectStatus.name eq 'In progress'}">
                                <tr class="tr-in-progress">
                            </c:when>
                            <c:when test="${project.projectStatus.name eq 'Inactive'}">
                                <tr class="tr-in-inactive">
                            </c:when>
                            <c:when test="${project.projectStatus.name eq 'Overdue'}">
                                <tr class="tr-in-overdue">
                            </c:when>
                            <c:when test="${project.projectStatus.name eq 'Completed'}">
                                <tr class="tr-in-complete">
                            </c:when>
                        </c:choose>
                            <td class="extend-table">
                            </td>
                            <td>
                                <a class="
                            <c:choose>
                                <c:when test="${project.projectStatus.name eq 'In progress'}">
                                prj-in-progress
                                </c:when>
                                <c:when test="${project.projectStatus.name eq 'Inactive'}">
                                prj-inactive
                                </c:when>
                                <c:when test="${project.projectStatus.name eq 'Overdue'}">
                                prj-overdue
                                </c:when>
                                <c:when test="${project.projectStatus.name eq 'Completed'}">
                                prj-complete
                                </c:when>
                            </c:choose>"
                                   href="${contextPath}/projects/${project.id}"><c:out value="${project.name}"/></a>
                            </td>
                            <td>
                                <c:out value="${project.projectStatus.name}"/>
                            </td>
                            <td>
                                <c:out value="${project.projectObjective.name}"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${project.startDate}" pattern="dd-MM-yyyy"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${project.endDate}" pattern="dd-MM-yyyy"/>
                            </td>
                        </tr>
                    </c:forEach>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <c:forEach var="project" items="${employee.projects}">
                        <c:choose>
                            <c:when test="${project.projectStatus.name eq 'In progress'}">
                                <tr class="tr-in-progress">
                            </c:when>
                            <c:when test="${project.projectStatus.name eq 'Inactive'}">
                                <tr class="tr-in-inactive">
                            </c:when>
                            <c:when test="${project.projectStatus.name eq 'Overdue'}">
                                <tr class="tr-in-overdue">
                            </c:when>
                            <c:when test="${project.projectStatus.name eq 'Completed'}">
                                <tr class="tr-in-complete">
                            </c:when>
                        </c:choose>
                            <td class="extend-table">
                            </td>
                            <td>
                                <a class="
                            <c:choose>
                                <c:when test="${project.projectStatus.name eq 'In progress'}">
                                prj-in-progress
                                </c:when>
                                <c:when test="${project.projectStatus.name eq 'Inactive'}">
                                prj-inactive
                                </c:when>
                                <c:when test="${project.projectStatus.name eq 'Overdue'}">
                                prj-overdue
                                </c:when>
                                <c:when test="${project.projectStatus.name eq 'Completed'}">
                                prj-complete
                                </c:when>
                            </c:choose>"
                                   href="${contextPath}/projects/${project.id}"><c:out value="${project.name}"/></a>
                            </td>
                            <td>
                                <c:out value="${project.projectStatus.name}"/>
                            </td>
                            <td>
                                <c:out value="${project.projectObjective.name}"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${project.startDate}" pattern="dd-MM-yyyy"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${project.endDate}" pattern="dd-MM-yyyy"/>
                            </td>
                            <td>
                                <form:form method="post" action="${contextPath}/admin/employees/${employee.id}/projects/${project.id}/detach">
                                    <button class="btn btn-default" type="submit">Detach</button>
                                </form:form>
                            </td>
                        </tr>
                    </c:forEach>
                </sec:authorize>
                </tbody>
            </table>
        </div>
    </div>
</firmManager:layout>



