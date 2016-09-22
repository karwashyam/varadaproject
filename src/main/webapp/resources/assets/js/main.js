//var Home = function() {
//
//	return {
//		// main function to initiate the module
//		init : function() {
//		}
//	};
//}();


function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
};



function validateForm1(){

		$('.errorMessage').remove();
		requireFieldArray =  validateRequireFields1;
		
		var isValid = validateFormFields();
		
		if(!isValid){
			return false;
		}
		
		return isValid;
	};
	
	function validateForm(){

		$('.errorMessage').remove();
		requireFieldArray =  validateRequireFields;
		
		var isValid = validateFormFields();
		
		if(!isValid){
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
