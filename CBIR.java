package assignment1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/* Project 1
*/
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.AbstractAction;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CBIR extends JFrame{
    
    private JLabel photographLabel = new JLabel();  //container to hold a large 
    private JButton [] button; //creates an array of JButtons
    private int [] buttonOrder = new int [101]; //creates an array to keep up with the image order
    private double [] imageSize = new double[101]; //keeps up with the image sizes
    private GridLayout gridLayout1;
    private GridLayout gridLayout2;
    private GridLayout gridLayout3;
    private GridLayout gridLayout4;
    private JPanel panelBottom1;
    private JPanel panelBottom2;
    private JPanel panelTop;
    private JPanel buttonPanel;
    private Double [][] intensityMatrix = new Double [100][25];
    private Double [][] colorCodeMatrix = new Double [100][64];
    private Double [][] mergedMatrix = new Double[100][89];
    private Double [][] normalizedFeatures = new Double[100][89];
    private Double [][] SortedNormalizedFeatures = new Double[100][89];
    Double[][] mergedNormalize;
    private Double [] standardDeviations = new Double[89];
    private Double [] relevantStandardDeviations = new Double[89];
    private Double [] updatedWeight = new Double[89];
    private Double [] normalizedWeight = new Double[89];
    private Double [] averages = new Double[89];
    private static HashMap<Integer, Double> record = new HashMap<>();
    private static HashMap<Integer, Double> record2 = new HashMap<>();

    private boolean IC = false; 
    private JCheckBox relevance = new JCheckBox("Relevance");
    private JCheckBox [] relevant = new JCheckBox[101];
    ArrayList<Integer> checkedBoxes = new ArrayList<>(); 
    int picNo = 0;
    int imageCount = 1; //keeps up with the number of images displayed since the first page.
    int pageNo = 1;
    private boolean relevanceOn = false; 
    private boolean atLeastOneCheckBox = false; 
    private boolean moreThanOneIteration = false; 
    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CBIR app = new CBIR();
                app.setVisible(true);
            }
        });
    }
    
    
    
    public CBIR() {
      //The following lines set up the interface including the layout of the buttons and JPanels.
		Border border = BorderFactory.createLineBorder(Color.black, 10);
		Border panelBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Icon Demo: Please Select an Image");        
        panelBottom1 = new JPanel();
        panelBottom2 = new JPanel();
        panelTop = new JPanel();
        buttonPanel = new JPanel();
        gridLayout1 = new GridLayout(4, 5, 5, 5);
        gridLayout2 = new GridLayout(2, 1, 5, 5);
        gridLayout3 = new GridLayout(1, 2, 5, 5);
        gridLayout4 = new GridLayout(6, 6, 3, 3);
        setLayout(gridLayout2);
        panelBottom1.setLayout(gridLayout1);
        panelBottom1.setBorder(panelBorder);
        panelBottom2.setLayout(gridLayout1);
        panelTop.setLayout(gridLayout3);
        add(panelTop);
        add(panelBottom1);
        photographLabel.setVerticalTextPosition(JLabel.BOTTOM);
        photographLabel.setHorizontalTextPosition(JLabel.CENTER);
        photographLabel.setHorizontalAlignment(JLabel.CENTER);
        photographLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        photographLabel.setBorder(border);
        buttonPanel.setLayout(gridLayout4);
        panelTop.add(photographLabel);

        panelTop.add(buttonPanel);
        JButton previousPage = new JButton("Previous Page");
        
        Border borderPrevious = BorderFactory.createLineBorder(Color.BLACK, 3);
        previousPage.setBackground(Color.LIGHT_GRAY); 
        previousPage.setForeground(Color.WHITE);
        previousPage.setBorder(borderPrevious);
        
        JButton nextPage = new JButton("Next Page");
        Border borderNext = BorderFactory.createLineBorder(Color.BLACK, 3);
        nextPage.setBackground(Color.DARK_GRAY); 
        nextPage.setForeground(Color.WHITE);
        nextPage.setBorder(borderNext);
        
        JButton intensity = new JButton("Intensity");
        intensity.setBackground(Color.DARK_GRAY); 
        intensity.setForeground(Color.WHITE);
        intensity.setBorder(borderPrevious);
        
        JButton colorCode = new JButton("Color Code");
        colorCode.setBackground(Color.LIGHT_GRAY); 
        colorCode.setForeground(Color.WHITE);
        colorCode.setBorder(borderNext);
        
        JButton IntensityAndColor = new JButton("Intensity & Color Code");
        IntensityAndColor.setBackground(Color.LIGHT_GRAY); 
        IntensityAndColor.setForeground(Color.WHITE);
        IntensityAndColor.setBorder(borderNext);
        
        relevance.setFocusable(false);
        relevance.addActionListener(new relevance());
        initializeRelevant(); 
        
        buttonPanel.add(previousPage);
        buttonPanel.add(nextPage);
        buttonPanel.add(intensity);
        buttonPanel.add(colorCode);
        buttonPanel.add(IntensityAndColor);
        buttonPanel.add(relevance);
        
        nextPage.addActionListener(new nextPageHandler());
        previousPage.addActionListener(new previousPageHandler());
        intensity.addActionListener(new intensityHandler());
        colorCode.addActionListener(new colorCodeHandler());
        IntensityAndColor.addActionListener(new intensityAndColorCode());
        //relevance.addActionListener(new relevantImages());
        setSize(1100, 750);
        // this centers the frame on the screen
        setLocationRelativeTo(null);
        
        
        button = new JButton[101];
        /*This for loop goes through the images in the database and stores them as icons and adds
         * the images to JButtons and then to the JButton array
        */
        for (int i = 1; i < 101; i++) {
                ImageIcon icon;
                try {
					icon = new ImageIcon(ImageIO.read(new File("C:\\College\\CSS484\\images\\images\\" + i + ".jpg")));
	                 if(icon != null){
	                     button[i] = new JButton(icon);
	                     panelBottom1.add(button[i]);
	                     button[i].addActionListener(new IconButtonHandler(i, icon));
	                     buttonOrder[i] = i;
	                     
	                    int height = icon.getIconHeight();       
	                    int width = icon.getIconWidth();
	                    imageSize[i] = width * height; 
	                 }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        } 
        new readImage();
        displayFirstPage();
    }
    
    public void initializeRelevant() {
    	for(int i = 0; i < 101; i++) {
    		relevant[i] = new JCheckBox("Relevance");
    		relevant[i].addActionListener( new relevantImages(i));
    	}
    }
    




	/*This method opens the intensity text file containing the intensity matrix with the histogram bin values for each image.
     * The contents of the matrix are processed and stored in a two dimensional array called intensityMatrix.
    */
    public void readIntensityFile(){
      StringTokenizer token;
      Scanner read = null;
      Double intensityBin;
      String line = "";
      int i = 0;
      int j = 0;
      try{
          read =new Scanner(new FileInputStream ("C:\\College\\CSS484\\intensityCodes.txt")); //Would need to change to graders path
        }
        catch(FileNotFoundException EE){
          System.out.println("The file intensityCodes.txt does not exist");
        }
     
      while(read.hasNextDouble()) {
    	  double value = read.nextDouble(); 
    	  if(j==25) {
    		  j = 0; 
    		  i++;
    		  intensityMatrix[i][j] = value;
    		  j++;
    	  }
    	  else {
    		  intensityMatrix[i][j] = value; 
    		  j++; 
    	  }
    	  
    	  
    	  
     }     
      sortImagesI(); 
    }
    
    /*This method opens the color code text file containing the color code matrix with the histogram bin values for each image.
     * The contents of the matrix are processed and stored in a two dimensional array called colorCodeMatrix.
    */
    private void readColorCodeFile(){
      StringTokenizer token;
      Scanner read = null;
      Double colorCodeBin;
      int lineNumber = 0;
      int i = 0;
      int j = 0;
         try{
           read =new Scanner(new FileInputStream ("C:\\College\\CSS484\\colorCodes.txt")); //Need to change to graders path
         }
         catch(FileNotFoundException EE){
           System.out.println("The file colorCodes.txt does not exist");
         }
      
         while(read.hasNextDouble()) {
       	  double value = read.nextDouble();
       	//  System.out.println(value);

       	  if(j == 64) {
       		  j = 0; 
       		  i++;
       		  colorCodeMatrix[i][j] = value; 
       		  j++; 
       	  }
       	  else {
       		  colorCodeMatrix[i][j] = value; 
       		  j++; 
       	  }
        }
        
         
         sortImagesCC();
         
    }
    
    private void sortImagesI() {
    	double distance; 
    	for(int i = 0; i < 100; i++) {
    		if(i != picNo - 1) {
    			distance = distanceI( 0, i);
    			record.put(i+1, distance);
    		}
    	}
   	
    	if(!IC) {
        	record = sortByValue(record);
        	buttonOrder(); 
    	}
    }
    
    private void sortImagesCC() {
    	double distance; 
    	for(int i = 0; i < 100; i++) {
    		if(i != picNo-1) {
    			distance = distanceCC( 0, i);
    			record.put(i+1, distance);
    		}
    	}
    	
    	if(!IC) {
        	record = sortByValue(record);
        	buttonOrder(); 
    	}

    	
    }
    
    private void buttonOrder() {
    	int i = 1; 
    	buttonOrder[0] = picNo; 
    	for(Map.Entry<Integer, Double> mapElement : record.entrySet()) {
    		int key = mapElement.getKey();
    		buttonOrder[i] = key; 
    		i++;
    	}
    	
    	if(!relevanceOn) {
        	displayFirstPage();
    	}
    	else {
    		displayFirstPageRelevance();
    	}
    }
    
    
    public static HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double> > list =
               new LinkedList<Map.Entry<Integer, Double> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double> >() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
         
        // put data from sorted list to hashmap
        HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        
        return temp;
    }
   
    
    private double distanceCC(int loop, int picNumber) { 
    	if(loop < 64) {
    		return Math.abs(((colorCodeMatrix[picNo-1][loop] / imageSize[picNo]) - (colorCodeMatrix[picNumber][loop] / imageSize[picNumber + 1]))) + distanceCC(++loop, picNumber); 
    	}
    	
    	return 0.0;
       }
    
    private double distanceI(int loop, int picNumber) {
    	if(loop < 25) {
    		return Math.abs(((intensityMatrix[picNo-1][loop] / imageSize[picNo]) - (intensityMatrix[picNumber][loop] / imageSize[picNumber + 1]))) + distanceI(++loop, picNumber); 
    	}    	
    	return 0.0;
       }
    
    private double distanceIC(int loop, int picNumber) {
    	if(loop < 89) {
    		return ( 0.01123595505 * (Math.abs(normalizedFeatures[picNo-1][loop] - normalizedFeatures[picNumber][loop]))) + (distanceIC(++loop, picNumber));
    	}
    	return 0.0; 
    }
    
    
    //Step one of Intensity & colorCode
    public void divideByImage() {
    	for(int i = 0; i < 100; i++) {
    		for(int j = 0; j < 25; j++) {
    			intensityMatrix[i][j] /= imageSize[i+1];
    		}
    		for(int j = 0; j < 64; j++) {
    			colorCodeMatrix[i][j] /= imageSize[i+1];
    		}
    	}
    	
    	merge(); 
    	
    }
    
    //step two of Intensity & colorCode
    public void merge() {
    	for(int i = 0; i < 100; i++) {
    		for(int j = 0; j < 25; j++) {
    			mergedMatrix[i][j] = intensityMatrix[i][j];
    		}
    		for(int j = 0; j < 64; j++) {
    			mergedMatrix[i][j+25] = colorCodeMatrix[i][j];
    		}
    	}
    	
    	
    	for(int i = 0; i <3; i++) {
    		for(int j= 0; j < 10 ; j++) {
    			//System.out.println("mergedMatrix[" + i + "][" + j + "] = " + mergedMatrix[i][j] );
    		}
    	}
    	calcAverage(); 
    }
    
    //step three of intensity and colorCode
    public void calcAverage() {
    	for(int i = 0; i < 89; i++) {
    		double sum = 0.0;
    		for(int j = 0; j < 100; j++) {
    			sum = sum + mergedMatrix[j][i];
    		}    		
    		averages[i] = sum/89.0; 
    	}
    	
		for(int i = 0; i < 13; i++) {
			//System.out.println("averages[" + i + "] = " + averages[i]);
		}
	    	calcSTD();
    }
    
    //step four of intensity and colorCode
    public void calcSTD() {
    	double std; 
    	for(int i = 0; i < 89; i++) {
    		 std = getSTD(i);
     		standardDeviations[i] = std; 
    	}   
    		for(int i = 0; i < 89; i++) {
    			//System.out.println("standardDeviations[" + i + "] = " + standardDeviations[i]);
    		}
  
    	normalize(); 
    }
    
    public double getSTD(int col) {
    	
        double sum = 0.0, standardDeviation = 0.0;
        int length = 100;

        
        for(int i = 0; i < 100; i++) {
        	sum += mergedMatrix[i][col];
        }
        
        double mean = sum/length;
        for(int i =0; i < 100; i++) {
            standardDeviation += Math.pow(mergedMatrix[i][col] - mean, 2);
        }       
        return Math.sqrt(standardDeviation/length);
    }
    
    
    //step five of the intensity and colorcode
    public void normalize() {   	
    	for(int i = 0; i < 100; i++) {
    		for(int j = 0; j < 89; j++) {
    			if(standardDeviations[j] == 0) {
    				normalizedFeatures[i][j] = 0.0;
    			}
    			else {
        			normalizedFeatures[i][j] = ((mergedMatrix[i][j] - averages[j]) / standardDeviations[j]);

    			}
    		}
    	}
    	
//    	for(int i = 0; i < 100; i++) {
//    		for(int j = 0; j < 89; j++) {
//    			normalizedFeatures[i][j] = ((mergedMatrix[i][j] - averages[j]) / standardDeviations[j]);
//    		}
//    	}
    	
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 89; j++) {
    			//System.out.println("normalizedFeatures[" + i + "] [" + j + "] = " + normalizedFeatures[i][j]);
    		}
    	}
    	
    	sortImagesIC(); 
    }
    
    public void sortImagesIC() {
    	double distance; 
    	for(int i = 0; i < 100; i++) {
    		//if(i != picNo-1) {
        		distance = distanceIC( 0, i);
        		//System.out.println("Image " + i + " distance " + distance); 
        		record.put(i+1, distance);
        	//}
    	}   	
    	
    	//System.out.println(record);
    	record = sortByValue(record);
    	
    		for(int j = 0; j < 89; j++) {
    	    	int i = 0; 
    	    	for(Map.Entry<Integer, Double> mapElement : record.entrySet()) {
    	    		int key = mapElement.getKey();
    	    		//System.out.print(key + " ");
    	    		SortedNormalizedFeatures[i][j] = normalizedFeatures[key-1][j];
    	    		i++;
    	    	}
    		}
    	
    	buttonOrder(); 
    }
    
    public void mergeSubmatrix() {
    	
    	for(int i =0; i < checkedBoxes.size(); i++) {
    		//System.out.println("checkedBoxes[" + i + "] =" + checkedBoxes.get(i));
    	}
    	mergedNormalize = new Double[checkedBoxes.size()][89];
    	for(int i = 0; i < 89; i++) {
    		//mergedNormalize[0][i] = SortedNormalizedFeatures[picNo-1][i];
    	   // System.out.println("mergedNormalize[0] [" + i + "] = " + mergedNormalize[0][i] );
    	}
    	for(int i = 0; i < checkedBoxes.size(); i++) {
    		for(int j = 0; j < 89; j++) {
    			mergedNormalize[i][j] = SortedNormalizedFeatures[checkedBoxes.get(i) - 1][j];
    	    //System.out.println("mergedNormalize[" + i + "] [" + j + "] = " + mergedNormalize[i][j] );
    		}
    	}
    	
    	//First fill up rStd array with std values then test edge cases after
    	// Get averages for only relevant normalized features
       	double std = 0.0; 
    	for(int i = 0; i < 89; i++) {
    		 std = getRelevantSTD(i);
    	     relevantStandardDeviations[i] = std; 
      	   //System.out.println("relevantStandardDeviations[" + i + "]  = " + relevantStandardDeviations[i] );
    	}
    	
    	double[] relevantAverage = new double[89];
    	for(int i = 0; i < 89; i++) {
        	double avgSum =0.0 ;
    		for(int j =0; j < checkedBoxes.size(); j++) {
    			avgSum += mergedNormalize[j][i];
    		}
    		relevantAverage[i] = avgSum/checkedBoxes.size(); 
    	}
  
    	for(int i =0; i <89; i++) {
    		if(relevantStandardDeviations[i] == 0 && relevantAverage[i] != 0) {
    			double min = 1000.0;
    			
    			for(int j = 0; j < i; j++) {
    				if(relevantStandardDeviations[j] < min && relevantStandardDeviations[j] != 0) {
    					min = relevantStandardDeviations[j];
    				}
    			}    			
    			std = 0.5 * min; 
    			relevantStandardDeviations[i] = std;
    		}
    	}
    	
    
    	for(int index= 0; index < 89; index++) {
     	   //System.out.println("relevantStandardDeviations[" + index + "]  = " + relevantStandardDeviations[index] );
    	}
    	
    	double sum = 0.0; 
    	for(int i = 0; i <89; i++) {
    		if(relevantStandardDeviations[i] == 0) {
    			updatedWeight[i] = 0.0; 
    		}
    		else {
        		updatedWeight[i] = 1.0/relevantStandardDeviations[i];
    		}
    	    //System.out.println("updatedWeight[" + i + "]  = " + updatedWeight[i] );
    	}
    	
    	for(int index= 0; index < 89; index++) {
    		sum+= updatedWeight[index];
    	}
    	
    	for(int i =0; i <89; i++) {
    		normalizedWeight[i] = updatedWeight[i]/sum; 
    	    //System.out.println("normalizedWeight[" + i + "]  = " + normalizedWeight[i] );
    	}   
    	
    	double distance; 
    	for(int i = 0; i < 100; i++) {
    		//if(i != picNo-1) {
        		distance = distanceRelevantIC( 0,0, i);
        		System.out.println("Image " + i + " distance " + distance); 
        		record2.put(i+1, distance);
        	//}
    	}   	
    	record2 = sortByValue(record2);
    	
		for(int j = 0; j < 89; j++) {
	    	int i = 0; 
	    	for(Map.Entry<Integer, Double> mapElement : record2.entrySet()) {
	    		int key = mapElement.getKey();
	    		//System.out.print(key + " ");
	    		SortedNormalizedFeatures[i][j] = normalizedFeatures[key-1][j];
	    		i++;
	    	}
		}
    	buttonOrderRelevant();    	
    }
    
    private void buttonOrderRelevant() {
    	int i = 1; 
    	buttonOrder[0] = picNo; 
    	for(Map.Entry<Integer, Double> mapElement : record2.entrySet()) {
    		int key = mapElement.getKey();
    		buttonOrder[i] = key; 
    		i++;
    	}
    	
    	checkedBoxes.clear();
    	
    	for(int j = 0; j < 100; j++) {
    		relevant[j].setSelected(false);
    	}
    	
    	record2.clear();
    	if(!relevanceOn) {
        	displayFirstPage();
    	}
    	else {
    		displayFirstPageRelevance();
    	}
    }
    
    private double distanceRelevantIC(int normalizedLoop,int loop, int picNumber) {
    	if(loop < 89) {
    		return (normalizedWeight[normalizedLoop] * (Math.abs(normalizedFeatures[picNo-1][loop] - normalizedFeatures[picNumber][loop]))) + (distanceRelevantIC(++normalizedLoop ,++loop, picNumber));
    	}
    	return 0.0; 
    }
    
    public double getRelevantSTD(int col) {
    	
        double sum = 0.0, standardDeviation = 0.0;
        int length = checkedBoxes.size();

        
        for(int i = 0; i < checkedBoxes.size(); i++) {
        	sum += mergedNormalize[i][col];
        }
        
        double mean = sum/(double)length;
        for(int i =0; i < checkedBoxes.size(); i++) {
            standardDeviation += Math.pow(mergedNormalize[i][col] - mean, 2);
        }       
        return Math.sqrt(standardDeviation/length);
    }
    
    
    /*This method displays the first twenty images in the panelBottom.  The for loop starts at number one and gets the image
     * number stored in the buttonOrder array and assigns the value to imageButNo.  The button associated with the image is 
     * then added to panelBottom1.  The for loop continues this process until twenty images are displayed in the panelBottom1
    */
    private void displayFirstPage(){
      int imageButNo = 0;
      panelBottom1.removeAll(); 
      for(int i = 1; i < 21; i++){
        imageButNo = buttonOrder[i];
        panelBottom1.add(button[imageButNo]); 
        imageCount ++;
      }
      panelBottom1.revalidate();  
      panelBottom1.repaint();

    }
    
    private void displayFirstPageRelevance() {

	        int imageButNo = 0;
	        panelBottom1.removeAll(); 
	        for(int i = 1; i < 21; i++){
	          imageButNo = buttonOrder[i];
	          panelBottom1.add(button[imageButNo]); 
	          relevant[i].setFocusable(false);
	          panelBottom1.add(relevant[i]);
	          imageCount++;
	        }
	        panelBottom1.revalidate();  
	        panelBottom1.repaint();
    	

    }
    
    /*This class implements an ActionListener for each iconButton.  When an icon button is clicked, the image on the 
     * the button is added to the photographLabel and the picNo is set to the image number selected and being displayed.
    */ 
    private class IconButtonHandler implements ActionListener{
      int pNo = 0;
      ImageIcon iconUsed;
      
      IconButtonHandler(int i, ImageIcon j){
        pNo = i;
        iconUsed = j;  //sets the icon to the one used in the button
      }
      
      public void actionPerformed( ActionEvent e){
        photographLabel.setIcon(iconUsed);
        picNo = pNo;
        //System.out.println(picNo);
      }
      
    }
    
    /*This class implements an ActionListener for the nextPageButton.  The last image number to be displayed is set to the 
     * current image count plus 20.  If the endImage number equals 101, then the next page button does not display any new 
     * images because there are only 100 images to be displayed.  The first picture on the next page is the image located in 
     * the buttonOrder array at the imageCount
    */
    private class nextPageHandler implements ActionListener{

      public void actionPerformed( ActionEvent e){
    	  if(!relevanceOn) {
	          int imageButNo = 0;
	          int endImage = imageCount + 20;
	          if(endImage <= 101){
	            panelBottom1.removeAll(); 
	            for (int i = imageCount; i < endImage; i++) {
	                    imageButNo = buttonOrder[i];
	                    panelBottom1.add(button[imageButNo]);
	                    imageCount++;
	          
	            } 
	            panelBottom1.revalidate();  
	            panelBottom1.repaint();
	          }
    	  }
    	  else {
	          int imageButNo = 0;
	          int endImage = imageCount + 20;
	          if(endImage <= 101){
	            panelBottom1.removeAll(); 
	            for (int i = imageCount; i < endImage; i++) {
	                    imageButNo = buttonOrder[i];
	                    relevant[i].setFocusable(false);
	                    panelBottom1.add(relevant[i]);
	                    panelBottom1.add(button[imageButNo]);
	                    imageCount++;
	          
	            } 
	            panelBottom1.revalidate();  
	            panelBottom1.repaint();
	          }
    	  }

      }
      
    }
    
    /*This class implements an ActionListener for the previousPageButton.  The last image number to be displayed is set to the 
     * current image count minus 40.  If the endImage number is less than 1, then the previous page button does not display any new 
     * images because the starting image is 1.  The first picture on the next page is the image located in 
     * the buttonOrder array at the imageCount
    */
    private class previousPageHandler implements ActionListener{

      public void actionPerformed( ActionEvent e){
    	  if(!relevanceOn) {
	          int imageButNo = 0;
	          int startImage = imageCount - 40;
	          int endImage = imageCount - 20;
	          if(startImage >= 1){
	            panelBottom1.removeAll();
	            /*The for loop goes through the buttonOrder array starting with the startImage value
	             * and retrieves the image at that place and then adds the button to the panelBottom1.
	            */
	            for (int i = startImage; i < endImage; i++) {
	                    imageButNo = buttonOrder[i];
	                    panelBottom1.add(button[imageButNo]);
	                    imageCount--;	          
	            }	  
	            panelBottom1.revalidate();  
	            panelBottom1.repaint();
	          }
    	  }
    	  else {
	          int imageButNo = 0;
	          int startImage = imageCount - 40;
	          int endImage = imageCount - 20;
	          if(startImage >= 1){
	            panelBottom1.removeAll();
	            /*The for loop goes through the buttonOrder array starting with the startImage value
	             * and retrieves the image at that place and then adds the button to the panelBottom1.
	            */
	            for (int i = startImage; i < endImage; i++) {
	                    imageButNo = buttonOrder[i];
	                    relevant[i].setFocusable(true);
	                    panelBottom1.add(relevant[i]);
	                    panelBottom1.add(button[imageButNo]);
	                    imageCount--;	          
	            }	  
	            panelBottom1.revalidate();  
	            panelBottom1.repaint();
	          }
    	  }

      }
      
    }
    
    
    /*This class implements an ActionListener when the user selects the intensityHandler button.  The image number that the
     * user would like to find similar images for is stored in the variable pic.  pic takes the image number associated with
     * the image selected and subtracts one to account for the fact that the intensityMatrix starts with zero and not one.
     * The size of the image is retrieved from the imageSize array.  The selected image's intensity bin values are 
     * compared to all the other image's intensity bin values and a score is determined for how well the images compare.
     * The images are then arranged from most similar to the least.
     */
    private class intensityHandler implements ActionListener{

      public void actionPerformed( ActionEvent e){
          
         readIntensityFile(); 
      }
      
    }
    
    
    private class intensityAndColorCode implements ActionListener{
        public void actionPerformed( ActionEvent e){
        	if(atLeastOneCheckBox) {
        		
        		mergeSubmatrix();
        	}
        	else {
            	IC = true; 
                readIntensityFile(); 
                readColorCodeFile(); 
                divideByImage(); 
        	}
        

         }
    }
    
    /*This class implements an ActionListener when the user selects the colorCode button.  The image number that the
     * user would like to find similar images for is stored in the variable pic.  pic takes the image number associated with
     * the image selected and subtracts one to account for the fact that the intensityMatrix starts with zero and not one. 
     * The size of the image is retrieved from the imageSize array.  The selected image's intensity bin values are 
     * compared to all the other image's intensity bin values and a score is determined for how well the images compare.
     * The images are then arranged from most similar to the least.
     */ 
    private class colorCodeHandler implements ActionListener{

      public void actionPerformed( ActionEvent e){
    
          readColorCodeFile(); 
      }
   }
    
   private class relevance implements ActionListener{
	   public void actionPerformed(ActionEvent e) {
		   if(relevance.isSelected()) {
			   relevanceOn = true;
			   displayFirstPageRelevance();   
		   }
		   else {
			   relevanceOn = false; 
			   displayFirstPage();
		   }
	   }
   }
   
   private class relevantImages implements ActionListener{
	   int picture = -1;
	    relevantImages(int i) {
	    	picture = i;
		}
	    public void actionPerformed( ActionEvent e){
	    	atLeastOneCheckBox = true; 
	        checkedBoxes.add(picture);
	    }
   }
}