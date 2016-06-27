var validateRequireFields = ["user_id","address","delivery_phone","product_no"];


$(document).ready(function() {
	var orderId=$('#order_id').val();
	$("#submit").click(function() {
		
		var isValid = validateBartenderForm();
		if (!isValid) {
			window.stop(); //should work in all major browsers
			document.execCommand("Stop"); //is necessary to support IE
			
			return false;
		}
	});
	
	function validateBartenderForm(){

		$('.errorMessage').remove();
		requireFieldArray =  validateRequireFields;
		
		var isValid = validateFormFields();
		
		if(!isValid || !isValidFN){
			return false;
		}
		
		return isValid;
	};
	function validateFields() {
		var hasError = false;
		var str = "";
		var arr = "";
		var newString = "";
		var tempString = "";
		var remString = str;
		var start = 0;
		var end = 0;
		var errorCount = 0;
		var erroMsg = "";

		for ( var i = 0; i < requireFieldArray.length; i++) {

			var selector = "#" + requireFieldArray[i];
			if ($.trim($('' + selector + '').val()) !== ''
					&& $.trim($('' + selector + '').val()) !== "-1") {

				$(selector).next(".errorMessage").remove();
//				$(selector + '-error').text('');
			} else {

				str = requireFieldArray[i];
				arr = getCapIndexArray(str);
				
				erroMsg = arr + " is required.";
//				$(selector + '-error').text(erroMsg);
				console.log("\n\t selector-->"+selector);
				$(selector).after('<span class="errorMessage">' + erroMsg + '</span>');
				
	/*
				if ($(selector).next().hasClass("errorMessage")) {
					$(selector).next(".errorMessage").html(erroMsg);
				} else {
					$(selector).after(
						"<span class='errorMessage'>" + erroMsg + "</span>");
				}

				$(selector + "Error").html(erroMsg);
	*/
				hasError = true;
			}
		}
		return hasError;
	}

	function validateFormFields() {
		var hasError = validateFields();
		if (hasError) {
			return false;
		}
		return true;
	}

	function getCapIndexArray(str) {
		var regex = /^[A-Z]+$/; // ^[a-zA-z]+$/
		var arr = [];
		var copyStr = str;
		var newStr = "";
		for ( var i = 0; i < copyStr.length; i++) {
			if (i == 0) {
				newStr = newStr + copyStr[i].toUpperCase();
			} else {
				if (regex.test(copyStr[i])) {
					arr.push(i);
					newStr = newStr + " " + copyStr[i];
				} else {
					newStr = newStr + copyStr[i];
				}
			}
		}
		return newStr;
	}
});

