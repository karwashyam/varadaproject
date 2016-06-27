var oTable;

$(document).ready(function() {

	fetchCoupons();
	$('.dataTables_filter input').attr("placeholder", "Search");
	$('#couponsTable_wrapper').prepend('<a href="' + basePath + '/coupons/add.do' + '" class="actionbtn" style="float:right;">Add Coupon</a>');
	
	$("#startDateDisplay").datepicker({
        dateFormat:"dd-mm-yy"
    });
	
	$("#endDateDisplay").datepicker({
        dateFormat:"dd-mm-yy"
    });
});

function fetchCoupons() {
	var i = 1;
	oTable = $("#couponsTable").dataTable({

		"info": false,
		"bProcesing" : true,
		"oLanguage": {"sSearch": ""},
		"order":[[6,"desc"]],
		"bServerSide" : true,
		"bLengthChange": false,
		"sAjaxSource" : basePath+'/coupons/list.json',
		
		"aoColumns" : [
				{
					"sTitle" : "Coupon Id",
					"mData" : "couponId",
					"bVisible":false 
				}, 
				{
					"sTitle" : "Sr. No.",
					"mData" : "srNo",
					"sClass" : "center"
				}, 
				{
					"mData" : "Coupon Code",
					"mData" : "couponCode",
					"sClass" : "center"
				}, 
				{
					"sTitle" : "Start Date",
					"mData" : "startDateDisplay",
					"sClass" : "center"
				}, 
				{
					"sTitle" : "End Date",
					"mData" : "endDateDisplay",
					"sClass" : "center"
				}, 
				{
					"sTitle" : "Discount In Percentage(%)",
					"mData" : "discountInPercentage",
					"sClass" : "center"
				},
				{
					"sTitle" : "Max times per user",
					"mData" : "maximumTimeUsed",
					"sClass" : "center"
				},
				{
					"sTitle" : "Max Disocunt",
					"mData" : "maxDisocunt",
					"sClass" : "center"
				},
				{
					"sTitle" : "Can Used With Referral",
					"mData" : "canUsedWithReferral",
					"sClass" : "center"
				},
				{
					"sTitle" : "Disount on min payable price",
					"mData" : "discountOnMinimumPayablePrice",
					"sClass" : "center"
				},
				{
					"sTitle" : "Action",
					"mData" : "action",
					"sClass" : "center",
					"bSortable":false 
				}
		],
		"columnDefs": [
	           {
	               // The `data` parameter refers to the data for the cell (defined by the
	               // `data` option, which defaults to the column being worked with, in
	               // this case `data: 0`.<a href="" onclick="confirmDelete('+"'"+data+"'"+');"><img src="'+
	              // basePath+'/resources/assets/img/internal/delete.png"></a>
	              "render": function ( data, type, row ) {
	            	  
	            	  //console.log('data = ' + data);
	            	  /*if (data == true)
	            	  {
	            		  console.log('data = true');
	            		  return 'Yes';
	            	  }
	            	  else if (data == false)
	            	  {
	            		  console.log('data = false');
	            		  return 'No';
	            	  }*/
	            	  return data;
	              },
	              "targets": 7
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