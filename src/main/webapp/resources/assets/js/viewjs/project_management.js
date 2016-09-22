var isEditAccess;
var isDeleteAccess;


jQuery(document).ready(function() {
	
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();
	
	if(isAddAccess !== "true"){
		$("#addProject").prop("disabled", true);
	}

//	webApp.datatables();
	
	projectManagement();
	$('#projectDatatable_wrapper').prepend('<a href="' + basePath + '/project/add.do' + '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">New Project</button></a>');
    

	jQuery("#addProject").click(function() {
		document.location = basePath + "/add-project.do";
	});
});

function projectManagement() {
	
	
	oTable = $('#projectDatatable')
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
								+ "/ajax/projects.json",
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
											actionsLinks += '<a href="javascript:void(0);" '+' onclick="editProject('+"'" + data+"'" + ');">'
									        	+'Edit'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" '+' onclick="deleteProject('+"'" + data+"'" + ');">Delete&nbsp; <i class="fa fa-trash font-size-17px"></i></a>';
										}else{
											actionsLinks += '<a class="disabled" href="javascript:void(0);" '+' onclick="editProject('+"'" + data+"'" + ');">'
								        	+'Edit'+'</a>&nbsp &nbsp';
										}
										
										
										actionsLinks += '</div>';
										
										
										return actionsLinks;
									},

									"aTargets" : [ 4 ]
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
							"sTitle" : "Project Name",
							"mData" : "title",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						{
							"sTitle" : "Super Build Up(%)",
							"mData" : "superBuildupPercentage",
							"sWidth" : "15%",
							"bSortable" : false,
							"sClass" : "center"
						}, // userName
						
						{
							"sTitle" : "Total No. Of Plots",
							"mData" : "totalPlots",
							"sWidth" : "15%",
							"bSortable" : false,
							"sClass" : "center"
						}, // userName
						
						{
							"sTitle" : "Action",
							"mData" : "projectId",
							"sWidth" : "15%",
							"sClass" : "center",
							"bSortable" : false
						}

						],
						
						"oLanguage": {
					        "sEmptyTable": "No Projects",
					        "sLoadingRecords": "Please wait - loading...",
					        "sInfo": "Got a total of _TOTAL_ entries to show (_START_ to _END_)"
					    },
                        "fnDrawCallback" : function(oSettings) {
                       	 var iTotalDisplayRecords = oTable.fnSettings().fnRecordsDisplay();

                       	 if (iTotalDisplayRecords == 0) {
                       		 $('#projectDatatable_info').css("visibility","hidden");
                       		 $('.dataTables_paginate').css("visibility","hidden");


                       	 } else {
                       		 $('#projectDatatable_info').css("visibility","visible");
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

function editProject(proejctId){
//	if(isEditAccess === "true"){
		document.location = basePath + "/project/edit/" + proejctId + ".do";
//	}
}

function deleteProject(stateId){
	if(isDeleteAccess === "true"){
		BootstrapDialog.show({
			message: 'Are you sure you want to delete this Project ?',
			title: 'Alert',
			buttons: [{
				label: 'YES',
				action: function(dialogItself) {
					$.ajax({
	
						type: "DELETE",
						url: basePath +"/ajax/project/delete.json?projectId="+stateId,
	
						dataType :'json',
						contentType: 'application/json',
						mimeType: 'application/json',
	
						success: function( response ) {
							if(response.success){
								$(".errorMessage").html("");
	
								BootstrapDialog.alert("project deleted successfully.");
								var table = $('#projectDatatable').DataTable();
								$('#projectDatatable').dataTable().fnDraw();
								dialogItself.close();
	
							} else if(response.error){
								BootstrapDialog.alert("Error! could not delete the selected project.");
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
	
	var table = $('#projectDatatable').DataTable();
	
	table.fnDestroy();
	
	projectManagement();
}