var oTable;
var isEditAccess;
var isDeleteAccess;

$(document).ready(function() {
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();
	
	citytable();
	
	if(isAddAccess !== "true"){
		$("#add").prop("disabled", true);
	}
	
	$('#city-datatable_wrapper').prepend('<a href="'+ basePath+ '/city/add'+ '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">New City</button></a>');
	
	jQuery("#add").click(function() {
		document.location = basePath + "/city/add";
	});
	
});

function citytable() {
	var i = 1;
	oTable = $("#city-datatable").dataTable({

		"info" : false,
		"bProcesing" : true,
		"oLanguage" : {
			"sSearch" : ""
		},
		//"order" : [ [ 1, "asc" ] ],
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/city/list.json',
		"columnDefs" : [ {
			"mRender" : function(data, type, row) {
				var actionsLinks = '<div style="">'; 
				if(isEditAccess === "true"){
					actionsLinks += '<a href="javascript:void(0);" '+' onclick="editCity('+"'" + data+"'" + ');">'
			        	+'Edit'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" '+' onclick="deleteCity('+"'" + data+"'" + ');">Delete&nbsp; <i class="fa fa-trash font-size-17px"></i></a>';
				}else{
					actionsLinks += '<a class="disabled" href="javascript:void(0);" '+' onclick="editCity('+"'" + data+"'" + ');">'
		        	+'Edit'+'</a>&nbsp &nbsp';
				}
				actionsLinks += '</div>';
				return actionsLinks;
			},
			"aTargets" : [ 4 ]
		}
		],
		"aoColumns" : [
		{
			"sTitle" : "S.No.",
			"mData" : "rowNo",
			"bSortable" : false
		},
		{
			"sTitle" : "City Id",
			"mData" : "cityId",
			"sClass" : "center",
			"bVisible" : false
		// "sWidth" : "6%"
		}, {
			"mData" : "City Name",
			"mData" : "cityName",
			"sClass" : "center",
			"bSortable" : true
		}, {
			"sTitle" : "State Name",
			"mData" : "stateName",
			"bSortable" : true
		}, {
			"sTitle" : "Action",
			"mData" : "cityId"
		} ],
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"dataType" : 'json',
				"type" : "GET",
				"url" : sSource,
				"data" : aoData,
				"success" : fnCallback,
				"error" : handleAjaxError
			});
		},
		"sPaginationType" : "full_numbers"

	});
	/*oTable.on( 'order.dt search.dt', function () {
		oTable.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
            cell.innerHTML = i+1;
        } );
    } ).draw();*/

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
		"timeout" : 10000,
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

function editCity(cityId){
	if(isEditAccess === "true"){
		document.location = basePath + "/city/edit-city/" + cityId;
	}
}

function deleteCity(cityId){
	if(isDeleteAccess === "true"){
		BootstrapDialog.show({
			message: 'Are you sure you want to delete this City ?',
			title: 'Alert',
			buttons: [{
				label: 'YES',
				action: function(dialogItself) {
					$.ajax({
	
						type: "DELETE",
						url: basePath +"/ajax/city/delete?cityId="+cityId,
	
						dataType :'json',
						contentType: 'application/json',
						mimeType: 'application/json',
	
						success: function( response ) {
							if(response.success){
								$(".errorMessage").html("");
	
								BootstrapDialog.alert("City deleted successfully.");
								var table = $('#city-datatable').DataTable();
								$('#city-datatable').dataTable().fnDraw();
								dialogItself.close();
	
							} else if(response.error){
								BootstrapDialog.alert("Error! could not delete the selected Deleted.");
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
	
	var table = $('#city-datatable').DataTable();
	
	table.fnDestroy();
	
	citytable();
}