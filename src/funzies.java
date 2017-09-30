import java.awt.Frame;
import java.awt.TextArea;
import java.io.File;
import java.util.ArrayList;
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

public class funzies {


	
	public static void main(String[] args) 
	{
		/*double vanCapacityRead;
		double minQuotaRead;
		int numberOfObjectsRead;
		
		String[] array;
		
		ArrayList <Problem> problems = new ArrayList <Problem>();
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
		
		System.out.println(problems.get(0).returnValueOfObj(8));*/
		
		
		
		/*JFrame f= new JFrame();  
        TextArea area=new TextArea("Welcome to javatpoint");  
    area.setBounds(10,30, 400,400);  
    f.add(area);  
    f.setSize(400,400);  
    f.setLayout(null);  
    f.setLocationRelativeTo(null);
    f.setVisible(true);  
    
    f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
    
    
    area.append("\nasdasdads");*/
		
		JPanel middlePanel = new JPanel ();
	    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Display Area" ) );

	    // create the middle panel components

	    JTextArea display = new JTextArea ( 16, 58 );
	    display.setEditable ( false ); // set textArea non-editable
	    JScrollPane scroll = new JScrollPane ( display );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

	    //Add Textarea in to middle panel
	    middlePanel.add ( scroll );

	    // My code
	    JFrame frame = new JFrame ();
	    frame.add ( middlePanel );
	    frame.pack ();
	    frame.setLocationRelativeTo ( null );
	    frame.setVisible ( true );
    
    
	}
}
