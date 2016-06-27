var Login = function() {
	var handleLogin = function() {

		jQuery("#btnCancel").click(function() {
			jQuery("#username").val("");
			jQuery("#password").val("");
			jQuery("#passwordText").val("");
		});

		document.body.onkeypress = enterKey;
		function enterKey(evt) {
			evt = (evt) ? evt : event;
			var charCode = (evt.which) ? evt.which : evt.keyCode;
			if (charCode === 13) {
				document.loginForm.submit();
			}
		};
		$('#loginForm').validate({
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
                 username: {
                     required: true,
                     email: true
                 },
                 password: {
                     required: true,
                     minlength: 6
                 }
             },
             messages: {
                 username: {
                     required: 'Please enter a username.',
                     email: 'Please enter a valid username.'
                 },
                 password: {
                     required: 'Please provide a password.',
                     minlength: 'Your password must be at least 6 characters long.'
                 }
             }
		 });
	};
	var handleFBLogin = function() {
		var isFbInitialized = false;
		/*document.getElementById('fb-button').style.display = 'none';*/
		jQuery("#LoginWithFacebookButton").click(function(event) {
			event.preventDefault();
			if (!isFbInitialized) {
				isFbInitialized = true;
				FB.init({
					appId : fbAppId,
					status : false,
					cookie : true,
					xfbml : true,
					oauth : true
				});
				FB.Event.subscribe('auth.login', function() {
					top.location.href = basePath + '/process-fb-account.do';
				});
			}
			setTimeout(function() {
				authUser();
			}, 100);

		});
		function authUser() {
			FB.getLoginStatus(function(o) {
				if (!o && o.status)
					return;
				if (o.status === 'connected') {
					// alert("USER IS LOGGED IN AND HAS AUTHORIZED APP");
					top.location.href = basePath + '/process-fb-account.do';
					// USER IS LOGGED IN AND HAS AUTHORIZED APP
				} else if (o.status === 'not_authorized') {

					FB.login(function() {
						// handle the response
					}, {
						scope : 'email,user_birthday'
					});
					// document.getElementById('fb-button').style.display =
					// 'block';
					return true;
					// USER IS LOGGED IN TO FACEBOOK (BUT HASN'T AUTHORIZED YOUR
					// APP YET)
				} else {
					// alert("USER NOT CURRENTLY LOGGED IN TO FACEBOOK");
					FB.login(function() {
						// handle the response
					}, {
						scope : 'email,user_birthday'
					});
				}
			});
		}
	};
	var handleTwitLogin = function() {
		jQuery("#LoginWithTwitterButton").click(function(event) {
			event.preventDefault();
			jQuery("#twitterClicked").val("yes");
			document.loginForm.submit();
		});
	};
	var handleGLogin = function() {
		jQuery("#LoginWithGoogleButton").click(function(event) {
			event.preventDefault();
			document.location.href = googleLoginUrl;
		});
	};
	return {
		// main function to initiate the module
		init : function() {

			handleLogin();
			handleFBLogin();
			handleTwitLogin();
			handleGLogin();
		}
	};
}();
