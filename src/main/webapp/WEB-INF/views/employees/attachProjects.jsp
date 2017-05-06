<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>

<firmManager:layout activeMenuItem="menu-employees" title="Employee: attach Projects">
    <div class="content-inner">
        <h2 align="center">Projects unrelated with the ${employee.firstName}&nbsp;${employee.lastName}</h2>
        <br>
        <br>
        <a class="btn btn-default" href="/employees/${employeeId}">Back to the employee details</a>
        <div class="table-back">
            <p class="colors-expl">
                <span class="prj-in-progress colored-square">&#9632;</span> - In progress
                <span class="prj-complete colored-square">&#9632;</span> - Completed
                <span class="prj-inactive colored-square">&#9632;</span> - Inactive<br>
                <span class="prj-overdue colored-square">&#9632;</span> - Overdue
                <span class="empl-fired colored-square">&#9632;</span> - Fired
            </p>
            <table id="employees" class="table table-striped table-bordered footable">
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
                            </c:choose>" href="/projects/${project.id}"><c:out value="${project.name}"/>
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
                            <c:forEach var="employee" items="${project.employees}">
                                <a class="${employee.fired == true ? 'empl-fired' : ''}"
                                   href="${contextPath}/employees/${employee.id}">
                                    <c:out value="${employee.firstName} ${employee.lastName}"/>
                                </a>
                                <hr>
                            </c:forEach>
                        </td>
                        <td>
                            <spring:url value="/admin/employees/{employeeId}/projects/{projectId}/attach" var="attachUrl">
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
