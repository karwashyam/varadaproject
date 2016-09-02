var oTable;
var isEditAccess;
var isDeleteAccess;

$(document).ready(function() {
	var isAddAccess = jQuery("#isAddAccess").val();
	isEditAccess = jQuery("#isEditAccess").val();
	isDeleteAccess = jQuery("#isDeleteAccess").val();
	
	citytable();
	
	if(isAddAccess !== "true"){
		$("#add").prop("disabled", true);
	}
	
	$('#booking-datatable_wrapper').prepend('<a href="'+ basePath+ '/booking/add'+ '" class="actionbtn" style="float:right;"><button type="button" class="btn btn-round btn-primary">New Booking</button></a>');
	
	jQuery("#add").click(function() {
		document.location = basePath + "/booking/add";
	});
	
	
	
	$("#projectId").change(function() {
    	var projectId=$("#projectId").val();
		  $.ajax({
	            type: "GET",
	            url:basePath+"/booking/fetch/"+projectId+".json",
	            async: false,
	            success: function (data) {
	            	$('#paymentSchemeId').selectpicker('refresh');
	            	   $("#paymentSchemeId").empty();
//	            	   $("#stateId option").addClass("bs-title-option");
	            	   $("#paymentSchemeId").append($("<option> "+                                                 
                    "</option>").val("NONE").html("Select Payment Scheme"));
	            	   
	            	   var temp = '';
	            	   
	            	for ( var i in data.paymentSchemeList) {
	         
	            		var id = data.paymentSchemeList[i].paymentSchemeId+"_"+data.paymentSchemeList[i].noOfMonths+"_"+data.paymentSchemeList[i].interestRate;
	            		var name = data.paymentSchemeList[i].title;
	            		
	            		  $("#paymentSchemeId").append($("<option> "+                                                 
	                       "</option>").val(id).html(name));
	            		
	            	}
	            	$('#paymentSchemeId').selectpicker('refresh');
	            	
	            	$('#plotId').selectpicker('refresh');
	            	   $("#plotId").empty();
//	            	   $("#stateId option").addClass("bs-title-option");
	            	   $("#plotId").append($("<option> "+                                                 
                    "</option>").val("NONE").html("Select Plot"));
	            	   
	            	   var temp = '';
	            	   
	            	for ( var i in data.plotList) {
	         
	            		var id = data.plotList[i].projectPlotId+"_"+data.plotList[i].plotSize;
	            		var name = data.plotList[i].plotName;
	            		
	            		  $("#plotId").append($("<option> "+                                                 
	                       "</option>").val(id).html(name));
	            		
	            	}
	            	$('#plotId').selectpicker('refresh');
	            	}
	        });
		 
	  });
	
	$("#paymentSchemeId").change(function() {
    	var paymentSchemeId=$("#paymentSchemeId").val().split("_");
		$("#months").text(paymentSchemeId[1]);
		$("#interestRate").text(paymentSchemeId[2]);
    	
	});
	$("#plotId").change(function() {
    	var paymentSchemeId=$("#plotId").val().split("_");
		$("#plotSize").text(paymentSchemeId[1]);
	});
	$("#memberId").change(function() {
    	var paymentSchemeId=$("#memberId").val().split("_");
		$("#fatherName").text(paymentSchemeId[2]);
		$("#memberName").text(paymentSchemeId[1]);
	});
	
	$("#ratePerYard").change(function() {
		var yardSize=$("#plotSize").text();
		var downPayment=$("#downPayment").val();
		var loanAmount = $("#ratePerYard").val()*yardSize-downPayment;
		var numberOfMonths = $("#months").text();
		var rateOfInterest = $("#interestRate").text();
		var monthlyInterestRatio = (rateOfInterest/100)/12;
			
		var top = Math.pow((1+monthlyInterestRatio),numberOfMonths);
		var bottom = top -1;
		var sp = top / bottom;
		var emi = ((loanAmount * monthlyInterestRatio) * sp);
		var full = numberOfMonths * emi;
		var interest = full - loanAmount;
		$("#emi").text(emi);
		$("#totalAmount").text(full);
		console.log(interest);
	});
	
	$("#downPayment").change(function() {
		var yardSize=$("#plotSize").text();
		var downPayment=$("#downPayment").val();
		var loanAmount = $("#ratePerYard").val()*yardSize-downPayment;
		var numberOfMonths = $("#months").text();
		var rateOfInterest = $("#interestRate").text();
		var monthlyInterestRatio = (rateOfInterest/100)/12;
			
		var top = Math.pow((1+monthlyInterestRatio),numberOfMonths);
		var bottom = top -1;
		var sp = top / bottom;
		var emi = ((loanAmount * monthlyInterestRatio) * sp);
		var full = numberOfMonths * emi;
		var interest = full - loanAmount;
		$("#emi").text(emi);
		$("#totalAmount").text(full);
		console.log(interest);
	});
	
	
});

function citytable() {
	var i = 1;
	oTable = $("#booking-datatable").dataTable({

		"info" : false,
		"bProcesing" : true,
		"oLanguage" : {
			"sSearch" : ""
		},
		"order" : [ [ 1, "asc" ] ],
		"bServerSide" : true,
		"bLengthChange" : false,
		"sAjaxSource" : basePath + '/booking/list.json',
		"columnDefs" : [ {
			"mRender" : function(data, type, row) {
				var actionsLinks = '<div style="">'; 
					actionsLinks += '<a href="javascript:void(0);" '+' onclick="editBooking('+"'" + data+"'" + ');">'
			        	+'Edit'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" '+' onclick="cancelBooking('+"'" + data+"'" + ');">Cancel&nbsp; <i class="fa fa-trash font-size-17px"></i></a>';
				actionsLinks += '</div>';
				return actionsLinks;
			},
			"aTargets" : [ 8 ]
			},
			{
				"mRender" : function(data, type, row) {
					var actionsLinks = '<div style="">'; 
					if(isEditAccess === "true"){
						actionsLinks += '<a href="javascript:void(0);" '+' onclick="editBooking('+"'" + data+"'" + ');">'
				        	+'Edit'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" '+' onclick="cancelBooking('+"'" + data+"'" + ');">Cancel&nbsp; <i class="fa fa-trash font-size-17px"></i></a>';
					}else{
						actionsLinks += '<a class="disabled" href="javascript:void(0);" '+' onclick="editState('+"'" + data+"'" + ');">'
			        	+'Edit'+'</a>&nbsp &nbsp';
					}
					actionsLinks += '</div>';
					return actionsLinks;
				},
				"aTargets" : [ 9 ]
				}
		],
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
			"sTitle" : "Add Payment",
			"mData" : "bookingId"
		}, {
			"sTitle" : "Action",
			"mData" : "bookingId"
		} ],
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

function editBooking(bookingId){
	if(isEditAccess === "true"){
		document.location = basePath + "/booking/edit/" + bookingId;
	}
}

function cancelBooking(bookingId){
	if(isDeleteAccess === "true"){
		BootstrapDialog.show({
			message: 'Are you sure you want to cancel this Booking ?',
			title: 'Alert',
			buttons: [{
				label: 'YES',
				action: function(dialogItself) {
					$.ajax({
	
						type: "DELETE",
						url: basePath +"/booking/cancel?bookingId="+bookingId,
	
						dataType :'json',
						contentType: 'application/json',
						mimeType: 'application/json',
	
						success: function( response ) {
							if(response.success){
								$(".errorMessage").html("");
								BootstrapDialog.alert("Booking cancelled successfully.");
								var table = $('#booking-datatable').DataTable();
								$('#booking-datatable').dataTable().fnDraw();
								dialogItself.close();
	
							} else if(response.error){
								BootstrapDialog.alert("Some error happened");
								dialogItself.close();
							}
							dialogItself.close();
						},
						error : function() {
							dialogItself.close();
							return false;
						}
					})
				}
			}, {
				label: 'NO',
				action: function(dialogItself) {
					dialogItself.close();
				}
			}]
		});
	}
}


function loadTable(){
	
	var table = $('#booking-datatable').DataTable();
	
	table.fnDestroy();
	
	citytable();
}