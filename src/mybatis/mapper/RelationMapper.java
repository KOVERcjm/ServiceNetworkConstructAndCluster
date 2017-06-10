package mybatis.mapper;

import java.util.List;

import mybatis.model.Description;
import mybatis.model.MashupRelation;
import mybatis.model.RelationDescription;

public interface RelationMapper
{
	/**
	 * ȥ��ApiIDΪ-1����Ч����
	 * @return ɾ�����ݽ��
	 */
	boolean delete();
	
	/**
	 * ��ȡMashupID����
	 * @return
	 */
	int countMashups();
	
	/**
	 * ��ȡMashupRelation����
	 * @return
	 */
	int countMashupRelations();
	
	/**
	 * ��ȡ����MashupID�б�
	 * @return ����Mashup��IDֵ
	 */
	List<Integer> selectAllMashupID();
	
	/**
	 * ��ȡMashupRelation�б�ÿ��ȡ1000����
	 * @param start
	 * @return ��ȡ�ӵ�start����ʼ��1000��MashupRelation
	 */
	List<MashupRelation> selectMashupRelations(int start);
	
	/**
	 * ����MashupID��ȡ��Ӧ��ApiID�б�
	 * @param mashupID
	 * @return ��Ӧ��API��IDֵ�б�
	 */
	List<Integer> selectByMashupID(int mashupID);
	
	/**
	 * ���뵥��MashupRelation
	 * @param mashupRelation
	 */
	void insertMashupRelation(MashupRelation mashupRelation);
	
	/**
	 * ��ȡMashup�б�
	 * @return
	 */
	List<Description> getMashup();
	
	/**
	 * ��ȡAPI�б�
	 * @return
	 */
	List<Description> getAPI();
	
	/**
	 * ��ȡ��������Mashup-API��ϵ��
	 * @return
	 */
	List<RelationDescription> getRelationDescription();
}
