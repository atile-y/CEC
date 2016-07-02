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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alejandro Alberto Yescas Benítez
 */
public class Connector {
    protected static final String URL="jdbc:mysql://localhost/test";
    protected static final String USER="test";
    protected static final String PASS="test";
    protected static Connection conn=null;
    private static boolean initialized = false;
    
    protected static void Connect() throws Exception{
        try {
            if( !initialized ){
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                initialized = true;
            }
            if( conn == null )
                conn = DriverManager.getConnection(URL, USER, PASS);
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException x ){
            System.out.println(x);
            initialized = false;
            throw new Exception(x.getMessage());
        } catch( SQLException x ){
            System.out.println("SQLException: " + x.getMessage());
            conn = null;
            throw new SQLException(x.getMessage());
        }
    }
}
