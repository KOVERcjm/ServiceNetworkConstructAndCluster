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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import kmeans.kmeans;
import kmeans.kmeans_data;
import kmeans.kmeans_param;
import mybatis.mapper.RelationMapper;
import mybatis.model.Description;
import mybatis.model.MashupRelation;
import mybatis.model.RelationDescription;

@Controller
public class RelationController
{
	/**
	 * Mybatis����־ò�Mapper
	 */
	@Autowired
	private RelationMapper relationMapper;
	
	/**
	 * ��ת��ָ��pageҳ��
	 * @param page
	 * @return (Spring MVC) ModelAndView
	 * @throws IOException 
	 */
	@RequestMapping("/navigate")
	public ModelAndView navigatePage(@RequestParam(value = "page")String page)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(page);
		return modelAndView;
	}
	
	/**
	 * ����׼��
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/prepareData")
	public void prepareData(HttpServletRequest request) throws IOException
	{
		/* Mashup������ */
		JSONArray jsonArray = new JSONArray();
		List<Description> mashups = relationMapper.getMashup();
		for (Description description : mashups)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("ID", description.getId());
			jsonObject.put("Name", description.getName());
			jsonObject.put("Description", description.getDescription());
			jsonArray.put(jsonObject);
		}
		FileWriter fileWriter = new FileWriter(request.getServletContext().getRealPath("/json/mashupTable.json"));
		fileWriter.write(jsonArray.toString());
		fileWriter.close();

		/* API������ */
		jsonArray = new JSONArray();
		List<Description> apis = relationMapper.getAPI();
		for (Description description : apis)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("ID", description.getId());
			jsonObject.put("Name", description.getName());
			jsonObject.put("Description", description.getDescription());
			jsonArray.put(jsonObject);
		}
		fileWriter = new FileWriter(request.getServletContext().getRealPath("/json/apiTable.json"));
		fileWriter.write(jsonArray.toString());
		fileWriter.close();

		/* Mashup - API��ϵ�� */
		jsonArray = new JSONArray();
		List<RelationDescription> relationDescriptions = relationMapper.getRelationDescription();
		for (RelationDescription relationDescription : relationDescriptions)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Mashup ID", relationDescription.getMashupID());
			jsonObject.put("Mashup Name", relationDescription.getMashupName());
			jsonObject.put("API ID", relationDescription.getApiID());
			jsonObject.put("API Name", relationDescription.getApiName());
			jsonArray.put(jsonObject);
		}
		fileWriter = new FileWriter(request.getServletContext().getRealPath("/json/relationTable.json"));
		fileWriter.write(jsonArray.toString());
		fileWriter.close();

		/* ���������ڽӱ� */
		jsonArray = new JSONArray();
		List<MashupRelation> mashupRelations = relationMapper.selectMashupRelations(0);
		for (MashupRelation mashupRelation : mashupRelations)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("MashupID_A", mashupRelation.getMashupID_A());
			jsonObject.put("MashupID_B", mashupRelation.getMashupID_B());
			jsonObject.put("Weight", mashupRelation.getWeight());
			jsonArray.put(jsonObject);
		}
		fileWriter = new FileWriter(request.getServletContext().getRealPath("/json/adjacencyTable.json"));
		fileWriter.write(jsonArray.toString());
		fileWriter.close();
	}
	
	/**
	 * ���ɷ���������
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping("/network")
	public void createServiceNetwork(HttpServletRequest request) throws IOException
	{
		/* ԭʼ���ݼ�����ȥ��ApiIDΪ-1����Ч���� */
		relationMapper.delete();
		
		/* ���ɷ����������ڽӱ���������Mashup������API�Ľ���ռ����֮�ȼ���Ȩ�� */
		List<Integer> mashupIDs = relationMapper.selectAllMashupID();
		mashupIDs.sort(null);

		for (int i = 0; i < mashupIDs.size(); i++)
		{
			Set<Integer> mashupI = new HashSet<Integer>(relationMapper.selectByMashupID(mashupIDs.get(i)));
			
			for (int j = i + 1; j < mashupIDs.size(); j++)
			{
				Set<Integer> mashupJ = new HashSet<Integer>(relationMapper.selectByMashupID(mashupIDs.get(j)));
				
				// ��������Mashup��ͬ���õ�API�Ľ���
				Set<Integer> intersection = new HashSet<Integer>();
				intersection.clear();
				intersection.addAll(mashupI);
				intersection.retainAll(mashupJ);
				if (intersection.size() == 0)
					continue;
				List<Integer> intersections = new ArrayList<Integer>(intersection);
				
				// ��������Mashup���е��õ�API�Ĳ���
				Set<Integer> union = new HashSet<Integer>();
				union.clear();
				union.addAll(mashupI);
				union.addAll(mashupJ);
				List<Integer> unions = new ArrayList<Integer>(union);
				
				// ���ݽ���ռ�������أ�����������Ȩ��ֵ
				double weight = (double) intersection.size() / union.size();
				relationMapper.insertMashupRelation(new MashupRelation(	mashupIDs.get(i),
																		mashupIDs.get(j),
																		RelationController.translate(intersections),
																		RelationController.translate(unions),
																		(1 > weight) ? weight : (unions.size() / 10.0 + 1)
																		));
			}
		}
		
		/* ���ɷ����������ڽӾ���д��json�ļ� */
		// �ڽӾ����ʼ��
		int mashupNumbers = relationMapper.countMashups();
		double[][] mashupMatrix = new double[mashupNumbers][mashupNumbers];
		for (int i = 0; i < mashupNumbers; i++)
			for (int j = 0; j < mashupNumbers; j++)
				mashupMatrix[i][j] = 0;
		
		// �����ڽӱ����ڽӾ���
		for (int i = 0; i <= relationMapper.countMashupRelations() / 1000; i++)
		{
			List<MashupRelation> mashupRelations = relationMapper.selectMashupRelations(i);
			for (MashupRelation mashupRelation : mashupRelations)
			{
				mashupMatrix[mashupIDs.indexOf(mashupRelation.getMashupID_A())][mashupIDs.indexOf(mashupRelation.getMashupID_B())] = mashupRelation.getWeight();
				mashupMatrix[mashupIDs.indexOf(mashupRelation.getMashupID_B())][mashupIDs.indexOf(mashupRelation.getMashupID_A())] = mashupRelation.getWeight();
			}
		}
		
		// ���ڽӾ���д��json�ļ�
		FileWriter fileWriter = new FileWriter(request.getServletContext().getRealPath("/json/MashupMatrix.json"), true);
		for (int i = 0; i < mashupNumbers; i++)
		{
			for (int j = 0; j < mashupNumbers; j++)
				fileWriter.write(mashupMatrix[i][j] + ",");
			fileWriter.write("\r\n");
		}
		fileWriter.close();
	}
	
	/**
	 * �������������
	 * ���ݴ���������k�������׾����㷨�������Ϊk���أ�������ó�����ȫ��ģ���
	 * @param request
	 * @param k
	 * @throws IOException
	 */
	@RequestMapping(value = "/cluster")
	public void serviceNetworkCluster(HttpServletRequest request, @RequestParam(value = "k")int k, @RequestParam(value = "m")double m) throws IOException
	{
		List<Integer> mashupIDs = relationMapper.selectAllMashupID();
		mashupIDs.sort(null);
		
		// ������������json�ļ���
		FileWriter fileWriterC = new FileWriter(request.getServletContext().getRealPath("/json/Cluster.json"));
		
		// ��json�ļ���������������ڽӾ���
		int mashupNumbers = relationMapper.countMashups();
		double[][] mashupMatrix = new double[mashupNumbers][mashupNumbers];
		BufferedReader bufferedReaderM = new BufferedReader(new FileReader(request.getServletContext().getRealPath("/json/MashupMatrix.json")));
		for (int i = 0; i < mashupNumbers; i++)
		{
			String[] list = bufferedReaderM.readLine().split(",");
			for (int j = 0; j < mashupNumbers; j++)
				mashupMatrix[i][j] = (Double.valueOf(list[j]) > m) ? Double.valueOf(list[j]) : 0;
		}
		bufferedReaderM.close();

		/* ʵ���׾��� */
		// ���ƶȾ���W
		Matrix W = new Matrix(mashupMatrix);

		// �ԽǾ���D
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

		// ������˹����L = D - W
		Matrix L = D.minus(W);

		// ������˹������������V
		EigenvalueDecomposition EL = new EigenvalueDecomposition(L);
		double[][] LV = EL.getV().getArray();		// ����ֵ����
		double[][] LD = EL.getD().getArray();		// ��������������

		double[][] LDArray = new double[mashupNumbers][2];
		for (int i = 0; i < mashupNumbers; i++)
		{
			LDArray[i][0] = LD[i][i];
			LDArray[i][1] = i;
		}
		for (int i = 0; i < mashupNumbers; i++)
			for (int j = 0; j < mashupNumbers - 1 - i; j++)
				if (LDArray[j][0] > LDArray[j + 1][0])
				{
					LDArray[j][0] += LDArray[j + 1][0];
					LDArray[j + 1][0] = LDArray[j][0] - LDArray[j + 1][0];
					LDArray[j][0] -= LDArray[j + 1][0];

					LDArray[j][1] += LDArray[j + 1][1];
					LDArray[j + 1][1] = LDArray[j][1] - LDArray[j + 1][1];
					LDArray[j][1] -= LDArray[j + 1][1];
				}

		FileWriter fileWriterV = new FileWriter(request.getServletContext().getRealPath("/json/Eigen" + m + ".json"));
		for (int i = 0; i < 30; i++)
			fileWriterV.write((int)LDArray[i][1] + ",");
		fileWriterV.write("\r\n");
		for (int i = 0; i < 30; i++)
		{
			for (int j = 0; j < mashupNumbers; j++)
				fileWriterV.write(LV[j][(int)LDArray[i][1]] + ",");
			fileWriterV.write("\r\n");
		}
		fileWriterV.close();

		double[][] eigenvalue = new double[mashupNumbers][30];
		BufferedReader bufferedReaderE = new BufferedReader(new FileReader(request.getServletContext().getRealPath("/json/Eigen" + m + ".json")));
		bufferedReaderE.readLine();
		for (int i = 0; i < 30; i++)
		{
			String[] list = bufferedReaderE.readLine().split(",");
			for (int j = 0; j < mashupNumbers; j++)
				eigenvalue[j][i] = Double.valueOf(list[j]);
		}
		bufferedReaderE.close();
		
		double[][] eigenvalueMatrix = new double[mashupNumbers][k];
		for (int i = 0; i < k; i++)
			for (int j = 0; j < mashupNumbers; j++)
				eigenvalueMatrix[j][i] = eigenvalue[j][i];
		
		// ����������V��K-means����
		kmeans_data data = new kmeans_data(eigenvalueMatrix, mashupNumbers, k);
		kmeans_param param = new kmeans_param();
		param.initCenterMehtod = kmeans_param.CENTER_RANDOM;					// �������ĵ�ĳ�ʼ��ģʽΪ���
		kmeans.doKmeans(k, data, param);
		fileWriterC.write("k = " + k + "\nThe labels of points is: \n");		// ���ÿ���������������
		for (int label : data.labels)
			fileWriterC.write(label + " ");

		/* ����ó�����ȫ��ģ���Q */
		int sum = 0;
		for (int i = 0; i < mashupNumbers; i++)
			for (int j = i + 1; j < mashupNumbers; j++)
				sum += (mashupMatrix[i][j] > m) ? 1 : 0;

		double q = 0;
		for (int i = 0; i < k; i++)
		{
			List<Integer> insideI = new ArrayList<Integer>();
			for (int j = 0; j < data.labels.length; j++)
				if (i == data.labels[j])
					insideI.add(j);

			double e = 0;
			for (int j = 0; j < insideI.size(); j++)
				for (int l = j + 1; l < insideI.size(); l++)
					e += (mashupMatrix[insideI.get(j)][insideI.get(l)] > m) ? 1 : 0;

			double a = e;
			for (int j = 0; j < insideI.size(); j++)
				for (int l = insideI.get(j) + 1; l < mashupNumbers; l++)
					if (0 != mashupMatrix[insideI.get(j)][l] && !insideI.contains(l))
						a++;

			e /= (2.0 * sum);
			a /= (2.0 * sum);
			q += (2 * e - a * a);
		}

		fileWriterC.write(q + "\r\n");
		fileWriterC.close();
	}
	
	/**
	 * List��ʽת��ΪString
	 * @param list
	 * @return
	 */
	private static String translate(List<Integer> list)
	{
		String string = "";
		for (int element : list)
			string += element + ",";
		return string.substring(0, string.length() - 1);
	}
}
