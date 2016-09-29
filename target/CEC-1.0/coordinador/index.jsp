<%-- 
    Document   : index
    Created on : 10/06/2016, 01:28:49 PM
    Author     : Alejandro Alberto Yescas Benítez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.isw.cec.DataWeb.Coordinador" %>
<%@page import="com.isw.cec.DataWeb.Curso" %>
<%@page import="com.isw.cec.DBManager.Reader" %>
<%@page import="java.util.List" %>
<%
if( request.getSession(false) == null ){
    response.sendRedirect("../login.jsp");
    return;
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="../css/reset.css">
        <link rel="stylesheet" type="text/css" href="../css/base.css">
        <link rel="stylesheet" type="text/css" href="../css/coordinador.css">
        <link rel="stylesheet" type="text/css" href="../css/coor_cursos.css">
        
        <script type="text/javascript" src="../js/jquery-3.0.0.min.js"></script>
        <script type="text/javascript" src="../js/coordinador.js"></script>
        <script type="text/javascript" src="../js/coor_cursos.js"></script>
        
        <title>M&oacute;dulo Coordinador</title>
        
        <%
        Integer idx = (Integer)session.getAttribute("curso_idx");
        if( idx != null ){ %>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#curso<%= idx %>").trigger("click");
            });
        </script>
        <% } %>
    </head>
    <body>
        <%
        Coordinador coor = (Coordinador)session.getAttribute("user");
        if( coor == null ){
            response.sendRedirect("../login.jsp");
            return;
        }
        List<Curso> cursos = Reader.getCursos();
        session.setAttribute("cursos", cursos);
        
        String fullName = coor.getNombreCompleto();
        if( fullName == null ) fullName = "";
        %>
        <div id="header">
            <div>
                <div id="logout">
                    <img src="../img/logout.png" alt="logout">
                </div>
                <div id="name">
                    <p>Bienvenido <%= fullName %></p>
                </div>
            </div>
        </div>

        <div id="container">
            <div id="menu">
                <div id="menu-cursos">
                    <p>C U R S O S</p>
                </div>
                <% if( cursos.isEmpty() ) { %>
                <div id="no-cursos">
                    <p>No ha agregado ning&uacute;n curso.</p>
                </div>
                <% } else { %>
                <div id="cursos">
                    <% for( int i=0;i<cursos.size();i++ ) {
                        String txt = "<p id=\"curso" + i + "\" class=\"";
                        txt += cursos.get(i).getEstado();
                        txt += "\">" + cursos.get(i).getNombre() + "</p>";
                        out.println(txt);
                    } %>
                </div>
                <% } %>
                <ul id="acciones">
                    <li id="borrar-curso" class="button">Borrar</li>
                    <li id="nuevo-curso" class="button">Nuevo</li>
                </ul>
                <div id="agregar-curso">
                    <input id="input-curso" type="text">
                    <p id="cancelar-curso" class="button">Cancelar</p>
                    <p id="aceptar-curso" class="button">Aceptar</p>
                    <div class="clear"></div>
                </div>
            </div>
            <div id="content"></div>
        </div>
    </body>
</html>
