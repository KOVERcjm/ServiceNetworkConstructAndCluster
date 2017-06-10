package mybatis.model;

public class RelationDescription
{
	int mashupID;
	String mashupName;
	int apiID;
	String apiName;
	
	public int getMashupID()
	{
		return mashupID;
	}
	public void setMashupID(int mashupID)
	{
		this.mashupID = mashupID;
	}
	public String getMashupName()
	{
		return mashupName;
	}
	public void setMashupName(String mashupName)
	{
		this.mashupName = mashupName;
	}
	public int getApiID()
	{
		return apiID;
	}
	public void setApiID(int apiID)
	{
		this.apiID = apiID;
	}
	public String getApiName()
	{
		return apiName;
	}
	public void setApiName(String apiName)
	{
		this.apiName = apiName;
	}

	public RelationDescription()
	{
		super();
	}
	public RelationDescription(int mashupID, String mashupName, int apiID, String apiName)
	{
		super();
		this.mashupID = mashupID;
		this.mashupName = mashupName;
		this.apiID = apiID;
		this.apiName = apiName;
	}
}
