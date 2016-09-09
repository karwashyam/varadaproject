var oTable;
var paymentId;
$(document).ready(function() {
	
	chequetable();
	
	$.ajax({
		
		type: "GET",
		url: basePath +"/tds/tds-due/",

		dataType :'json',
		contentType: 'application/json',
		mimeType: 'application/json',

		success: function( response ) {
			if(response.success!=''){
				$(".errorMessage").html("");

				$('#tdsDue').text(response.success);

			} else if(response.error){

			}

		},
		error : function() {
		}
	});
});

function chequetable() {
	var i = 1;
	oTable = $("#tds-datatable").dataTable({

		"info" : false,
		"bProcesing" : true,
		"oLanguage" : {
			"sSearch" : ""
		},
		//"order" : [ [ 1, "asc" ] ],
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/tds/list.json',
		"columnDefs" : [ 
		],
		"aoColumns" : [
		{
			"sTitle" : "Sr. No",
			"mData" : "srNo"
			
		},
		{
			"sTitle" : "Franchisee",
			"mData" : "franchiseeName",
			"sClass" : "center",
			"bVisible" : true,
			"bSortable":false
		// "sWidth" : "6%"
		}, {
			"sTitle" : "TDS",
			"mData" : "tds",
			"bSortable" : false
		}, {
			"sTitle" : "Cheque Number",
			"mData" : "chequeNumber",
			"bSortable" : false
		},
		{
			"sTitle" : "Bank",
			"mData" : "bank",
			"bSortable" : false
		},
		{
			"sTitle" : "Account Holder",
			"mData" : "accountHolder",
			"bSortable" : false
		},
		{
			"sTitle" : "Cheque Date",
			"mData" : "chequeDateString",
			"bSortable" : false
		},
		{
			"sTitle" : "Added Date",
			"mData" : "createdAtString",
			"bSortable" : false
		},
		{
			"sTitle" : "TDS Amount",
			"mData" : "status",
			"bSortable" : false
		}
		],
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


function loadTable(){
	
	var table = $('#tds-datatable').DataTable();
	
	table.fnDestroy();
	
	citytable();
}

