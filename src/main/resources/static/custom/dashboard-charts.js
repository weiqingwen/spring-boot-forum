function loadPieChart() {
	$.ajax({
		url: '/dashboard/numOfPostsByCategories',
		dataType: 'json',
		}).done(function (results) {
			var labels = [], colors = [], data = [];
			jQuery.each(results, function(key, val) {
				labels.push(key);
				data.push(val);
				var color = randomColor({luminosity: 'light'});
				colors.push(color);
			});
			
			var config = {
				type: 'pie',
				data: {
				    datasets:
					    	[{
					        data: data,
					        backgroundColor: colors,
					        label: 'Dataset 1'
				        	}],
				    labels: labels
				    },
				    
				    options: {
				        responsive: true
				    }
			};
			
			var pieChartContext = document.getElementById("pieChart").getContext("2d");
			var myPieChart = new Chart(pieChartContext, config);
		}
	);
}

function loadBarChart() {
	$.ajax({
		url: '/dashboard/numOfPostsByMonths',
		dataType: 'json',
		}).done(function (results) {
			// populate colors list
			var colors = randomColor({luminosity: 'light', count: 12});
			// populate labels and date
			var labels = [], data = [];
			jQuery.each(results, function(key, val) {
				if (key == "monthsList") {
					labels = val;
				}
				
				if (key == "postsNumList") {
					data = val;
				}
			});
			
			var config = {
				type: 'bar',
				data: {
				      labels: labels,
				      datasets: [
				        {
				          backgroundColor: colors,
				          data: data
				        }
				      ]
				    },
				    
				    options: {
				    		legend: { display: false },
				    		title: {
				    			display: false,
				    			text: '过去十二个月的帖子数量'
				    			}
				    }
			};
			
			var barChartContext = document.getElementById("barChart").getContext("2d");
			var myBarChart = new Chart(barChartContext, config);
		}
	);
}

loadPieChart();
loadBarChart();