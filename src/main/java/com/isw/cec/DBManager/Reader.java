/*
 * Copyright (C) 2016 Alejandro Alberto Yescas Benítez
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.isw.cec.DBManager;

import com.isw.cec.DataWeb.Asistente;
import com.isw.cec.DataWeb.Coordinador;
import com.isw.cec.DataWeb.Curso;
import com.isw.cec.DataWeb.Instructor;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Alejandro Alberto Yescas Benítez
 */
public class Reader extends Connector{
    public static Coordinador validateCoor(String user, String pass){
        Statement state = null;
        ResultSet rs = null;
        Coordinador coor = null;
        
        if( user == null || pass == null )
            return null;
        
        try {
            Connect();
            state = conn.createStatement();
            rs = state.executeQuery(
                    "SELECT * FROM tblPersona T1 JOIN tblCoordinador T2"
                  + "   ON T1.IdPersona = T2.IdCoordinador"
                  + "   AND T2.Usuario = '" + user + "'");
            
            if( !rs.next() )
                return null;

            if( !pass.equals(rs.getString(9)) )
                return null;

            coor = new Coordinador(rs.getInt(1), rs.getString(8), rs.getString(9));
            coor.setNombre(rs.getString(2));
            coor.setApPaterno(rs.getString(3));
            coor.setApMaterno(rs.getString(4));
            coor.setCorreo(rs.getString(5));
            coor.setTelefono(rs.getString(6));
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
            coor = null;
        } catch( Exception x ){
            System.out.println(x);
            coor = null;
        } finally{
            if( rs != null ){
                try { rs.close(); }
                catch( SQLException x ) { }
            }
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }

        return coor;
    }

    public static Asistente validateAsist(String user, String pass){
        Statement state = null;
        ResultSet rs = null;
        Asistente asist = null;
        
        if( user == null || pass == null )
            return null;
        
        try {
            Connect();
            state = conn.createStatement();
            rs = state.executeQuery(
                    "SELECT * FROM tblPersona T1 JOIN tblAsistente T2"
                  + "   ON T1.IdPersona = T2.IdAsistente"
                  + "   AND T2.Usuario = '" + user + "'");
            
            if( !rs.next() )
                return null;

            if( !pass.equals(rs.getString(9)) )
                return null;

            asist = new Asistente(rs.getInt(1), rs.getString(8), rs.getString(9));
            
            asist.setNombre(rs.getString(2));
            asist.setApPaterno(rs.getString(3));
            asist.setApMaterno(rs.getString(4));
            asist.setCorreo(rs.getString(5));
            asist.setTelefono(rs.getString(6));
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
            asist = null;
        } catch( Exception x ){
            System.out.println(x);
            asist = null;
        } finally{
            if( rs != null ){
                try { rs.close(); }
                catch( SQLException x ) { }
            }
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }

        return asist;
    }

    public static List<Instructor> getInstructores(){
        Statement state = null;
        ResultSet rs = null;
        List<Instructor> ins = new ArrayList<>();
        Instructor in;
        
        try {
            Connect();
            state = conn.createStatement();
            rs = state.executeQuery(
                    "SELECT * FROM tblPersona T1 JOIN tblInstructor T2"
                  + "   ON T1.IdPersona = T2.IdInstructor"
                  + "   ORDER BY Nombre, ApPaterno, ApMaterno");
            
            while( rs.next() ){
                in = new Instructor(rs.getInt(1), rs.getString(8));
                in.setNombre(rs.getString(2));
                in.setApPaterno(rs.getString(3));
                in.setApMaterno(rs.getString(4));
                in.setCorreo(rs.getString(5));
                in.setTelefono(rs.getString(6));
                ins.add(in);
            }
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
        } catch( Exception x ){
            System.out.println(x);
        } finally{
            if( rs != null ){
                try { rs.close(); }
                catch( SQLException x ) { }
            }
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }

        return ins;
    }
    
    public static List<Curso> getCursos(){
        Statement state = null;
        ResultSet rs = null;
        List<Curso> cursos = new ArrayList<>();
        Curso c;
        
        try {
            Connect();
            state = conn.createStatement();
            rs = state.executeQuery(
                    "SELECT * FROM tblCurso"
                  + "   WHERE Estado = 'PRE' OR Estado = 'OPENED'");
            
            while( rs.next() ){
                c = new Curso(rs.getInt(1), rs.getString(2));
                c.setCostoGeneral(rs.getBigDecimal(3));
                c.setCostoComunidad(rs.getBigDecimal(4));
                c.setHoraInicio(rs.getTime(5));
                c.setHoraFinal(rs.getTime(6));
                c.setPeriodo(rs.getString(7));
                c.setTemario(rs.getString(8));
                c.setMinAlumnos(rs.getInt(9));
                c.setMaxAlumnos(rs.getInt(10));
                c.setEvaluacion(rs.getBoolean(11));
                switch(rs.getString(12)){
                    case "PRE":
                        c.setEstado(Curso.Estado.PRE);
                        break;
                    case "OPENED":
                        c.setEstado(Curso.Estado.OPENED);
                        break;
                    case "CLOSED":
                        c.setEstado(Curso.Estado.CLOSED);
                        break;
                }
                c.setFolios(rs.getString(13));
                cursos.add(c);
            }
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
        } catch( Exception x ){
            System.out.println("Exception: " + x);
        } finally{
            if( rs != null ){
                try { rs.close(); }
                catch( SQLException x ) { }
            }
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }
        
        return cursos;
    }
    
    public static int getNumAlumnosEnCurso(int IdCurso){
        Statement state = null;
        ResultSet rs = null;
        int num = 0;
        
        try {
            Connect();
            state = conn.createStatement();
            rs = state.executeQuery(
                    "SELECT COUNT(IdAlumno) FROM tblAlumno_Curso"
                  + "   WHERE IdCurso = " + IdCurso);
            
            if( rs.next() )
                num = rs.getInt(1);
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
        } catch( Exception x ){
            System.out.println("Exception: " + x);
        } finally{
            if( rs != null ){
                try { rs.close(); }
                catch( SQLException x ) { }
            }
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }
        
        return num;
    }

    public static List<Integer> getIDAlumnosEnCurso(int IdCurso){
        Statement state = null;
        ResultSet rs = null;
        List<Integer> alums = new ArrayList<>();
        
        try {
            Connect();
            state = conn.createStatement();
            rs = state.executeQuery(
                    "SELECT IdPersona "
                  + "FROM tblPersona T1 JOIN tblAlumno T2"
                  + "   ON T1.IdPersona = T2.IdAlumno"
                  + "   JOIN tblAlumno_Curso T3"
                  + "   ON T1.IdPersona = T3.IdAlumno"
                  + "   AND T3.IdCurso = " + IdCurso);
            
            while( rs.next() )
                alums.add(rs.getInt(1));
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
        } catch( Exception x ){
            System.out.println("Exception: " + x);
        } finally{
            if( rs != null ){
                try { rs.close(); }
                catch( SQLException x ) { }
            }
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }
        
        return alums;
    }

    public static BigDecimal getIngresosDelCurso(int IdCurso){
        Statement state = null;
        ResultSet rs = null;
        BigDecimal ingresos = new BigDecimal("0.0");
        
        try {
            Connect();
            state = conn.createStatement();
            rs = state.executeQuery(
                    "SELECT"
                  + "   SUM(TF.costo) "
                  + "FROM"
                  + "   (SELECT"
                  + "       SUM(T3.CostoGeneral) AS 'costo'"
                  + "   FROM"
                  + "       tblAlumno T1"
                  + "       JOIN tblAlumno_Curso T2 ON T1.IdAlumno = T2.IdAlumno"
                  + "       JOIN tblCurso T3 ON T2.IdCurso = T3.IdCurso"
                  + "   WHERE T3.IdCurso = " + IdCurso + " AND T1.Tipo = 'PG'"
                  + "   UNION"
                  + "   SELECT"
                  + "       SUM(T3.CostoComunidad)"
                  + "   FROM"
                  + "       tblAlumno T1"
                  + "       JOIN tblAlumno_Curso T2 ON T1.IdAlumno = T2.IdAlumno"
                  + "       JOIN tblCurso T3 ON T2.IdCurso = T3.IdCurso"
                  + "   WHERE T3.IdCurso = " + IdCurso + " AND T1.Tipo = 'CP'"
                  + "   ) TF");

            if( rs.next() )
                ingresos = new BigDecimal(rs.getString(1));
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
        } catch( Exception x ){
            System.out.println("Exception: " + x);
        } finally{
            if( rs != null ){
                try { rs.close(); }
                catch( SQLException x ) { }
            }
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }
        
        return ingresos;
    }

    public static List<Entry<String,String>> getAlumFolios(int IdCurso){
        Statement state = null;
        ResultSet rs = null;
        List<Entry<String,String>> folios = new ArrayList<>();
        SimpleEntry<String,String> fol;
        String key;
        
        try {
            Connect();
            state = conn.createStatement();
            rs = state.executeQuery(
                    "SELECT"
                  + "   T1.ApPaterno,"
                  + "   T1.ApMaterno,"
                  + "   T1.Nombre,"
                  + "   T2.FolioDeposito "
                  + "FROM"
                  + "   tblPersona T1"
                  + "   JOIN tblAlumno_Curso T2 ON T1.IdPersona = T2.IdAlumno "
                  + "WHERE IdCurso = " + IdCurso + " "
                  + "ORDER BY ApPaterno, ApMaterno, Nombre");

            while( rs.next() ){
                key = rs.getString(1) == null ? "" : rs.getString(1);
                key += " " + (rs.getString(2) == null ? "" : rs.getString(2));
                key += " " + (rs.getString(3) == null ? "" : rs.getString(3));
                key = key.trim();
                fol = new SimpleEntry(key,
                        rs.getString(4) == null ? "" : rs.getString(4));
                
                folios.add(fol);
            }
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
        } catch( Exception x ){
            System.out.println("Exception: " + x);
        } finally{
            if( rs != null ){
                try { rs.close(); }
                catch( SQLException x ) { }
            }
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }
        
        return folios;
    }
}
