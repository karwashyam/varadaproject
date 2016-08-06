var oTable;

$(document).ready(function() {

	citytable();
	
	$('#ordersTable_wrapper').prepend('<a href="' + basePath + '/order/add.do' + '" class="actionbtn" style="float:right;">New Order</a>');
});

function citytable() {
	var i=1;
	oTable = $("#city-datatable").dataTable({

		"info": false,
		"bProcesing" : true,
		"oLanguage": {"sSearch": ""},
		"order":[[1,"asc"]],
		"bServerSide" : true,
		"bLengthChange": false,
		"sAjaxSource" : basePath+'/city/list.json',
		"columnDefs": [
		               {
		                   /*basePath+'/resources/assets/img/internal/delete.png"></a>
		                   "render": function ( data, type, row ) {
		                       return 'aa<a href='+basePath+'/order/edit.do?order_id='+data+'>edit</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		                   },
		                   "targets": 8*/
		               }
		             
		           ],
		"aoColumns" : [
		               
				{
					"sTitle" : "City Id",
					"mData" : "cityId",
					"sClass" : "center",
					"bVisible":false
			//		"sWidth" : "6%"
				}, 
				{
					"mData" : "City Name",
					"mData" : "cityName",
					"sClass" : "center"
				}, 
				{
					"sTitle" : "State Name",
					"mData" : "stateName"
				},
				{
					"sTitle" : "Action",
					"mData" : "action"
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