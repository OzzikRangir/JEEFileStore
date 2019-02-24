<%-- 
    Document   : upload
    Created on : 2018-01-21, 20:22:03
    Author     : Windows
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>JSP Page</title>
        </head>
        <body>
            <h1><h:outputText value="Dodano plik"/></h1>
            <%out.println("<a class=\'button rounded-full-btn reload-btn s-2 margin-bottom\' href=");
            out.println(request.getHeader("referer"));
            out.println("><i class='icon-sli-arrow-left'>Powrót</i></a>");%>
<%
    String redirectURL = request.getHeader("referer");
    response.sendRedirect(redirectURL);
%>
        </body>
    </html>
</f:view>
