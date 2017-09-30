import java.util.ArrayList;

public class Problem 
{
	private double vanCapacity;
	private double minQuota;
	private int numberOfObjects;
	private ArrayList <String> names;
	private ArrayList <Double> weights;
	private ArrayList <Double> values;
	
	Problem(double vanCapacity, double minQuota, int numberOfObjects)
	{
		this.vanCapacity = vanCapacity;
		this.minQuota = minQuota;
		this.numberOfObjects = numberOfObjects;
		
		names = new ArrayList <String>();
		weights = new ArrayList <Double>();
		values = new ArrayList <Double>();
	}
	
	public void addName (String name)
	{
		this.names.add(name);
	}
	
	public void addValue (double value)
	{
		this.values.add(value);
	}
	
	public void addWeight(double weight)
	{
		this.weights.add(weight);
	}
	
	public double returnVanCapacity()
	{
		return this.vanCapacity;
	}
	
	public double returnMinQuota()
	{
		return this.minQuota;
	}
	
	public int returnNumberOfObjects()
	{
		return this.numberOfObjects;
	}
	
	public String returnNameOfObj(int index)
	{
		return this.names.get(index);
	}
	
	public Double returnValueOfObj(int index)
	{
		return this.values.get(index);
	}
	
	public Double returnWeightOfObj(int index)
	{
		return this.weights.get(index);
	}
	
	public ArrayList<String> returnNames()
	{
		return this.names;
	}
	
	public ArrayList<Double> returnValues()
	{
		return this.values;
	}
	
	public ArrayList<Double> returnWeights()
	{
		return this.weights;
	}
	
	

}
