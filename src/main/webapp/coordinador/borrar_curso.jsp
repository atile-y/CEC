<%-- 
    Document   : borrar_curso
    Created on : 14/06/2016, 08:32:04 PM
    Author     : Alejandro Alberto Yescas Benítez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.isw.cec.DataWeb.Curso" %>
<%@page import="com.isw.cec.DBManager.Writer" %>
<%
if( request.getSession(false) == null ){
    response.sendRedirect("../login.jsp");
    return;
}

Curso curso = (Curso)session.getAttribute("curso");
if( Writer.deleteCurso(curso.getID()) ){
    out.println("OK");
    return;
}

out.println("Error");
%>
