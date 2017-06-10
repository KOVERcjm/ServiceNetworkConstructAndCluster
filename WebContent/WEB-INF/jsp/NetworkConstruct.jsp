<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>  
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>服务复杂网络构建 - Restful服务网络构建与聚类算法实现</title>
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.css">
    <!-- Bootstrap Table CSS -->
    <link rel="stylesheet" href="css/bootstrap-table.min.css">
    <!-- DataTables CSS -->
	<link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.css">
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
          <a class="navbar-brand" href="navigate.do?page=Index">Restful服务网络构建与聚类算法实现</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="navigate.do?page=Index">项目概況</a></li>
            <li><a href="navigate.do?page=DataVisualization">服务数据集管理</a></li>
            <li class="active"><a href="#">服务复杂网络构建</a></li>
            <li><a href="navigate.do?page=Cluster">服务聚类</a></li>
            <li><a href="navigate.do?page=Result">聚类可视化</a></li>
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
            <li><a href="#RelationTable">Mashup - API对应关系</a></li>
            <li><a href="#AdjacencyList">关系网络的邻接表表示</a></li>
            <li><a href="#DataVisualization">服务复杂网络可视化</a></li>
          </ul>
        </div>
        
        <div class="col-md-10 panel">
        	<hr>
        	<hr>
        	<h3 id="RelationTable" class="target">Mashup - API对应关系</h3>
        	<hr>
        	<hr>
				<table id="relationTable" class="display">
				    <thead>
				        <tr>
				            <th>Mashup ID</th>
				            <th>Mashup Name</th>
				            <th>API ID</th>
				            <th>API Name</th>
				        </tr>
				    </thead>
				    <tbody> </tbody>
				</table>
			<hr>
			<hr>
			<h3>关系网络的邻接表表示（前1000条）</h3>
        	<hr>
        	<hr>
        	<p>由于所生成的邻接表共有2 788 738条记录，故此仅展示前1000条记录。</p>
        	<h3 id="AdjacencyList" class="target"></h3>
				<table id="adjacencyTable" class="display">
				    <thead>
				        <tr>
				            <th>MashupID_A</th>
				            <th>MashupID_B</th>
				            <th>Weight</th>
				        </tr>
				    </thead>
				    <tbody> </tbody>
				</table>
			<hr>
			<hr>
			<h3>关系网络可视化</h3>
        	<hr>
        	<hr>
        	<h3 id="Matrix" class="target"></h3>
        	<p>由于生成的关系网络太庞大，故此仅展示权重值大于1.2的部分。</p>
        	<a href="navigate.do?page=Overview"><img src="pic/Overview.png"  alt="关系网络可视化"/></a>
        </div>
      </div>
    </div>
    
    <!-- jQuery JS -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="js/bootstrap.js"></script>
    <!-- Bootstrap Table JS -->
    <script src="js/bootstrap-table.min.js"></script>
    <!-- Bootstrap Table Chinese Language Package JS -->
    <script src="js/bootstrap-table-zh-CN.min.js"></script>
    <!-- DataTables JS -->
	<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.js"></script>
	<script type="text/javascript">
	$(document).ready(
		function(){
			$('#relationTable').DataTable({
	    		"lengthChange":true,
	    		"ajax": {"url": "json/relationTable.json", "dataSrc": ""},
	  	    	"columns": [
	  	    	    {"data": "Mashup ID"},
	  	    	    {"data": "Mashup Name"},
	  	    		{"data": "API ID"},
	  	    	    {"data": "API Name"}],
	  	    	  "pagingType": "full_numbers"
	  	    });
	    	$('#adjacencyTable').DataTable({
	    		"lengthChange":true,
	    		"ajax": {"url": "json/adjacencyTable.json", "dataSrc": ""},
	  	    	"columns": [
	  	    	    {"data": "MashupID_A"},
	  	    	    {"data": "MashupID_B"},
	  	    	    {"data": "Weight"}],
	  	    	  "pagingType": "full_numbers"
	  	    });
		}
	);
	</script>
  </body>
</html>