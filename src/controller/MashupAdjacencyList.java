package controller;

public class MashupAdjacencyList
{
	private int mashupID_A;
	private int mashupID_B;
	private String intersection;
	private String union;
	private int weight;
	public int getMashupID_A()
	{
		return mashupID_A;
	}
	public void setMashupID_A(int mashupID_A)
	{
		this.mashupID_A = mashupID_A;
	}
	public int getMashupID_B()
	{
		return mashupID_B;
	}
	public void setMashupID_B(int mashupID_B)
	{
		this.mashupID_B = mashupID_B;
	}
	public String getIntersection()
	{
		return intersection;
	}
	public void setIntersection(String intersection)
	{
		this.intersection = intersection;
	}
	public String getUnion()
	{
		return union;
	}
	public void setUnion(String union)
	{
		this.union = union;
	}
	public int getWeight()
	{
		return weight;
	}
	public void setWeight(int weight)
	{
		this.weight = weight;
	}
	
	public MashupAdjacencyList(int mashupID_A, int mashupID_B, String apiID)
	{
		super();
		this.mashupID_A = mashupID_A;
		this.mashupID_B = mashupID_B;
		this.intersection = apiID;
		this.union = apiID;
		this.weight = 0;
	}
	public MashupAdjacencyList()
	{
		super();
	}

}
