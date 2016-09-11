var areaChartData;
//var $ = jQuery;

//google.charts.load('current', {'packages':['line']});
google.load('visualization', '1', {'packages': ['line']});

jQuery(document).ready(function() { 

//	google.charts.load("current", {packages:['corechart']});
//google.charts.setOnLoadCallback(drawChart);


//handleGraphData();


jQuery("#year").on('change',function(){
	
	jQuery('#lineChart').remove(); // this is my <canvas> element
	jQuery('#chart').append('<canvas id="lineChart" style="height:250px !important"></canvas>');
	handleGraphData();
	
});


function handleGraphData(){
	/*if (ajaxInProgress === true) {
		return;
	}*/

	ajaxInProgress = true;
	data = null;
	data = new google.visualization.DataTable();
	$.ajax({
				dataType : "json",
				method : "GET",
				url : basePath + "/report/ajax/monthly/bookings.json?year="+jQuery('#year').val(),
				success : function(result) {
					ajaxInProgress = false;
					 var chartData = [[]];
					 var subchartData = [[]];
					console.log("\n\t ===result.musicCounts==>"+result.musicCounts);
					console.log("\n\t ===result.labelsArray==>"+result.labelsArray);

					var arr= result.labelsArray;//["A","B","C"];
					var arr1=result.musicCounts;

					var arr2=[];

					for(var i=0;i<arr.length; i++){
					  var temp=[];
//					  console.log(arr[i]+", "+44)
					  temp[0]=arr[i];
					  temp[1]=arr1[i];
					  arr2.push(temp);
					}


					data.addColumn('string', 'Months');
					data.addColumn('number', 'Bookings');
					    
				    	data.addRows(arr2); 

					google.charts.setOnLoadCallback(drawChart(data));


				},
				error : function(xhr) {
					ajaxInProgress = false;
					console.log("Error..");
				}
			}); // Ajax Ends Here
}

function drawChart(data) {

    var options = {
      chart: {
        title: 'Box Office Earnings in First Two Weeks of Opening',
        subtitle: 'in millions of dollars (USD)'
      },
      width: 900,
      height: 500,
      axes: {
        x: {
          0: {side: 'top'}
        }
      }
    };

    var chart = new google.charts.Line(document.getElementById('line_top_x'));

    chart.draw(data, options);
  }


});
