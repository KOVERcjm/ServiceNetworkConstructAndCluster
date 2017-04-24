<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>  
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>网络构建 - Restful服务网络构建与聚类算法实现</title>
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.css">
    <!-- Bootstrap Table CSS -->
    <link rel="stylesheet" href="css/bootstrap-table.min.css">
  </head>
  
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Restful服务网络构建与聚类算法实现</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="navigate.do?page=Index">项目概況</a></li>
            <li><a href="navigate.do?page=DataVisualization">实验数据</a></li>
            <li class="active"><a href="#">网络构建</a></li>
            <li><a href="#">网络聚类</a></li>
            <li><a href="#">结果分析</a></li>
          </ul>
        </div>
      </div>
    </nav>

	<style>
		.panel {
		  padding: 15px;
		}
		.target {
		  position: relative;
		  padding-top: 100px;
		  margin-top: -100px;
		}
	</style>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="col-md-2 sidebar">
          <ul class="nav nav-pills nav-stacked" data-spy="affix">
            <li><a href="#RelationalTable">Mashup - Api关系表</a></li>
            <li><a href="#AdjacencyList">关系网络的邻接表表示</a></li>
            <li><a href="#Matrix">关系网络的矩阵表示</a></li>
          </ul>
        </div>
        
		<!-- jQuery JS -->
	    <script src="js/jquery-3.2.1.min.js"></script>
	    <!-- Bootstrap JS -->
	    <script src="js/bootstrap.js"></script>
	    <!-- Bootstrap Table JS -->
	    <script src="js/bootstrap-table.min.js"></script>
	    <!-- Bootstrap Table Chinese Language Package JS -->
	    <script src="js/bootstrap-table-zh-CN.min.js"></script>
	    <!-- ECharts -->
    	<script src="js/echarts.js"></script>
        
        <div class="col-md-10 panel">
        	<h3 id="RelationalTable" class="target">Mashup - Api关系表</h3>
        	<p></p>
        	<table	data-toggle="table"
        			data-url="getJson.do?table=mashupapi">
			    <thead>
			        <tr>
			            <th data-field="mashupID">Mashup ID</th>
			            <th data-field="apiID">Api ID</th>
			        </tr>
			    </thead>
			</table>
        	<hr>
        	<h3 id="AdjacencyList" class="target">关系网络的邻接表表示</h3>
        	<table	data-toggle="table"
        			data-url="getJson.do?table=mashupRelation"
        			data-sidePagination="client"
        			data-pageSize="10">
			    <thead>
			        <tr>
			            <th data-field="source">Source</th>
			            <th data-field="target">Target</th>
			            <th data-field="weight">Weight</th>
			        </tr>
			    </thead>
			</table>
        	<hr>
        	<h3 id="Matrix" class="target">关系网络的矩阵表示</h3>
        	<p> </p>
        	<div id="main" style="width: 600px;height:400px;"></div>
		    <script type="text/javascript">
		        // 基于准备好的dom，初始化echarts实例
		        var myChart = echarts.init(document.getElementById('main'));
		
		        // 指定图表的配置项和数据
		        var option = {
		            title: {
		                text: 'ECharts 入门示例'
		            },
		            tooltip: {},
		            legend: {
		                data:['销量']
		            },
		            xAxis: {
		                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
		            },
		            yAxis: {},
		            series: [{
		                name: '销量',
		                type: 'bar',
		                data: [5, 20, 36, 10, 10, 20]
		            }]
		        };
		
		        // 使用刚指定的配置项和数据显示图表。
		        myChart.setOption(option);
		    </script>
        </div>
      </div>
    </div>
  </body>
</html>