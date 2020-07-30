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
<firmManager:layout activeMenuItem="menu-projects" title="Project"
                    contextPath="${contextPath}">
    <div class="content-inner">
        <h2 align="center">Project's information</h2>
        <div class="table-back">
            <table class="table table-striped footable">
                <tr>
                    <th>Name</th>
                    <td>
                        <p>
                            <b><c:out value="${project.name}"/></b>
                        </p>
                    </td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td>
                        <p class="
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
                                </c:choose>">
                            <b><c:out value="${project.projectStatus.name}"/></b>
                        </p>

                    </td>
                </tr>
                <c:if test="${project.daysLeft != null}">
                    <tr>
                        <th>Days left</th>
                        <td>
                            <c:out value="${project.daysLeft}"/>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <th>Objective</th>
                    <td><c:out value="${project.projectObjective.name}"/></td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td><c:out value="${project.description}"/></td>
                </tr>
                <tr>
                    <th>Notes</th>
                    <td><c:out value="${project.notes}"/></td>
                </tr>
                <tr>
                    <th>Start date</th>
                    <td><fmt:formatDate value="${project.startDate}" pattern="dd-MM-yyyy"/></td>
                </tr>
                <tr>
                    <th>End date</th>
                    <td><fmt:formatDate value="${project.endDate}" pattern="dd-MM-yyyy"/></td>
                </tr>
            </table>
        </div>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <spring:url value="${contextPath}/admin/projects/{projectId}/edit" var="editUrl">
                <spring:param name="projectId" value="${project.id}"/>
            </spring:url>
            <a href="${editUrl}" class="btn btn-default btn-center">Edit Project</a>

            <spring:url value="${contextPath}/admin/projects/{projectId}/delete" var="deleteUrl">
                <spring:param name="projectId" value="${project.id}"/>
            </spring:url>
            <form:form method="post" action="${deleteUrl}">
                <button class="btn btn-default btn-center full-width" type="submit">Delete Project</button>
            </form:form>

            <spring:url value="${contextPath}/admin/projects/{projectId}/employees/attach" var="attachUrl">
                <spring:param name="projectId" value="${project.id}"/>
            </spring:url>
            <a href="${attachUrl}" class="btn btn-default btn-center">Attach Employee</a>
        </sec:authorize>
        <h2 align="center">Employees attached to the project</h2>
        <div class="table-back">
            <table class="table table-striped footable">
                    <thead>
                        <tr>
                            <th class="extend-table">
                                <span class="glyphicon glyphicon-plus"></span>
                            </th>
                            <th>Name</th>
                            <th>Position</th>
                            <th data-title="Experience" data-breakpoints="xs sm md">Experience</th>
                            <th data-title="Age" data-breakpoints="xs sm md">Age</th>
                            <th data-title="Gender" data-breakpoints="xs sm md">Gender</th>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <th>Detach</th>
                            </sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <c:forEach var="employee" items="${project.employees}">
                            <tr>
                                <td class="extend-table">
                                </td>
                                <td>
                                    <a class="${employee.fired == true ? 'empl-fired' : ''}"
                                       href="${contextPath}/employees/${employee.id}">
                                        <c:out value="${employee.firstName} ${employee.lastName}"/>
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${employee.workingPosition.name}"/>
                                </td>
                                <td>
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${employee.experience}"/> <c:out value=" years"/>                                </td>
                                <td>
                                    <c:out value="${employee.age} years"/>
                                </td>
                                <td>
                                    <c:out value="${employee.gender.name}"/>
                                </td>
                                <td>
                                    <spring:url value="${contextPath}/admin/projects/{project.id}/employees/{employee.id}/detach" var="detachUrl">
                                        <spring:param name="project.id" value="${project.id}"/>
                                        <spring:param name="employee.id" value="${employee.id}"/>
                                    </spring:url>
                                    <form:form method="post" action="${detachUrl}">
                                        <button class="btn btn-default btn-center full-width" type="submit">Detach</button>
                                    </form:form>
                                </td>
                            </tr>
                        </c:forEach>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_USER')">
                        <c:forEach var="employee" items="${project.employees}">
                            <tr>
                                <td class="extend-table">
                                </td>
                                <td>
                                    <a class="${employee.fired == true ? 'empl-fired' : ''}"
                                       href="${contextPath}/employees/${employee.id}">
                                        <c:out value="${employee.firstName} ${employee.lastName}"/>
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${employee.workingPosition.name}"/>
                                </td>
                                <td>
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${employee.experience}"/> <c:out value=" years"/>                                </td>
                                <td>
                                    <c:out value="${employee.age} years"/>
                                </td>
                                <td>
                                    <c:out value="${employee.gender.name}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </sec:authorize>
                    </tbody>
            </table>
        </div>
    </div>
</firmManager:layout>
