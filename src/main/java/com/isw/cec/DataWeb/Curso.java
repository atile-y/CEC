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
package com.isw.cec.DataWeb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;

/**
 *
 * @author Alejandro Alberto Yescas Benítez
 */
public class Curso implements Serializable{
    public enum Estado{ PRE, OPENED, CLOSED };
    
    private int ID;
    private String Nombre;
    private BigDecimal CostoGeneral;
    private BigDecimal CostoComunidad;
    private Time HoraInicio;
    private Time HoraFinal;
    private String Periodo;
    private String Temario;
    private int MinAlumnos;
    private int MaxAlumnos;
    private boolean Evaluacion;
    private Estado Estado;
    private String Folios;

    public Curso(int id){
        ID = id;
        Nombre = null;
        CostoGeneral = null;
        CostoComunidad = null;
        HoraInicio = null;
        HoraFinal = null;
        Periodo = null;
        Temario = null;
        MinAlumnos = -1;
        MaxAlumnos = -1;
        Evaluacion = false;
        Estado = null;
        Folios = null;
    }
    
    public Curso(int id, String name){
        ID = id;
        Nombre = name;
        CostoGeneral = null;
        CostoComunidad = null;
        HoraInicio = null;
        HoraFinal = null;
        Periodo = null;
        Temario = null;
        MinAlumnos = -1;
        MaxAlumnos = -1;
        Evaluacion = false;
        Estado = null;
        Folios = null;
    }

    public int getID(){ return ID; }
    public String getNombre(){ return Nombre; }
    public BigDecimal getCostoGeneral(){ return CostoGeneral; }
    public BigDecimal getCostoComunidad(){ return CostoComunidad; }
    public Time getHoraInicio(){ return HoraInicio; }
    public Time getHoraFinal(){ return HoraFinal; }
    public String getPeriodo(){ return Periodo; }
    public String getTemario(){ return Temario; }
    public int getMinAlumnos(){ return MinAlumnos; }
    public int getMaxAlumnos(){ return MaxAlumnos; }
    public boolean isEvaluacion(){ return Evaluacion; }
    public Estado getEstado(){ return Estado; }
    public String getFolios(){ return Folios; }

    public void setID(int ID){ this.ID = ID; }
    public void setNombre(String Nombre){ this.Nombre = Nombre; }
    public void setCostoGeneral(BigDecimal CG){ this.CostoGeneral = CG; }
    public void setCostoComunidad(BigDecimal CC){ this.CostoComunidad = CC; }
    public void setHoraInicio(Time HoraInicio){ this.HoraInicio = HoraInicio; }
    public void setHoraFinal(Time HoraFinal){ this.HoraFinal = HoraFinal; }
    public void setPeriodo(String Periodo){ this.Periodo = Periodo; }
    public void setTemario(String Temario){ this.Temario = Temario; }
    public void setMinAlumnos(int MinAlumnos){ this.MinAlumnos = MinAlumnos; }
    public void setMaxAlumnos(int MaxAlumnos){ this.MaxAlumnos = MaxAlumnos; }
    public void setEvaluacion(boolean Eval){ this.Evaluacion = Eval; }
    public void setEstado(Estado Estado){ this.Estado = Estado; }
    public void setFolios(String Folios){ this.Folios = Folios; }
}
