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
package com.isw.cec.Servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Alejandro Alberto Yescas Benítez
 */
@WebServlet("/coordinador/GuardarCurso")
@MultipartConfig(location = "uploadFiles/temarios",
                 fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
                 maxFileSize       = 1024 * 1024 * 10, // 10MB
                 maxRequestSize    = 1024 * 1024 * 50) // 50MB
public class GuardarCursoServlet extends HttpServlet {
    private static final String SAVE_DIR = "uploadFiles"+File.separator+"temarios";
    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + SAVE_DIR;
        
        File fileSaveDir = new File(savePath);
        if( !fileSaveDir.exists() )
            fileSaveDir.mkdirs();
        
        Part part = request.getPart("temario");
        String fileName = extractFileName(part);
        if( !fileName.equals("") ){
            OutputStream out = null;
            InputStream fileContent = null;
            
            try {
                out = new FileOutputStream(new File(
                        savePath + File.separator + fileName));
                fileContent = part.getInputStream();
                
                int read;
                byte[] bytes = new byte[1024];
                
                while( (read=fileContent.read(bytes)) != -1 )
                    out.write(bytes, 0, read);
            } catch( FileNotFoundException x ){
                System.out.println(x);
            } finally {
                if( out != null )
                    out.close();
                if( fileContent != null )
                    fileContent.close();
            }
        }

        request.setAttribute("temario", fileName);
        getServletContext().getRequestDispatcher("/coordinador/guardar_curso.jsp").forward(request, response);
    }
    
    private String extractFileName(Part part){
        String[] items = part.getHeader("content-disposition").split(";");
        for(String s : items)
            if( s.trim().startsWith("filename") )
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
        
        return "";
    }
}
