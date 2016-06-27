var ForgotPassword = function() {
	var handleforgotPassword = function() {
		
		jQuery("#progress").hide();
		jQuery("#send-forgot-password").click(function(event) {

			event.preventDefault();
			jQuery.ajax({
				type: "POST",
				url: basePath + "/xhr/forgot-password/"+ jQuery("#forgotPasswordEmail").val() +".json",
				contentType: "application/json",
				dataType : 'json',
				beforeSend: function () {
					jQuery( "#progress").show();
					jQuery( "#forgot-error-div label" ).html("");
					jQuery( "#forgot-error-div" ).hide();
					jQuery( "#forgot-success-div" ).hide();
					jQuery( "#send-forgot-password" ).hide();
					//xhr.setRequestHeader('Content-Type', 'application/json');
				}
			}).done(function(data) {
				
				if(data!==null){
					jQuery( "#forgot-success-div").show();
				}
				
			}).fail(function(err) {
				
				if(err.status === 400){
					jQuery( "#forgot-error-div").show();
					jQuery( "#forgot-error-div label" ).html(err.responseJSON.messages[0].message);
				}	
				
			}).always(function() {
				
				jQuery( "#progress").hide();
				jQuery( "#send-forgot-password" ).show();
				
			});
		});
		
		$('#forgotPassForm').validate({
			focusFirstInput: true,
			errorClass: 'help-block',
			errorElement: 'div',
            errorPlacement: function(error, e) {
            	e.parents('.form-group').append(error);
            },
            highlight: function(e) {
                $(e).closest('.form-group').removeClass('has-success has-error').addClass('has-error');
                $(e).closest('.help-block').remove();
            },
            success: function(e) {
                e.closest('.form-group').removeClass('has-success has-error').addClass('has-success');
                e.closest('.help-block').remove();
            },
            rules: {
            	forgotPasswordEmail: {
                    required: true,
                    email: true
                }
            },
            messages: {
            	forgotPasswordEmail: {
                    required: 'Please enter a email.',
                    email: 'Please enter a valid email.'
                }
            }
		 });
	};
	
	return {
		// main function to initiate the module
		init : function() {

			handleforgotPassword();
		}
	};
}();
