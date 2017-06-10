<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>  
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>聚类可视化 - Restful服务网络构建与聚类算法实现</title>
    
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
          <a class="navbar-brand" href="navigate.do?page=Index">Restful服务网络构建与聚类算法实现</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="navigate.do?page=Index">项目概況</a></li>
            <li><a href="navigate.do?page=DataVisualization">服务数据集管理</a></li>
            <li><a href="navigate.do?page=NetworkConstruct">服务复杂网络构建</a></li>
            <li><a href="navigate.do?page=Cluster">服务聚类</a></li>
            <li class="active"><a href="#">聚类可视化</a></li>
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
            <li><a href="#1">K=15，权重阈值等于0.35时</a></li>
          </ul>
        </div>
        <div class="col-md-10 panel">
        	<hr>
        	<hr>
        	<h3 id="1" class="target">K=15，权重阈值等于0.35时</h3>
        	<hr>
        	<hr>
        		<p>
        		在谱聚类参数K=15、权重阈值下限取0.35时，模块度Q值达到了相对较大的值，说明这次聚类效果很好。
        		选取其中一个具有360个节点的簇，对其进行可视化如下：
        		</p>
        		<a href="navigate.do?page=clusterResult"><img src="pic/Result.png"  alt="聚类结果可视化"/></a>
        		<p>
        		通过对其中Mashup的随机检验，可以得知，这个簇中大多数Mashup都调用了Flickr API（ID：62666）这个API。
        		可见这个簇的内聚度相对较高。
        		</p>
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