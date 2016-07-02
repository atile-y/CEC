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

$( document ).ready( coordinador );

function coordinador(){
    $("#cursos > p").click(function(){
        $("#cursos > p").removeClass("curso-selected");
        $(this).addClass("curso-selected");
        $.get("coor_cursos.jsp", {idx: $(this).attr("id")}, function(data) {
            $( "#content" ).html(data);
            coor_cursos();
        }, "html");
    });
    
    $("#nuevo-curso").click(function(){
        $("ul#acciones").hide();
        $("#agregar-curso").show().children("#input-curso").val("");
    });
    
    $("#input-curso").focusin(function(){
        $(this).css("border", "2px inset");
    });
    
    $("#aceptar-curso").click(function(){
        var $curso = $("#input-curso");
        if( $curso.val() === "" ){
            $curso.css("border", "2px #CC0000 solid");
            return;
        }
        $.post("guardar_curso.jsp", {
                nuevo: "nuevo",
                nombre: $curso.val()
            }, function(data){
                data = data.trim();
                if( data !== "OK" )
                    alert("Error al guardar el curso.");
                location.reload();
            }, "text");
    });

    $("#cancelar-curso").click(function(){
        $("#agregar-curso").hide().children("#input-curso").
                val("").trigger("focusin");
        $("ul#acciones").show();
    });
    
    $("#borrar-curso").click(function(){
        var $curso = $(".curso-selected.PRE");
        if( $curso.length !== 1 )
            return;
        
        if( confirm("¿Seguro que quiere borrar \""+$curso.html()+"\"?") ){
            $.post("borrar_curso.jsp", {},
                function(data) {
                    data = data.trim();
                    if( data !== "OK" )
                        alert("Error al borrar el curso.");
                    location.reload();
                }, "text");
        }
    });
    
    $("#logout > img").click(function(){
        window.location = "../login.jsp";
    });
}
