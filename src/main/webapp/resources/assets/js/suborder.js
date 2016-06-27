var oTable;


$(document).ready(function() {
	var orderId=$('#order_id').val();
	suborders(orderId);
	
});

function suborders(orderId) {
	var i=1;
	oTable = $("#subordersTable").dataTable({

		"info": false,
		"bProcesing" : true,
		"iDisplayLength": 100,
		"oLanguage": {"sSearch": ""},
		"bServerSide" : true,
		"bLengthChange": false,
		"sAjaxSource" : basePath+'/order/edit/list.json?orderId='+orderId,
		"columnDefs": [
		               {
		               }
		             
		           ],
		"aoColumns" : [
		               
				{
					"sTitle" : "Sub Order Id",
					"mData" : "order_product_id",
					"sClass" : "center",
			//		"sWidth" : "6%"
				}, 
				{
					"mData" : "Product Id",
					"mData" : "product_id",
					"sClass" : "center",
				}, 
				{
					"sTitle" : "MRP",
					"mData" : "price"
				}, 
				{
					"sTitle" : "Discounted Price",
					"mData" : "discounted_price",
				}, 
				{
					"sTitle" : "Weight",
					"mData" : "weight"
				},
				{
					"sTitle" : "Product Type",
					"mData" : "product_type_name"
				},
		
				{
					"sTitle" : "Action",
					"mData" : "action",
					"sClass" : "center",
					"bSortable":false 
		
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

function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}