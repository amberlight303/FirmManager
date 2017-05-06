<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="activeMenuItem" %>
<%@ attribute name="enableMetaCsrf" %>
<%@ attribute name="title" required="true" %>

<!DOCTYPE html>
<html>
<head>
    <firmManager:htmlHeader title="${title}" enableMetaCsrf="${enableMetaCsrf}"/>
</head>
<body id="${activeMenuItem}">
    <div class="wrapper">
        <firmManager:bodyHeader/>
        <div class="container content-container">
            <div class="content clearfix">
                <jsp:doBody/>
            </div>
        </div>
        <firmManager:footer/>
    </div>
    <firmManager:footer_scripts/>
</body>
</html>