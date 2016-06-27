jQuery(document).ready(function() {
	
	jQuery("#btnchangePassword").click(function() {
		var oldPassword = jQuery("#oldPassword").val();
		var newPassword = jQuery("#newPassword").val();
		var confirmPassword = jQuery("#confirmPassword").val();

		if (oldPassword === "") {
			jQuery(".errorMessage").text(
					"Old password should not be empty.");
			return false;
		}

		if (newPassword === "") {
			jQuery(".errorMessage").text(
					"New password should not be empty.");
			return false;
		}

		if (confirmPassword === "") {
			jQuery(".errorMessage").text(
					"confirm password should not be empty.");
			return false;
		}

		if (newPassword !== confirmPassword) {
			jQuery(".errorMessage").text(
					"New and confirm password not matched.");
			return false;
		}
		
		document.setNewPasswordForm.submit();
	});

	if (errorMsg === "Password changed successfully.") {
		jQuery(".errorMessage").css("color", "green");
	}

	jQuery("#btnBack").click(function(event) {
		event.preventDefault();
		// window.location.href = basePath +
		// "/secure/users/admin/admin-dashboard.do";
	});

});