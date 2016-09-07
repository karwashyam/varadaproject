var oTable;
var isEditAccess;
var isDeleteAccess;

$(document).ready(function() {
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();

	franchiseCommissiontable();

	
	  if(isAddAccess !== "true"){ $("#add").prop("disabled", true); }
	  
	  $('#franchisee-commission-datatable_wrapper').prepend('<a href="'+ basePath+ '/franchisee-commission/add-franchisee-commission'+ '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">Add Franchise Commission</button></a>');
	  
	 jQuery("#add").click(function() { document.location = basePath +"/franchisee-commission/add-franchisee-commission"; });
	 

});
function franchiseCommissiontable() {
	var i = 1;
	oTable = $("#franchisee-commission-datatable").dataTable({
		"info" : false,
		"bProcesing" : true,
		"oLanguage" : {
			"sSearch" : ""
				},
		// "order" : [ [ 1, "asc" ] ],
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/franchisee-commission/list.json',
		"aoColumns" : [ {
			"sTitle" : "S.No.",
			"sWidth" : "5px",
			"mData" : "srNo"
		}, {
			"sTitle" : "Franchise Id",
			"mData" : "franchiseeId",
			"sClass" : "center",
			"bVisible" : false
		// "sWidth" : "6%"
		}, {
			"sTitle" : "Franchise Name",
			"mData" : "franchiseeName"
		},  {
			"sTitle" : "PAN Card",
			"mData" : "pan"
		}, {
			"sTitle" : "Commission Paid",
			"mData" : "commissionPaid"
		}, {
			"sTitle" : "TDS",
			"mData" : "tds"
		}, {
			"sTitle" : "Commission Due",
			"mData" : "commissionUnpaid"
		}, {
			"sTitle" : "Action",
			"mData" : "action"
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

function viewFranchiseDetail(franchiseId) {
	if (isEditAccess === "true") {
		document.location = basePath + "/franchisee-commission/view-franchisee/" + franchiseId;
	}
}

function loadTable() {

	var table = $('#franchisee-commission-datatable').DataTable();

	table.fnDestroy();

	franchiseCommissiontable();
}