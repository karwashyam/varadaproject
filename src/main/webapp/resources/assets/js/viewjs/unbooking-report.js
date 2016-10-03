var startLen,limit;
var oTable;
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
//	var reportType=$("#reportType").val();
    bookingReportDetails();

	 $("#btnReport").click(function(event){
		 	  $('.nav-tabs li').removeClass('active'); // remove active class from tabs
		       $(this).addClass('active');
		       bookingReportDetails();
	 });
     
 	$("#btnExport").click(function(event) {
	
	  $(this).addClass('active');
      fnExcelReport();
	});
	
	
});

function fnExcelReport()
{
    var tab_text = '<table border="1px" style="font-size:20px" ">';
    var textRange; 
    var j = 0;
    var tab = document.getElementById('tapHistoryForBar'); // id of table
    var lines = tab.rows.length;

    // the first headline of the table
    if (lines > 0) {
        tab_text = tab_text + '<tr bgcolor="#DFDFDF">' + tab.rows[0].innerHTML + '</tr>';
// Row heading can be inserted here
    }

    // table data lines, loop starting from 1
    for (j = 1 ; j < lines; j++) {     
        tab_text = tab_text + "<tr>" + tab.rows[j].innerHTML + "</tr>";
// row data can be inserted here from json data
    }

    tab_text = tab_text + "</table>";
    tab_text = tab_text.replace(/<A[^>]*>|<\/A>/g, "");             //remove if u want links in your table
    tab_text = tab_text.replace(/<img[^>]*>/gi,"");                 // remove if u want images in your table
    tab_text = tab_text.replace(/<input[^>]*>|<\/input>/gi, "");    // reomves input params
    // console.log(tab_text); // aktivate so see the result (press F12 in browser)

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE "); 

     // if Internet Explorer
    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
        txtArea1.document.open("txt/html","replace");
        txtArea1.document.write(tab_text);
        txtArea1.document.close();
        txtArea1.focus(); 
        sa = txtArea1.document.execCommand("SaveAs", true, "DataTableExport.xls");
    }  
    else // other browser not tested on IE 11
        sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));  

    return (sa);
}


function bookingReportDetails() {
	var startDate=$("#startDate").val();
 	var endDate=$("#endDate").val();
 	oTable=$("#tapHistoryForBar").dataTable({
		"info": false,
		"bProcesing" : true,
		"bDestroy": true,
		"bServerSide" : true,
		"iDisplayStart" : 0,
		"iDisplayLength" : 2,
		"bRetrieve" : true,
		"bSort" : true,
		"bFilter" : true,
		"oLanguage": {"sSearch": ""},
		"bLengthChange": false,
		"sAjaxSource" : basePath+"/report/ajax/unbookeddata.json",//?startDate="+startDate+"&endDate="+endDate,
		
		"aoColumns" : [
		               
				{
					"sTitle" : "Sr.No",
					"mData" : "srNo",
					"bVisible":true 
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

           }
			,
		 "fnFooterCallback": function(nRow, aaData, iStart, iEnd, aiDisplay) {
			 console.log("iStart=="+aaData+"\t--iEnd--"+aiDisplay);
			 startLen=iStart;
			 limit=iEnd;
//			  alert(aaData);
		 },
		"sPaginationType" : "full_numbers"

		});// dataTable
	
		
}
/*

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
		"sPaginationType" : "full_numbers"

		});// dataTable
	
		
}*/

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



