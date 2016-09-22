var oTable;
var isEditAccess;
var isDeleteAccess;
var validateRequireFields = ["memberId","franchiseeId","projectId","paymentSchemeId","plotId","ratePerYard","nomineeName","nomineeFather","nomineeAddress","nomineeRelation","paymentDate","nomineeDob"];
var validateRequireFields1 = ["bookingId"];

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
	         
	            		var id = data.plotList[i].projectPlotId+"_"+data.plotList[i].plotSize+"_"+data.plotList[i].plotName;
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
		$("#interestRate1").val(emi);
		$("#noOfEmi").val(emi);
    	
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
	
	$('input:radio[name="paymentMethod"]').change(
			function(){
		        if ($(this).is(':checked') && $(this).val() == 'Cash') {
		        	$("#chequeNo").hide();	
		        	$("#issueDate").hide();
		        	$("#bank").hide();
		        	$("#accountHolder").hide();
		        }else  if ($(this).is(':checked') && $(this).val() == 'Cheque') {
		        	$("#chequeNo").show();
		        	$("#issueDate").show();
		        	$("#bank").show();
		        	$("#accountHolder").show();
				}else  if ($(this).is(':checked') && $(this).val() == 'Online') {
					$("#chequeNo").show();
		        	$("#issueDate").hide();
		        	$("#bank").show();
		        	$("#accountHolder").show();
				}
		    });
	$('input:radio[name="paymentMode"]').change(
			function(){
		        if ($(this).is(':checked') && $(this).val() == 'Cash') {
		        	$("#chequeNo").hide();	
		        	$("#issueDate").hide();
		        	$("#bank").hide();
		        	$("#accountHolder").hide();
		        }else  if ($(this).is(':checked') && $(this).val() == 'Cheque') {
		        	$("#chequeNo").show();
		        	$("#issueDate").show();
		        	$("#bank").show();
		        	$("#accountHolder").show();
				}else  if ($(this).is(':checked') && $(this).val() == 'Online') {
					$("#chequeNo").show();
		        	$("#issueDate").hide();
		        	$("#bank").show();
		        	$("#accountHolder").show();
				}
		    });
		        
	
	$("#ratePerYard").change(function() {
		var yardSize=$("#plotSize").text();
		var downPayment=$("#downPayment").val();
		var loanAmount = $("#ratePerYard").val()*yardSize-downPayment;
		var numberOfMonths = $("#months").text();
		var rateOfInterest = $("#interestRate").text();
		var monthlyInterestRatio = (rateOfInterest/100)/12;
		var emi;
		var full;
		if(rateOfInterest<=0){
			full=loanAmount;
			emi=full/numberOfMonths;
		}else{
			var top = Math.pow((1+monthlyInterestRatio),numberOfMonths);
			var bottom = top -1;
			var sp = top / bottom;
			emi = ((loanAmount * monthlyInterestRatio) * sp);
			full = numberOfMonths * emi;
			var interest = full - loanAmount;
		}
		emi=Math.ceil(emi);
		full=Math.ceil(full);
		$("#emi").text(emi);
		$("#emi1").val(emi);
		$("#totalAmount").text(full);
		$("#price").val(full);
	});
	
	$("#downPayment").change(function() {
		var yardSize=$("#plotSize").text();
		var downPayment=$("#downPayment").val();
		var loanAmount = $("#ratePerYard").val()*yardSize-downPayment;
		var numberOfMonths = $("#months").text();
		var rateOfInterest = $("#interestRate").text();
		var monthlyInterestRatio = (rateOfInterest/100)/12;
			
		var emi;
		var full;
		if(rateOfInterest<=0){
			full=loanAmount;
			emi=full/numberOfMonths;
		}else{
			var top = Math.pow((1+monthlyInterestRatio),numberOfMonths);
			var bottom = top -1;
			var sp = top / bottom;
			emi = ((loanAmount * monthlyInterestRatio) * sp);
			full = numberOfMonths * emi;
			var interest = full - loanAmount;
		}
		emi=Math.ceil(emi);
		full=Math.ceil(full);
		$("#emi").text(emi);
		$("#emi1").val(emi);
		$("#price").val(full);
		$("#totalAmount").text(full);
		console.log(interest);
	});
	
$("#submit").click(function() {
	validateRequireFields = ["memberId","franchiseeId","projectId","paymentSchemeId","plotId","ratePerYard","nomineeName","nomineeFather","nomineeAddress","nomineeRelation","paymentDate","nomineeDob"];	
		var isValid = validateForm();
		if (!isValid) {
			window.stop(); //should work in all major browsers
			document.execCommand("Stop"); //is necessary to support IE
			return false;
		}
	});
$("#add-payment-button").click(function() {
	validateRequireFields = ["paymentDate","amount"];
	var isValid = validateForm();
	if (!isValid) {
		window.stop(); //should work in all major browsers
		document.execCommand("Stop"); //is necessary to support IE
		return false;
	}
});
	$("#edit-payment-button").click(function() {
		validateRequireFields = ["emiDateString","paymentAmount"];
		var isValid = validateForm();
		if (!isValid) {
			window.stop(); //should work in all major browsers
			document.execCommand("Stop"); //is necessary to support IE
			return false;
		}
	});
	/*if($('input:radio[name="paymentMethod"]').val()=="Cheque"){
		validateRequireFields = ["chequeNumber","chequeDate","bankName","accountHolderName"];
		isValid = validateForm();
		if (!isValid) {
			window.stop(); //should work in all major browsers
			document.execCommand("Stop"); //is necessary to support IE
			return false;
		}
	}
	if($('input:radio[name="paymentMethod"]').val()=="Online"){
		validateRequireFields = ["chequeNumber"];
		isValid = validateForm();
		if (!isValid) {
			window.stop(); //should work in all major browsers
			document.execCommand("Stop"); //is necessary to support IE
			return false;
		}
	}*/

$("#add-penalty-button").click(function() {
	validateRequireFields = ["amount1","description"];
	var isValid = validateForm();
	if (!isValid) {
		window.stop(); //should work in all major browsers
		document.execCommand("Stop"); //is necessary to support IE
		return false;
	}
});

$("#transfer-button").click(function() {
	validateRequireFields1 = ["bookingId"];
	var isValid = validateForm1();
	if (!isValid) {
		window.stop(); //should work in all major browsers
		document.execCommand("Stop"); //is necessary to support IE
		return false;
	}
});
	
$(function(){	
if ( $('input:radio[name="paymentMode"]:checked') == 'Cash') {
	$("#chequeNo").hide();	
	$("#issueDate").hide();
	$("#bank").hide();
	$("#accountHolder").hide();
}else  if ( $('input:radio[name="paymentMode"]:checked').val() == 'Cheque') {
	$("#chequeNo").show();
	$("#issueDate").show();
	$("#bank").show();
	$("#accountHolder").show();
}else  if ( $('input:radio[name="paymentMode"]:checked').val() == 'Online') {
	$("#chequeNo").show();
	$("#issueDate").hide();
	$("#bank").show();
	$("#accountHolder").show();
}	
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

function viewBooking(bookingId){
	document.location = basePath + "/booking/view/" + bookingId;
}

function addPayment(bookingId){
	document.location = basePath + "/booking/add-payment/" + bookingId;
}

function cancelBooking(bookingId){
	BootstrapDialog.show({
		message: 'Are you sure you want to cancel this Booking ?',
		title: 'Alert',
		buttons: [{
			label: 'YES',
			action: function(dialogItself) {
				$.ajax({

					type: "DELETE",
					url: basePath +"/booking/cancel-booking/"+bookingId,

					dataType :'json',
					contentType: 'application/json',
					mimeType: 'application/json',

					success: function( response ) {
						if(response.success){
							$(".errorMessage").html("");
							window.location.reload(false);
							BootstrapDialog.alert("Booking cancelled successfully.");

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

function transferBooking(bookingId,memberId){
	BootstrapDialog.show({
		message: 'Are you sure you want to transfer amount to another Booking ?',
		title: 'Alert',
		buttons: [{
			label: 'YES',
			action: function(dialogItself) {
				$.ajax({

					type: "GET",
					url: basePath +"/booking/transfer-booking/"+bookingId+"/"+memberId,

					dataType :'json',
					contentType: 'application/json',
					mimeType: 'application/json',

					success: function( response ) {
						if(response.success){
							$(".errorMessage").html("");
							document.location = basePath + "/booking/transfer/"+bookingId+"/"+memberId;

						} else if(response.error){
							BootstrapDialog.alert("This member doesnot have any other active booking");
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
};


function loadTable(){
	
	var table = $('#booking-datatable').DataTable();
	
	table.fnDestroy();
	
	citytable();
}