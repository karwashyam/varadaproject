/**
 * Add lesson
 */

/**
 * 
 */
var editor; 
var oTable;
$(document).ready(function(){
	var today = new Date();
	var projectId;
/*    
	if($("#stateId").val() != ""){
		
		$('#instruType').attr('disabled', true);
		
	}
	*/
	projectId=$('#paymentSchemeId').val();
	
	console.log("\n\n\t =========projectId===>"+projectId);
	  $('#completionDate')
      .datepicker({
    	  autoclose: true,  
    	  maxDate : today,
          format: 'mm/dd/yyyy'
      })
      .on('changeDate', function(e) {
          // Revalidate the date field
//          $('#projectFrm').validate('revalidateField', 'date');
      });
	
    $('#paymentSchFrm').validate({
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
            downPayment: {
                required: true,
                maxlength:50
            },
            noOfMonths: {
                required: true,
                maxlength:4
            },
            interestRate: {
                required: true,
                maxlength:50
            },
            prepaymentPossible:{
            	required: true
            	
            }
            
        },
        messages: {
        
        	title: {
                required: 'Please enter Scheme Name'
            },
            downPayment: {
                required: 'Please enter down payment'
            },
            noOfMonths: {
                required: 'Please enter No.of Months'
            },
            interestRate: {
                required: 'Please enter interest rate'
            },
            prepaymentPossible: {
            	
                required: 'Please select pre payment possible or not'

            }
      
        }
    });
    
   
    
    $( "#btnSave" ).click(function() {
    	if($("#paymentSchFrm").valid()){
    		
    		document.getElementById("projectFrm").submit();
    	}
    });
    
    $( "#btnUpdate" ).click(function() {
    	if($("#editpaymentSchFrm").valid()){
    		
    		document.getElementById("#editpaymentSchFrm").submit();
    	}
    });
    
//	paymentschmemangement(projectId);

	
});



function paymentschmemangement(projectId) {
	
	console.log("\n\n\t =========projectId1===>"+projectId);

	oTable = $('#paymentSchemeDatatable')
			.dataTable(
					{

						//"aaSorting" : [ [ 1, "desc" ] ],
						"bProcessing" : true,
						"bServerSide" : true,
						"bPaginate" : true,
						"iDisplayStart" : 0,
						"iDisplayLength" : 10,
						"bRetrieve" : true,
						"bSort" : true,
						"bFilter" : true,
						"bLengthChange" : false,
						"fnServerData" : fnServerData,
						"sAjaxSource" : basePath
								+ "/ajax/paymentScheme.json",
						"aoColumnDefs" : [
								{
									"mRender" : function(data, type, row) {
										var active = 'Deactivate';
										if(row['active'] == false){
											active = 'Activate';
										}
//										 console.log(row['active']);
										var actionsLinks = '<div style="">'; 
											actionsLinks += '<a href="javascript:void(0);" '+' onclick="editPaymentScheme('+"'" + data+"'" + ');">'
									        	+'Edit'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" '+' onclick="deletePaymentScheme('+"'" + data+"'" + ');">Delete&nbsp; <i class="fa fa-trash font-size-17px"></i></a>';
										
										
										actionsLinks += '</div>';
										
										
										return actionsLinks;
									},

									"aTargets" : [ 6]
								}, ],

						"aoColumns" : [

						{
							"sTitle" : "Sr. No",
							"mData" : "srNo",
							"sWidth" : "15%",
							"bSortable" : false,
							"sClass" : "center"
						}, // Sr.No
						{
							"sTitle" : "Scheme Name",
							"mData" : "title",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						{
							"sTitle" : "Down payment",
							"mData" : "downPayment",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						
						{
							"sTitle" : "No of Months",
							"mData" : "noOfMonths",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						{
							"sTitle" : "Interest Rate",
							"mData" : "interestRate",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						{
							"sTitle" : "Pre payment possible",
							"mData" : "prepaymentPossible",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						{
							"sTitle" : "Action",
							"mData" : "projectPlotId",
							"sWidth" : "15%",
							"sClass" : "center",
							"bSortable" : false
						}

						],
						
						"oLanguage": {
					        "sEmptyTable": "No payment schemes",
					        "sLoadingRecords": "Please wait - loading...",
					        "sInfo": "Got a total of _TOTAL_ entries to show (_START_ to _END_)"
					    },
                        "fnDrawCallback" : function(oSettings) {
                       	 var iTotalDisplayRecords = oTable.fnSettings().fnRecordsDisplay();

                       	 if (iTotalDisplayRecords == 0) {
                       		 $('#paymentSchemeDatatable_info').css("visibility","hidden");
                       		 $('.dataTables_paginate').css("visibility","hidden");


                       	 } else {
                       		 $('#paymentSchemeDatatable_info').css("visibility","visible");
                       		 $('.dataTables_paginate').css("visibility","visible");
                       	 }
                        }

					});
}

new $.fn.dataTable.Buttons( oTable, [
                                    { extend: "create", editor: editor },
                                    { extend: "edit",   editor: editor },
                                    { extend: "remove", editor: editor }
                                ] );

function fnServerData(sSource, aoData, fnCallback) {
	isSessionExtend = true;
	$.ajax({
		"dataType" : 'json',
		"type" : "GET",
		"url" : sSource,
		"contentType" : 'application/json',
		"data" : aoData,
		"success" : fnCallback,
		"timeout" : 8000,
		"cache" : false,
		"error" : handleAjaxError
	});

}

function handleAjaxError(xhr, textStatus, error) {

	if (textStatus == 'timeout') {
		alert('The server took too long to send the data.');
	} else if (textStatus == "parsererror") {
		alert("Ajax error occured.");
	}
}
