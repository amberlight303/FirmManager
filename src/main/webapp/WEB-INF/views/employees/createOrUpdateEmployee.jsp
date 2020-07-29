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
                    title="${employee['new']?'New Employee':'Employee'}"
                    contextPath="${contextPath}">
    <div class="content-inner">
        <div id="edit-page-wrapper">
            <h2 align="center">
                <c:if test="${employee['new']}">New </c:if> Employee
            </h2>
            <c:choose>
                <c:when test="${emptyPhotoName != null}">
                    <img src="${contextPath}/resources/images/noPhoto.png" class="empl-photo">
                </c:when>
                <c:otherwise>
                    <img src="${contextPath}/uploads/${photoName}" class="empl-photo">
                </c:otherwise>
            </c:choose>
            <form:form modelAttribute="employee" method="post"
                       class="form-horizontal" enctype="multipart/form-data">
                <form:input type="hidden" path="id" value="${employee.id}"/>
                <form:input type="hidden" path="fired" value="${employee.fired}"/>
                <form:input type="hidden" path="userIdToAttachWithEmpl" value="${employee.userIdToAttachWithEmpl}"/>
                <form:input type="hidden" path="oldEmplPhotoName" value="${employee.photoFileName}"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Photo:</label>
                    <div class="col-sm-10">
                        <form:input type="file" path="image" accept="image/jpeg,image/png,image/gif"/>
                    </div>
                </div>
                <spring:bind path="firstName">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField name="firstName" label="First Name"/>
                        <p class="error-p"><form:errors path="firstName"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="lastName">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField name="lastName" label="Last Name"/>
                        <p class="error-p"><form:errors path="lastName"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="birthDate">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField placeholder="e.g. 26/01/1995" name="birthDate" label="Birth Date"/>
                        <p class="error-p"><form:errors path="birthDate"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="email">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField name="email" label="E-mail"/>
                        <p class="error-p"><form:errors path="email"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="address">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField name="address" label="Address"/>
                        <p class="error-p"><form:errors path="address"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="city">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField name="city" label="City"/>
                        <p class="error-p"><form:errors path="city"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="country">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField name="country" label="Country"/>
                        <p class="error-p"><form:errors path="country"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="telephone">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField name="telephone" label="Telephone"/>
                        <p class="error-p"><form:errors path="telephone"></form:errors></p>
                    </div>
                </spring:bind>
                <spring:bind path="hireDate">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <firmManager:inputField placeholder="e.g. 07/07/2007" name="hireDate" label="Hire Date"/>
                        <p class="error-p"><form:errors path="hireDate"></form:errors></p>
                    </div>
                </spring:bind>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Gender</label>
                    <div class="col-sm-10">
                        <form:select class="form-control" path="gender.id">
                            <c:forEach var="gender" items="${genders}">
                                <form:option value="${gender.id}">${gender.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Position</label>
                    <div class="col-sm-10">
                        <form:select class="form-control" path="workingPosition.id">
                            <c:forEach var="workingPosition" items="${workingPositions}">
                                <form:option value="${workingPosition.id}">${workingPosition.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <c:choose>
                            <c:when test="${employee['new']}">
                                <button class="btn btn-default btn-center" type="submit">Add Employee</button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-default btn-center" type="submit">Update Employee</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</firmManager:layout>
