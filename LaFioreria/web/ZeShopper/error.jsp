<%-- 
    Document   : error
    Created on : Jul 15, 2025, 3:17:33 AM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/ZeShopper/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ZeShopper/css/responsive.css" rel="stylesheet">
        <title>Error Page</title>
    </head>
    <body>
        <jsp:include page="/ZeShopper/header.jsp"/>
        <div class="container" style="padding-top: 50px; padding-bottom: 50px; text-align: center;">
            <div class="row">
                <div class="col-sm-12">
                    <div class="error-template">
                        <h1>
                            Oops! Something went wrong.
                        </h1>
                        <h2>
                            An error has occurred.
                        </h2>
                        <div class="error-details">
                            We're sorry, but an unexpected error occurred. Please try again later or contact support if the issue persists.
                            <%-- You can display the exception details here if needed, but be cautious in a production environment --%>
                            <%
                                Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
                                if (exception != null) {
                            %>
                            <p>Error message: <%= exception.getMessage() %></p>
                            <%
                                }
                            %>
                        </div>
                        <div class="error-actions">
                            <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>
                                Take Me Home </a>
                            <a href="${pageContext.request.contextPath}/ZeShopper/contact-us.jsp" class="btn btn-default btn-lg"><span class="glyphicon glyphicon-envelope"></span>
                                Contact Support </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="/ZeShopper/footer.jsp"/>
    </body>
</html>
