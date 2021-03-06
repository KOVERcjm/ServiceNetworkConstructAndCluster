<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>  
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>项目概况 - Restful服务网络构建与聚类算法实现</title>
    
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
            <li><a href="prepareData.do">数据准备</a></li>
            <li class="active"><a href="#">项目概況</a></li>
            <li><a href="navigate.do?page=DataVisualization">服务数据集管理</a></li>
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
            <li><a href="#Overview">综述</a></li>
            <li><a href="#DataSource">实验数据源</a></li>
          </ul>
        </div>
        <div class="col-md-10 panel">
        	<hr>
        	<hr>
        	<h3 id="Overview" class="target">综述</h3>
        	<hr>
        	<hr>
        	<p>课题来源：国家自然科学基金项目</p>
			<p>
			课题意义与主要内容：目前，Web服务已得到工业界和学术界的广泛关注。
			网络中已存在大量的Restful服务，这些服务间可以相互调用来实现服务的增值，
			从而提高服务的可利用性，降低生产的成本。如何利用现有的Restful服务，
			挖掘其内部的可调用关系已成为当前研究的热点。在以上研究背景下，
			本题目拟利用聚类算法挖掘Restful服务间的相互调用关系，并验证其有效性。
        	</p>
        	<hr>
        	<hr>
        	<h3 id="DataSource" class="target">实验数据源</h3>
        	<hr>
        	<hr>
        	<p>
        	实验所用数据集爬取自API通用资源网站ProgrammableWeb(ProgrammableWeb.com)，
        	涵盖了网站上API、Mashup、Category几乎所有条目，包含Mashup 6254 个，API 13869 个，
        	Mashup与API对应数据条目13105条（有效数据11005条），本课题主要使用了其中Mashup与
        	API对应数据表。
        	</p>
        </div>
      </div>
    </div>

	<!-- jQuery JS -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="js/bootstrap.js"></script>
  </body>
</html>