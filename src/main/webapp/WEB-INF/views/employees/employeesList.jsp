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

<firmManager:layout activeMenuItem="menu-employees" title="Employees"
                    contextPath="${contextPath}">
    <div class="content-inner">
        <div class="search-wrapper">
            <form:form modelAttribute="employee" action="${contextPath}/employees/find" method="get" class="form-horizontal"
                       id="search-owner-form">
                <div class="form-group">
                    <div class="control-group" id="name">
                        <label class="col-sm-2 control-label">Last Name</label>
                        <div class="col-sm-10">
                            <form:input class="form-control" path="lastName"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="control-group" id="projectObjective.name">
                        <label class="col-sm-2 control-label">Working Position </label>
                        <div class="col-sm-10">
                            <form:select class="form-control" path="workingPosition.name" size="1">
                                <form:option value=""> </form:option>
                                <form:option value="Java junior">Java junior</form:option>
                                <form:option value="Java middle">Java middle</form:option>
                                <form:option value="Java senior">Java senior</form:option>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                        <button type="submit" class="btn btn-default submit-search">Find Employees</button>
                </div>
            </form:form>
        </div>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="/admin/employees/new" class="btn btn-default add-empl-btn">Add Employee</a>
        </sec:authorize>
        <div class="table-back">
            <p class="colors-expl">
                <span class="prj-in-progress colored-square">&#9632;</span> - In progress
                <span class="prj-complete colored-square">&#9632;</span> - Completed
                <span class="prj-inactive colored-square">&#9632;</span> - Inactive<br>
                <span class="prj-overdue colored-square">&#9632;</span> - Overdue
                <span class="empl-fired colored-square">&#9632;</span> - Fired
            </p>
            <table class="table table-striped table-bordered footable">
                <thead>
                <tr>
                    <th class="extend-table"><span class="glyphicon glyphicon-plus"></span></th>
                    <th>Name</th>
                    <th>Working Position</th>
                    <th data-title="Experience" data-breakpoints="xs sm md">Experience</th>
                    <th data-title="Age" data-breakpoints="xs sm md">Age</th>
                    <th data-title="Gender" data-breakpoints="xs sm md">Gender</th>
                    <th>Projects</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${selections}" var="employee">
                    <tr>
                        <td class="extend-table">
                        </td>
                        <td>
                            <div>
                                <a class="${employee.fired == true ? 'empl-fired' : ''}"
                                   href="${contextPath}/employees/${employee.id}">
                                    <c:out value="${employee.firstName} ${employee.lastName}"/>
                                </a>
                            </div>
                        </td>
                        <td>
                            <p>
                                <c:out value="${employee.workingPosition.name}"/>
                            </p>
                        </td>
                        <td>
                            <c:out value="${employee.experience}"/>
                        </td>
                        <td>
                            <c:out value="${employee.age}"/>
                        </td>
                        <td>
                            <c:out value="${employee.gender.name}"/>
                        </td>
                        <td>
                            <c:forEach var="project" items="${employee.projects}">
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
                                <hr>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</firmManager:layout>