var oTable;
var isEditAccess;
var isDeleteAccess;
var validateRequireFields = ["memberId","franchiseeId","projectId","paymentSchemeId","plotId","ratePerYard","nomineeName","nomineeFather","nomineeAddress","nomineeRelation","paymentDate","nomineeDob"];
var validateRequireFields1 = ["bookingId"];

$(document).ready(function() {
	var today = new Date();
	  $('#startDate')
    .datepicker({
  	  autoclose: true,  
  	  maxDate : today,
        format: 'mm/dd/yyyy'
    })
    .on('changeDate', function(e) {
        // Revalidate the date field
//        $('#projectFrm').validate('revalidateField', 'date');
    });
	  
	  $('#endDate')
    .datepicker({
  	  autoclose: true,  
  	  maxDate : today,
        format: 'mm/dd/yyyy'
    })
    .on('changeDate', function(e) {
        // Revalidate the date field
//        $('#projectFrm').validate('revalidateField', 'date');
    });
	  
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	var reportType=$("#reportType").val();
	
	citytable();
	 $("#btnReport").click(function(event){
	 	  $('.nav-tabs li').removeClass('active'); // remove active class from tabs
	       $(this).addClass('active');
	       citytable();
});
	 $("#btnExport").click(function(event){
	       $(this).addClass('active');
	       fnExcelReport();
});
	
});



function fnExcelReport()
{
    var tab_text = '<table border="1px" style="font-size:20px" ">';
    var textRange; 
    var j = 0;
    var tab = document.getElementById('customerfilter-datatable'); // id of table
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


function citytable() {
	var i = 1;
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	var reportType=$("#reportType").val();

	oTable = $("#customerfilter-datatable").dataTable({

		"info" : false,
		"bProcesing" : true,
		"bPaginate" : true,
		"iDisplayStart" : 0,
		"iDisplayLength" : 10,
		"bDestroy": true,
		"oLanguage" : {
			"sSearch" : ""
		},
		"order" : [ [ 1, "asc" ] ],
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/report/customerfilter/list.json?startDate='+startDate+'&endDate='+endDate+'&reportType='+reportType,
	/*	"columnDefs" : [ {
			"mRender" : function(data, type, row) {
				var actionsLinks = '<div style="">'; 
					actionsLinks += '<a href="javascript:void(0);" '+' onclick="addPayment('+"'" + data+"'" + ');">'
			        	+'Add Payment'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>';
				actionsLinks += '</div>';
				return actionsLinks;
			},
			"aTargets" : [ 8 ]
			},
			{
				"mRender" : function(data, type, row) {
					var actionsLinks = '<div style="">'; 
						actionsLinks += '<a href="javascript:void(0);" '+' onclick="viewBooking('+"'" + data+"'" + ');">'
			        	+'View Details'+'</a>&nbsp &nbsp';
					actionsLinks += '</div>';
					return actionsLinks;
				},
				"aTargets" : [ 9 ]
				}
		],*/
		"aoColumns" : [

		{
			"sTitle" : "Project",
			"mData" : "projectName",
			"sClass" : "center",
			"bVisible" : false
		// "sWidth" : "6%"
		}, {
			"mData" : "Booking No.",
			"mData" : "bookingCode",
			"sClass" : "center"
		}, {
			"sTitle" : "Plot No.",
			"mData" : "plotName"
		},{
			"sTitle" : "Franchisee",
			"mData" : "franchiseeName"
		},{
			"sTitle" : "Member",
			"mData" : "memberName"
		},{
			"sTitle" : "Member Code",
			"mData" : "memberCode"
		},{
			"sTitle" : "Phone",
			"mData" : "phone1"
		},{
			"sTitle" : "Email",
			"mData" : "email"
		},{
			"sTitle" : "Remaining Payment",
			"mData" : "remainingPayment"
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
	
	var table = $('#customerfilter-datatable').DataTable();
	
	table.fnDestroy();
	
	citytable();
}