var validateRequireFields = ["bookingCode"];
var oTable;
var paymentId;
$(document).ready(function() {
	
	citytable();
	chequetable();
	$("#submit").click(function() {
			var isValid = validateForm();
			if (!isValid) {
				window.stop(); //should work in all major browsers
				document.execCommand("Stop"); //is necessary to support IE
				return false;
			}else{
				document.location = basePath + "/booking/add-payment/" + $("#bookingCode").val();
			}
	});
});

function chequetable() {
	var i = 1;
	oTable = $("#cheque-datatable").dataTable({

		"info" : false,
		"bProcesing" : true,
		"oLanguage" : {
			"sSearch" : ""
		},
		//"order" : [ [ 1, "asc" ] ],
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/payment/cheque-list.json',
		"columnDefs" : [ 
		{
			"mRender" : function(data, type, row) {
				var actionsLinks = '<div style="">'; 
					actionsLinks += '<a href="javascript:void(0);" '+' onclick="clearedCheque('+"'" + data+"'" + ');">Cleared</a> / ';
				actionsLinks += '</div>';
				actionsLinks += '<div style="cursor:pointer;" onclick="rejectedCheque('+"'" + data+"'" + ')">Rejected'; 
				actionsLinks += '</div>';
				return actionsLinks;
				paymentId=data;
			},
			"aTargets" : [ 10 ]
		}
		],
		"aoColumns" : [
		{
			"sTitle" : "Receipt No",
			"mData" : "receiptNo"
			
		},
		{
			"sTitle" : "Project",
			"mData" : "projectName",
			"sClass" : "center",
			"bVisible" : true,
			"bSortable":true
		// "sWidth" : "6%"
		}, {
			"sTitle" : "Member",
			"mData" : "memberName",
			"bSortable" : true
		}, {
			"sTitle" : "Booking Code",
			"mData" : "bookingCode",
			"bSortable" : true
		},
		{
			"sTitle" : "Amount",
			"mData" : "paymentAmount",
			"bSortable" : true
		},
		{
			"sTitle" : "Payment Date",
			"mData" : "emiDateString",
			"bSortable" : true
		},
		{
			"sTitle" : "Cheque Number",
			"mData" : "chequeNumber",
			"bSortable" : true
		},
		{
			"sTitle" : "Cheque Date",
			"mData" : "chequeDateString",
			"bSortable" : true
		},
		{
			"sTitle" : "Bank",
			"mData" : "bank",
			"bSortable" : true
		},
		{
			"sTitle" : "Account Holder",
			"mData" : "accountHolder",
			"bSortable" : true
		},
		{
			"sTitle" : "Action",
			"mData" : "paymentId"
		}],
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
		"sAjaxSource" : basePath + '/payment/list.json',
		"columnDefs" : [ 
		{
			"mRender" : function(data, type, row) {
				var actionsLinks = '<div style="">'; 
					actionsLinks += '<a href="javascript:void(0);" '+' onclick="editPayment('+"'" + data+"'" + ');">Edit Payment</a> / ';
				actionsLinks += '</div>';
				actionsLinks += '<div style="cursor:pointer;" onclick="printReceipt('+"'" + data+"'" + ')">Print Receipt'; 
				actionsLinks += '</div>';
				return actionsLinks;
				paymentId=data;
			},
			"aTargets" : [ 8 ]
		}/*,
		{
			"mRender" : function(data, type, row) {
				var actionsLinks = '<div onclick="printReceipt('+"'" + paymentId+"'" + ')">'+data+'XXX'; 
				actionsLinks += '</div>';
				return actionsLinks;
			},
			"aTargets" : [ 0 ]
		}*/
		],
		"aoColumns" : [
		{
			"sTitle" : "Receipt No",
			"mData" : "receiptNo"
			
		},
		{
			"sTitle" : "Project",
			"mData" : "projectName",
			"sClass" : "center",
			"bVisible" : true,
			"bSortable":true
		// "sWidth" : "6%"
		}, {
			"mData" : "Franchisee",
			"mData" : "franchiseeName",
			"sClass" : "center",
			"bSortable" : true
		}, {
			"sTitle" : "Member",
			"mData" : "memberName",
			"bSortable" : true
		}, {
			"sTitle" : "Booking Code",
			"mData" : "bookingCode",
			"bSortable" : true
		},
		{
			"sTitle" : "Plot No",
			"mData" : "plotName",
			"bSortable" : true
		},
		{
			"sTitle" : "Amount",
			"mData" : "paymentAmount",
			"bSortable" : true
		},
		{
			"sTitle" : "Payment Date",
			"mData" : "emiDateString",
			"bSortable" : true
		},
		{
			"sTitle" : "Action",
			"mData" : "paymentId"
		}],
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
	
	var table = $('#city-datatable').DataTable();
	
	table.fnDestroy();
	
	citytable();
}

function clearedCheque(paymentId){
	if(isDeleteAccess === "true"){
		BootstrapDialog.show({
			message: 'This cheque got cleared ?',
			title: 'Alert',
			buttons: [{
				label: 'YES',
				action: function(dialogItself) {
					$.ajax({
	
						type: "GET",
						url: basePath +"/payment/clear/"+paymentId,
	
						dataType :'json',
						contentType: 'application/json',
						mimeType: 'application/json',
	
						success: function( response ) {
							if(response.success){
								$(".errorMessage").html("");
	
								BootstrapDialog.alert("Details saved successfully.");
								var table = $('#cheque-datatable').DataTable();
								$('#cheque-datatable').dataTable().fnDraw();
								dialogItself.close();
	
							} else if(response.error){
								BootstrapDialog.alert("Error!");
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

function rejectedCheque(paymentId){
	if(isDeleteAccess === "true"){
		BootstrapDialog.show({
			message: 'This cheque got rejected ?',
			title: 'Alert',
			buttons: [{
				label: 'YES',
				action: function(dialogItself) {
					$.ajax({
	
						type: "GET",
						url: basePath +"/payment/reject/"+paymentId,
	
						dataType :'json',
						contentType: 'application/json',
						mimeType: 'application/json',
	
						success: function( response ) {
							if(response.success){
								$(".errorMessage").html("");
	
								BootstrapDialog.alert("Details saved successfully.");
								var table = $('#cheque-datatable').DataTable();
								$('#cheque-datatable').dataTable().fnDraw();
								dialogItself.close();
	
							} else if(response.error){
								BootstrapDialog.alert("Error!");
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
function editPayment(paymentId){
	document.location = basePath + "/booking/edit-payment/" + paymentId;
}
function printReceipt(paymentId){
	window.open(
			  basePath+'/payment/print-receipt/'+paymentId,
			  '_blank' // <- This is what makes it open in a new window.
			);
};