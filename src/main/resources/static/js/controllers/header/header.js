define(['jquery', 'bootstrap',],
    function(jquery, bootstrap ){

    $("#btnSelecionarEmpresa").click(function(){
            var empresa = $("input[type='radio']:checked").val();
            var token = $("input[name='_csrf']").val();
            $.ajax({ 
                url: "login/trocarEmpresa", 
                method: 'POST', 
                contentType: 'application/json',
                headers : { 'X-CSRF-TOKEN' : token},
                data: empresa, 
                success: function() {
                    $('#modalSelecaoEmpresa').modal('hide');
                    location.reload();
                }, 
                error: function(){
                    $('#modalSelecaoEmpresa').modal('hide');
                    $('#modalErroSelecaoEmpresa').modal('show');
                    $("html,body").css({"overflow":"hidden"});
                }, 
            }); 
    });

    var obterLarguraTela = function(){
        var janela = $(window).width()
        
        mostrarCampoEmpresaDesktop();
        mostrarCampoEmpresaMobile();
        
        $(window).resize(function(){
            if(janela <= 768){
                mostrarCampoEmpresaMobile()
            }else if(janela >=769){
                mostrarCampoEmpresaDesktop();
            } 
        })
    }

    var mostrarCampoEmpresaDesktop = function(){
        var tamanhoTexto = $('#nameEmpresa').text().trim();
        $('.company-data').fadeIn('1000');
        if(tamanhoTexto.length > 20){
            $('#nameEmpresa').text(tamanhoTexto.substring(0,20)+ "...");
        }  
    }

    var mostrarCampoEmpresaMobile = function(){
        var tamanhoTexto = $('#nameEmpresaMobile').text().trim();
        $('.company-data-mobile').fadeIn('1000');
        if(tamanhoTexto.length > 30){
            $('#nameEmpresaMobile').text(tamanhoTexto.substring(0,30)+ "...");
        }
    }

    var verificarCampoUsuario = function(){
        var nomeUser = $('#nameUsuario').text();
        var firstNome = nomeUser.split(' ')[0];
        $('#nameUsuario').text(firstNome);
        $('.personal-data').fadeIn('1000');
    }

    var mostrarMenu = function(){
        $('.item-menu > a').click(function() {
            $(this).children('.sub-menu').slideToggle(300);
            $(this).children('.icon-caret').toggleClass('fa-rotate-90');
            $(this).next('.sub-menu').stop(true, false, true).slideToggle(300);
            return false;
        });
    }

    var mostrarSaudacao = function(){
        var date = new Date();
        var hora = date.getHours()
        var campo = $("#campoSaudacao");
        if(hora >= 0 && hora < 12){
            $(campo).text("Bom dia!");
        }else if(hora >= 12 && hora < 18){
            $(campo).text("Boa tarde!");
        }else if(hora >= 18 && hora < 24){
            $(campo).text("Boa noite!") 
        }else{
            $(campo).text("Olá!")
        }
        return campo;
    }

    verificarCampoUsuario();
    mostrarMenu();
    mostrarSaudacao();
    obterLarguraTela();
    
});