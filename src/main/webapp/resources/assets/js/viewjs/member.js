var oTable;
var isEditAccess;
var isDeleteAccess;

$(document).ready(function() {
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();
	membertable();
	if(isAddAccess !== "true"){ 
		$("#add").prop("disabled", true); 
	}
	$('#member-datatable_wrapper').prepend('<a href="'+ basePath+ '/member/add'+ '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">New Member</button></a>');
	jQuery("#add").click(function() { document.location = basePath +"/member/add"; });
	
});

function membertable() {
	var i = 1;
	oTable = $("#member-datatable").dataTable({
		"info" : false,
		"bProcesing" : true,
		"oLanguage" : {
			"sSearch" : ""
		},
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/member/list.json',
		
		"aoColumns" : [ {
			"sTitle" : "S.No.",
			"sWidth" : "5px",
			"mData" : "srNo"
		},{
			"sTitle" : "Member Id",
			"mData" : "memberId",
			"sClass" : "center",
			"bVisible" : false
		// "sWidth" : "6%"
		},{
			"sTitle" : "Member Name",
			"mData" : "memberName"
		},{
			"sTitle" : "Member Email",
			"mData" : "email"
		},{
			"sTitle" : "Mobile No.",
			"mData" : "phone1"
		},{
			"sTitle" : "Franchise Name",
			"mData" : "franchiseeName"
		},{
			"sTitle" : "Reference Code",
			"mData" : "refMemberCode",
			"bVisible" : false
		},{
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

function editMember(memberId) {
	if (isEditAccess === "true") {
		document.location = basePath + "/member/edit-member/" + memberId;
	}
}

function changeMemberStatus(memberId) {
	if (isDeleteAccess === "true") {
		$.ajax({
			type: "DELETE",
			url: basePath +"/ajax/member/changeStatus?memberId="+memberId,

			dataType :'json',
			contentType: 'application/json',
			mimeType: 'application/json',

			success: function( response ) {
				var table = $('#member-datatable').DataTable();
				$('#member-datatable').dataTable().fnDraw();
			}
	     });
	}
}

function loadTable() {

	var table = $('#member-datatable').DataTable();

	table.fnDestroy();

	membertable();
}