<%-- 
    Document   : guardar_curso
    Created on : 11/06/2016, 09:37:22 AM
    Author     : Alejandro Alberto Yescas Benítez
--%>

<%@page contentType="text/text" pageEncoding="UTF-8"%>
<%@page import="com.isw.cec.DataWeb.Curso" %>
<%@page import="com.isw.cec.DBManager.Writer" %>
<%@page import="java.math.BigDecimal" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.sql.Time" %>
<%

if( request.getSession(false) == null ){
    response.sendRedirect("../login.jsp");
    return;
}

String nuevo = request.getParameter("nuevo");
String dato;
Curso curso;

if( nuevo == null ){
    curso = (Curso)session.getAttribute("curso");
    if( curso == null ) {
        out.println("Error");
        return;
    }

    SimpleDateFormat timeF = new SimpleDateFormat("HH:mm");

    dato = request.getParameter("nombre");
    if( dato.equals("") ){
        out.println("Error");
        return;
    }
    curso.setNombre(dato);
    
    dato = request.getParameter("costoPG");
    curso.setCostoGeneral(dato.equals("")?null:new BigDecimal(dato));
    dato = request.getParameter("costoCP");
    curso.setCostoComunidad(dato.equals("")?null:new BigDecimal(dato));
    
    dato = request.getParameter("horaIni");
    curso.setHoraInicio(dato.equals("")?null:new Time(timeF.parse(dato).getTime()));
    dato = request.getParameter("horaFin");
    curso.setHoraFinal(dato.equals("")?null:new Time(timeF.parse(dato).getTime()));
    
    dato = request.getParameter("periodo");
    curso.setPeriodo(dato.equals("")?null:dato);
    
    dato = (String)request.getAttribute("temario");
    if( !dato.equals("") )
        curso.setTemario(dato);
    
    dato = request.getParameter("minAlum");
    curso.setMinAlumnos(dato.equals("")?-1:Integer.parseInt(dato));
    dato = request.getParameter("maxAlum");
    curso.setMaxAlumnos(dato.equals("")?-1:Integer.parseInt(dato));
    
    curso.setEvaluacion(request.getParameter("evaluacion").equals("1"));
}
else if( nuevo.equals("nuevo") ){
    dato = request.getParameter("nombre");
    if( dato == null || dato.equals("") ){
        out.println("Error");
        return;
    }
    
    curso = new Curso(-1, dato);
}
else{
    out.println("Error");
    return;
}

if( Writer.saveCurso(curso) ){
    out.println("OK");
    return;
}
out.println("Error");
%>
