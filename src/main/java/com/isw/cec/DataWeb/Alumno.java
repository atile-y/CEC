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
public class Alumno extends Persona {
    public enum Tipo{ General, Comunidad };
    
    private String CURP;
    private Tipo Tipo;
    private Map<Grupo,Double> Grupos;
    
    public Alumno(int id, String curp, Tipo t){
        super(id);
        CURP = curp;
        Tipo = t;
        Grupos = new HashMap<>();
    }
    
    public String getCURP(){ return CURP; }
    public Tipo getTipo(){ return Tipo; }
    public List<Grupo> getGrupos(){ return new ArrayList<>(Grupos.keySet()); }
    public double getCalificacion(Grupo g){ return Grupos.get(g); }
    
    public void setCURP(String curp){ CURP = curp; }
    public void setTipo(Tipo t){ Tipo = t; }
    public void addGrupo(Grupo g){ Grupos.put(g, -1.0); }
    public void addGrupo(Grupo g, double c){ Grupos.put(g, c); }
    public void setCalificacion(Grupo g, double c){ Grupos.replace(g, c); }
}
