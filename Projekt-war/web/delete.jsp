<%-- 
    Document   : delete
    Created on : 2018-01-21, 19:32:05
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
            <h1><h:outputText value="dodano"/></h1>
            <h:outputText value="#{fileWebBean.delete()}" />
<%
    String redirectURL = request.getHeader("referer");
    response.sendRedirect(redirectURL);
%>
            
        </body>
    </html>
</f:view>
