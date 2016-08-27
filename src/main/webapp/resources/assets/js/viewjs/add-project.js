/**
 * Add lesson
 */

/**
 * 
 */
var editor; 
var oTable;
$(document).ready(function(){
	var today = new Date();
	var projectId;
/*    
	if($("#stateId").val() != ""){
		
		$('#instruType').attr('disabled', true);
		
	}
	*/
	projectId=$('#projectId').val();
	
	console.log("\n\n\t =========projectId===>"+projectId);
	  $('#completionDate')
      .datepicker({
    	  autoclose: true,  
    	  maxDate : today,
          format: 'mm/dd/yyyy'
      })
      .on('changeDate', function(e) {
          // Revalidate the date field
//          $('#projectFrm').validate('revalidateField', 'date');
      });
	
    $('#projectFrm').validate({
    	errorClass: 'help-block', // You can add help-inline instead of help-block if you like validation messages to the right of the inputs
        errorElement: 'div',
        errorPlacement: function(error, e) {
            e.parents('.form-group > div').append(error);
            submitFlag=false;
        },
        highlight: function(e) {
            $(e).closest('.form-group').removeClass('has-success has-error').addClass('has-error');
            $(e).closest('.help-block').remove();
            submitFlag=false;
        },
        success: function(e) {
            // You can remove the .addClass('has-success') part if you don't want the inputs to get green after success!
            e.closest('.form-group').removeClass('has-success has-error').addClass('has-success');
            e.closest('.help-block').remove();
            submitFlag=true;
        },
       rules: {
    	   title: {
                required: true,
                maxlength:50
            },
            bookingPrefix: {
                required: true,
                maxlength:50
            },
            totalPlots: {
                required: true,
                maxlength:4
            },
            projectOverview: {
                required: true,
                maxlength:50
            },
            completionDate:{
            	required: true,
            	 date : true
            	
            }
            
        },
        messages: {
        
        	title: {
                required: 'Please enter Project Name'
            },
            bookingPrefix: {
                required: 'Please enter Booking Prefix'
            },
            totalPlots: {
                required: 'Please enter Total No.of Plots'
            },
            projectOverview: {
                required: 'Please enter Project Overview'
            },
            completionDate: {
            	
                required: 'Please select Completion date',
               	 date : "Please enter valid date"

            }
      
        }
    });
    
   
    
    $( "#btnSave" ).click(function() {
    	if($("#projectFrm").valid()){
    		
    		document.getElementById("projectFrm").submit();
    	}
    });
    
    $( "#btnUpdate" ).click(function() {
    	if($("#editProjectFrm").valid()){
    		
    		document.getElementById("editProjectFrm").submit();
    	}
    });
    
	projectPlotsManagement(projectId);

	
	
/*	 editor = new $.fn.dataTable.Editor( {
	        ajax:basePath
			+ "/ajax/projectplots.json?projectId="+projectId,
	        table: "#projectPlotsDatatable",
	        fields: [ {
	                label: "Plot No",
	                name: "plotName"
	            }, {
	                label: "Plot Size",
	                name: "plotSize"
	            }, {
	                label: "Plot Id:",
	                name: "projectPlotId"
	            }
	        ]
	    } );*/
	 
	 

	    $('#projectPlotsDatatable tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	oTable.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    } );
	 
});



function projectPlotsManagement(projectId) {
	
	console.log("\n\n\t =========projectId1===>"+projectId);

	oTable = $('#projectPlotsDatatable')
			.dataTable(
					{

						//"aaSorting" : [ [ 1, "desc" ] ],
						"bProcessing" : true,
						"bServerSide" : true,
						"bPaginate" : true,
						"iDisplayStart" : 0,
						"iDisplayLength" : 10,
						"bRetrieve" : true,
						"bSort" : true,
						"bFilter" : true,
						"bLengthChange" : false,
						"fnServerData" : fnServerData,
						"sAjaxSource" : basePath
								+ "/ajax/projectplots.json?projectId="+projectId,
						"aoColumnDefs" : [
								{
									"mRender" : function(data, type, row) {
										var active = 'Deactivate';
										if(row['active'] == false){
											active = 'Activate';
										}
//										 console.log(row['active']);
										var actionsLinks = '<div style="">'; 
											actionsLinks += '<a href="javascript:void(0);" '+' onclick="editProjectPlot('+"'" + data+"'" + ');">'
									        	+'Edit'+'&nbsp; <i class="fa fa-edit font-size-17px"></i></a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" '+' onclick="deleteProjectPlot('+"'" + data+"'" + ');">Delete&nbsp; <i class="fa fa-trash font-size-17px"></i></a>';
										
										
										actionsLinks += '</div>';
										
										
										return actionsLinks;
									},

									"aTargets" : [ 3 ]
								}, ],

						"aoColumns" : [

						{
							"sTitle" : "Sr. No",
							"mData" : "srNo",
							"sWidth" : "15%",
							"bSortable" : false,
							"sClass" : "center"
						}, // Sr.No
			/*			{
							"sTitle" : "Project Name",
							"mData" : "title",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
*/						{
							"sTitle" : "Plot Name",
							"mData" : "plotName",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						
						{
							"sTitle" : "Plot Size",
							"mData" : "plotSize",
							"sWidth" : "15%",
							"bSortable" : true,
							"sClass" : "center"
						}, // userName
						
						{
							"sTitle" : "Action",
							"mData" : "projectPlotId",
							"sWidth" : "15%",
							"sClass" : "center",
							"bSortable" : false
						}

						],
						
						"oLanguage": {
					        "sEmptyTable": "No Project plots",
					        "sLoadingRecords": "Please wait - loading...",
					        "sInfo": "Got a total of _TOTAL_ entries to show (_START_ to _END_)"
					    },
                        "fnDrawCallback" : function(oSettings) {
                       	 var iTotalDisplayRecords = oTable.fnSettings().fnRecordsDisplay();

                       	 if (iTotalDisplayRecords == 0) {
                       		 $('#projectPlotsDatatable_info').css("visibility","hidden");
                       		 $('.dataTables_paginate').css("visibility","hidden");


                       	 } else {
                       		 $('#projectPlotsDatatable_info').css("visibility","visible");
                       		 $('.dataTables_paginate').css("visibility","visible");
                       	 }
                        }

					});
}

/*new $.fn.dataTable.Buttons( oTable, [
                                    { extend: "create", editor: editor },
                                    { extend: "edit",   editor: editor },
                                    { extend: "remove", editor: editor }
                                ] );*/

function fnServerData(sSource, aoData, fnCallback) {
	isSessionExtend = true;
	$.ajax({
		"dataType" : 'json',
		"type" : "GET",
		"url" : sSource,
		"contentType" : 'application/json',
		"data" : aoData,
		"success" : fnCallback,
		"timeout" : 8000,
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
