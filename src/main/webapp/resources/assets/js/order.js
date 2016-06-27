var oTable;

$(document).ready(function() {

	orders();
	
	$('#ordersTable_wrapper').prepend('<a href="' + basePath + '/order/add.do' + '" class="actionbtn" style="float:right;">New Order</a>');
	//$('.dataTables_filter input').attr("placeholder", "Search");
	
  /*  $('#bar-details tbody').dblclick(function (event) {
    	event.preventDefault();
		document.location = basePath + "/secure/vendor/profile.do?studentId="+selectedStudentId;
		return;
  });*/
	
	/*$("#btnAddBar").click(function(event){
		window.location = basePath + "/secure/admin/bar/add.do"		
	});*/
});

function orders() {
	
	var userId = $("#userId").val();
	
	var i=1;
	oTable = $("#ordersTable").dataTable({

		"info": false,
		"bProcesing" : true,
		"oLanguage": {"sSearch": ""},
		"order":[[6,"desc"]],
		"bServerSide" : true,
		"bLengthChange": false,
		"sAjaxSource" : basePath+'/order/list.json?userId='+userId,
		"columnDefs": [
		               {
		                   /*// The `data` parameter refers to the data for the cell (defined by the
		                   // `data` option, which defaults to the column being worked with, in
		                   // this case `data: 0`.<a href="" onclick="confirmDelete('+"'"+data+"'"+');"><img src="'+
	                      // basePath+'/resources/assets/img/internal/delete.png"></a>
		                   "render": function ( data, type, row ) {
		                       return 'aa<a href='+basePath+'/order/edit.do?order_id='+data+'>edit</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		                   },
		                   "targets": 8*/
		               }
		             
		           ],
		"aoColumns" : [
		               
				{
					"sTitle":"Sr.No",
					"mData" : "order_id",
					"bVisible":false 
				},
				{
					"sTitle" : "Order Id",
					"mData" : "order_id",
					"sClass" : "center",
					"sWidth" : "6%"
				}, 
				{
					"mData" : "User Id",
					"mData" : "user_id",
					"sClass" : "center",
				}, 
				{
					"sTitle" : "User Name",
					"mData" : "user_full_name"
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
					"sTitle" : "Products",
					"mData" : "product_id"
				},
		
				{
					"sTitle" : "Order Total",
					"mData" : "total"
				},
				{
					"sTitle" : "Order Date",
					"mData" : "created_date"
					
		
				},
				{
					"sTitle" : "Payment",
					"mData" : "payment_method"
					
		
				},
				{
					"sTitle" : "Courier",
					"mData" : "courier_method"
					
		
				},
				{
					"sTitle" : "Order Status",
					"mData" : "order_status",
					"sWidth" : "12%"
				},
		
				{
					"sTitle" : "Action",
					"mData" : "action",
					"sClass" : "center",
					"bSortable":false ,
					"sWidth" : "8%"
		
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
		/*jQuery("#bar-details tbody").click(function(event) {

		if (jQuery(event.target.parentNode).hasClass('row_selected')) {
			jQuery(event.target.parentNode).removeClass('row_selected');

		} else {
			jQuery('#bar-details').find('tr').each(function() {
				jQuery('tr').removeClass('row_selected');

			});
			jQuery(event.target.parentNode).addClass('row_selected');
		}
		
		var anSelected = fnGetSelected(oTable);
		selectedCraftBarId= anSelected.toString().split(",")[0];
		});*/
		
	
	
	
	
}

/*function fnGetSelected(oTableLocal) {
	var anSelected = fnGetSelectedTr(oTableLocal);
	if (anSelected[0] != undefined) {
		var rows = [];
		for ( var i = 0; i < anSelected.length; i++) {
			var aPos = oTableLocal.fnGetPosition(anSelected[i]);
			var aData = oTableLocal.fnGetData(aPos);
			rows.push(aData);
		}

		return rows;
	} else {
		return null;
	}
}

function fnGetSelectedTr(oDataTable) {
	var aReturn = new Array();
	var aTrs = oDataTable.fnGetNodes();
	
	for ( var i = 0; i < aTrs.length; i++) {
		if ($(aTrs[i]).hasClass('row_selected')) {
			aReturn.push(aTrs[i]);
		}
	}
	return aReturn;
}*/


/*function confirmDelete(bartenderId){
	if(confirm("Are you sure you want to delete?")){
		
		$.ajax({
			type: "GET",
			url: basePath+"/secure/bar/admin/bartenders/bartender/delete.do",
			data: { bartenderId: bartenderId }
			})
			.done(function( msg ) {	
				var table = $('#bartenderTable').DataTable();
				table.ajax.reload();
		});
		
}}*/

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

