<%-- 
    Document   : index
    Created on : 10/06/2016, 01:28:49 PM
    Author     : Alejandro Alberto Yescas Benítez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.isw.cec.DataWeb.Asistente" %>
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
        <link rel="stylesheet" type="text/css" href="../css/asistente.css">
        
        <script type="text/javascript" src="../js/jquery-3.0.0.min.js"></script>
        <script type="text/javascript" src="../js/asistente.js"></script>
        
        <title>M&oacute;dulo Asistente</title>
    </head>
    <body>
        <%
        Asistente asist = (Asistente)session.getAttribute("user");
        if( asist == null ){
            response.sendRedirect("../login.jsp");
            return;
        }
        List<Curso> cursos = Reader.getCursos();
        //session.setAttribute("cursos", cursos);
        
        String fullName = asist.getNombreCompleto();
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
                <p id="ins" class="selected">Inscribir Alumno</p><!--
             --><p id="cap">Capturar registro de asistencia y calificaciones</p><!--
             --><p id="pag">Imprimir Documentos</p>
            </div>
            <div id="content">
                <form id="inscribir-alumno">
                    <div class="row">
                        <label>Curso: </label>
                        <select name="Curso">
                            <option value="-">Selecciona un Curso</option>
                            <%
                            for(int i=0;i<cursos.size();i++){
                                out.print("<option value=\"" + i + "\">");
                                out.print(cursos.get(i).getNombre());
                                out.println("</option>");
                            }
                            %>
                        </select>
                    </div>
                    <div class="row">
                        <label>Folio de Dep&oacute;sito: </label>
                        <input name="Folio" type="text" maxlength="20">
                    </div>
                    <div class="row center">
                        <label>Datos del Alumno</label>
                    </div>
                    <div class="row">
                        <label>CURP: </label>
                        <input name="CURP" type="text" maxlength="18">
                        <input name="Tipo" type="radio" value="PG">
                        <label>P&uacute;blico General</label>
                        <input name="Tipo" type="radio" value="CP">
                        <label>Comunidad Polit&eacute;cnica</label>
                    </div>
                    <div class="row">
                        <label>Nombre: </label>
                        <input name="Nombre" type="text" maxlength="50">
                    </div>
                    <div class="row">
                        <label>Apellido Paterno: </label>
                        <input name="ApPat" type="text" maxlength="50">
                        <label>Apellido Materno: </label>
                        <input name="ApMat" type="text" maxlength="50">
                    </div>
                    <div class="row">
                        <label>Correo: </label>
                        <input name="Correo" type="text" maxlength="50">
                        <label>Tel&eacute;fono: </label>
                        <input name="Telefono" type="text" maxlength="15">
                    </div>
                    <div class="row center">
                        <div class="button">Inscribir Alumno</div>
                        <div class="button">Restablecer</div>
                    </div>
                </form>
                <form id="capturar-asistencia">
                    <div class="row center">
                        <label>Grupo: </label>
                        <select name="Grupo">
                            <option value="-">Selecciona un Grupo</option>
                        </select>
                    </div>
                </form>
                <div id="imprimir-documentos">
                    
                </div>
            </div>
        </div>
    </body>
</html>
