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

import com.isw.cec.DataWeb.Curso;
import com.isw.cec.DataWeb.Grupo;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alejandro Alberto Yescas Benítez
 */
public class Writer extends Connector {
    public static boolean saveCurso(Curso c){
        Statement state = null;
        String query;
        
        try {
            Connect();
            state = conn.createStatement();
            
            if( c.getID() != -1 ){
                query = "UPDATE tblCurso SET NOMBRE = '" + c.getNombre() + "'";
                query += ", CostoGeneral = ";
                query += c.getCostoGeneral() == null ? "NULL" : c.getCostoGeneral();
                query += ", CostoComunidad = ";
                query += c.getCostoComunidad() == null ? "NULL" : c.getCostoComunidad();
                query += ", HoraInicio = ";
                query += c.getHoraInicio() == null ? "NULL" : "'"+c.getHoraInicio().toString().substring(0, 5)+"'";
                query += ", HoraFinal = ";
                query += c.getHoraFinal() == null ? "NULL" : "'"+c.getHoraFinal().toString().substring(0, 5)+"'";
                query += ", Periodo = ";
                query += c.getPeriodo() == null ? "NULL" : "'"+c.getPeriodo()+"'";
                query += ", Temario = ";
                query += c.getTemario() == null ? "NULL" : "'"+c.getTemario()+"'";
                query += ", MinAlumnos = ";
                query += c.getMinAlumnos() == -1 ? "NULL" : c.getMinAlumnos();
                query += ", MaxAlumnos = ";
                query += c.getMaxAlumnos() == -1 ? "NULL" : c.getMaxAlumnos();
                query += ", Evaluacion = ";
                query += c.isEvaluacion() ? "TRUE" : "FALSE";
                query += " WHERE IdCurso = " + c.getID() + ";";
            }
            else
                query = "INSERT INTO tblCurso(Nombre) VALUES('" + c.getNombre() + "')";

            if( state.executeUpdate(query) == 0 )
                return false;
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
            return false;
        } catch( Exception x ){
            System.out.println(x);
            return false;
        } finally{
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }

        return true;
    }

    public static boolean saveGrupo(Grupo g, List<Integer> ids){
        Statement state = null;
        ResultSet rs = null;
        String query;
        int IdG;
        
        try {
            Connect();
            state = conn.createStatement();
            
            if( g.getID() != -1 ){
                // TODO Update Grupo
            }
            else{
                query = "INSERT INTO tblGrupo(IdInstructor, IdCurso, Nombre";
                query += ", Aula) VALUES(" + g.getInstructor().getID();
                query += ", " + g.getCurso().getID();
                query += ", '" + g.getNombre() + "'";
                query += ", '" + g.getAula() + "')";
                
                if( state.executeUpdate(query, Statement.RETURN_GENERATED_KEYS) == 0 )
                    return false;
                
                rs = state.getGeneratedKeys();
                
                if( rs.next() )
                    IdG = rs.getInt(1);
                else
                    return false;
                
                for(int IdA : ids){
                    query = "INSERT INTO tblAlumno_Grupo(IdAlumno, ";
                    query += "IdGrupo) VALUES(" + IdA + ", " + IdG + ")";
                    
                    state.executeUpdate(query);
                }
            }
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
            return false;
        } catch( Exception x ){
            System.out.println(x);
            return false;
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

        return true;
    }

    public static boolean openCurso(int id, String folio){
        Statement state = null;
        String query;
        
        try {
            Connect();
            state = conn.createStatement();
            
            query = "UPDATE tblCurso SET Estado = 'OPENED', ";
            query += "Folios = '" + folio + "' ";
            query += "WHERE IdCurso = " + id;

            if( state.executeUpdate(query) == 0 )
                return false;
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
            return false;
        } catch( Exception x ){
            System.out.println(x);
            return false;
        } finally{
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }

        return true;
    }

    public static boolean deleteCurso(int id){
        Statement state = null;
        String query;
        
        try {
            Connect();
            state = conn.createStatement();
            
            query = "DELETE FROM tblCurso WHERE IdCurso = " + id;

            if( state.executeUpdate(query) == 0 )
                return false;
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            System.out.println("SQLState: " + x.getSQLState());
            System.out.println("VendorError: " + x.getErrorCode());
            return false;
        } catch( Exception x ){
            System.out.println(x);
            return false;
        } finally{
            if( state != null ){
                try{ state.close(); }
                catch( SQLException x ) { }
            }
        }

        return true;
    }
}
