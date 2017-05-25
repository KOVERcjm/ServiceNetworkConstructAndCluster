package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Jama.*;
import kmeans.kmeans;
import kmeans.kmeans_data;
import kmeans.kmeans_param;
import mybatis.mapper.RelationMapper;
import mybatis.model.MashupRelation;

@Controller
public class RelationController
{
	@Autowired
	private RelationMapper relationMapper;

	@RequestMapping("/navigate")
	public ModelAndView navigatePage(@RequestParam(value = "page") String page)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(page);
		return modelAndView;
	}

	@RequestMapping(value = "/processData")
	public void processData(HttpServletRequest request) throws IOException
	{
		System.out.println("~~~~START~~~~~");
		// mashup()
		// 去除ApiID为-1的无效数据
		relationMapper.delete();
		System.out.println("delete()");
		List<Integer> mashupIDs = relationMapper.selectAllMashupID();

		for (int i = 0; i < mashupIDs.size(); i++)
		{
			Set<Integer> mashupI = new HashSet<Integer>(relationMapper.selectByMashupID(mashupIDs.get(i)));

			for (int j = 1; j < mashupIDs.size(); j++)
			{
				Set<Integer> mashupJ = new HashSet<Integer>(relationMapper.selectByMashupID(mashupIDs.get(j)));

				Set<Integer> intersection = new HashSet<Integer>();
				intersection.clear();
				intersection.addAll(mashupI);
				intersection.retainAll(mashupJ);
				if (intersection.size() == 0)
					continue;
				List<Integer> intersections = new ArrayList<Integer>(intersection);

				Set<Integer> union = new HashSet<Integer>();
				union.clear();
				union.addAll(mashupI);
				union.addAll(mashupJ);
				List<Integer> unions = new ArrayList<Integer>(union);

				double weight = (double) intersection.size() / union.size();
				relationMapper.insertMashupRelation(new MashupRelation(mashupIDs.get(i), mashupIDs.get(j), RelationController.translate(intersections), RelationController.translate(unions), weight));
			}
		}
		System.out.println("mashup()");
		// mashup() end

		int mashupNumbers = relationMapper.countMashups();
		List<Integer> allMashupIDs = relationMapper.selectAllMashupID();

		double[][] mashupMatrix = new double[mashupNumbers][mashupNumbers];

		for (int i = 0; i < mashupNumbers; i++)
			for (int j = 0; j < mashupNumbers; j++)
				mashupMatrix[i][j] = 0;

		for (int i = 0; i < 5584; i++)
		{
			List<MashupRelation> mashupRelations = relationMapper.selectMashupRelations(i * 1000);

			for (MashupRelation mashupRelation : mashupRelations)
			{
				int mashupID_A = allMashupIDs.indexOf(mashupRelation.getMashupID_A());
				int mashupID_B = allMashupIDs.indexOf(mashupRelation.getMashupID_B());
				double weight = mashupRelation.getWeight();
				
				mashupMatrix[mashupID_A][mashupID_B] = weight;
				mashupMatrix[mashupID_B][mashupID_A] = weight;
			}
		}
		System.out.println("Mashup Matrix Created.");

		FileWriter fileWriter = new FileWriter(request.getServletContext().getRealPath("/json/MashupMatrix.json"), true);
		for (int i = 0; i < mashupNumbers; i++)
		{
			for (int j = 0; j < mashupNumbers; j++)
				fileWriter.write(mashupMatrix[i][j] + ",");
			fileWriter.write("\r\n");
		}
		fileWriter.close();

		int sum = 0;
		for (int i = 0; i < mashupNumbers; i++)
			for (int j = 0; j < mashupNumbers; j++)
				sum += (mashupMatrix[i][j] != 0) ? 1 : 0;
		System.out.println("all " + sum);
		for (int i = 0; i < mashupNumbers; i++)
			for (int j = i + 1; j < mashupNumbers; j++)
				sum += (mashupMatrix[i][j] != 0) ? 1 : 0;
		System.out.println("edges " + sum);

		System.out.println("Mashup Matrix Counted.             processData()");

		// culster()
		Matrix W = new Matrix(mashupMatrix);

		double[][] diagonalMatrix = new double[mashupNumbers][mashupNumbers];
		for (int i = 0; i < mashupNumbers; i++)
		{
			double sum1 = 0;
			for (int j = 0; j < mashupNumbers; j++)
			{
				diagonalMatrix[i][j] = 0;
				sum1 += mashupMatrix[i][j];
			}
			diagonalMatrix[i][i] = sum1;
		}
		Matrix D = new Matrix(diagonalMatrix);

		Matrix L = D.minus(W);

		Matrix V = new EigenvalueDecomposition(L).getV();
		System.out.println("W D L V");

		int k = 2; // cateID

		Matrix eigenvalue = new EigenvalueDecomposition(L).getD();

		double[][] v = V.getArrayCopy();
		FileWriter fileWriterV = new FileWriter(request.getServletContext().getRealPath("/json/VMatrix.json"), true);
		for (int i = 0; i < v[0].length; i++)
		{
			for (int j = 0; j < v.length / v[0].length; j++)
				fileWriterV.write(v[i][j] + ",");
			fileWriterV.write("\r\n");
		}
		double[][] d = eigenvalue.getArrayCopy();
		fileWriterV.close();
		FileWriter fileWriterD = new FileWriter(request.getServletContext().getRealPath("/json/eigenvalueMatrix.json"), true);
		for (int i = 0; i < d[0].length; i++)
		{
			for (int j = 0; j < d.length / d[0].length; j++)
				fileWriterD.write(d[i][j] + ",");
			fileWriterD.write("\r\n");
		}
		fileWriterD.close();
		System.out.println("V:" + v[0].length + "*" + (v.length / v[0].length));
		System.out.println("D:" + d[0].length + "*" + (d.length / d[0].length));

		kmeans_data data = new kmeans_data(V.getArray(), mashupNumbers, k); // 初始化数据结构
		kmeans_param param = new kmeans_param(); // 初始化参数结构
		param.initCenterMehtod = kmeans_param.CENTER_RANDOM; // 设置聚类中心点的初始化模式为随机模式
		// 做kmeans计算，分两类
		kmeans.doKmeans(k, data, param);
		// 查看每个点的所属聚类标号
		System.out.print("The labels of points is: ");
		for (int lable : data.labels)
			System.out.print(lable + " ");

		System.out.print("Cluster() end ");
		// cluster() end
		
		// result()
		double q = 0;
		for (int i = 0; i < 399; i++)
		{
			double e = 0;
			List<Integer> edgeInsideI = new ArrayList<Integer>();
			for (int j = 0; j < data.labels.length; j++)
				if (i == data.labels[j])
					edgeInsideI.add(j);
			for (int j = 0; j < edgeInsideI.size() - 1; j++)
				for (int l = j + 1; l < edgeInsideI.size(); l++)
					e += (mashupMatrix[j][l] != 0) ? 1 : 0; //
			e /= sum;

			double a = 0;
			for (int j = 0; j < mashupNumbers; j++)
				if (0 != mashupMatrix[i][j] && edgeInsideI.contains(j))
					a++;
			a /= sum;

			q += e - a * a;
		}

		System.out.println("Q: " + q);
		System.out.println("Result() end");
		// result() end
		
		System.out.println("!!!!!FINISHED!!!!!");
	}

	@RequestMapping("/mashup")
	public void mashup()
	{
		// 去除ApiID为-1的无效数据
		relationMapper.delete();

		List<Integer> mashupIDs = relationMapper.selectAllMashupID();

		for (int i = 0; i < mashupIDs.size(); i++)
		{
			Set<Integer> mashupI = new HashSet<Integer>(relationMapper.selectByMashupID(mashupIDs.get(i)));

			for (int j = 1; j < mashupIDs.size(); j++)
			{
				Set<Integer> mashupJ = new HashSet<Integer>(relationMapper.selectByMashupID(mashupIDs.get(j)));

				Set<Integer> intersection = new HashSet<Integer>();
				intersection.clear();
				intersection.addAll(mashupI);
				intersection.retainAll(mashupJ);
				if (intersection.size() == 0)
					continue;
				List<Integer> intersections = new ArrayList<Integer>(intersection);

				Set<Integer> union = new HashSet<Integer>();
				union.clear();
				union.addAll(mashupI);
				union.addAll(mashupJ);
				List<Integer> unions = new ArrayList<Integer>(union);

				double weight = (double) intersection.size() / union.size();
				relationMapper.insertMashupRelation(new MashupRelation(mashupIDs.get(i), mashupIDs.get(j), RelationController.translate(intersections), RelationController.translate(unions), weight));
			}
		}
	}

	@RequestMapping("/hello")
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("msg", "complete");
		modelAndView.setViewName("Hello");
		return modelAndView;
	}

	@RequestMapping("/cluster")
	public void mashupCluster(HttpServletRequest request) throws IOException
	{
		int mashupNumbers = relationMapper.countMashups();
		double[][] mashupMatrix = new double[mashupNumbers][mashupNumbers];

		BufferedReader bufferedReader = new BufferedReader(new FileReader(request.getServletContext().getRealPath("/json/MashupMatrix.json")));
		for (int i = 0; i < mashupNumbers; i++)
		{
			String[] list = bufferedReader.readLine().split(",");
			for (int j = 0; j < mashupNumbers; j++)
				mashupMatrix[i][j] = Double.valueOf(list[j]);
		}
		bufferedReader.close();
		Matrix W = new Matrix(mashupMatrix);

		double[][] diagonalMatrix = new double[mashupNumbers][mashupNumbers];
		for (int i = 0; i < mashupNumbers; i++)
		{
			double sum = 0;
			for (int j = 0; j < mashupNumbers; j++)
			{
				diagonalMatrix[i][j] = 0;
				sum += mashupMatrix[i][j];
			}
			diagonalMatrix[i][i] = sum;
		}
		Matrix D = new Matrix(diagonalMatrix);

		Matrix L = D.minus(W);

		Matrix V = new EigenvalueDecomposition(L).getV();
		System.out.println("W D L V");

		int k = 200; // cateID

		// PrintWriter printWriter = new PrintWriter(new
		// FileWriter(request.getServletContext().getRealPath("/json/VMatrix.json"),
		// true));
		// V.print(printWriter, 7, 2);
		Matrix eigenvalue = new EigenvalueDecomposition(L).getD();
		// printWriter = new PrintWriter(new
		// FileWriter(request.getServletContext().getRealPath("/json/eigenvalueMatrix.json"),
		// true));
		// eigenvalue.print(printWriter, 7, 2);

		double[][] v = V.getArrayCopy();
		FileWriter fileWriterV = new FileWriter(request.getServletContext().getRealPath("/json/VMatrix.json"), true);
		for (int i = 0; i < v[0].length; i++)
		{
			for (int j = 0; j < v.length / v[0].length; j++)
				fileWriterV.write(v[i][j] + ",");
			fileWriterV.write("\r\n");
		}
		double[][] d = eigenvalue.getArrayCopy();
		fileWriterV.close();
		FileWriter fileWriterD = new FileWriter(request.getServletContext().getRealPath("/json/eigenvalueMatrix.json"), true);
		for (int i = 0; i < d[0].length; i++)
		{
			for (int j = 0; j < d.length / d[0].length; j++)
				fileWriterD.write(d[i][j] + ",");
			fileWriterD.write("\r\n");
		}
		fileWriterD.close();
		System.out.println("V:" + v[0].length + "*" + (v.length / v[0].length));
		System.out.println("D:" + d[0].length + "*" + (d.length / d[0].length));

		kmeans_data data = new kmeans_data(V.getArray(), mashupNumbers, k); // 初始化数据结构
		kmeans_param param = new kmeans_param(); // 初始化参数结构
		param.initCenterMehtod = kmeans_param.CENTER_RANDOM; // 设置聚类中心点的初始化模式为随机模式
		// 做kmeans计算，分两类
		kmeans.doKmeans(k, data, param);
		// 查看每个点的所属聚类标号
		System.out.print("The labels of points is: ");
		for (int lable : data.labels)
			System.out.print(lable + "  ");
	}

	@RequestMapping("/result")
	public void result(HttpServletRequest request) throws IOException
	{
		BufferedReader bufferedReader1 = new BufferedReader(new FileReader(request.getServletContext().getRealPath("/json/Cluster.json")));
		String[] list1 = bufferedReader1.readLine().split(" ");
		int[] mashupCluster = new int[list1.length];
		for (int i = 0; i < list1.length; i++)
			mashupCluster[i] = Integer.valueOf(list1[i]);
		bufferedReader1.close();

		int mashupNumbers = relationMapper.countMashups();
		double[][] mashupMatrix = new double[mashupNumbers][mashupNumbers];
		BufferedReader bufferedReader2 = new BufferedReader(new FileReader(request.getServletContext().getRealPath("/json/MashupMatrix.json")));
		for (int i = 0; i < mashupNumbers; i++)
		{
			String[] list2 = bufferedReader2.readLine().split(",");
			for (int j = 0; j < mashupNumbers; j++)
				mashupMatrix[i][j] = Double.valueOf(list2[j]);
		}
		bufferedReader2.close();

		int x = 0;
		for (int j = 0; j < mashupNumbers; j++)
			for (int k = 0; k < mashupNumbers; k++)
				x += (mashupMatrix[j][k] != 0) ? 1 : 0;
		System.out.println(x);
		double m = 5583193.0; // mashupRelation 5883197

		double q = 0;
		for (int i = 0; i < 399; i++)
		{
			double e = 0;
			List<Integer> edgeInsideI = new ArrayList<Integer>();
			for (int j = 0; j < mashupCluster.length; j++)
				if (i == mashupCluster[j])
					edgeInsideI.add(j);
			for (int j = 0; j < edgeInsideI.size() - 1; j++)
				for (int k = j + 1; k < edgeInsideI.size(); k++)
					e += (mashupMatrix[j][k] != 0) ? 1 : 0; //
			e /= m;

			double a = 0;
			for (int j = 0; j < mashupNumbers; j++)
				if (0 != mashupMatrix[i][j] && edgeInsideI.contains(j))
					a++;
			a /= m;

			q += e - a * a;
		}

		System.out.println(q);
	}

	private static double[] translate(String string, int n)
	{
		String[] strings = string.split(",");

		double[] list = new double[n];
		for (int i = 0; i < n; i++)
			list[i] = Integer.parseInt(strings[i]);
		return list;
	}

	private static String translate(List<Integer> list)
	{
		String string = "";
		for (int element : list)
			string += element + ",";
		return string.substring(0, string.length() - 1);
	}

}
