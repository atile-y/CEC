<%-- 
    Document   : coor_cursos
    Created on : 10/06/2016, 09:55:25 PM
    Author     : Alejandro Alberto Yescas Benítez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.isw.cec.DataWeb.Curso" %>
<%@page import="com.isw.cec.DataWeb.Coordinador" %>
<%@page import="com.isw.cec.DataWeb.Instructor" %>
<%@page import="com.isw.cec.DBManager.Reader" %>
<%@page import="java.math.BigDecimal" %>
<%@page import="java.util.List" %>
<%

if( request.getSession(false) == null ){
    response.sendRedirect("../login.jsp");
    return;
}

List<Curso> cursos = (List<Curso>)session.getAttribute("cursos");
if( cursos == null ) {
    response.sendRedirect("../login.jsp");
    return;
}

int idx = Integer.parseInt(request.getParameter("idx").substring(5));
session.setAttribute("curso_idx", idx);

Curso curso = cursos.get(idx);
session.setAttribute("curso", curso);

int numAlumnos = Reader.getNumAlumnosEnCurso(curso.getID());
%>
<form id="info-curso">
    <input name="valido" type="hidden" value="0">
    <div id="nombre-curso">
        <p><%= curso.getNombre() %></p>
        <input name="nombre" type="text" maxlength="80">
    </div>
    <div id="costoPG-curso">
        <label>Costo al P&uacute;blico General: </label>
        <p><% out.print(curso.getCostoGeneral()==null?"-":curso.getCostoGeneral()); %></p>
        <input name="costoPG" type="text" maxlength="9">
    </div>
    <div id="costoCP-curso">
        <label>Costo a la Com. Polit&eacute;cnica: </label>
        <p><% out.print(curso.getCostoComunidad()==null?"-":curso.getCostoComunidad()); %></p>
        <input name="costoCP" type="text" maxlength="9">
    </div>
    <div id="horario-curso">
        <label>Horario: </label>
        <p><% if( curso.getHoraInicio() != null )
            out.print(curso.getHoraInicio().toString().substring(0, 5));
        else
            out.print("--:--"); %></p>
        <input name="horaIni" type="text" maxlength="5">
        <label> - </label>
        <p><% if( curso.getHoraFinal() != null )
            out.print(curso.getHoraFinal().toString().substring(0, 5));
        else
            out.print("--:--"); %></p>
        <input name="horaFin" type="text" maxlength="5">
    </div>
    <div id="periodo-curso">
        <label>Periodo: </label>
        <p><% out.print(curso.getPeriodo()==null?"-":curso.getPeriodo()); %></p>
        <input name="periodo" type="text" size="10">
    </div>
    <div id="temario-curso">
        <label>Temario: </label>
        <p><% if( curso.getTemario() == null )
            out.print("Sin Temario");
        else{
            out.print("<a href=\"/CEC/uploadFiles/temarios/" + curso.getTemario() +
                    "\" target=\"_blank\">" + curso.getTemario() + "</a>");
        }%></p>
        <input name="temario" type="file" accept=".pdf">
    </div>
    <div id="cupo-curso">
        <label>Cupo: </label>
        <p><% out.print(curso.getMinAlumnos()==-1?"_":curso.getMinAlumnos()); %></p>
        <input name="minAlum" type="text" maxlength="4">
        <label> - </label>
        <p><% out.print(curso.getMaxAlumnos()==-1?"_":curso.getMaxAlumnos()); %></p>
        <input name="maxAlum" type="text" maxlength="4">
    </div>
    <div id="evaluacion-curso">
        <label>¿Genera constancia?</label>
        <p><% out.print(curso.isEvaluacion()?"S&iacute;":"No"); %></p>
        <input name="evaluacion" type="radio" value="1"><span>S&iacute;</span>
        <input name="evaluacion" type="radio" value="0"><span>No</span>
    </div>
    <% if( curso.getEstado() == Curso.Estado.PRE ) { %>
    <div id="modif-curso">
        <div id="mod-editar-curso" class="button">Editar</div>
        <div id="mod-cancelar-curso" class="button">Cancelar</div>
        <div id="mod-aceptar-curso" class="button">Aceptar</div>
        <div class="clear"></div>
    </div>
    <% } %>
</form>
<% if( curso.getEstado() == Curso.Estado.PRE ) { %>
<form id="apertura-curso">
    <input name="numGrupos" type="hidden" value="1">
    <div class="title">Apertura del Curso</div>
    <p>Alumnos Inscritos al Curso: <span id="alum-ins"><%= numAlumnos %></span></p>
    <table><tbody>
        <tr>
            <th>Grupo</th>
            <th>Aula</th>
            <th>Instructor</th>
            <th>Alumnos</th>
            <th></th>
        </tr>
        <tr id="primer-grupo">
            <td><input class="in-grupo" name="grupo0" type="text" maxlength="10"></td>
            <td><input class="in-aula" name="aula0" type="text" maxlength="10"></td>
            <td>
                <select class="in-instructor" name="instructor0">
                    <option value="-"></option>
                    <%
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
                </select>
            </td>
            <td><input class="in-num" name="numAlum0" type="text" maxlength="4"</td>
            <td><div class="delete-grupo"></div></td>
        </tr>
        <tr id="new-grupo"></tr>
        <tr class="last-child">
            <td></td><td></td>
            <td><p>Alumnos Restantes:</p></td>
            <td><p id="alum-rest"><%= numAlumnos %></p></td>
            <td><div id="add-grupo"></div></td>
        </tr>
    </tbody></table>
    <div id="abrir-curso">
        <p class="button">Abrir Curso</p>
    </div>
</form>
<% } else if( curso.getEstado() == Curso.Estado.OPENED ) {
        String tmp = Reader.getIngresosDelCurso(curso.getID()).toString();
        String ingresos = "";

        for(int ini = tmp.length()%3;ini<tmp.length();ini+=3){
            if( ini == 0 )
                ini = 3;
            if( ini < 3 )
                ingresos = tmp.substring(0, ini);
            else
                ingresos += tmp.substring(ini - 3, ini);
            if( ini + 3 < tmp.length() )
                ingresos += ",";
            else
                ingresos += tmp.substring(ini);
        }
%>
<div id="info-extra-curso">
    <div class="title">Informaci&oacute;n Adicional</div>
    <p>Ingresos del Curso: <span><%= ingresos %></span></p>
    <p>Folios de Movimientos Bancarios de los Alumnos:
        <a href="/CEC/uploadFiles/folios/<%= curso.getFolios() %>" target="_blank">Folios</a>
    </p>
</div>
<% } %>
