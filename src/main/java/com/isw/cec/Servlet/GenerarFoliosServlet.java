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

import com.isw.cec.DataWeb.Curso;
import com.isw.cec.DBManager.Reader;
import com.isw.cec.DBManager.Writer;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alejandro Alberto Yescas Benítez
 */
@WebServlet("/coordinador/GenerarFolios")
public class GenerarFoliosServlet extends HttpServlet {
    private static final String SAVE_DIR = "uploadFiles"+File.separator+"folios";
    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + SAVE_DIR;
        
        File fileSaveDir = new File(savePath);
        if( !fileSaveDir.exists() )
            fileSaveDir.mkdirs();
        
        String fileName = java.util.UUID.randomUUID().toString() + ".pdf";
        Curso curso = (Curso)request.getAttribute("curso");
        List<Entry<String,String>> alum_fol = Reader.getAlumFolios(curso.getID());
        
        OutputStream fos = new FileOutputStream(savePath + File.separator + fileName);
        PdfWriter writer = new PdfWriter(fos);
        
        PdfDocument pdf = new PdfDocument(writer);
        
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(85, 57, 71, 71);

        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

        Paragraph p = new Paragraph("Folios de los alumnos inscritos al curso");
        p.setTextAlignment(TextAlignment.CENTER);
        document.add(p);
        
        p = new Paragraph(curso.getNombre());
        p.setFont(bold);
        p.setTextAlignment(TextAlignment.CENTER);
        p.setMarginBottom(70);
        document.add(p);
        
        Table table = new Table(new float[]{1, 1});
        table.setWidthPercent(100);
        
        Cell c = new Cell().add(new Paragraph("ALUMNO").setFont(bold));
        c.setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(c);
        
        c = new Cell().add(new Paragraph("FOLIO").setFont(bold));
        c.setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(c);
        
        for(Entry<String,String> e : alum_fol){
            table.addCell(new Cell().add(new Paragraph(e.getKey()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(e.getValue()).setFont(font)));
        }
        
        document.add(table);
        
        document.close();
        
        Writer.openCurso(curso.getID(), fileName);
        response.getWriter().println("OK");
    }
}
