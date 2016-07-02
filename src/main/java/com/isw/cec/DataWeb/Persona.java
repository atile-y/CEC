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

/**
 *
 * @author Alejandro Alberto Yescas Benítez
 */
public class Persona {
    protected int ID;
    protected String Nombre;
    protected String ApPaterno;
    protected String ApMaterno;
    protected String Correo;
    protected String Telefono;
    
    public Persona(int id){
        ID = id;
        Nombre = null;
        ApPaterno = null;
        ApMaterno = null;
        Correo = null;
        Telefono = null;
    }

    public Persona(int id, String name){
        ID = id;
        Nombre = name;
        ApPaterno = null;
        ApMaterno = null;
        Correo = null;
        Telefono = null;
    }

    public Persona(int id, String name, String appat){
        ID = id;
        Nombre = name;
        ApPaterno = appat;
        ApMaterno = null;
        Correo = null;
        Telefono = null;
    }
    
    public int getID(){ return ID; }
    public String getNombre(){ return Nombre; }
    public String getApPaterno(){ return ApPaterno; }
    public String getApMaterno(){ return ApMaterno; }
    public String getCorreo(){ return Correo; }
    public String getTelefono(){ return Telefono; }

    public void setID(int ID){ this.ID = ID; }
    public void setNombre(String Nombre){ this.Nombre = Nombre; }
    public void setApPaterno(String ApPaterno){ this.ApPaterno = ApPaterno; }
    public void setApMaterno(String ApMaterno){ this.ApMaterno = ApMaterno; }
    public void setCorreo(String Correo){ this.Correo = Correo; }
    public void setTelefono(String Telefono){ this.Telefono = Telefono; }

    public String getNombreCompleto(){
        String nombre = "";
        
        if( Nombre != null )
            nombre = Nombre;
        if( ApPaterno != null )
            nombre += " " + ApPaterno;
        if( ApMaterno != null )
            nombre += " " + ApMaterno;
        
        return nombre.trim();
    }
}
