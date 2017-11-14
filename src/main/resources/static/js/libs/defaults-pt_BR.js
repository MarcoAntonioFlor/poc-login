/*
 * Translated default messages for bootstrap-select and bootstrap-datepicker
 * Locale: PT (Portuguese; português)
 * Region: BR (Brazil; Brasil)
 */
(function($) {
	$.fn.selectpicker.defaults = {
        style: 'btn-default',
        size: 'auto',
        title: 'Selecione',
        deselectAllText:'Nenhum',
        selectAllText:'Todos',
        selectedTextFormat : 'values',
        noneSelectedText : 'Nenhum selecionado',
        noneResultsText : 'Nenhum encontrado contendo',
		countSelectedText : 'Selecionado {0} de {1}',
        maxOptionsText: ['Limite excedido (máx. {n} {var})', 'Limite do grupo excedido (máx. {n} {var})', ['itens','item']],
        width: false,
        container: false,
        hideDisabled: false,
        showSubtext: false,
        showIcon: true,
        showContent: true,
        dropupAuto: true,
        header: false,
        liveSearch: false,
        actionsBox: false,
        multipleSeparator: ', ',
        iconBase: 'fa',
        tickIcon: 'fa-check',
        maxOptions: false
    };
    
	$.fn.datepicker.dates['pt-BR'] = {
	    days:["Domingo","Segunda","Terça","Quarta","Quinta","Sexta","Sábado"],
        daysShort:["Dom","Seg","Ter","Qua","Qui","Sex","Sáb"],
        daysMin:["Dom","Seg","Ter","Qua","Qui","Sex","Sáb"],
        months:["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"],
        monthsShort:["Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"],
        today:"Hoje",
        monthsTitle:"Meses",
        clear:"Limpar",
        format:"dd/mm/yyyy"
    }
}(jQuery));


        