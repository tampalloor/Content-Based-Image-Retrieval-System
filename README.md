# Content-Based-Image-Retrieval-System
This project is to implment a simple Content-Based Image Retrieval system based on two
different color histogram comparison methods. 

1. Test Image Database
This test image database includes 100 true-color images in .jpg format.

2. Color Histogram
Color histogram comparison is a simple but effective apporach in CBIR systems.
Here are two ways to combine the information from 3 color channels (R, G, B):

  A. Intensity Method
  By this way, the 24-bit of RGB (8 bits for each color channel) color intensities
  can be transformed into a single 8-bit value. The histogram bins selected for
  this case are listed below:
  ![image](https://github.com/tampalloor/Content-Based-Image-Retrieval-System/assets/63935525/91252284-bfec-410f-b484-25fbd957b0f5)

  B. Color-Code Method
  The 24-bit of RGB color intensities can be transformed into a 6-bit color code,
  composed from the most significant 2 bits of each of the three color
  components, as illustrated in the following figure.
  ![image](https://github.com/tampalloor/Content-Based-Image-Retrieval-System/assets/63935525/a057fe2a-26ae-426f-87b7-3418989d73d1)
The 6-bit color code will provide 64 histogram bins.

For example, the R, G, and B values for a pixel are 128, 0, and 255 respectively. So the
bit representations of them are 10000000, 00000000, and 11111111. Then the 6-bit color
code value will be 100011.
In color code, there will be 64 bins with H1: 000000, H2: 000001, H3: 000010, … H64:
111111

3. Histogram Comparison
Implemented the distance metrics for histogram comparison. Hi(j)
denotes the number of pixels in jth bin for the ith image. Then the difference between
the i
th image and kth image can be given by the following distance metric:
![image](https://github.com/tampalloor/Content-Based-Image-Retrieval-System/assets/63935525/6c81b332-7cbe-4c7c-8cf9-c42b61609ab8)
where Mi*Ni is the number of pixels in image i, and Mk*Nk is the number of pixels in
image k.
