define(['jquery', 'dataTables', 'bootstrap', 'selectpicker', 'datepicker', 'i18n', 'daterangepicker', 'moment', 'momentConfig','header'], 
function($, dataTables , bootstrap, selectpicker, datepicker, i18n, daterangepicker, moment, momentConfig, header ){    
    
    

    var methodsComponents = {}
    
    
    methodsComponents.mostrarModalResposta = function(){
        $('#modalResposta').modal('show');
    }

    $('.register').on("click", function() {
        $('.menu-internal').slideToggle();
        
    })

    $('.btn-file').on('click', function(){
        $('.form-file-hidden').trigger('click');
            return false;
    })

    $('.form-file-hidden').on('change', function() {
        var fileName = $(this)[0].files[0].name;
        $('#copiaDocumentoMedicoFake').val(fileName);
    })

    methodsComponents.fakeUpload = function(originalInputId, changeCallback, classesExtra, placeholder){
        $('#'+originalInputId).after('<input type="text"' 
                +' id="'+originalInputId+'Fake"' 
                +' class="form-file '+ !!classesExtra ? classesExtra.join(' ') : ''+'"'
                +' placeholder="'+ !!placeholder ? placeholder : 'Escolha o arquivo'+'"'
                +' readonly="readonly">'
                +'<button type="button"'
                +' class="btn-file fa fa-upload"' 
                +' title="Selecione o arquivo"></button>');
        
        $('#'+originalInputId).hide();

        $('#'+originalInputId).on('change', function() {
            var fileName = $(this)[0].files[0].name;
            $('#'+originalInputId+'Fake').val(fileName);
            if(!!changeCallback) 
                changeCallback();
        });

        $('.btn-file').on('click', function() {
            $('#'+originalInputId).trigger('click');
            return false;
        });
    }

    function daterangePickerDefault(){
        var tipoPerfil = $("#campoTipoPerfil").val();
        
        var paramsDateLimit = {
            minDate: moment().subtract(18,'months').startOf('month'),
            maxDate: moment(),
            autoApply: false,
            locale: {
                format: 'DD/MM/YYYY',
                applyLabel: 'Ok',
            },
            autoUpdateInput: false,
            startDate: $("#inputStartDate").val() != '' ? $("#inputStartDate").val() : moment().format('DD/MM/YYYY'),
            endDate: $("#inputEndDate").val() != '' ? $("#inputEndDate").val() : moment().format('DD/MM/YYYY'),
        }


        $('#inputStartDate').on('hide.daterangepicker', function(ev, picker) {
            updateTextDate(picker.startDate.format('DD/MM/YYYY'), picker.endDate.format('DD/MM/YYYY'));
        });

        $('#inputStartDate').on('cancel.daterangepicker', function(ev, picker) {
            updateTextDate("", "");
        });                         
        
        if(tipoPerfil == "CREDENCIAMENTO"){
            paramsDateLimit.dateLimit = {
                days: 60,
            }
        }
        else if(tipoPerfil == "MEDICO"){
            paramsDateLimit.dateLimit = {
                days: moment(),
            }
        }
        if ($('#inputStartDate').val() != ''){
            updateTextDate($('#inputStartDate').val(), $('#inputEndDate').val());
        } else {
            updateTextDate("", "");
        }

        $('#inputStartDate').daterangepicker(paramsDateLimit);
    }
    
    function updateTextDate(inicio, fim) {
        $('#inputStartDate').val(inicio);
        $('#inputEndDate').val(fim);
    }
    $('#inputEndDate').on('focus', function() {
        $('#inputStartDate').focus();
    });

    /*Seleção unica de data atual ou até 2 meses para frente*/
    methodsComponents.dateRangePickerSimple = function(){
        $('.dateSimple').daterangepicker({
            minDate: moment(),
            maxDate: moment().add(2,'months'),
            autoApply: false,
            singleDatePicker: true,
            // autoUpdateInput: false,
            label:false,
            locale: {
                format: "DD/MM/YYYY",
                //viewMode: "months", 
                //minViewMode: "months"
            },
        },function(start, end, label){
        })
        //$('.dateSimple').on('apply.daterangepicker', function(ev, picker) {
        //  $(this).val(picker.startDate.format('MM/YYYY'));
        //});
    }
    
    /*Seleção de mês/ano upload NF*/
    methodsComponents.selecionarExercicio = function(){
        $('.monthPicker').datepicker({ 
            language:'pt-BR',
            format: "mm/yyyy",
            startDate: '-4m',
            endDate: '+2d',
            viewMode: "months", 
            minViewMode: "months",
            maxViewMode: "years",
            autoclose: true,
            orientation: 'bottom',
            templates : {
                leftArrow: '<i class="fa fa-chevron-left"></i>',
                rightArrow: '<i class="fa fa-chevron-right"></i>'
            }
        });
    }

    methodsComponents.monthpickerDefault = function(){
        $('.monthPickerDefault').datepicker({ 
            language:'pt-BR',
            format: "mm/yyyy",
            startDate: '-18m',
            endDate: '0',
            viewMode: "months", 
            minViewMode: "months",
            maxViewMode: "years",
            autoclose: true,
            orientation: 'bottom',
            templates : {
                leftArrow: '<i class="fa fa-chevron-left"></i>',
                rightArrow: '<i class="fa fa-chevron-right"></i>'
            }
        });
    }
    
    methodsComponents.carregarDataTablesSimple = function(){
        $('#dataTablesSimple').DataTable({  
            "bLengthChange":false,
            "bInfo":false,
            "searching":false,
            "language": {  
                "lengthMenu":"_MENU_ ",
                "zeroRecords":"Nenhum registro encontrado",
                "infoEmpty":"Mostrando 0 / 0 de 0 registros",
                "infoFiltered":"(filtrado de _MAX_ registros)",
                "paginate":{  
                    "first":"First page<font></font>",
                    "previous":"<i class= 'fa fa-chevron-left'></i>",
                    "next":"<i class = 'fa fa-chevron-right'></i>",
                    "last":"Último"
                }
            },
            "order":[ 0,"asc"],
            "scrollX":true
        });
    }

    return methodsComponents;
});