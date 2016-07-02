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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alejandro Alberto Yescas Benítez
 */
public class Grupo {
    private int ID;
    private Instructor Instructor;
    private Curso Curso;
    private String Nombre;
    private String Aula;
    private Map<Alumno,Double> Alumnos;
    
    public Grupo(int id, Instructor ins, Curso c){
        ID = id;
        Instructor = ins;
        Curso = c;
        Nombre = null;
        Aula = null;
        Alumnos = new HashMap<>();
    }

    public int getID(){ return ID; }
    public Instructor getInstructor(){ return Instructor; }
    public Curso getCurso(){ return Curso; }
    public String getNombre(){ return Nombre; }
    public String getAula(){ return Aula; }
    public List<Alumno> getAlumnos(){ return new ArrayList<>(Alumnos.keySet()); }
    public double getCalificacion(Alumno a){ return Alumnos.get(a); }

    public void setID(int ID){ this.ID = ID; }
    public void setInstructor(Instructor ins){ this.Instructor = ins; }
    public void setCurso(Curso Curso){ this.Curso = Curso; }
    public void setNombre(String Nombre){ this.Nombre = Nombre; }
    public void setAula(String Aula){ this.Aula = Aula; }
    public void addAlumno(Alumno a){ this.Alumnos.put(a, -1.0); }
    public void addAlumno(Alumno a, double c){ this.Alumnos.put(a, c); }
    public void setCalificacion(Alumno a, double c){ this.Alumnos.replace(a, c); }
}
