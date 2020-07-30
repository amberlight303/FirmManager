<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<firmManager:layout activeMenuItem="menu-projects" title="Project: attach Employees"
                    contextPath="${contextPath}">
    <div class="content-inner">
        <h2 align="center">Employees unrelated with the project ${project.name}</h2>
        <br>
        <br>
        <a class="btn btn-default" href="${contextPath}/projects/${projectId}">Back to the project details</a>
        <div class="table-back">
            <div class="colors-expl">
                <div class="colors-expl-item prj-in-progress colored-square">&#9632; - In progress</div>
                <div class="colors-expl-item prj-complete colored-square">&#9632; - Completed</div>
                <div class="colors-expl-item prj-inactive colored-square">&#9632; - Inactive</div>
                <div class="colors-expl-item prj-overdue colored-square">&#9632; - Overdue</div>
            </div>
            <table id="employees" class="table table-striped footable">
                <thead>
                <tr>
                    <th class="extend-table"><span class="glyphicon glyphicon-plus"></span></th>
                    <th>Name</th>
                    <th>Position</th>
                    <th data-title="Experience" data-breakpoints="xs sm md">Experience</th>
                    <th data-title="Age" data-breakpoints="xs sm md">Age</th>
                    <th data-title="Gender" data-breakpoints="xs sm md">Gender</th>
                    <th>Projects</th>
                    <th>Attach</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${selections}" var="employee">
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
                            <fmt:formatNumber type="number" maxFractionDigits="2" value="${employee.experience}"/> <c:out value=" years"/>                        </td>
                        <td>
                            <c:out value="${employee.age} years"/>
                        </td>
                        <td>
                            <c:out value="${employee.gender.name}"/>
                        </td>
                        <td>
                            <c:forEach var="project" varStatus="loop" items="${employee.projects}">
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
                                </c:choose>" href="${contextPath}/projects/${project.id}"><c:out value="${project.name}"/></a>
                                ${!loop.last ? '<hr>' : ''}
                            </c:forEach>
                        </td>
                        <td>
                            <spring:url value="${contextPath}/admin/projects/{projectId}/employees/{employeeId}/attach" var="attachUrl">
                                <spring:param name="projectId" value="${project.id}"/>
                                <spring:param name="employeeId" value="${employee.id}"/>
                            </spring:url>
                            <form:form method="post" action="${attachUrl}">
                                <button class="btn btn-default" style="font-size: smaller" type="submit">Attach</button>
                            </form:form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</firmManager:layout>