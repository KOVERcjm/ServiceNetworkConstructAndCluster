package mybatis.model;

public class MashupRelation
{
	private int mashupID_A;
	private int mashupID_B;
	private String intersections;
	private String unions;
	private double weight;
	
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
		return intersections;
	}
	public void setIntersection(String intersection)
	{
		this.intersections = intersection;
	}
	public String getUnion()
	{
		return unions;
	}
	public void setUnion(String union)
	{
		this.unions = union;
	}
	public double getWeight()
	{
		return weight;
	}
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	public MashupRelation()
	{
		super();
	}
	public MashupRelation(int mashupID_A, int mashupID_B, String intersection, String union, double weight)
	{
		super();
		this.mashupID_A = mashupID_A;
		this.mashupID_B = mashupID_B;
		this.intersections = intersection;
		this.unions = union;
		this.weight = weight; //TODO weight
	}
	@Override
	public String toString()
	{
		return "MashupRelation [mashupID_A=" + mashupID_A + ", mashupID_B=" + mashupID_B + ", intersection=" + intersections + ", union=" + unions + ", weight=" + weight + "]";
	}
	
	
}
