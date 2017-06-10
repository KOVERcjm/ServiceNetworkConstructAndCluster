package mybatis.mapper;

import java.util.List;

import mybatis.model.Description;
import mybatis.model.MashupRelation;
import mybatis.model.RelationDescription;

public interface RelationMapper
{
	/**
	 * 去除ApiID为-1的无效数据
	 * @return 删除数据结果
	 */
	boolean delete();
	
	/**
	 * 获取MashupID数量
	 * @return
	 */
	int countMashups();
	
	/**
	 * 获取MashupRelation数量
	 * @return
	 */
	int countMashupRelations();
	
	/**
	 * 获取完整MashupID列表
	 * @return 所有Mashup的ID值
	 */
	List<Integer> selectAllMashupID();
	
	/**
	 * 获取MashupRelation列表（每次取1000条）
	 * @param start
	 * @return 获取从第start条开始的1000条MashupRelation
	 */
	List<MashupRelation> selectMashupRelations(int start);
	
	/**
	 * 根据MashupID获取对应的ApiID列表
	 * @param mashupID
	 * @return 对应的API的ID值列表
	 */
	List<Integer> selectByMashupID(int mashupID);
	
	/**
	 * 插入单个MashupRelation
	 * @param mashupRelation
	 */
	void insertMashupRelation(MashupRelation mashupRelation);
	
	/**
	 * 获取Mashup列表
	 * @return
	 */
	List<Description> getMashup();
	
	/**
	 * 获取API列表
	 * @return
	 */
	List<Description> getAPI();
	
	/**
	 * 获取带描述的Mashup-API关系表
	 * @return
	 */
	List<RelationDescription> getRelationDescription();
}
