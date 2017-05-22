package mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mybatis.model.MashupRelation;
import mybatis.model.Relation;

public interface RelationMapper
{
	boolean delete();
	List<Integer> selectAllApiID();
	int countMashups();
	List<Integer> selectAllMashupID();
	List<Relation> selectAllRelation();
	List<MashupRelation> selectMashupRelations(int start);
	List<Integer> selectByApiID(int apiID);
	List<Integer> selectByMashupID(int mashupID);
	MashupRelation selectByTwoMashupIDs(@Param("a")int mashupID_A, @Param("b")int mashupID_B);
	void insertMashupRelation(MashupRelation mashupRelation);
	void updateMashupRelation(MashupRelation mashupRelation);
}
