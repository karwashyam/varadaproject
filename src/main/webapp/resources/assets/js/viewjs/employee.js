var oTable;
var isEditAccess;
var isDeleteAccess;

$(document).ready(function() {
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();

	employeetable();

	
	  if(isAddAccess !== "true"){ $("#add").prop("disabled", true); }
	  
	  $('#employee-datatable_wrapper').prepend('<a href="'+ basePath+ '/employee/add'+ '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">New Employee</button></a>');
	  
	 jQuery("#add").click(function() { document.location = basePath +"/employee/add"; });
	 

});
function employeetable() {
	var i = 1;
	oTable = $("#employee-datatable").dataTable({
		"info" : false,
		"bProcesing" : true,
		"oLanguage" : {
			"sSearch" : ""
				},
		// "order" : [ [ 1, "asc" ] ],
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/employee/list.json',
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
			"aTargets" : [ 4 ]
		} ],
		"aoColumns" : [ {
			"sTitle" : "S.No.",
			"sWidth" : "5px",
			"mData" : "rowNo"
		}, {
			"sTitle" : "Employee Id",
			"mData" : "userId",
			"sClass" : "center",
			"bVisible" : false
		// "sWidth" : "6%"
		}, {
			"mData" : "Employee Code",
			"mData" : "userName",
			"sClass" : "center"
		}, {
			"sTitle" : "Full Name",
			"mData" : "fullName"
		}, {
			"sTitle" : "Date of Birth",
			"mData" : "birthDate"
		}, {
			"sTitle" : "Employee Email",
			"mData" : "email"
		}, {
			"sTitle" : "Address",
			"mData" : "address"
		}, {
			"sTitle" : "Phone No.",
			"mData" : "phone"
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

function editEmployee(employeeId) {
	if (isEditAccess === "true") {
		document.location = basePath + "/employee/edit-employee/" + employeeId;
	}
}

function changeEmployeeStatus(employeeId) {
	if (isDeleteAccess === "true") {
		$.ajax({
			type: "DELETE",
			url: basePath +"/ajax/employee/changeStatus?employeeId="+employeeId,

			dataType :'json',
			contentType: 'application/json',
			mimeType: 'application/json',

			success: function( response ) {
				var table = $('#employee-datatable').DataTable();
				$('#employee-datatable').dataTable().fnDraw();
			}
	     });
	}
}

function loadTable() {

	var table = $('#employee-datatable').DataTable();

	table.fnDestroy();

	employeetable();
}