var isEditAccess;
var isDeleteAccess;


jQuery(document).ready(function() {
	
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();
	
	if(isAddAccess !== "true"){
		$("#addPaymentScheme").prop("disabled", true);
	}

//	webApp.datatables();
	
	paymentschmemangement();
	$('#paymentSchemeDatatable_wrapper').prepend('<a href="' + basePath + '/payment-scheme/add.do' + '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">New PaymentScheme</button></a>');
    

	jQuery("#addPaymentScheme").click(function() {
		document.location = basePath + "/add-project.do";
	});
});

function paymentschmemangement() {
	
	
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
//										if(isEditAccess === "true"){
											actionsLinks += '<a href="javascript:void(0);" '+' onclick="editPaymentScheme('+"'" + data+"'" + ');">'
									        	+'Edit'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" '+' onclick="deletePaymentScheme('+"'" + data+"'" + ');">Delete&nbsp; <i class="fa fa-trash font-size-17px"></i></a>';
									/*	}else{
											actionsLinks += '<a class="disabled" href="javascript:void(0);" '+' onclick="editProject('+"'" + data+"'" + ');">'
								        	+'Edit'+'</a>&nbsp &nbsp';
										}*/
										
										
										actionsLinks += '</div>';
										
										
										return actionsLinks;
									},

									"aTargets" : [ 6 ]
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
							"mData" : "paymentSchemeId",
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

function editPaymentScheme(proejctId){
//	if(isEditAccess === "true"){
		document.location = basePath + "/payment-scheme/edit/" + proejctId + ".do";
//	}
}

function deleteProject(stateId){
	if(isDeleteAccess === "true"){
		BootstrapDialog.show({
			message: 'Are you sure you want to delete this payment scheme ?',
			title: 'Alert',
			buttons: [{
				label: 'YES',
				action: function(dialogItself) {
					$.ajax({
	
						type: "DELETE",
						url: basePath +"/ajax/payment-scheme/delete.json?paymentSchemeId="+stateId,
	
						dataType :'json',
						contentType: 'application/json',
						mimeType: 'application/json',
	
						success: function( response ) {
							if(response.success){
								$(".errorMessage").html("");
	
								BootstrapDialog.alert("payement scheme deleted successfully.");
								var table = $('#paymentSchemeDatatable').DataTable();
								$('#paymentSchemeDatatable').dataTable().fnDraw();
								dialogItself.close();
	
							} else if(response.error){
								BootstrapDialog.alert("Error! could not delete the selected payment scheme.");
								dialogItself.close();
	
							}
							dialogItself.close();
	
						},
						error : function() {
							dialogItself.close();
							return false;
						}
					})
				}
			}, {
				label: 'NO',
				action: function(dialogItself) {
					dialogItself.close();
				}
			}]
		});
	}
}


function loadTable(){
	
	var table = $('#paymentSchemeDatatable').DataTable();
	
	table.fnDestroy();
	
	paymentschmemangement();
}