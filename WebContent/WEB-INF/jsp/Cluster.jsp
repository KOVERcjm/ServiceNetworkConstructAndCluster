<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>  
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>服务聚类 - Restful服务网络构建与聚类算法实现</title>
    
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
            <li><a href="navigate.do?page=NetworkConstruct">服务复杂网络构建</a></li>
            <li class="active"><a href="#">服务聚类</a></li>
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
            <li><a href="#Cluster">谱聚类算法简介</a></li>
            <li><a href="#ClusterTable">聚类结果展示</a></li>
            <li><a href="#Modularity">分析指标</a></li>
            <li><a href="#Analyze">聚类结果分析</a></li>
          </ul>
        </div>
        <div class="col-md-10 panel">
        	<hr>
        	<hr>
        	<h3 id="Cluster" class="target">谱聚类算法简介</h3>
        	<hr>
        	<hr>
        	<p>
        	本次实验采用谱聚类算法。谱聚类算法建立在谱图理论基础上，
        	与传统的聚类算法相比，它具有能在任意形状的样本空间上聚类且收敛于全局最优解的优点。
        	</p>
			<p>
			该算法首先根据给定的样本数据集定义一个描述成对数据点相似度的亲合矩阵，
			并且计算矩阵的特征值和特征向量，然后对合适的向量进行聚类，得到最终的谱聚类结果。
        	</p>
        	<p>
        	1. 根据数据构造一个Graph，Graph的每一个节点对应一个数据点，将相似的点连接起来，
        	并且边的权重用于表示数据之间的相似度。把这个Graph用邻接矩阵的形式表示出来，记为 W。
        	</p>
        	<p>
			2. 把 W 的每一列元素加起来得到N个数，把它们放在对角线上（其他都是零），组成一个N^N的矩阵，记为D。并令L = D - W。
			</p>
        	<p>
			3. 求出L的前k个特征值（按照特征值的大小从小到大的顺序）以及对应的特征向量。
			</p>
        	<p>
			4. 把这k个特征（列）向量排列在一起组成一个N^k的矩阵，将其中每一行看作k维空间中的一个向量，
			并使用K-means算法进行聚类。聚类的结果中每一行所属的类别就是原来Graph中的节点亦即最初的N个数据点分别所属的类别。
        	</p>
        	<hr>
        	<hr>
        	<h3 id="ClusterTable" class="target">聚类结果展示</h3>
        	<hr>
        	<hr>
        		<table id="clusterTable" class="display">
				    <thead>
				        <tr>
				            <th>谱聚类参数K</th>
				            <th>复杂网络边的权重阈值下限</th>
				            <th>模块度Q</th>
				        </tr>
				    </thead>
				    <tbody> </tbody>
				</table>
			<hr>
        	<hr>
			<h3 id="Modularity" class="target">分析指标</h3>
			<hr>
        	<hr>
	        	<p>
	        	社区全局模块度，Modularity，是用来衡量一个社区的划分是不是相对比较好的结果。
				一个相对好的结果在社区内部的节点相似度较高，而在社区外部节点的相似度较低。
	        	</p>
	        	<p>
	        	本次聚类分析指标，模块度公式推导如下：<img src="pic/Q.png"  alt="模块度公式"/>。其中：
	        	</p>
	        	<p>
	        	<img src="pic/e.png"  alt="参数e"/>代表社区i和社区j内部边数之和与总边数的比例
	        	</p>
	        	<p>
	        	<img src="pic/a.png"  alt="参数a"/>代表社区i内部的点所关联的所有边数与总边数的比例
	        	</p>
        	<hr>
        	<hr>
			<h3 id="Analyze" class="target">聚类结果分析</h3>
				<p>
				我们可以看到，K值对模块度Q值的影响以K=15为分界，K小于15时，K与Q成正比；K大于15时，K与Q成反比。
				同理，权重阈值对Q值的影响以Q=0.35为分界。Q=0.35，意味着在网络聚类的时候，系统将网络中权重值小于等于
				0.35的边视作没有该边的存在，即，筛选出了以所有权重值大于0.35的边所构建的网络，依此来进行聚类。同样的，
				权重阈值小于0.35时，与Q成正比；反之，与Q成反比。
				</p>
				<p>
				需要说明的是，原则上模块度不会出现负值。然而在谱聚类参数K=25、权重阈值为0的情况下，
				Q的值达到了负数，为-0.098 336 888 066 466 77。可以说明此时的聚类效果极差，
				这是一个失败的聚类。而模块度Q的最大值在K=15、权重阈值等于0.35时取到，为0.855 338 096 635 150 3。
				这是个很不错的聚类，每个簇之间的耦合度相当低，同时每个簇内部的内聚度很高，聚类效果很好。
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
    <!-- DataTables JS -->
	<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.js"></script>
	<script type="text/javascript">
	$(document).ready(
		function(){
			$('#clusterTable').DataTable({
	    		"lengthChange":true,
	  	    	"ajax": {"url": "json/ClusterResult.json", "dataSrc": ""},
	  	    	"columns": [
	  	    	    {"data": "k"},
	  	    	    {"data": "m"},
	  	    	    {"data": "q"}],
	  	    	  "pagingType": "full_numbers"
	  	    });
		}
	);
	</script>
  </body>
</html>