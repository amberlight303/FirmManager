<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<firmManager:layout activeMenuItem="menu-employees"
                    title="Employee: attach Projects" contextPath="${contextPath}">
    <div class="content-inner">
        <h2 align="center">Projects unrelated with the ${employee.firstName}&nbsp;${employee.lastName}</h2>
        <br>
        <br>
        <a class="btn btn-default" href="${contextPath}/employees/${employeeId}">Back to the employee details</a>
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
                        <th class="extend-table">
                            <span class="glyphicon glyphicon-plus"></span>
                        </th>
                        <th>Name</th>
                        <th data-title="Project Status" data-breakpoints="xs sm md">Project Status</th>
                        <th>Project Objective</th>
                        <th data-title="Start Date" data-breakpoints="xs sm md">Start Date</th>
                        <th data-title="End Date" data-breakpoints="xs sm md">End Date</th>
                        <th data-title="Employees" data-breakpoints="xs sm md">Employees</th>
                        <th>Attach</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${selections}" var="project">
                    <tr>
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
                            </c:choose>" href="${contextPath}/projects/${project.id}"><c:out value="${project.name}"/>
                            </a>
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
                            <c:forEach var="employee" varStatus="loop" items="${project.employees}">
                                <a class="${employee.fired == true ? 'empl-fired' : ''}"
                                   href="${contextPath}/employees/${employee.id}">
                                    <c:out value="${employee.firstName} ${employee.lastName}"/>
                                </a>
                                ${!loop.last ? '<hr>' : ''}
                            </c:forEach>
                        </td>
                        <td>
                            <spring:url value="${contextPath}/admin/employees/{employeeId}/projects/{projectId}/attach" var="attachUrl">
                                <spring:param name="employeeId" value="${employee.id}"/>
                                <spring:param name="projectId" value="${project.id}"/>
                            </spring:url>
                            <form:form method="post" action="${attachUrl}">
                                <button class="btn btn-default attach-btn" type="submit">Attach</button>
                            </form:form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</firmManager:layout>
