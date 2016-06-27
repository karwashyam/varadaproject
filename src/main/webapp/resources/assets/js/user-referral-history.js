var oTable;

$(document).ready(function() {

	users();

	var userId = $("#userId").val();
	$('#usersReferralHistoryTable_wrapper').prepend('<a href="' + basePath + '/users/edit/referral-history/add.do?userId='+ userId + '" class="actionbtn" style="float:right;">Add Wallet Balance</a>');
	
});

function users() {
	
	var userId = $("#userId").val();
	
	var i = 1;
	oTable = $("#usersReferralHistoryTable").dataTable({

		"info": false,
		"bProcesing" : true,
		"oLanguage": {"sSearch": ""},
		"bServerSide" : true,
		"bLengthChange": false,
		"sAjaxSource" : basePath+'/users/edit/referral-history/list.json?userId='+userId,
		"columnDefs": [
		               {
		                   
		               }
		           ],
		"aoColumns" : [
				/*{
					"sTitle" : "Sr. No.",
					"mData" : "srNo",
					"sClass" : "center",
				}, */
				{
					"mData" : "User Id",
					"mData" : "userId",
					"sClass" : "center",
				},
				{
					"mData" : "User Name",
					"mData" : "userName",
					"sClass" : "center",
				},
				{
					"sTitle" : "Amount",
					"mData" : "amount"
				}, 
				/*{
					"sTitle" : "Order Id",
					"mData" : "orderId",
				}, 
				{
					"sTitle" : "User Used",
					"mData" : "userUsed"
				},*/
				{
					"sTitle" : "History Note",
					"mData" : "historyNote"
				},
				{
					"sTitle" : "Used Date",
					"mData" : "usedDate"
				}
		],
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"dataType" : 'json',
				"type" : "GET",
				"url" : sSource,
				"data" : aoData,
				"success" : fnCallback,
				"error":handleAjaxError
			});
		},
		"sPaginationType" : "full_numbers"

		});
}

function fnServerData ( sSource, aoData, fnCallback ) {
	isSessionExtend = true;
	$.ajax({
		 "dataType": 'json',
		 "type": "GET",
		 "url": sSource,
		 "contentType": 'application/json',
		 "data": aoData,
		 "success": fnCallback,
		 "timeout":10000,
		 "cache" :false,	
		 "error":handleAjaxError
	});	
}

function handleAjaxError( xhr, textStatus, error ) {
	
	if ( textStatus == 'timeout' ) {
	  alert( 'The server took too long to send the data.' );
	}
	else if(textStatus == "parsererror") {
		alert("Ajax error occured.");
	}
}