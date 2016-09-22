/**
 * Add lesson
 */

/**
 * 
 */
var editor; 
var oTable;
var projPlotsList;
$(document).ready(function(){
	var today = new Date();
	var projectId;
	var arr=[];
	
	
	projectId=$('#projectId').val();
	
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
                required: true
            },
            bookingPrefix: {
                required: true
            },
            totalPlots: {
                required: true
            },
            excelFile: {
                required: true,
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
            excelFile: {
            	requires: 'Please select plot excel file'
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

	
	
	 
	var form = $('#plotprojectFrm').html();

	
	 
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
							"mData" : "projectId",
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

function editProjectPlot(proejctId){
//	if(isEditAccess === "true"){
		document.location = basePath + "/project/editplot/" + proejctId + ".do";
//	}
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


function addTable() {
    var myTableDiv = document.getElementById("metric_results")
    var table = document.createElement('TABLE')
    var tableBody = document.createElement('TBODY')

    table.border = '1'
    table.appendChild(tableBody);

    var heading = new Array();
    heading[0] = "Project Plot"
    heading[1] = "Plot Name"
    heading[2] = "Plot Size"
//    heading[3] = "Group C"
//    heading[4] = "Total"

    	$.ajax({
			dataType : "json",
			method : "GET",
			url : basePath + "/ajax/projectplot/list.json?projectId="+jQuery('#projectId').val(),
			success : function(result) {
				ajaxInProgress = false;
				
				projPlotsList=result.list;
		/*	
				for(var i=0;i<projPlotsList.length;i++){
					console.log("\n\t "+projPlotsList[i].projectPlotId+"\t plot nam="+projPlotsList[i].plotName);
				}*/
				 var tr = document.createElement('TR');
				    tableBody.appendChild(tr);
				    for (var i = 0; i < heading.length; i++) {
				        var th = document.createElement('TH')
				        th.width = '75';
				        th.appendChild(document.createTextNode(heading[i]));
				        tr.appendChild(th);
				    }
				   
				    //TABLE ROWS
				    for (var i = 0; i < projPlotsList.length; i++) {
				    	
				    	console.log("\n\t "+projPlotsList[i].projectPlotId+"\t plot nam="+projPlotsList[i].plotName);
				        var tr = document.createElement('TR');
//				        for (j = 0; j < projPlotsList[i].length; j++) {
				            var td = document.createElement('TD')
				            var input1=document.createElement('input')
				            input1.setAttribute("type", "text");
				            input1.setAttribute("value", projPlotsList[i].projectPlotId);
//				            input1.value=projPlotsList[i].projectPlotId;
				            td.appendChild(document.createTextNode(input1));
				            tr.appendChild(td)
				            
				             var td = document.createElement('TD')
				            td.appendChild(document.createTextNode(projPlotsList[i].plotName));
				            tr.appendChild(td)
				            
				            
				             var td = document.createElement('TD')
				            td.appendChild(document.createTextNode(projPlotsList[i].plotSize));
				            tr.appendChild(td)
//				        }
				        tableBody.appendChild(tr);
				    }  
				    myTableDiv.appendChild(table)
			},
			error : function(xhr) {
				ajaxInProgress = false;
				console.log("Error..");
			}
		}); // Ajax Ends Here
    console.log("Error.."+projPlotsList);
    	
/*    var stock = new Array()
    stock[0] = new Array("Cars", "88.625", "85.50", "85.81", "987")
    stock[1] = new Array("Veggies", "88.625", "85.50", "85.81", "988")
    stock[2] = new Array("Colors", "88.625", "85.50", "85.81", "989")
    stock[3] = new Array("Numbers", "88.625", "85.50", "85.81", "990")
    stock[4] = new Array("Requests", "88.625", "85.50", "85.81", "991")
*/
    //TABLE COLUMNS
   
}