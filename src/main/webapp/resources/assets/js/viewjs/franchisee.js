var oTable;
var isEditAccess;
var isDeleteAccess;

$(document).ready(function() {
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();

	franchisetable();

	
	  if(isAddAccess !== "true"){ $("#add").prop("disabled", true); }
	  
	  $('#franchise-datatable_wrapper').prepend('<a href="'+ basePath+ '/franchisee/add'+ '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">New Franchise</button></a>');
	  
	 jQuery("#add").click(function() { document.location = basePath +"/franchisee/add"; });
	 

});
function franchisetable() {
	var i = 1;
	oTable = $("#franchise-datatable").dataTable({
		"info" : false,
		"bProcesing" : true,
		"oLanguage" : {
			"sSearch" : ""
				},
		// "order" : [ [ 1, "asc" ] ],
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/franchisee/list.json',
		"columnDefs" : [ {
			"mRender" : function(data, type, row) {
				var dateValue=new Date(data);
				if(dateValue.getTime() === (new Date("Thu Jan 01 1970 05:30:00 GMT+0530 (India Standard Time)")).getTime()){
					return "";
				}
				var day = dateValue.getDate();
				var month = (dateValue.getMonth() + 1);
				var year = dateValue.getFullYear();
				return day+"/"+month+"/"+year;
			},
			"aTargets" : [ 2 ]
		} ],
		"aoColumns" : [ {
			"sTitle" : "S.No.",
			"sWidth" : "5px",
			"mData" : "rowNo"
		}, {
			"sTitle" : "Franchise Id",
			"mData" : "franchiseeId",
			"sClass" : "center",
			"bVisible" : false
		// "sWidth" : "6%"
		}, {
			"mData" : "Reg. Date",
			"mData" : "registeredDate",
			"sClass" : "center"
		}, {
			"sTitle" : "Franchise Name",
			"mData" : "franchiseeName"
		},  {
			"sTitle" : "Franchise Email",
			"mData" : "email"
		}, {
			"sTitle" : "Mobile No.",
			"mData" : "phone1"
		}, {
			"sTitle" : "Address",
			"mData" : "address"
		}, {
			"sTitle" : "Commission",
			"mData" : "commissionPercentage"
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

function editFranchise(franchiseId) {
	if (isEditAccess === "true") {
		document.location = basePath + "/franchisee/edit-franchisee/" + franchiseId;
	}
}

function changeFranchiseStatus(franchiseId) {
	if (isDeleteAccess === "true") {
		$.ajax({
			type: "DELETE",
			url: basePath +"/ajax/franchisee/changeStatus?franchiseeId="+franchiseId,

			dataType :'json',
			contentType: 'application/json',
			mimeType: 'application/json',

			success: function( response ) {
				var table = $('#franchise-datatable').DataTable();
				$('#franchise-datatable').dataTable().fnDraw();
			}
	     });
	}
}

function loadTable() {

	var table = $('#franchise-datatable').DataTable();

	table.fnDestroy();

	franchisetable();
}