<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>  
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>服务数据集 - Restful服务网络构建与聚类算法实现</title>
    
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
            <li class="active"><a href="#">服务数据集</a></li>
            <li><a href="navigate.do?page=NetworkConstruct">服务复杂网络构建</a></li>
            <li><a href="#">服务聚类</a></li>
            <li><a href="#">实验结果</a></li>
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
            <li><a href="#">Mashup服务</a></li>
            <li><a href="#">Api服务</a></li>
            <li><a href="#">数据可视化</a></li>
          </ul>
        </div>
        <div class="col-md-10 panel">
        	<h3 id="Overview" class="target">Mashup服务</h3>
        	<p></p>
			<p>
			
        	</p>
        	<hr>
        	<h3 id="DataSource" class="target">Api服务</h3>
        	<p>
        	
        	</p>
        	<hr>
        	<h3 id="UsedTechnology" class="target">数据可视化</h3>
        	<p>主要使用</p>
        	<p>聚类算法：	</p>
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
  </body>
</html>