;(function(undefined){


    require.config({
        baseUrl: '/js/',
        paths: {
            /*Libs*/
            jquery: 'libs/jquery-3.2.1.min',
            bootstrap : 'libs/bootstrap.min',
            selectpicker : 'libs/bootstrap-select.min',
            datepicker : 'libs/bootstrap-datepicker.min',
            daterangepicker : 'libs/daterangepicker',
            jqueryMask : 'libs/jquery.mask',
            dataTables : 'libs/datatables.min',
            recaptcha: '//www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit',
            //jspdf
            jspdf : 'libs/jspdf.min',
            addHtml: 'libs/addHtml',
            html2: 'libs/html2canvas.min',

            /*config languages components/masks*/
            i18n : 'libs/defaults-pt_BR',
            moment: 'libs/moment.min',
            momentConfig : 'libs/moment-config',
            mascaraValidacao : 'libs/mascaraValidacao',
        
            /*components default */
            components: 'components',

            /*controllers*/
            login: 'controllers/login/login',
            uploadNotaFiscal: 'controllers/notaFiscal/uploadNotaFiscal',
            consultarNotaFiscal: 'controllers/notaFiscal/consultaNotaFiscal',
            uploadResumoMedico: 'controllers/uploadResumoMedico/uploadResumoMedico',
            demonstrativos: 'controllers/demonstrativos/demonstrativos',
            modalTrocaEmpresa: 'controllers/modalEmpresa/modalTrocaEmpresa',
            header: 'controllers/header/header',
            
            /*cadastros*/
            consultaEmpresa: 'controllers/cadastros/cadastroEmpresa/consultaEmpresa',
            consultaMedico: 'controllers/cadastros/cadastroMedico/consultaMedico'
        },
        
        shim:{
            bootstrap:{
                deps:['jquery'],
                exports:'bootstrap'
            },
            selectpicker:{
                deps:['jquery', 'bootstrap'],
                exports:'selectpicker'
            },
            datepicker:{
                deps:['jquery'],
                exports:'datepicker'
            },
            daterangepicker:{
                deps:['jquery', 'momentConfig'],
                exports:'daterangepicker'
            },
            i18n:{
                deps:['jquery', 'selectpicker', 'datepicker'],
                exports:'i18n'
            },
            momentConfig:{
                deps:['moment'],
                exports:'momentConfig' 
            },
            recaptcha: { 
                exports: 'recaptcha' 
            },
            components:{
                deps:['jquery', 'bootstrap'],
                exports:'momentConfig' 
            },
            addHtml:{
                deps:['jquery', 'jspdf', 'html2'],
                exports:'addHtml' 
            },
            mascaraValidacao:{
                deps:['jqueryMask'],
                exports:'mascaraValidacao',
            },
           /* html2:{
                deps:['addHtml'],
                exports:'addHtml' 
            },*/

            /*controllers*/
            login:{
               deps:['recaptcha','jquery', 'bootstrap', ],  
               exports:'login' 
            },
            consultarNotaFiscal:{
               deps:['components', ],  
               exports:'consultarNotaFiscal' 
            },
          /*  demonstrativos:{
               deps:['components'],  
               exports:'demonstrativos' 
            }*/

        },
    });

    
   
})();

var onloadCallback = function(){
    if (!document.getElementById('g-recaptcha')){
        return;
    }
    recaptchaClientId = grecaptcha.render("g-recaptcha",{
        "sitekey": "6LcLNzMUAAAAALkGQ0-OuRnVGDSmypC0OqN3n_L9",
        "callback": onReCaptchaSuccess,
        "expired-callback":onReCaptchaExpired,
    });
}
var onReCaptchaSuccess = function(response) {
    $("#captchaError").html("").hide();
};

var onReCaptchaExpired = function(response) {
    grecaptcha.reset();
};