/**
 * Add lesson
 */

/**
 * 
 */


$(document).ready(function(){
/*    
	if($("#stateId").val() != ""){
		
		$('#instruType').attr('disabled', true);
		
	}
	*/
	
    $('#stateFrm').validate({
    	errorClass: 'help-block', // You can add help-inline instead of help-block if you like validation messages to the right of the inputs
        errorElement: 'div',
        errorPlacement: function(error, e) {
            e.parents('.form-group > div').append(error);
            submitFlag=false;
        },
        highlight: function(e) {
            $(e).closest('.form-group').removeClass('has-success has-error').addClass('has-error');
            $(e).closest('.help-block').remove();
            submitFlag=false;
        },
        success: function(e) {
            // You can remove the .addClass('has-success') part if you don't want the inputs to get green after success!
            e.closest('.form-group').removeClass('has-success has-error').addClass('has-success');
            e.closest('.help-block').remove();
            submitFlag=true;
        },
       rules: {
        	
    	   /*	instruType: {
                required:  function(element){
                    if ($("#lessonId").val() == '') {
                        return true;
                    } else {
                        return false;
                    }
                }
            },*/
        	stateName: {
                required: true,
                maxlength:50
            },
         
            countryId: {
                required: true
            }
        },
        messages: {
        
        	stateName: {
                required: 'Please enter State Name'
            },
        	
            countryId: {
                required: 'Please select country'
            }
        }
    });
    
   
    
    $( "#btnSave" ).click(function() {
    	if($("#stateFrm").valid()){
    		
    		document.getElementById("stateFrm").submit();
    	}
    });
    
    $( "#btnUpdate" ).click(function() {
    	if($("#editstateFrm").valid()){
    		
    		document.getElementById("editstateFrm").submit();
    	}
    });
    
   
});