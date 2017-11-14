define(['jquery','bootstrap', 'components'],
    function($, bootstrap, components){
		
		$('#btEntrar').click(function(event){
			event.preventDefault();
			var formData= $('form').serialize();
			if (typeof grecaptcha !== 'undefined') {
				var resp = grecaptcha.getResponse();
				if (resp.length == 0) {
					//$("#modalValidacaoCaptcha").show()
					$("#captchaError").show().html("Por favor valide o captcha");
					return false;
				}
			}

			$.ajax({
				url : "/user/registrationCaptcha",
				method : 'POST',
				data : formData,
				success : function() {
					$("#formLogin").submit();
				},
				error : function(data){
					$("#captchaError").html(data.responseJSON.message).show();
					grecaptcha.reset();
					return;
				}
			});
		});

		$('#password').focus(function(e){
			var that = $(this)
			that.tooltip('show');
			setTimeout(function() {
				that.tooltip('hide');
			}, 5000);
		});

		components.mostrarModalResposta();
});
