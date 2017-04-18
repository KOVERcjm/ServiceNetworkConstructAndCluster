<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test D3</title>
</head>

<style>

.labeltext {
	font-size: 16px ;
	font-family: SimSun;
	fill:#000000;
}

.nodetext {
	font-size: 12px ;
	font-family: SimSun;
	fill:#000000;
}

.linetext {
	font-size: 12px ;
	font-family: SimSun;
	fill:#0000FF;
	fill-opacity:0.0;
}

</style>

<body>
	<p>${msg}</p>
	
	<p>${nodes}</p>
	
	<script src="http://d3js.org/d3.v3.min.js" charset="UTF-8"> </script>
	<script>
		var width = 600;
		var height = 600;
		var nodes = '$nodesJson';
		var edges = '$edgesJson';
		
		/*var nodes = [ { name: "桂林"    }, { name: "广州" },
					  { name: "厦门"    }, { name: "杭州"   },
					  { name: "上海"   }, { name: "青岛"    },
					  { name: "天津"    } ];
					 
		var edges = [  { source : 0  , target: 1 } , { source : 0  , target: 2 } ,
					   { source : 0  , target: 3 } , { source : 1  , target: 4 } ,
					   { source : 1  , target: 5 } , { source : 1  , target: 6 }  ];	
		*/
		var svg = d3.select("body").append("svg")
								.attr("width",width)
								.attr("height",height);
		
		var force = d3.layout.force()
						.nodes(nodes)
						.links(edges)
						.size([width,height])
						.linkDistance(10)
						.charge(-1500)
						.start();

		var edges_line = svg.selectAll("line")
							.data(edges)
							.enter()
							.append("line")
							.style("stroke","#ccc")
							.style("stroke-width",1);
								
		var edges_text = svg.selectAll(".linetext")
							.data(edges)
							.enter()
							.append("text")
							.attr("class","linetext")
							.text(function(d){
								return d.weight;
							});
			
			/*var nodes_img = svg.selectAll("image")
								.data(root.nodes)
								.enter()
								.append("image")
								.attr("width",img_w)
								.attr("height",img_h)
								.on("mouseover",function(d,i){
									//显示连接线上的文字
									edges_text.style("fill-opacity",function(edge){
										if( edge.source === d || edge.target === d ){
											return 1.0;
										}
									});
								})
								.on("mouseout",function(d,i){
									//隐去连接线上的文字
									edges_text.style("fill-opacity",function(edge){
										if( edge.source === d || edge.target === d ){
											return 0.0;
										}
									});
								})
								.on("dblclick",function(d,i){
									d.fixed = false;
								})
								.call(drag);*/
			
			var svg_nodes = svg.selectAll("circle")
							.data(nodes)
							.enter()
							.append("circle")
							.attr("r",20)
							.style("fill","#ccc")
							.call(force.drag);	//使得节点能够拖动

		//添加描述节点的文字
		var svg_texts = svg.selectAll("text")
							.data(nodes)
							.enter()
							.append("text")
							.style("fill", "black")
							.attr("dx", 20)
							.attr("dy", 8)
							.text(function(d){
								return d.name;
							});
			
			var text_dx = -20;
			var text_dy = 20;
			
	/*		var nodes_text = svg.selectAll(".nodetext")
								.data(root.nodes)
								.enter()
								.append("text")
								.attr("class","nodetext")
								.attr("dx",text_dx)
								.attr("dy",text_dy)
								.text(function(d){
									return d.name;
								});*/
			
								
			force.on("tick", function(){
				
				//限制结点的边界
				/*nodes.forEach(function(d,i){
					d.x = d.x - img_w/2 < 0     ? img_w/2 : d.x ;
					d.x = d.x + img_w/2 > width ? width - img_w/2 : d.x ;
					d.y = d.y - img_h/2 < 0      ? img_h/2 : d.y ;
					d.y = d.y + img_h/2 + text_dy > height ? height - img_h/2 - text_dy : d.y ;
				});*/
			
				//更新连接线的位置
				 edges_line.attr("x1",function(d){ return d.source.x; });
				 edges_line.attr("y1",function(d){ return d.source.y; });
				 edges_line.attr("x2",function(d){ return d.target.x; });
				 edges_line.attr("y2",function(d){ return d.target.y; });
				 
				 //更新连接线上文字的位置
				 edges_text.attr("x",function(d){ return (d.source.x + d.target.x) / 2 ; });
				 edges_text.attr("y",function(d){ return (d.source.y + d.target.y) / 2 ; });
				 
				 
				 //更新结点文字
				 //nodes_text.attr("x",function(d){ return d.x; });
				 //nodes_text.attr("y",function(d){ return d.y; });
			});
		//});
</script>
</body>

</html>