import java.awt.TextArea;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;

public class RunApplication {
	
	private static int numberOfObjects;
	private static double vanCapicity;
	private static double minQuota;
	
	private static double mutationProbability;
	private static double crossoverProbability;
	
	private static Chromosome solution;
	private static double solutionValue;
	private static double solutionWeight;
	
	
	private static int populationSize;
	private static int maxGenerations;
	private static int generationCount;
	private static int problemCount;

    private static ArrayList<Chromosome> population = new ArrayList<Chromosome>();
    private static ArrayList<Chromosome> breedPopulation = new ArrayList<Chromosome>();
    
    private static ArrayList<Double> objectWeight = new ArrayList<Double>();
    private static ArrayList<Double> objectValue = new ArrayList<Double>();
    private static ArrayList<String> objectName = new ArrayList<String>();
    private static ArrayList <Problem> problems = new ArrayList <Problem>();

    private static Chromosome one = new Chromosome();
    private static Chromosome two = new Chromosome();
    
    private static boolean solutionFound = false;
    
    private static JTextArea area;


	public static void main(String[] args) 
	{
	    populationSize = 100;
	    maxGenerations = 10000;
	    
	    crossoverProbability = 0.8;
	    mutationProbability = 0.1;
	    
	    
	    readFileCompletely();
	
	    JPanel middlePanel = new JPanel ();
	    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "COMP304 Assignment 2 : Divyan Hirasen 215018696" ) );

	    // create the middle panel components

	    area = new JTextArea ( 16, 58 );
	    area.setEditable ( false ); // set textArea non-editable
	    JScrollPane scroll = new JScrollPane ( area );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

	    //Add Textarea in to middle panel
	    middlePanel.add ( scroll );

	    // My code
	    JFrame frame = new JFrame ();
	    frame.add ( middlePanel );
	    frame.pack ();
	    frame.setLocationRelativeTo ( null );
	    frame.setVisible ( true );
	    
	    area.append(String.format("%-20s%-20s", "Population Size", populationSize));
	    area.append(String.format("\n%-20s%-20s", "Max Generations", maxGenerations));
	    area.append(String.format("\n%-20s%-20s", "Crossover Prob", crossoverProbability*100));
	    area.append(String.format("\n%-20s%-20s", "Mutation Prob", mutationProbability*100));



	    
	    long startTime = System.currentTimeMillis();
	    
	    for(int pr = 0; pr < problems.size(); pr++)
	    {
	    	problemCount = pr + 1;
	    	
	    	//area.append("\n======Problem: "+problemCount+"========");
	    	area.append("\n==================================");
	    	area.append("\nWorking on problem "+problemCount+"...");
	    	
	    	reset();
	    	
	    	numberOfObjects = problems.get(pr).returnNumberOfObjects();
			vanCapicity = problems.get(pr).returnVanCapacity();
			minQuota = problems.get(pr).returnMinQuota();
			
			objectValue.clear();
			objectValue.addAll(problems.get(pr).returnValues());
			
			objectWeight.clear();
			objectWeight.addAll(problems.get(pr).returnWeights());
			
			objectName.clear();
			objectName.addAll(problems.get(pr).returnNames());
			
			population.clear();
			breedPopulation.clear();
			
			area.append("\nConstraints: ");
			area.append("\nVan Capacity: "+vanCapicity);
			area.append("\nMin Quota: "+minQuota);
			
			area.append("\n");
			
			for(int k = 0; k < numberOfObjects; k++)
			{
				area.append(String.format("\n%-20s%-20s%-20s", objectName.get(k), objectWeight.get(k), objectValue.get(k)));
			}
			
		    makeInitialGeneration();
			    
			//printPopulation();

            initilisePopuationFitness();

		    while(solutionFound == false && generationCount < maxGenerations)
		    {
		    	breedPopulation();   
		    }
		    
		    if(solutionFound == true)
		    {
		    	printSolution();
		    }
		    else
		    {
		    	area.append("\n======Problem: "+problemCount+"========");
		    	area.append("\nNo Solution");
		    }
		    
		 
			
	    }
	    
	    long endTime = System.currentTimeMillis();
	    
	    area.append("\n==================================");
	    area.append("\nCOMPLETE");	
	    area.append("\nExecution Time: "+((endTime-startTime)/1000.00) + "s");
	}
	
	public static void reset()
	{
		numberOfObjects = 0;
		vanCapicity = 0;
		minQuota = 0;
		
		objectValue.clear();
		
		objectWeight.clear();
		
		objectName.clear();
		
		population.clear();
		
		breedPopulation.clear();
		
		solutionFound = false;
	}
	
	public static void readFileCompletely()
	{
		double vanCapacityRead;
		double minQuotaRead;
		int numberOfObjectsRead;
		
		String[] array;
		
		try
		{
			int problemCount = 0;
			File selectedFile = null;
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

			int returnValue = jfc.showOpenDialog(null);
			// int returnValue = jfc.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = jfc.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath());
			}

			File file = new File(selectedFile.getPath());			
			
			Scanner sc = new Scanner(file);

			String line = sc.nextLine();
			
			while(line != null)
			{
				if(line.contains("*"))
				{
					line = sc.nextLine();
					
					try
					{
						
						while(line.trim() == "")
						{
							line = sc.nextLine();
						}
					}
					catch (Exception e)
					{
						System.out.println("Error Removing Blank Lines");
					}
					
					vanCapacityRead = Double.parseDouble(line);
					minQuotaRead = Double.parseDouble(sc.nextLine());
					numberOfObjectsRead = Integer.parseInt(sc.nextLine());
					
					problems.add(new Problem(vanCapacityRead, minQuotaRead, numberOfObjectsRead));

					for(int k = 0; k < numberOfObjectsRead; k++)
					{
						array = sc.nextLine().split(" +");
						
						//System.out.println(array[0]+":"+array[1]+":"+array[2]);
						problems.get(problemCount).addName(array[0]);
						problems.get(problemCount).addWeight(Double.parseDouble(array[1]));
						problems.get(problemCount).addValue(Double.parseDouble(array[2]));
					}
					
					problemCount++;
				}
				//System.out.println(line);
				
				try
				{
					line = sc.nextLine();
				}
				catch(Exception e){break;}
			}
			
			System.out.println(problemCount);
		}
		catch (Exception e)
		{
			System.out.println("Error Reading File..."+e.toString());
		}
	}
	
	public static void printSolution()
	{
		
		area.append("\n======Problem: "+problemCount+"========");
		area.append("\nSolution in Generation :"+generationCount);
		area.append("\n"+solution.getGeneSequence());
		area.append("\nObject List: ");
		
		for(int k = 0; k < solution.getGeneSequence().length(); k++)
		{
			if(solution.getGeneSequence().charAt(k) == '1')
			{
				area.append(objectName.get(k));
			}
		}
		
		area.append("\nSolution Weight :"+solutionWeight);
		area.append("\nSolution Value :"+solutionValue);
		
	
	}
	
	public static void printPopulation()
	{
		 System.out.println("Generation :"+generationCount);
		    
		    for(int k = 0; k < populationSize; k ++)
		    {
		    	System.out.println(population.get(k).getGeneSequence());
		    }
			
		    System.out.println("--------");
	}
	
	public static void printBreedPopulation()
	{
		 System.out.println("Breed Population");
		    
		    for(int k = 0; k < breedPopulation.size(); k ++)
		    {
		    	System.out.println(breedPopulation.get(k).getGeneSequence());
		    }
			
		    System.out.println("--------");
	}
	
	public static void makeInitialGeneration()
	{
		generationCount = 1;
		
		for(int k = 0; k < populationSize; k++)
		{
			population.add(new Chromosome(numberOfObjects));
		}
	}
	

	public static void printPopulationFitness()
	{
		System.out.println("Fitness");
		 System.out.println("Generation :"+generationCount);
		    
		    for(int k = 0; k < populationSize; k ++)
		    {
		    	System.out.println(population.get(k).getChromosomeFitness());
		    }
			
		    System.out.println("--------");
	}

	public static void initilisePopuationFitness()
	{
		int totalWeight;
		int totalValue;
		
		Chromosome i;
		
		for(int k = 0; k < populationSize; k++)
		{
			totalWeight = 0;
			totalValue = 0;
			
			i = population.get(k);
			
			for(int j = 0; j < numberOfObjects; j++)
			{
				char c = i.getGeneSequence().charAt(j);

				if(c == '1')
				{
					totalWeight += objectWeight.get(j);
					totalValue += objectValue.get(j);
				}
			}
			
			
			if((totalWeight>vanCapicity) && (totalValue>=minQuota))
			{
				double weightabove=((Double.parseDouble(""+totalWeight)-Double.parseDouble(""+vanCapicity))/Double.parseDouble(""+vanCapicity));
				//System.out.println("weight above "+weightabove);
				i.setFitness(weightabove);
			}
			else if((totalWeight<=vanCapicity) &&(totalValue<minQuota))//weight in bound , value out of bound
			{
				
				double valueBelow=((Double.parseDouble(""+minQuota)-Double.parseDouble(""+totalValue))/(Double.parseDouble(""+minQuota)));
				//System.out.println("Value below "+valueBelow);
				i.setFitness(valueBelow);
			}
			else if ((totalWeight>vanCapicity) && (totalValue<minQuota))//both out of bound
			{
				double weightabove=((Double.parseDouble(""+totalWeight)-Double.parseDouble(""+vanCapicity))/Double.parseDouble(""+vanCapicity));
				double valueBelow=((Double.parseDouble(""+minQuota)-Double.parseDouble(""+totalValue))/(Double.parseDouble(""+minQuota)));
				i.setFitness(Double.parseDouble(""+(valueBelow+weightabove)));
				//System.out.println("both:  "+(weightabove+valueBelow));
			}
			else//perfect
			{
				//System.out.println("weak");
				i.setFitness(0.0);
				solutionFound = true;
				solution = i;
				solutionValue = totalValue;
				solutionWeight = totalWeight;
			}
		}
	}
	
	public static Chromosome getTournamentWinner()
	{
		int tournamentSize = (int)(populationSize*0.10);
		
		ArrayList <Chromosome> tournament = new ArrayList <Chromosome>();
		
		 Random rand = new Random();
		 int randomNum;

		 for(int k = 0; k < tournamentSize; k++)
		 {
			 randomNum = rand.nextInt((populationSize -1  - 0) + 1) + 0;
			 tournament.add(population.get(randomNum));
		 }
		 
		 return getMostFitOfList(tournament);
	}
	
	public static Chromosome getMostFitOfList(ArrayList <Chromosome> theList)
	{
		double current = theList.get(0).getChromosomeFitness();
		int index = 0;
		
		for(int k = 0; k < theList.size(); k++)
		{
			if(theList.get(k).getChromosomeFitness() < current)
			{
				current = theList.get(k).getChromosomeFitness();
				index = k;
			}
		}
		
		return theList.get(index);
	}
	
	public static ArrayList <Chromosome> crossover(Chromosome parentOne, Chromosome parentTwo)
	{
		String childOne = parentOne.getGeneSequence();
		String childTwo = parentTwo.getGeneSequence();
		
		ArrayList <Chromosome> children = new ArrayList <Chromosome>();
		
		for(int k = 0; k < numberOfObjects; k++)
		{
			if(Math.random() > crossoverProbability)
			{
				char temp = childOne.charAt(k);
				childOne = childOne.substring(0,k) + childTwo.charAt(k) + childOne.substring(k+1);
				childTwo = childTwo.substring(0, k) + temp + childTwo.substring(k+1);
			}
		}
		
		children.add(new Chromosome(childOne));
		children.add(new Chromosome(childTwo));
		
		return children;
	}
	
	public static void mutateBreedPopulation()
	{
		Chromosome i;
		
		for(int k = 0; k < breedPopulation.size(); k++)
		{
			i = breedPopulation.get(k);
			
			for(int j = 0; j < numberOfObjects; j++)
			{
				if(Math.random() < mutationProbability)
				{
					if(i.getGeneSequence().charAt(j) == '0')
					{
						i.setGeneSequence(i.getGeneSequence().substring(0,j) + "1" + i.getGeneSequence().substring(j+1));
					}
					else
					{
						i.setGeneSequence(i.getGeneSequence().substring(0,j) + "0" + i.getGeneSequence().substring(j+1));
					}
				}
			}
		}
	}
	
	public static void replaceOriginalPopulation()
	{
		population.clear();
		population.addAll(breedPopulation);
		
		initilisePopuationFitness();
	}
	
	public static void breedPopulation()
	{
		generationCount++;
		Chromosome parentOne = getTournamentWinner();
		Chromosome parentTwo = getTournamentWinner();
		
		//Elitism
		breedPopulation.add(getMostFitOfList(population));
		
		while(breedPopulation.size() < populationSize)
		{
			parentOne = getTournamentWinner();
			parentTwo = getTournamentWinner();
			
			breedPopulation.addAll(crossover(parentOne, parentTwo));
		}
		
		while(breedPopulation.size() > populationSize)
		{
			breedPopulation.remove(breedPopulation.size()-1);
		}
		
		mutateBreedPopulation();
		
		replaceOriginalPopulation();
	}
}
