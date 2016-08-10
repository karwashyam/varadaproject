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
	
    $('#projectFrm').validate({
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
    	   title: {
                required: true,
                maxlength:50
            },
            bookingPrefix: {
                required: true,
                maxlength:50
            },
            totalPlots: {
                required: true,
                maxlength:4
            },
            projectOverview: {
                required: true,
                maxlength:50
            }
        },
        messages: {
        
        	title: {
                required: 'Please enter Project Name'
            },
            bookingPrefix: {
                required: 'Please enter Booking Prefix'
            },
            totalPlots: {
                required: 'Please enter Total No.of Plots'
            },
            projectOverview: {
                required: 'Please enter Project Overview'
            }
      
        }
    });
    
   
    
    $( "#btnSave" ).click(function() {
    	if($("#projectFrm").valid()){
    		
    		document.getElementById("projectFrm").submit();
    	}
    });
    
    $( "#btnUpdate" ).click(function() {
    	if($("#editProjectFrm").valid()){
    		
    		document.getElementById("editProjectFrm").submit();
    	}
    });
    
   
});