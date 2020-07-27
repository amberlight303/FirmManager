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

<firmManager:layout activeMenuItem="menu-projects" title="Projects"
                    contextPath="${contextPath}">
    <div class="content-inner">
        <div class="search-wrapper">
            <form:form modelAttribute="project" action="${contextPath}/projects/find" method="get" class="form-horizontal"
                       id="search-owner-form">
                <div class="form-group">
                    <div class="control-group" id="name">
                        <label class="col-sm-2 control-label">Name </label>
                        <div class="col-sm-10">
                            <form:input class="form-control" path="name" size="30" maxlength="80"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="control-group" id="projectObjective.name">
                        <label class="col-sm-2 control-label">Objective </label>
                        <div class="col-sm-10">
                            <form:select class="form-control" path="projectObjective.name" size="1">
                                <form:option value=""> </form:option>
                                <form:option value="Android application">Android application</form:option>
                                <form:option value="Android game">Android game</form:option>
                                <form:option value="Blog">Blog</form:option>
                                <form:option value="Browser game">Browser game</form:option>
                                <form:option value="Web-shop">Web-shop</form:option>
                                <form:option value="Social network">Social network</form:option>
                                <form:option value="Other"></form:option>
                            </form:select>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="control-group" id="projectStatus.name">
                        <label class="col-sm-2 control-label">Status </label>
                        <div class="col-sm-10">
                            <form:select class="form-control" path="projectStatus.name" size="1">
                                <form:option value=""></form:option>
                                <form:option value="In progress">In progress</form:option>
                                <form:option value="Completed">Completed</form:option>
                                <form:option value="Inactive">Inactive</form:option>
                                <form:option value="Overdue">Overdue</form:option>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                        <button type="submit" class="btn btn-default  submit-search">Find Projects</button>
                </div>
            </form:form>
        </div>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="${contextPath}/admin/projects/new" class="btn btn-default add-proj-btn">Add Project</a> <br>
        </sec:authorize>

        <div class="table-back">
            <div class="colors-expl">
                <div class="colors-expl-item prj-in-progress colored-square">&#9632; - In progress</div>
                <div class="colors-expl-item prj-complete colored-square">&#9632; - Completed</div>
                <div class="colors-expl-item prj-inactive colored-square">&#9632; - Inactive</div>
                <div class="colors-expl-item prj-overdue colored-square">&#9632; - Overdue</div>
                <div class="colors-expl-item empl-fired colored-square">&#9632; - Fired</div>
            </div>

            <table id="projects" class="table table-striped table-bordered footable">
                <thead>
                    <tr>
                        <th class="extend-table hidden-default">
                            <span class="glyphicon glyphicon-plus"></span>
                        </th>
                        <th>Name</th>
                        <th data-title="Status" data-breakpoints="xs sm md">Status</th>
                        <th data-title="Objective" data-breakpoints="xs sm md">Objective</th>
                        <th>Days Left</th>
                        <th data-title="Start Date" data-breakpoints="xs sm md">Start Date</th>
                        <th data-title="End Date" data-breakpoints="xs sm md">End Date</th>
                        <th>Employees</th>
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
                            </c:choose>"
                               href="${contextPath}/projects/${project.id}"><c:out value="${project.name}"/></a>
                        </td>
                        <td>
                            <c:out value="${project.projectStatus.name}"/>
                        </td>
                        <td>
                            <c:out value="${project.projectObjective.name}"/>
                        </td>
                        <td><c:choose>
                            <c:when test="${project.daysLeft == null || project.daysLeft <= 0}">
                                    &mdash;
                            </c:when>
                            <c:otherwise>
                                    <c:out value="${project.daysLeft}"/>
                            </c:otherwise>
                        </c:choose>

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
                                    <c:out value="${employee.firstName} "/>
                                    <c:out value="${employee.lastName}"/>
                                </a>
                                ${!loop.last ? '<hr>' : ''}
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</firmManager:layout>
