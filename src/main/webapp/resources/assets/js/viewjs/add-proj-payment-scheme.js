/**
 * Add lesson
 */

/**
 * 
 */
var oTable;
$(document).ready(function(){
	var projPaymentSchemeId;
/*    
	if($("#stateId").val() != ""){
		
		$('#instruType').attr('disabled', true);
		
	}
	*/
	projPaymentSchemeId=$('#projPaymentSchemeId').val();
	
	console.log("\n\n\t =========projPaymentSchemeId===>"+projPaymentSchemeId);
	
    $('#projPaymentSchFrm').validate({
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
    	   projectTitle: {
                required: true
            },
            paymentSchemeTitle: {
                required: true
            }
            
        },
        messages: {
        
        	paymentSchemeTitle: {
                required: 'Please enter Scheme Name'
            },
            projectTitle: {
                required: 'Please enter project title'
            }      
        }
    });
    
   
    
    $( "#btnSave" ).click(function() {
    	if($("#projPaymentSchFrm").valid()){
    		
    		document.getElementById("projPaymentSchFrm").submit();
    	}
    });
    
    $( "#btnUpdate" ).click(function() {
    	if($("#editprojPaymentSchFrm").valid()){
    		
    		document.getElementById("#editprojPaymentSchFrm").submit();
    	}
    });
    var projId=$("#projId").val();
	$('#projectId option').each(function(){
			if($(this).val()==projId){
				$(this).attr('selected','selected');
			}
	});
    
	
    var payschemeId=$("#paymentSchId").val();
	$('#paymentSchemeId option').each(function(){
			if($(this).val()==payschemeId){
				$(this).attr('selected','selected');
			}
	});
//	paymentschmemangement(projectId);

	
});



