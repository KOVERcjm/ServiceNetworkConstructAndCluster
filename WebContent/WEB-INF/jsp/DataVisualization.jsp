<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>  
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>服务数据集管理 - Restful服务网络构建与聚类算法实现</title>
    
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
            <li class="active"><a href="#">服务数据集管理</a></li>
            <li><a href="navigate.do?page=NetworkConstruct">服务复杂网络构建</a></li>
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
            <li><a href="#Mashup">Mashup服务</a></li>
            <li><a href="#API">API服务</a></li>
            <li><a href="#DataVisualization">数据可视化</a></li>
          </ul>
        </div>
        <div class="col-md-10 panel">
        	<hr>
        	<hr>
        	<h3 id="Mashup" class="target">Mashup服务</h3>
        	<hr>
        	<hr>
				<table id="mashupTable" class="display">
				    <thead>
				        <tr>
				            <th>ID</th>
				            <th>Name</th>
				            <th>Description</th>
				        </tr>
				    </thead>
				    <tbody> </tbody>
				</table>
			<hr>
			<hr>
        	<h3 id="API" class="target">API服务</h3>
        	<hr>
        	<hr>
        		<table id="apiTable" class="display">
				    <thead>
				        <tr>
				            <th>ID</th>
				            <th>Name</th>
				            <th>Description</th>
				        </tr>
				    </thead>
				    <tbody> </tbody>
				</table>
        	<hr>
        	<hr>
        	<h3>数据可视化</h3>
        	<hr>
        	<hr>
        	<h3 id="DataVisualization" class="target"></h3>
        	<a href="navigate.do?page=Mashup"><img src="pic/Mashups.png"  alt="Mashup服务可视化"/></a>
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
			$('#mashupTable').DataTable({
	    		"lengthChange":true,
	  	    	"ajax": {"url": "json/mashupTable.json", "dataSrc": ""},
	  	    	"columns": [
	  	    	    {"data": "ID"},
	  	    	    {"data": "Name"},
	  	    	    {"data": "Description"}],
	  	    	  "pagingType": "full_numbers"
	  	    });
	    	$('#apiTable').DataTable({
	    		"lengthChange":true,
	    		"ajax": {"url": "json/apiTable.json", "dataSrc": ""},
	  	    	"columns": [
	  	    	    {"data": "ID"},
	  	    	    {"data": "Name"},
	  	    	    {"data": "Description"}],
	  	    	  "pagingType": "full_numbers"
	  	    });
		}
	);
	</script>
  </body>
</html>