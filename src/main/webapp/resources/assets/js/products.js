var oTable;

$(document).ready(function() {

	fetchProducts();
	$('.dataTables_filter input').attr("placeholder", "Search");
	
  /*  $('#bar-details tbody').dblclick(function (event) {
    	event.preventDefault();
		document.location = basePath + "/secure/vendor/profile.do?studentId="+selectedStudentId;
		return;
  });*/
	
	/*$("#btnAddBar").click(function(event){
		window.location = basePath + "/secure/admin/bar/add.do"		
	});*/
});

function fetchProducts() {
	var i=1;
	oTable = $("#productsTable").dataTable({

		"info": false,
		"bProcesing" : true,
		"oLanguage": {"sSearch": ""},
		"order":[[6,"desc"]],
		"bServerSide" : true,
		"bLengthChange": false,
		"sAjaxSource" : basePath+'/products/list.json',
		/*"columnDefs": [
		               {
		                   // The `data` parameter refers to the data for the cell (defined by the
		                   // `data` option, which defaults to the column being worked with, in
		                   // this case `data: 0`.<a href="" onclick="confirmDelete('+"'"+data+"'"+');"><img src="'+
	                      // basePath+'/resources/assets/img/internal/delete.png"></a>
		                   "render": function ( data, type, row ) {
		                       return 'aa<a href='+basePath+'/order/edit.do?order_id='+data+'>edit</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		                   },
		                   "targets": 8
		               }
		           ],*/
		"aoColumns" : [
				{
					"sTitle" : "Product Id",
					"mData" : "productId",
					"sClass" : "center"
				}, 
				{
					"mData" : "Product Type",
					"mData" : "productTypeName",
					"sClass" : "center"
				},
				{
					"mData" : "Product Tags",
					"mData" : "tagName",
					"sClass" : "center"
				},
				{
					"sTitle" : "Product Price",
					"mData" : "productPrice",
					"sClass" : "center"
				}, 
				{
					"sTitle" : "Quantity",
					"mData" : "quantity",
					"sClass" : "center"
				}, 
				{
					"sTitle" : "Weight",
					"mData" : "weight",
					"sClass" : "center"
				},
				{
					"sTitle" : "Caption",
					"mData" : "caption",
					"sClass" : "center"
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
		alert("some error occured.");
	}
}