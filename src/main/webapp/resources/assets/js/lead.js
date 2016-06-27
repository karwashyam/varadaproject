var oTable;

$(document).ready(function() {

	users();

});

function users() {
	var i=1;
	oTable = $("#leadTable").dataTable({

		"info": false,
		"bProcesing" : true,
		"oLanguage": {"sSearch": ""},
		"bServerSide" : true,
		"bLengthChange": false,
		"sAjaxSource" : basePath+'/lead/list.json',
		"columnDefs": [
		               {
		                   
		               }
		             
		           ],
		"aoColumns" : [
				{
					"sTitle" : "Sr. No.",
					"mData" : "srNo",
					"sClass" : "center",
			//		"sWidth" : "6%"
				}, 
				{
					"sTitle" : "Phone",
					"mData" : "phone",
				}, 
				{
					"sTitle" : "Email",
					"mData" : "email"
				},
				{
					"sTitle" : "Lead From",
					"mData" : "leadFrom"
				},
				{
					"sTitle" : "Joined Date",
					"mData" : "created_date"
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
	$.ajax( {
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