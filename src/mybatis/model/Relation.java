package mybatis.model;

public class Relation
{
	private int mashupID;
	private int apiID;
	
	public int getMashupID()
	{
		return mashupID;
	}
	public void setMashupID(int mashupID)
	{
		this.mashupID = mashupID;
	}
	public int getApiID()
	{
		return apiID;
	}
	public void setApiID(int apiID)
	{
		this.apiID = apiID;
	}
	
	public Relation(int mashupID, int apiID)
	{
		super();
		this.mashupID = mashupID;
		this.apiID = apiID;
	}
	
	public Relation()
	{
		super();
	}
	
	@Override
	public String toString()
	{
		return "Relation [mashupID=" + mashupID + ", apiID=" + apiID + "]";
	}
	
}
