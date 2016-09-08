var isEditAccess;
var isDeleteAccess;


jQuery(document).ready(function() {
	
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();
	
	if(isAddAccess !== "true"){
		$("#addState").prop("disabled", true);
	}

//	webApp.datatables();
	
	stateManagement();
	$('#stateManageTable_wrapper').prepend('<a href="' + basePath + '/add-state.do' + '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">New State</button></a>');
    

	jQuery("#addState").click(function() {
		document.location = basePath + "/add-state.do";
	});
});

function stateManagement() {
	
	
	oTable = $('#stateManageTable')
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
								+ "/ajax/states.json",
						"aoColumnDefs" : [
								{
									"mRender" : function(data, type, row) {
										var active = 'Deactivate';
										if(row['active'] == false){
											active = 'Activate';
										}
//										 console.log(row['active']);
										var actionsLinks = '<div style="">'; 
										if(isEditAccess === "true"){
											actionsLinks += '<a href="javascript:void(0);" '+' onclick="editState('+"'" + data+"'" + ');">'
									        	+'Edit'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" '+' onclick="deleteState('+"'" + data+"'" + ');">Delete&nbsp; <i class="fa fa-trash font-size-17px"></i></a>';
										}else{
											actionsLinks += '<a class="disabled" href="javascript:void(0);" '+' onclick="editState('+"'" + data+"'" + ');">'
								        	+'Edit'+'</a>&nbsp &nbsp';
										}
										
										
										actionsLinks += '</div>';
										
										
										return actionsLinks;
									},

									"aTargets" : [ 2 ]
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
							"sTitle" : "State Name",
							"mData" : "stateName",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						{
							"sTitle" : "Action",
							"mData" : "stateId",
							"sWidth" : "15%",
							"sClass" : "center",
							"bSortable" : false
						}

						],
						
						"oLanguage": {
					        "sEmptyTable": "No States",
					        "sLoadingRecords": "Please wait - loading...",
					        "sInfo": "Got a total of _TOTAL_ entries to show (_START_ to _END_)"
					    },
						"sPaginationType" : "full_numbers",
                        "fnDrawCallback" : function(oSettings) {
                       	 var iTotalDisplayRecords = oTable.fnSettings().fnRecordsDisplay();

                       	 if (iTotalDisplayRecords == 0) {
                       		 $('#stateManageTable_info').css("visibility","hidden");
                       		 $('.dataTables_paginate').css("visibility","hidden");


                       	 } else {
                       		 $('#stateManageTable_info').css("visibility","visible");
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

function editState(lessonId){
	if(isEditAccess === "true"){
		document.location = basePath + "/edit-state/" + lessonId + ".do";
	}
}

function deleteState(stateId){
	if(isDeleteAccess === "true"){
		BootstrapDialog.show({
			message: 'Are you sure you want to delete this State ?',
			title: 'Alert',
			buttons: [{
				label: 'YES',
				action: function(dialogItself) {
					$.ajax({
	
						type: "DELETE",
						url: basePath +"/ajax/state/delete.json?stateId="+stateId,
	
						dataType :'json',
						contentType: 'application/json',
						mimeType: 'application/json',
	
						success: function( response ) {
							if(response.success){
								$(".errorMessage").html("");
	
								BootstrapDialog.alert("state deleted successfully.");
								var table = $('#stateManageTable').DataTable();
								$('#stateManageTable').dataTable().fnDraw();
								dialogItself.close();
	
							} else if(response.error){
								BootstrapDialog.alert("Error! could not delete the selected State.");
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
	
	var table = $('#stateManageTable').DataTable();
	
	table.fnDestroy();
	
	stateManagement();
}