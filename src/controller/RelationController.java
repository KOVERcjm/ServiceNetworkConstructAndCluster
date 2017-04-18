package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.mapper.RelationMapper;
import mybatis.model.MashupRelation;

@Controller
public class RelationController
{
	@Autowired
	private RelationMapper relationMapper;
	
	@RequestMapping("/hello")
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception
	{
		// 去除无效数据
//		relationMapper.delete();
//		
//		List<Integer> apiIDs = relationMapper.selectAllApiID();
//
//		for(int apiID : apiIDs)
//		{
//			List<Integer> mashupIDs = relationMapper.selectByApiID(apiID);
//			
//			if (1 < mashupIDs.size())
//			{
//				MashupRelation mashupRelation = null;
//				mashupIDs.sort(null);
//				//System.out.println(mashupIDs.toString());
//				for(int i = 0; i < mashupIDs.size(); i++)
//					for(int j = i + 1; j < mashupIDs.size(); j++)
//					{
//						//System.out.println(i + " " + j + " " + mashupIDs.get(i) + ", " + mashupIDs.get(j));
//						mashupRelation = relationMapper.selectByTwoMashupIDs(mashupIDs.get(i), mashupIDs.get(j));
//						if (null != mashupRelation)
//						{
//							mashupRelation.setIntersection(mashupRelation.getIntersection()+ "," + apiID);
//							mashupRelation.setUnion(mashupRelation.getUnion() + "," + apiID);
//							// TODO Weight
//							relationMapper.updateMashupRelation(mashupRelation);
//						}
//						else
//						{
//							mashupRelation = new MashupRelation(mashupIDs.get(i), mashupIDs.get(j), apiID + "", apiID + "", 0);
//							relationMapper.insertMashupRelation(mashupRelation);
//						}
//						mashupRelation = null;
//					}
//			}
//		}
		
		// 去重
		//BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(arg0.getServletContext().getRealPath("/WEB-INF/js/MashupRelation.json")));
		List<Integer> mashupIDs = relationMapper.selectAllMashupID();
		JSONArray mashupIDJson = new JSONArray();
		for (int mashupID : mashupIDs)
			mashupIDJson.put(new JSONObject().put("name", mashupID));
		
		
		List<MashupRelation> mashupRelations = relationMapper.selectAllMashupRelation();
		JSONArray mashupRelationJson = new JSONArray();
		for (MashupRelation mashupRelation : mashupRelations)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("source", mashupIDs.indexOf(mashupRelation.getMashupID_A()));
			jsonObject.put("target", mashupIDs.indexOf(mashupRelation.getMashupID_B()));
			jsonObject.put("weight", 1); //mashupIDs.indexOf(mashupRelation.getWeight()));
			mashupRelationJson.put(jsonObject);
		}
		//System.out.println(mashupRelationJson.toString());
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("msg", "complete");
		modelAndView.addObject("nodesJson", mashupIDJson.toString());
		modelAndView.addObject("edgesJson", mashupRelationJson.toString());
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
