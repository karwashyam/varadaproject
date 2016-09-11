var startLen=-1,limit=0,display;
var oTable;
var tableTotalRows=0;
jQuery(document).ready(function() {
	var today = new Date();
	  $('#startDate')
      .datepicker({
    	  autoclose: true,  
    	  maxDate : today,
          format: 'mm/dd/yyyy'
      })
      .on('changeDate', function(e) {
          // Revalidate the date field
//          $('#projectFrm').validate('revalidateField', 'date');
      });
	  
	  $('#endDate')
      .datepicker({
    	  autoclose: true,  
    	  maxDate : today,
          format: 'mm/dd/yyyy'
      })
      .on('changeDate', function(e) {
          // Revalidate the date field
//          $('#projectFrm').validate('revalidateField', 'date');
      });
	  
	var startDate=$("#startDate").val();
 	var endDate=$("#endDate").val();
	var reportType=$("#reportType").val();


	 
	 $("#btnReport").click(function(event){
		 	  $('.nav-tabs li').removeClass('active'); // remove active class from tabs
		       $(this).addClass('active');
		   	 reportType=$("#reportType").val();

		       if(reportType=='3'){
		    	   alert("hi");
		    	   console.log("unbooked");
		    	   unbookingReportDetails();
		       }else{
		    	   bookingReportDetails();
		       }
		       console.log("\t\t display="+display);
		       if(display==undefined){
		    	   console.log('123');
		       }
		       if(display!='undefined' && display!=''){
		    	   $("#btnExport").attr("disabled",false);
		       }
			 });
	 
	 
	/* $("#btnReport").click(function(event){
		 	  $('.nav-tabs li').removeClass('active'); // remove active class from tabs
		       $(this).addClass('active');
		       bookingReportDetails();
	 });*/
     
 	$("#btnExport").click(function(event) {
		 var fromDate=$("#startDate").val();
		 var toDate=$("#endDate").val();
			var reportType=$("#reportType").val();

	var searchString=$('.tapHistoryForBar_filter .input').val();
//		url=basePath + "/report/unbooking/export.json?fromDate="+startDate+"&toDate="+endDate+"&start="+startLen+"&end="+limit+"&searchString="+searchString,
//	var tableLen=oTable.fnSettings().aoData.length;
//	  var totalDisplayRecord =oTable.page.info().recordsDisplay
	console.log("\t\t oTable totalDisplayRecord=\t\t --"+searchString);
		 url=basePath + "/report/bookingdata/export.json?fromDate="+fromDate+"&toDate="+toDate+"&start="+startLen+"&end="+limit+"&reportType="+reportType;

//	window.open(url);
	});
	
	
});



function bookingReportDetails() {
	var startDate=$("#startDate").val();
 	var endDate=$("#endDate").val();
	var reportType=$("#reportType").val();
	oTable=$("#tapHistoryForBar").dataTable({
		"info": false,
		"bProcesing" : true,
		"bDestroy": true,
		"bServerSide" : true,
		"iDisplayStart" : 0,
		"iDisplayLength" : 10,
		"bRetrieve" : true,
		"bSort" : true,
		"oLanguage": {"sSearch": ""},
		"bLengthChange": false,
		"sAjaxSource" : basePath+"/report/ajax/bookingdata.json?startDate="+startDate+"&endDate="+endDate+"&reportType="+reportType,
		
		"aoColumns" : [
		               
				{
					"sTitle" : "Sr.No",
					"mData" : "srNo",
					"bVisible":true 
				}, 
				
				{
					"sTitle" : "bookingId",
					"mData" : "bookingId",
					"bVisible":false 
				}, 
				{
					"sTitle" : "Booking Code",
					"mData" : "bookingCode",
					"bVisible":true 
				}, 
				{
					"sTitle" : "Franchisee Name",
					"mData" : "franchiseeName",
					"bVisible":true 
				},
				{
					"sTitle" : "Member Name",
					"mData" : "memberName",
					"bVisible":true 
				},
				{
					"sTitle" : "Project Name",
					"mData" : "projectName"
				}, 
				{
					"sTitle" : "Plot Name",
					"mData" : "plotName"
				},
				{
					"sTitle" : "Plot Size",
					"mData" : "plotSize"
				},
				{
					"sTitle" : "Date",
					"mData" : "month"
				}
				
				

		],


		"fnServerData" : function(sSource, aoData, fnCallback) {
		$.ajax({
		"dataType" : 'json',
		"type" : "GET",
		"url" : sSource,
		"data" : aoData,
		"success" : fnCallback
		});
		},
		 "fnFooterCallback": function(nRow, aaData, iStart, iEnd, aiDisplay) {
			 console.log("iStart=="+aaData+"\t--iEnd--"+aiDisplay);
			 startLen=iStart;
			 limit=iEnd;
			 display=aiDisplay;
//			  alert(aaData);
		 },
		"sPaginationType" : "full_numbers"

		});// dataTable
	
		
}


function unbookingReportDetails() {
	   console.log("unbooked");

	var startDate=$("#startDate").val();
 	var endDate=$("#endDate").val();
	$("#tapHistoryForBar").dataTable({
		"info": false,
		"bProcesing" : true,
		"bServerSide" : true,
		"oLanguage": {"sSearch": ""},
		"bLengthChange": false,
		"sAjaxSource" : basePath+"/report/ajax/unbookeddata.json?startDate="+startDate+"&endDate="+endDate,
		
		"aoColumns" : [
		               
				{
					"sTitle" : "Sr.No",
					"mData" : "srNo",
					"bVisible":true 
				}, 
				{
					"sTitle" : "Plot Id",
					"mData" : "projectPlotId",
					"bVisible":false
				}, 
				{
					"sTitle" : "Project Id",
					"mData" : "projectId",
					"bVisible":false
				}, 
				{
					"sTitle" : "Plot Id",
					"mData" : "projectPlotId",
					"bVisible":false
				}, 
				{
					"sTitle" : "Project Name",
					"mData" : "title"
				}, 
				{
					"sTitle" : "Plot Name",
					"mData" : "plotName"
				},
				{
					"sTitle" : "Plot Size",
					"mData" : "plotSize"
				},
				{
					"sTitle" : "Date",
					"mData" : "month"
				}
				
				

		],


		"fnServerData" : function(sSource, aoData, fnCallback) {
		$.ajax({
		"dataType" : 'json',
		"type" : "GET",
		"url" : sSource,
		"data" : aoData,
		"success" : fnCallback
		});
		},
		  "fnDrawCallback" : function(oSettings) {
	          	 var iTotalDisplayRecords = oTable.fnSettings().fnRecordsDisplay();
	          	 console.log(iTotalDisplayRecords);

	           },
		 "fnFooterCallback": function(nRow, aaData, iStart, iEnd, aiDisplay) {
			 console.log("iStart=="+aaData+"\t--iEnd--"+aiDisplay);
			 startLen=iStart;
			 limit=iEnd;
//			  alert(aaData);
			 display=aiDisplay;

		 },
		"sPaginationType" : "full_numbers"

		});// dataTable
	
		
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

function fnGetSelected(oTableLocal) {
	
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
}

function displayWarningNoRowSelectedDialog() {
	var warningDialogHtml = jQuery('<div></div>').html('No Row Selected.');

	warningDialogHtml.dialog({
		modal : true,
		title : 'Warning!',
		buttons : {
			Ok : function() {
				jQuery(this).dialog("close");
			}
		}
	});
}

function displayConfirmationDialog() {
	
}



