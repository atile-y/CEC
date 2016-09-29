<%-- 
    Document   : abrir_curso
    Created on : 13/06/2016, 01:07:46 PM
    Author     : Alejandro Alberto Yescas Benítez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.isw.cec.DataWeb.Curso" %>
<%@page import="com.isw.cec.DataWeb.Grupo" %>
<%@page import="com.isw.cec.DataWeb.Instructor" %>
<%@page import="com.isw.cec.DBManager.Reader" %>
<%@page import="com.isw.cec.DBManager.Writer" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%

if( request.getSession(false) == null ){
    response.sendRedirect("../login.jsp");
    return;
}

Curso curso = (Curso)session.getAttribute("curso");
if( curso == null ) {
    out.println("Error: curso == null");
    return;
}

List<Integer> IdAlums = Reader.getIDAlumnosEnCurso(curso.getID());
List<Integer> ids;
List<Instructor> lins = Reader.getInstructores();
Grupo grupo;
String[] datos;
int numGrupos = Integer.parseInt(request.getParameter("numGrupos"));
int idCont = 0, idLim;

for(int i=0;i<numGrupos;i++){
    datos = request.getParameter("instructor"+i).split(",");
    if( datos.length != 3 ){
        out.println("Error: instructor"+i+" split in " + datos.length);
        return;
    }

    grupo = null;
    for(Instructor in : lins)
        if( in.getApPaterno().equals(datos[0])
         && in.getApMaterno().equals(datos[1])
         && in.getNombre().equals(datos[2]) ){
            grupo = new Grupo(-1, in, curso);
            break;
        }
    if( grupo == null ){
        out.println("Error: Instructor \""+datos[0]+" "+datos[1]+" "+datos[2]+"\" not found");
        return;
    }
    
    grupo.setNombre(request.getParameter("grupo"+i));
    grupo.setAula(request.getParameter("aula"+i));
    
    ids = new ArrayList<>();
    idLim = Integer.parseInt(request.getParameter("numAlum"+i));
    if( idCont + idLim > IdAlums.size() ){
        out.println("Error: Try to enroll "+(idCont+idLim)+" students of "+IdAlums.size());
        return;
    }
    
    for(int j=0;j<idLim;j++)
        ids.add(IdAlums.get(j+idCont));
    idCont += idLim;
    
    if( !Writer.saveGrupo(grupo, ids) ){
        out.println("Error: Can't save group " + grupo.getNombre());
        return;
    }
}

request.setAttribute("curso", curso);
request.getRequestDispatcher("GenerarFolios").forward(request, response);
%>