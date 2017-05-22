package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import mybatis.mapper.RelationMapper;
import mybatis.model.MashupRelation;
import mybatis.model.Relation;

@Controller
public class RelationController
{
	@Autowired
	private RelationMapper relationMapper;
	
	@RequestMapping("/navigate")
	public ModelAndView navigatePage(@RequestParam(value = "page")String page)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(page);
		return modelAndView;
	}
	
	@RequestMapping("/getJson")
	public void getJson(@RequestParam(value = "table")String table, HttpServletResponse response)
	{
		JSONArray relationJson = new JSONArray();
		if("mashupapi".equals(table))
		{
			List<Relation> relations = relationMapper.selectAllRelation();
			for (Relation relation : relations)
			{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("mashupID", relation.getMashupID());
				jsonObject.put("apiID", relation.getApiID());
				relationJson.put(jsonObject);
			}
		}
		else
			if("mashupRelation".equals(table))
			{
				List<MashupRelation> mashupRelations = relationMapper.selectAllMashupRelation();
				for (MashupRelation mashupRelation : mashupRelations)
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("source", mashupRelation.getMashupID_A());
					jsonObject.put("target", mashupRelation.getMashupID_B());
					jsonObject.put("weight", mashupRelation.getWeight());
					relationJson.put(jsonObject);
				}
			}
		
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");  
		PrintWriter out = null;  
		try
		{  
			out = response.getWriter();  
			out.append(relationJson.toString());  
		}
		catch(IOException e)
		{  
			e.printStackTrace();  
		}
		finally
		{  
			if (out != null)
				out.close();
		}
	}
	
	@RequestMapping(value="/processData")
	public void processData(HttpServletRequest request)
	{
		int mashupNumbers = relationMapper.countMashups();
		List<Integer> allMashupIDs = relationMapper.selectAllMashupID();
		
		double[][] mashupMatrix = new double[mashupNumbers][mashupNumbers];
		
		for (int i = 0; i < mashupNumbers; i++)
			for (int j = 0; j < mashupNumbers; j++)
				mashupMatrix[i][j] = 0;
		
		for (int i = 0; i < 5583; i++)
		{
			List<MashupRelation> mashupRelations = relationMapper.selectMashupRelations(i * 1000);
			
			for (MashupRelation mashupRelation : mashupRelations)
			{
				int mashupID_A = allMashupIDs.indexOf(mashupRelation.getMashupID_A());
				int mashupID_B = allMashupIDs.indexOf(mashupRelation.getMashupID_B());
				double weight = mashupRelation.getWeight();
				System.out.println(mashupID_A + " " + mashupID_B + " " + weight);
				mashupMatrix[mashupID_A][mashupID_B] = weight;
				mashupMatrix[mashupID_B][mashupID_A] = weight;
			}
		}
		System.out.println("end.");
		try
		{
			FileWriter fileWriter = new FileWriter(request.getServletContext().getRealPath("/json/MashupMatrix.json"), true);
			
			for (int i = 0; i < mashupNumbers; i++)
			{
				for (int j = 0; j < mashupNumbers; j++)
					fileWriter.write(mashupMatrix[i][j] + ",");
				fileWriter.write("\r\n");
			}
			
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	@RequestMapping("/mashup")
	public void mashup()
	{
		// 去除ApiID为-1的无效数据
		relationMapper.delete();
		
		List<Integer> mashupIDs = relationMapper.selectAllMashupID();
		
		for(int i = 0; i < mashupIDs.size(); i++)
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
				
				double weight = (double)intersection.size() / union.size();
				relationMapper.insertMashupRelation(new MashupRelation(	mashupIDs.get(i),
																		mashupIDs.get(j),
																		RelationController.translate(intersections),
																		RelationController.translate(unions),
																		weight
																		));
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
	
	private static List<Integer> translate(String string)
	{
		String[] strings = string.split(",");
		
		List<Integer> list = new ArrayList<Integer>();
		for (String element : strings)
			list.add(Integer.parseInt(element));
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
