<%-- 
    Document   : validate_login
    Created on : 10/06/2016, 10:04:44 AM
    Author     : Alejandro Alberto Yescas Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.isw.cec.DataWeb.Asistente" %>
<%@page import="com.isw.cec.DataWeb.Coordinador" %>
<%@page import="com.isw.cec.DBManager.Reader" %>

<%
Coordinador c = Reader.validateCoor(
        request.getParameter("user"),
        request.getParameter("pass"));

if( c != null ){
    session.invalidate();
    session = request.getSession();
    session.setAttribute("user", c);
    response.sendRedirect("coordinador/");
    return;
}

Asistente a = Reader.validateAsist(
        request.getParameter("user"),
        request.getParameter("pass"));

if( a != null ){
    session.invalidate();
    session = request.getSession();
    session.setAttribute("user", a);
    response.sendRedirect("asistente/");
    return;
}

response.sendRedirect("login.jsp");
%>
