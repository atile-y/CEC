<%-- 
    Document   : getInstructores
    Created on : 13/06/2016, 07:59:23 AM
    Author     : Alejandro Alberto Yescas Benítez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.isw.cec.DataWeb.Instructor" %>
<%@page import="com.isw.cec.DBManager.Reader" %>
<%@page import="java.util.List" %>
<%
out.println("<option value=\"-\"></option>");
List<Instructor> ins = Reader.getInstructores();
String str;

for(int i=0;i<ins.size();i++){
    str = "<option value=\"" + ins.get(i).getApPaterno();
    str += "," + ins.get(i).getApMaterno() + ",";
    str += ins.get(i).getNombre() + "\">";
    str += ins.get(i).getNombreCompleto() + "</option>";
    out.println(str);
}
%>