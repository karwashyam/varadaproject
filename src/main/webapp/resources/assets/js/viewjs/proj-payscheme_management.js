var isEditAccess;
var isDeleteAccess;


jQuery(document).ready(function() {
	
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();
	
	if(isAddAccess !== "true"){
		$("#addProjPaymentScheme").prop("disabled", true);
	}

//	webApp.datatables();
	
	projpaymentschmemangement();
	$('#projPaymentSchemeDatatable_wrapper').prepend('<a href="' + basePath + '/proj-payment-scheme/add.do' + '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">New PaymentScheme</button></a>');
    

	/**/
});

function projpaymentschmemangement() {
	
	
	oTable = $('#projPaymentSchemeDatatable')
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
								+ "/ajax/projpaymentScheme.json",
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
											actionsLinks += '<a href="javascript:void(0);" '+' onclick="editProjPaymentScheme('+"'" + data+"'" + ');">'
									        	+'Edit'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" '+' onclick="deleteProjPaymentScheme('+"'" + data+"'" + ');">Delete&nbsp; <i class="fa fa-trash font-size-17px"></i></a>';
									/*	}else{
											actionsLinks += '<a class="disabled" href="javascript:void(0);" '+' onclick="editProject('+"'" + data+"'" + ');">'
								        	+'Edit'+'</a>&nbsp &nbsp';
										}*/
										
										
										actionsLinks += '</div>';
										
										
										return actionsLinks;
									},

									"aTargets" : [5]
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
							"sTitle" : "Project name",
							"mData" : "projectId",
							"sWidth" : "15%",
							"bSortable" : true,
							"bVisible":false,
							"sClass" : "center"
						}, // userName
						
						{
							"sTitle" : "Project name",
							"mData" : "projectTitle",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						{
							"sTitle" : "Scheme Name",
							"mData" : "paymentSchemeId",
							"bVisible":false,
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						{
							"sTitle" : "Scheme Name",
							"mData" : "paymentSchemeTitle",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						{
							"sTitle" : "Action",
							"mData" : "projectPaymentSchemeId",
							"sWidth" : "15%",
							"sClass" : "center",
							"bSortable" : false
						}

						],
						
						"oLanguage": {
					        "sEmptyTable": "No project payment schemes",
					        "sLoadingRecords": "Please wait - loading...",
					        "sInfo": "Got a total of _TOTAL_ entries to show (_START_ to _END_)"
					    },
                        "fnDrawCallback" : function(oSettings) {
                       	 var iTotalDisplayRecords = oTable.fnSettings().fnRecordsDisplay();

                       	 if (iTotalDisplayRecords == 0) {
                       		 $('#projPaymentSchemeDatatable_info').css("visibility","hidden");
                       		 $('.dataTables_paginate').css("visibility","hidden");


                       	 } else {
                       		 $('#projPaymentSchemeDatatable_info').css("visibility","visible");
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

function editProjPaymentScheme(projPaymentSchemeId){
//	if(isEditAccess === "true"){
		document.location = basePath + "/proj-payment-scheme/edit/" + projPaymentSchemeId + ".do";
//	}
}

function deleteProjPaymentScheme(projPaySchemeId){
//	if(isDeleteAccess === "true"){
		BootstrapDialog.show({
			message: 'Are you sure you want to delete this project payment scheme ?',
			title: 'Alert',
			buttons: [{
				label: 'YES',
				action: function(dialogItself) {
					$.ajax({
	
						type: "DELETE",
						url: basePath +"/ajax/projpaymentScheme/delete.json?projectPaymentSchemeId="+projPaySchemeId,
	
						dataType :'json',
						contentType: 'application/json',
						mimeType: 'application/json',
	
						success: function( response ) {
							if(response.success){
								$(".errorMessage").html("");
	
								BootstrapDialog.alert("project payement scheme deleted successfully.");
								var table = $('#projPaymentSchemeDatatable').DataTable();
								$('#projPaymentSchemeDatatable').dataTable().fnDraw();
								dialogItself.close();
	
							} else if(response.error){
								BootstrapDialog.alert("Error! could not delete the selected project payment scheme.");
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
//	}
}


function loadTable(){
	
	var table = $('#projPaymentSchemeDatatable').DataTable();
	
	table.fnDestroy();
	
	projpaymentschmemangement();
}