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

function coor_cursos() {
    validateData();
    
    $( "#mod-editar-curso" ).click(function() {
        $(this).hide().siblings().show();
        
        var ps = $(this).parent().siblings().children("p");
        var inputs = $(ps).siblings("input");
        
        inputs[0].value = ps[0].innerHTML;
        inputs[1].value = ps[1].innerHTML === "-" ? "" : ps[1].innerHTML;
        inputs[2].value = ps[2].innerHTML === "-" ? "" : ps[2].innerHTML;
        inputs[3].value = ps[3].innerHTML === "--:--" ? "" : ps[3].innerHTML;
        inputs[4].value = ps[4].innerHTML === "--:--" ? "" : ps[4].innerHTML;
        inputs[5].value = ps[5].innerHTML === "-" ? "" : ps[5].innerHTML;
        inputs[7].value = ps[7].innerHTML === "_" ? "" : ps[7].innerHTML;
        inputs[8].value = ps[8].innerHTML === "_" ? "" : ps[8].innerHTML;
        if( ps[9].innerHTML === "Sí" ){
            inputs[9].checked = true;
            inputs[10].checked = false;
        }
        else{
            inputs[9].checked = false;
            inputs[10].checked = true;
        }
        
        $(inputs[6]).siblings("label").css("width", "195px");
        $(ps).hide();
        $(inputs).show().siblings("span").show();
    });

    $( "input" ).focusin(function() {
        if( $(this).attr("type") === "file" )
            $(this).css("border", "0px inset");
        else
            $(this).css("border", "2px inset");
    });
    
    $( "#mod-aceptar-curso" ).click(function() {
        var valido = true;
        var reCosto = /^\d{1,6}(\.\d{1,2})?$/g;
        var reHora = /^([01]\d|2[0-3]):[0-5]\d$/g;
        
        var dataForm = new FormData($("form#info-curso")[0]);
        var inputs = $(this).parent().siblings().children("input");
        
        if( dataForm.get("nombre") === "" ){
            $(inputs[1]).css("border", "2px #CC0000 solid");
            valido = false;
        }
        if( dataForm.get("costoPG").match(reCosto) === null && dataForm.get("costoPG") !== "" ){
            $(inputs[2]).css("border", "2px #CC0000 solid");
            valido = false;
        }
        if( dataForm.get("costoCP").match(reCosto) === null && dataForm.get("costoCP") !== "" ){
            $(inputs[3]).css("border", "2px #CC0000 solid");
            valido = false;
        }
        if( dataForm.get("horaIni").match(reHora) === null && dataForm.get("horaIni") !== "" ){
            $(inputs[4]).css("border", "2px #CC0000 solid");
            valido = false;
        }
        if( dataForm.get("horaFin").match(reHora) === null && dataForm.get("horaFin") !== "" ){
            $(inputs[5]).css("border", "2px #CC0000 solid");
            valido = false;
        }
        var fileName = dataForm.get("temario").name;
        if( fileName === undefined )
            fileName = "";
        if( !fileName.endsWith(".pdf") && fileName !== "" ){
            $(inputs[7]).css("border", "2px #CC0000 solid");
            valido = false;
        }
        if( dataForm.get("minAlum").match(/^\d+$/g) === null && dataForm.get("minAlum") !== "" ){
            $(inputs[8]).css("border", "2px #CC0000 solid");
            valido = false;
        }
        if( dataForm.get("maxAlum").match(/^\d+$/g) === null && dataForm.get("maxAlum") !== "" ){
            $(inputs[9]).css("border", "2px #CC0000 solid");
            valido = false;
        }
        
        if( !valido )
            return;
        
        $.post({
            url: "GuardarCurso",
            data: dataForm,
            contentType: false,
            processData: false,
            dataType: "text",
            success: function(data){
                data = data.trim();
                if( data === "OK" ){
                    var valido = true;
                    var ps = $("#info-curso p");
                    var dataForm = new FormData($("#info-curso")[0]);
                    
                    ps[0].innerHTML = dataForm.get("nombre");
                    
                    var str = dataForm.get("costoPG");
                    if( str === "" ){
                        ps[1].innerHTML = "-";
                        valido = false;
                    }
                    else
                        ps[1].innerHTML = str;
                    
                    str = dataForm.get("costoCP");
                    if( str === "" ){
                        ps[2].innerHTML = "-";
                        valido = false;
                    }
                    else
                        ps[2].innerHTML = str;
                    
                    str = dataForm.get("horaIni");
                    if( str === "" ){
                        ps[3].innerHTML = "--:--";
                        valido = false;
                    }
                    else
                        ps[3].innerHTML = str;
                    
                    str = dataForm.get("horaFin");
                    if( str === "" ){
                        ps[4].innerHTML = "--:--";
                        valido = false;
                    }
                    else
                        ps[4].innerHTML = str;

                    str = dataForm.get("periodo");
                    if( str === "" ){
                        ps[5].innerHTML = "-";
                        valido = false;
                    }
                    else
                        ps[5].innerHTML = str;

                    str = dataForm.get("temario").name;
                    if( str === undefined )
                        str = "";
                    if( str !== "" ){
                        ps[6].innerHTML = "<a href=\"/CEC/uploadFiles/temarios/" +
                            str + "\" target=\"_blank\">" +
                            str + "</a>";
                    }
                    if( ps[6].innerHTML === "Sin Temario" )
                        valido = false;
                    
                    str = dataForm.get("minAlum");
                    if( str === "" ){
                        ps[7].innerHTML = "_";
                        valido = false;
                    }
                    else
                        ps[7].innerHTML = str;
                    
                    str = dataForm.get("maxAlum");
                    if( str === "" ){
                        ps[8].innerHTML = "_";
                        valido = false;
                    }
                    else
                        ps[8].innerHTML = str;

                    ps[9].innerHTML = dataForm.get("evaluacion")==="1"?"S&iacute;":"No";

                    $("#info-curso input[name=valido]").val(valido ? 1 : 0);

                    $("#mod-cancelar-curso").trigger("click");
                }
                else
                    alert("Error al guardar.");
            }});
    });

    $( "#mod-cancelar-curso" ).click(function() {
        $(this).hide().siblings("#mod-aceptar-curso").hide().
                siblings("#mod-editar-curso").show().
                parent().siblings().children("input").
                trigger("focusin").hide().
                siblings("p").show().siblings("span").hide();
        $("#temario-curso").children("label").css("width", "300px");
    });
    
    $( ".in-num" ).focus(function(){
        $(this).attr("oldValue", $(this).val());
    });
    
    $( ".in-num" ).blur(function(){
        inNum($(this));
    });
    
    $( "#add-grupo" ).click(function(){
        var $inp = $("input[name=numGrupos]");
        var NG = $inp.val();
        
        if( $("#alum-rest").html() === "0" )
            return;
        
        var $ng = $("#new-grupo");
        $ng.html(
            '<td><input class="in-grupo" name="grupo'+NG+'" type="text" maxlength="10"></td>' +
            '<td><input class="in-aula" name="aula'+NG+'" type="text" maxlength="10"></td>' +
            '<td><select class="in-instructor" name="instructor'+NG+'">' +
                '<option value="-"></option>' +
            '</select></td>' +
            '<td><input class="in-num" name="numAlum'+NG+'" type="text" maxlength="4"</td>' +
            '<td><div class="delete-grupo"></div></td>');
        $ng.removeAttr("id");
        $ng.after('<tr id="new-grupo"></tr>');

        $inp.val(parseInt(NG)+1);
        
        $.get({
            url: "getInstructores.jsp",
            success: function(data){
                data = data.trim();
                $ng.find("select.in-instructor").html(data);
            },
            dataType: "html"
        });
        
        var $in = $ng.find(".in-num");
        $in.focus(function(){
            $(this).attr("oldValue", $(this).val());
        });
    
        $in.blur(function(){
            inNum($(this));
        });

        $ng.find(".delete-grupo").click(function(){
            deleteGrupo($(this).parent().parent());
        });
    });
    
    $( ".delete-grupo" ).click(function(){
        deleteGrupo($(this).parent().parent());
    });
    
    $( "select" ).focusin(function() {
        $(this).css("border", "1px solid #A9A9A9");
    });
    
    $( "#alum-rest" ).mouseover(function(){
        $(this).css("border", "1px solid white");
    });
    
    $( "#abrir-curso > .button" ).click(function(){
        if( $("#info-curso input[name=valido]").val() === "0" ){
            $("#mod-editar-curso").trigger("click");
            $("#nombre-curso input").trigger("focus");
            return;
        }
        
        var valido = true;
        
        var dataForm = new FormData($("form#apertura-curso")[0]);
        var inputs = $(this).parent().prev().find("input");
        var selects = $(this).parent().prev().find("select");
        var numG = parseInt(dataForm.get("numGrupos"));
        var $alumR = $("#alum-rest");
        
        for(var i=0;i<numG;i++){
            if( dataForm.get("grupo"+i) === "" ){
                $(inputs[3*i + 0]).css("border", "2px #CC0000 solid");
                valido = false;
            }
            if( dataForm.get("aula"+i) === "" ){
                $(inputs[3*i + 1]).css("border", "2px #CC0000 solid");
                valido = false;
            }
            if( dataForm.get("instructor"+i) === "-" ){
                $(selects[i]).css("border", "1px #CC0000 solid");
                valido = false;
            }
            if( dataForm.get("numAlum"+i) === "" ){
                $(inputs[3*i + 2]).css("border", "2px #CC0000 solid");
                valido = false;
            }
        }
        
        if( $alumR.html() !== "0" ){
            $alumR.css("border", "2px #CC0000 solid");
            valido = false;
        }
        
        if( !valido )
            return;
        
        var minAlum = $("#cupo-curso > p")[0].innerHTML;
        var maxAlum = $("#cupo-curso > p")[1].innerHTML;

        minAlum = minAlum === "_" ? 0 : parseInt(minAlum);
        maxAlum = maxAlum === "_" ? 10000 : parseInt(maxAlum);

        for(var i=0;i<numG;i++){
            var numAlum = parseInt(dataForm.get("numAlum"+i));
            if( numAlum < minAlum || numAlum > maxAlum ){
                $(inputs[3*i + 2]).css("border", "2px #CC0000 solid");
                valido = false;
            }
        }

        if( !valido ){
            var msg = "Algunos grupos no cumplen con el cupo asignado. ";
            msg += "¿Desea continuar?";
            if( !confirm(msg) )
                return;
        }
        
        var data = {};
        dataForm.forEach(function(v, k){
            data[k] = v;
        });
        
        $.post("abrir_curso.jsp",
            data,
            function(data){
                data = data.trim();
                console.log(data);
                if( data === "OK" ){
                    $("#apertura-curso").hide();
                    $("#info-extra-curso").show();
                    location.reload();
                }
                else
                    alert("Error al abrir el curso.");
            }, "text");
    });
}

function validateData(){
    var valido = true;
    
    if( $("#nombre-curso > p").html() === "" )
        valido = false;
    if( $("#costoPG-curso > p").html() === "-" )
        valido = false;
    if( $("#costoCP-curso > p").html() === "-" )
        valido = false;
    if( $("#horario-curso > p")[0].innerHTML === "--:--" )
        valido = false;
    if( $("#horario-curso > p")[1].innerHTML === "--:--" )
        valido = false;
    if( $("#periodo-curso > p").html() === "-" )
        valido = false;
    if( $("#temario-curso > p").html() === "Sin Temario" )
        valido = false;
    if( $("#cupo-curso > p")[0].innerHTML === "_" )
        valido = false;
    if( $("#cupo-curso > p")[1].innerHTML === "_" )
        valido = false;
    
    $("#info-curso input[name=valido]").val(valido ? 1 : 0);
}

function inNum($t){
    var oldValS = $t.attr("oldValue");
    var newValS = $t.val();

    if( newValS.match(/^\d+$/g) === null && newValS !== "" ){
        $t.val(oldValS);
        return;
    }

    var alres = parseInt($("#alum-rest").html());
    var newVal = newValS === "" ? 0 : parseInt(newValS);
    var oldVal = oldValS === "" ? 0 : parseInt(oldValS);

    if( newVal - oldVal > alres ){
        $t.val(oldValS);
        return;
    }

    $("#alum-rest").html(alres - newVal + oldVal);
}

function deleteGrupo($row){
    var $inp = $("input[name=numGrupos]");
    var NG = parseInt($inp.val());
    var $alres = $("#alum-rest");
    var alres = parseInt($alres.html());
    
    if( $row.attr("id") === "primer-grupo" ){
        if( NG === 1 )
            return;
        $row.next().attr("id", "primer-grupo");
    }

    $row.nextAll().each(function(i, e){
        var $ele = $(e);
        $ele.find("input").each(function(i, e){
            var $ele = $(e);
            var name = $ele.attr("name").match(/[^\d]+/g);
            var num = parseInt($ele.attr("name").match(/\d+/g));
            $ele.attr("name", name + (num - 1));
        });
        $ele.find("select").each(function(i, e){
            var $ele = $(e);
            var name = $ele.attr("name").match(/[^\d]+/g);
            var num = parseInt($ele.attr("name").match(/\d+/g));
            $ele.attr("name", name + (num - 1));
        });
    });
    var numal = $row.find(".in-num").val();
    numal = numal === "" ? 0 : parseInt(numal);
    $alres.html(alres + numal);
    $row.remove();

    $inp.val(NG-1);
}
