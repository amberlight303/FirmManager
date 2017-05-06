<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>

<firmManager:layout activeMenuItem="menu-projects" title="${project['new']?'New Project':'Project'}">
    <div class="content-inner">
        <div id="edit-page-wrapper">
            <h2 align="center">
                <c:if test="${project['new']}">New </c:if> Project
            </h2>
            <form:form modelAttribute="project" method="post"
                       class="form-horizontal">
                <input type="hidden" name="id" value="${project.id}"/>
                <spring:bind path="name">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField name="name" label="Name"/>
                        <p class="error-p"><form:errors path="name"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="startDate">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField placeholder="e.g. 01/10/2016" name="startDate" label="Start Date"/>
                        <p class="error-p"><form:errors path="startDate"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="endDate">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField placeholder="e.g. 01/03/2017" name="endDate" label="End Date"/>
                        <p class="error-p"><form:errors path="endDate"></form:errors></p>
                    </div>
                </spring:bind>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Description</label>
                    <div class="col-sm-10">
                        <form:textarea cssClass="js-textarea-minr3" path="description"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">Notes</label>
                    <div class="col-sm-10">
                        <form:textarea cssClass="js-textarea-minr3" path="notes"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">Status</label>
                    <div class="col-sm-10">
                        <form:select class="form-control" path="projectStatus.id">
                            <c:forEach var="projectStatus" items="${projectStatuses}">
                                <form:option value="${projectStatus.id}">${projectStatus.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">Objective</label>
                    <div class="col-sm-10">
                        <form:select class="form-control" path="projectObjective.id">
                            <c:forEach var="projectObjective" items="${projectObjectives}">
                                <form:option value="${projectObjective.id}">${projectObjective.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <c:choose>
                            <c:when test="${project['new']}">
                                <button class="btn btn-default btn-center" type="submit">Add Project</button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-default btn-center" type="submit">Update Project</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</firmManager:layout>