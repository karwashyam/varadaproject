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
	citytable();
	
	
});

function citytable() {
	var i = 1;
	oTable = $("#franchisee-datatable").dataTable({

		"info" : false,
		"bProcesing" : true,
		"oLanguage" : {
			"sSearch" : ""
		},
		"order" : [ [ 1, "asc" ] ],
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/report/franchisee/list.json',
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
		},/* {
			"sTitle" : "Plot No.",
			"mData" : "plotName"
		},*/
		{
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
	
	var table = $('#franchisee-datatable').DataTable();
	
	table.fnDestroy();
	
	citytable();
}