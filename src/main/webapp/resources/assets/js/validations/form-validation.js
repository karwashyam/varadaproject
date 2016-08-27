function validateForm() {
	var fullName ;
    var password;
    var confirmPassword ;
    var email ;
    var birthDate ;
    var phone ;
    var valid = true;
    var validField = true;
    
    var nameValidation=/^[a-zA-Z0-9() ]+$/;
    var passwordValidation=/^.{4,}$/;
    var emailValidation = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var dateValidation = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
    var phoneValidation=/^[0-9]{10}$/;
    
	if(document.forms["employeeModel"] != null ){
		$("span[id*='.errors']").hide();
		fullName = document.forms["employeeModel"]["fullName"].value;
	    password= document.forms["employeeModel"]["password"].value;
	    confirmPassword = document.forms["employeeModel"]["confirmPassword"].value;
	    email = document.forms["employeeModel"]["email"].value;
	    birthDate = document.forms["employeeModel"]["birthDate"].value;
	    phone = document.forms["employeeModel"]["phone"].value;
	}
    if (fullName != null && fullName != "" ) {
    	validField = nameValidation.test(fullName);
    	if(!validField)
    		document.getElementById("fullNameError").innerHTML="Enter the valid Full Name";
    	else
    		document.getElementById("fullNameError").innerHTML="";
    	valid = valid && validField;
    }
    else{
    	document.getElementById("fullNameError").innerHTML="Full Name is Required";
    	valid = false
    }
    
    if (password != null && password != "") {
    	validField = passwordValidation.test(password);
    	if(!validField)
    		document.getElementById("passwordError").innerHTML="Please enter Atleast 4 characters";
    	else
    		document.getElementById("passwordError").innerHTML="";
    	valid = valid && validField;
    }
    else{
    	document.getElementById("passwordError").innerHTML="Password is Required";
    	valid = false
    }
    
    if (confirmPassword != null && confirmPassword != "") {
    	validField = (password == confirmPassword);
    	if(!validField)
    		document.getElementById("confirmPasswordError").innerHTML="Password is not Matching";
    	else
    		document.getElementById("confirmPasswordError").innerHTML="";
    	valid = valid && validField;
    }
    else{
    	document.getElementById("confirmPasswordError").innerHTML="Confirm Password is Required";
    	valid = false
    }
    
    if (email != null && email != "") {
    	validField = emailValidation.test(email);
    	if(!validField)
    		document.getElementById("emailError").innerHTML="Enter valid Email";
    	else
    		document.getElementById("emailError").innerHTML="";
    	valid = valid && validField;
    }
    else {
    	document.getElementById("emailError").innerHTML="Email is Required";
    	valid = false
    }
    
    if (birthDate != null && birthDate != "") {
    	validField = dateValidation.test(birthDate);
    	if(!validField)
    		document.getElementById("birthDateError").innerHTML="Enter valid Date";
    	else{
    		var from = birthDate.split("/");
    		var givenDate = new Date(from[2], from[1]-1,from[0]);
    		var todayDate = new Date();
    		var timeDiff = todayDate.getTime() - givenDate.getTime();
    		var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
    		var daysOfHunderedYears= 100*356;
    		validField = (diffDays>0 && diffDays<daysOfHunderedYears);
    		if(!validField)
    			document.getElementById("birthDateError").innerHTML="Date is not in Range";
    		else
    			document.getElementById("birthDateError").innerHTML="";
    	}
    	valid = valid && validField;
    }
    
    if (phone != null && phone != "") {
    	validField = phoneValidation.test(phone);
    	if(!validField)
    		document.getElementById("phoneError").innerHTML="Enter valid Phone Number";
    	else
    		document.getElementById("phoneError").innerHTML="";
    	valid = valid && validField;
    }
    else{
    	document.getElementById("phoneError").innerHTML="Phone Number is Required";
    	valid = false
    }
    
    return valid;
}