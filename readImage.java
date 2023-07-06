package assignment1;

import java.awt.Color;

/*
 * Project 1
*/

import java.awt.image.BufferedImage;
import java.lang.Object.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.lang.Exception;


public class readImage
{
  int imageCount = 1;
  int imageIndex = 0;
  double intensityBins [] = new double [26];
  double intensityMatrix [][] = new double[100][25];
  double colorCodeBins [] = new double [64];
  double colorCodeMatrix [][] = new double[100][64];

  /*Each image is retrieved from the file.  The height and width are found for the image and the getIntensity and
   * getColorCode methods are called.
  */
  public readImage()
  {
	  
    while(imageCount < 101){
      try
      {
    	  BufferedImage image = ImageIO.read(new File("C:\\College\\CSS484\\images\\images\\" + imageCount + ".jpg")); //needs to change to graders path 
    	  int height = image.getHeight();
    	  int width = image.getWidth();
    	  getIntensity(image, height, width);
    	  getColorCode(image, height, width);
    	  imageCount++;
    	  imageIndex++;
      } 
      catch (Exception e)
      {
        System.out.println("Error occurred when reading the file with image " + imageCount);
      }
    }
    writeIntensity();
    writeColorCode();
    
  }
  
  //intensity method 
/*
 * Gets intensity values from every pixel of every image and increments the bins accordingly 
 */
  public void getIntensity(BufferedImage image, int height, int width){
	//  System.out.println("Image: " + imageCount + " HEIGHT: " + height + " WIDTH: " + width);

	  for(int i =0; i <  width; i++) {
		  for(int j = 0; j < height; j++) {
			  
			  int rgb = image.getRGB(i, j);
			  Color pixel = new Color(rgb);
			  int red = pixel.getRed();
			  int green = pixel.getGreen(); 
			  int blue = pixel.getBlue();
			  
			  
			  double intensity = (0.2999 * red) + (0.587 * green) + (0.114 * blue);
			//  System.out.println("RED: " + red + " GREEN: " + green + " BLUE: " + blue + " for image " + imageCount + " at pixel [" + i + "] [" + j + "] With intensity " + intensity);

			  if(intensity >= 0 && intensity < 10) {
				  intensityMatrix[imageIndex][0]++;
			  }
			  else if(intensity < 20) {
				  intensityMatrix[imageIndex][1]++;
			  }
			  else if(intensity < 30) {
				  intensityMatrix[imageIndex][2]++;
			  }
			  else if(intensity < 40) {
				  intensityMatrix[imageIndex][3]++;
			  }
			  else if(intensity < 50) {
				  intensityMatrix[imageIndex][4]++;
			  }
			  else if(intensity < 60) {
				  intensityMatrix[imageIndex][5]++;
			  }
			  else if(intensity < 70) {
				  intensityMatrix[imageIndex][6]++;
			  }
			  else if(intensity < 80) {
				  intensityMatrix[imageIndex][7]++;
			  }
			  else if(intensity < 90) {
				  intensityMatrix[imageIndex][8]++;
			  }
			  else if(intensity < 100) {
				  intensityMatrix[imageIndex][9]++;
			  }
			  else if(intensity < 110) {
				  intensityMatrix[imageIndex][10]++;
			  }
			  else if(intensity < 120) {
				  intensityMatrix[imageIndex][11]++;
			  }
			  else if(intensity < 130) {
				  intensityMatrix[imageIndex][12]++;
			  }
			  else if(intensity < 140) {
				  intensityMatrix[imageIndex][13]++;
			  }
			  else if(intensity < 150) {
				  intensityMatrix[imageIndex][14]++;
			  }
			  else if(intensity < 160) {
				  intensityMatrix[imageIndex][15]++;
			  }
			  else if(intensity < 170) {
				  intensityMatrix[imageIndex][16]++;
			  }
			  else if(intensity < 180) {
				  intensityMatrix[imageIndex][17]++;
			  }
			  else if(intensity < 190) {
				  intensityMatrix[imageIndex][18]++; 
			  }
			  else if(intensity < 200) {
				  intensityMatrix[imageIndex][19]++;
			  }
			  else if(intensity < 210) {
				  intensityMatrix[imageIndex][20]++;
			  }
			  else if(intensity < 220) {
				  intensityMatrix[imageIndex][21]++;
			  }
			  else if(intensity < 230) {
				  intensityMatrix[imageIndex][22]++;
			  }
			  else if(intensity < 240) {
				  intensityMatrix[imageIndex][23]++;
			  }
			  else if(intensity >= 240 && intensity <= 255) {
				  intensityMatrix[imageIndex][24]++;
			  }
		  }
	  }
  }
  
  //color code method
  /*
   * Gets the color code value from every pixel of every image and increments accordingly
   */
  public void getColorCode(BufferedImage image, int height, int width){
	  for(int i =0; i < width ; i++) {
		  for(int j = 0; j < height; j++) {
			  int rgb = image.getRGB(i, j);
			  Color pixel = new Color(rgb);
			  int red = pixel.getRed();
			  int green = pixel.getGreen(); 
			  int blue = pixel.getBlue();
			  
			  String redBinary = Integer.toBinaryString(red);
			  String greenBinary = Integer.toBinaryString(green);
			  String blueBinary = Integer.toBinaryString(blue);
			  			  
			 String twoDigits = "";
			 if(redBinary.length() >= 8) {
				 twoDigits += redBinary.substring(0, 2);

			 }
			 else if(redBinary.length() == 7) {
				 twoDigits += "0" + redBinary.substring(0, 1);
			 }
			 else {
				 twoDigits += "00";
			 }
			 
			 if(greenBinary.length() >= 8) {
				 twoDigits += greenBinary.substring(0, 2);

			 }
			 else if(greenBinary.length() == 7) {
				 twoDigits += "0" + greenBinary.substring(0, 1);
			 }
			 else {
				 twoDigits += "00";
			 }
			 
			 if(blueBinary.length() >= 8) {
				 twoDigits += blueBinary.substring(0, 2);

			 }
			 else if(blueBinary.length() == 7) {
				 twoDigits += "0" + blueBinary.substring(0, 1);
			 }
			 else {
				 twoDigits += "00";
			 }
			 
			 double decimal = Integer.parseInt(twoDigits, 2);
			 
			 if(decimal >= 0 && decimal < 1) {
				  colorCodeMatrix[imageIndex][0]++;
			  }
			  else if(decimal < 2) {
				  colorCodeMatrix[imageIndex][1]++;
			  }
			  else if(decimal < 3) {
				  colorCodeMatrix[imageIndex][2]++;
			  }
			  else if(decimal < 4) {
				  colorCodeMatrix[imageIndex][3]++;
			  }
			  else if(decimal < 5) {
				  colorCodeMatrix[imageIndex][4]++;
			  }
			  else if(decimal < 6) {
				  colorCodeMatrix[imageIndex][5]++;
			  }
			  else if(decimal < 7) {
				  colorCodeMatrix[imageIndex][6]++;
			  }
			  else if(decimal < 8) {
				  colorCodeMatrix[imageIndex][7]++;
			  }
			  else if(decimal < 9) {
				  colorCodeMatrix[imageIndex][8]++;
			  }
			  else if(decimal < 10) {
				  colorCodeMatrix[imageIndex][9]++;
			  }
			  else if(decimal < 11) {
				  colorCodeMatrix[imageIndex][10]++;
			  }
			  else if(decimal < 12) {
				  colorCodeMatrix[imageIndex][11]++;
			  }
			  else if(decimal < 13) {
				  colorCodeMatrix[imageIndex][12]++;
			  }
			  else if(decimal < 14) {
				  colorCodeMatrix[imageIndex][13]++;
			  }
			  else if(decimal < 15) {
				  colorCodeMatrix[imageIndex][14]++;
			  }
			  else if(decimal < 16) {
				  colorCodeMatrix[imageIndex][15]++;
			  }
			  else if(decimal < 17) {
				  colorCodeMatrix[imageIndex][16]++;
			  }
			  else if(decimal < 18) {
				  colorCodeMatrix[imageIndex][17]++; 
			  }
			  else if(decimal < 19) {
				  colorCodeMatrix[imageIndex][18]++;
			  }
			  else if(decimal < 20) {
				  colorCodeMatrix[imageIndex][19]++;
			  }
			  else if(decimal < 21) {
				  colorCodeMatrix[imageIndex][20]++;
			  }
			  else if(decimal < 22) {
				  colorCodeMatrix[imageIndex][21]++;
			  }
			  else if(decimal < 23) {
				  colorCodeMatrix[imageIndex][22]++;
			  }
			  else if(decimal < 24) {
				  colorCodeMatrix[imageIndex][23]++;
			  } 
			  else if(decimal < 25) {
				  colorCodeMatrix[imageIndex][24]++;
			  } 
			  else if(decimal < 26) {
				  colorCodeMatrix[imageIndex][25]++;
			  } 
			  else if(decimal < 27) {
				  colorCodeMatrix[imageIndex][26]++;
			  } 
			  else if(decimal < 28) {
				  colorCodeMatrix[imageIndex][27]++;
			  } 
			  else if(decimal < 29) {
				  colorCodeMatrix[imageIndex][28]++;
			  } 
			  else if(decimal < 30) {
				  colorCodeMatrix[imageIndex][29]++;
			  } 
			  else if(decimal < 31) {
				  colorCodeMatrix[imageIndex][30]++;
			  } 
			  else if(decimal < 32) {
				  colorCodeMatrix[imageIndex][31]++;
			  } 
			  else if(decimal < 33) {
				  colorCodeMatrix[imageIndex][32]++;
			  } 
			  else if(decimal < 34) {
				  colorCodeMatrix[imageIndex][33]++;
			  } 
			  else if(decimal < 35) {
				  colorCodeMatrix[imageIndex][34]++;
			  } 
			  else if(decimal < 36) {
				  colorCodeMatrix[imageIndex][35]++;
			  } 
			  else if(decimal < 37) {
				  colorCodeMatrix[imageIndex][36]++;
			  } 
			  else if(decimal < 38) {
				  colorCodeMatrix[imageIndex][37]++;
			  } 
			  else if(decimal < 39) {
				  colorCodeMatrix[imageIndex][38]++;
			  } 
			  else if(decimal < 40) {
				  colorCodeMatrix[imageIndex][39]++;
			  } 
			  else if(decimal < 41) {
				  colorCodeMatrix[imageIndex][40]++;
			  } 
			  else if(decimal < 42) {
				  colorCodeMatrix[imageIndex][41]++;
			  } 
			  else if(decimal < 43) {
				  colorCodeMatrix[imageIndex][42]++;
			  } 
			  else if(decimal < 44) {
				  colorCodeMatrix[imageIndex][43]++;
			  } 
			  else if(decimal < 45) {
				  colorCodeMatrix[imageIndex][44]++;
			  } 
			  else if(decimal < 46) {
				  colorCodeMatrix[imageIndex][45]++;
			  } 
			  else if(decimal < 47) {
				  colorCodeMatrix[imageIndex][46]++;
			  } 
			  else if(decimal < 48) {
				  colorCodeMatrix[imageIndex][47]++;
			  } 
			  else if(decimal < 49) {
				  colorCodeMatrix[imageIndex][48]++;
			  } 
			  else if(decimal < 50) {
				  colorCodeMatrix[imageIndex][49]++;
			  } 
			  else if(decimal < 51) {
				  colorCodeMatrix[imageIndex][50]++;
			  } 
			  else if(decimal < 52) {
				  colorCodeMatrix[imageIndex][51]++;
			  } 
			  else if(decimal < 53) {
				  colorCodeMatrix[imageIndex][52]++;
			  } 
			  else if(decimal < 54) {
				  colorCodeMatrix[imageIndex][53]++;
			  } 
			  else if(decimal < 55) {
				  colorCodeMatrix[imageIndex][54]++;
			  } 
			  else if(decimal < 56) {
				  colorCodeMatrix[imageIndex][55]++;
			  } 
			  else if(decimal < 57) {
				  colorCodeMatrix[imageIndex][56]++;
			  } 
			  else if(decimal < 58) {
				  colorCodeMatrix[imageIndex][57]++;
			  } 
			  else if(decimal < 59) {
				  colorCodeMatrix[imageIndex][58]++;
			  } 
			  else if(decimal < 60) {
				  colorCodeMatrix[imageIndex][59]++;
			  } 
			  else if(decimal < 61) {
				  colorCodeMatrix[imageIndex][60]++;
			  } 
			  else if(decimal < 62) {
				  colorCodeMatrix[imageIndex][61]++;
			  } 
			  else if(decimal < 63) {
				  colorCodeMatrix[imageIndex][62]++;
			  } 
			  else if(decimal < 64) {
				  colorCodeMatrix[imageIndex][63]++;
			  } 
			  else if(decimal < 65) {
				  colorCodeMatrix[imageIndex][64]++;
			  } 
		  }
	  }
  }
 
  
  //This method writes the contents of the colorCode matrix to a file named colorCodes.txt.
  
  public void writeColorCode(){
	  PrintWriter outputStream = null;
	  try {
		outputStream = new PrintWriter(new FileOutputStream("C:\\College\\CSS484\\colorCodes.txt")); //needs to change to graders path
	} catch (FileNotFoundException e) {
		System.out.println("colorCodes.txt not found");
	}
	      
	  for(int i = 0; i < 100; i++) {
		  outputStream.println();
		  for(int j =0; j < 64; j++) {
			  outputStream.print(colorCodeMatrix[i][j] + " ");
		  }
	  }
	  	  outputStream.close(); 
  }
  
  //This method writes the contents of the intensity matrix to a file called intensity.txt
  public void writeIntensity(){
	  
	  PrintWriter outputStream = null;
	  try {
		outputStream = new PrintWriter(new FileOutputStream("C:\\College\\CSS484\\intensityCodes.txt")); //needs to change to graders path
	} catch (FileNotFoundException e) {
		System.out.println("intensity.txt not found");
	}
	  
	  
	  for(int i = 0; i < 100; i++) {
		  outputStream.println();
		  for(int j =0; j < 25; j++) {
			  outputStream.print(intensityMatrix[i][j] + " ");
		  }
	  }	  
	  outputStream.close();
  }
  
  /*public static void main(String[] args)
  {
    new readImage();
  }*/

}
