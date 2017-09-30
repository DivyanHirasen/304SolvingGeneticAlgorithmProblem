
public class Chromosome 
{
	private String geneSequence;
	private double chromosomeFitness;
	
	Chromosome(int length)
	{
		this.geneSequence = "";
		this.chromosomeFitness = 0.0;
		for(int k = 0; k < length; k++)
		{
			if(Math.random() > 0.5)
			{
				this.geneSequence += "1";
			}
			else
			{
				this.geneSequence += "0";
			}
		}
	}
	
	Chromosome()
	{
		this.geneSequence = "";
		this.chromosomeFitness = 0.0;
	}
	
	Chromosome(String sequence)
	{
		this.geneSequence = sequence;
		this.chromosomeFitness = 0.0;
	}
	
	public String getGeneSequence()
	{
		return this.geneSequence;
	}
	
	public double getChromosomeFitness()
	{
		return chromosomeFitness;
	}
	
	public void setFitness(double fitness)
	{
		this.chromosomeFitness = fitness;
	}
	
	public void setGeneSequence(String sequence)
	{
		this.geneSequence = sequence;
	}
	

}
