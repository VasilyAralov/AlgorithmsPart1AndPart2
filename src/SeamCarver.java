import java.awt.Color;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

 private class Pixel implements Comparable<Pixel> {
  
  private int x;
  private int y;
  private double minSeamEnergy;
  
  private Pixel(int x, int y) {
   this.x = x;
   this.y = y;
  }
  
  @Override
  public int compareTo(Pixel o) {
   if (this.minSeamEnergy < o.minSeamEnergy) {
    return -1;
   }
   if (this.minSeamEnergy > o.minSeamEnergy) {
    return 1;
   }
   if (this.x > o.x) {
    return -1;
   }
   if (this.x < o.x) {
    return 1;
   }
   return 0;
  }
  
 }
 
 private int[][] picture;
 
 public SeamCarver(Picture picture) {
  // create a seam carver object based on the given picture
  this.picture = new int[picture.width()][picture.height()];
  for (int x = 0; x < picture.width(); x++) {
   for (int y = 0; y < picture.height(); y++) {
    this.picture[x][y] = picture.get(x, y).getRGB();
   }
  }
 }

 public Picture picture() {
  // current picture
  Picture newPicture = new Picture(picture.length, picture[0].length);
  for (int i = 0; i < newPicture.width(); i++) {
   for (int j = 0; j < newPicture.height(); j++) {
    newPicture.set(i, j, new Color(picture[i][j]));
   }
  }
  return newPicture;
 }

 public int width() {
  // width of current picture
  return picture.length;
 }

 public int height() {
  // height of current picture
  return picture[0].length;
 }

 public double energy(int x, int y) {
  // energy of pixel at column x and row y
  if (x < 0 || x >= width() || y < 0 || y >= height()) {
   throw new IndexOutOfBoundsException();
  }
  return computeEnergy(x, y);
 }
 
 private double computeEnergy(int x, int y) {
  if (x == 0 || x == (width() -1) || y == 0 || y == (height() - 1)) {
   return 195075.0;
  }
  double energyX = getEnergy(picture[x - 1][y], picture[x + 1][y]);
  double energyY = getEnergy(picture[x][y - 1], picture[x][y + 1]);
  return energyX + energyY;
 }

 private double getEnergy(int rgbColor, int rgbColor2) {
  double redEnergy = colorEnergy(getRed(rgbColor), getRed(rgbColor2));
  double greenEnergy = colorEnergy(getGreen(rgbColor), getGreen(rgbColor2));
  double blueEnergy = colorEnergy(getBlue(rgbColor), getBlue(rgbColor2));
  return redEnergy + greenEnergy + blueEnergy;
 }
 
 private int getRed(int rgbColor) {
  return (rgbColor >> 16) & 0xFF;
 }

 private int getGreen(int rgbColor) {
  return (rgbColor >> 8) & 0xFF;
 }
 
 private int getBlue(int rgbColor) {
  return (rgbColor >> 0) & 0xFF;
 }
 
 private double colorEnergy(int ec1, int ec2) {
  int dif = ec1 - ec2;
  return dif * dif;
 }

 public int[] findVerticalSeam() {
  // sequence of indices for vertical seam
  if ((width() <= 2) || (height() <= 2)) {
   int[] path = new int[height()];
   for (int i = 0; i < height(); i++) {
    path[i] = 0;
   }
   return path;
  }
  return getShortPath(true);
 }

 public int[] findHorizontalSeam() {
  // sequence of indices for horizontal seam
  if ((width() <= 2) || (height() <= 2)) {
   int[] path = new int[width()];
   for (int i = 0; i < width(); i++) {
    path[i] = 0;
   }
   return path;
  }
  return getShortPath(false);
 }
 
 private int[] getShortPath(boolean isTranspose) {
  MinPQ<Pixel> shortPath = new MinPQ<Pixel>();
  double[][] pahtEnergy;
  if (isTranspose) {
   pahtEnergy = new double[height()][width()];
  } else {
   pahtEnergy = new double[width()][height()];
  }
  int[][] minPath = new int[pahtEnergy.length][pahtEnergy[0].length];
  for (int i = 0; i < pahtEnergy.length; i++) {
   for (int j = 0; j < pahtEnergy[0].length; j++) {
    pahtEnergy[i][j] = Integer.MAX_VALUE;
    minPath[i][j] = Integer.MIN_VALUE;
   }
  }
  for (int j = 1; j < pahtEnergy[0].length - 1; j++) {
   Pixel pixel = new Pixel(1, j);
   if (isTranspose) {
    pahtEnergy[1][j] = energy(j, 1);
   } else {
    pahtEnergy[1][j] = energy(1, j);
   }
   pixel.minSeamEnergy = pahtEnergy[1][j];
   shortPath.insert(pixel);
  }
  Pixel lastPixel = shortPath.delMin();
  while (lastPixel.x < pahtEnergy.length - 1) {
   if (lastPixel.y > 1) {
    Pixel newPixel = new Pixel(lastPixel.x + 1, lastPixel.y - 1);
    newPixel.minSeamEnergy = lastPixel.minSeamEnergy + getPixelEnergy(newPixel, isTranspose);
    if (pahtEnergy[newPixel.x][newPixel.y] > newPixel.minSeamEnergy) {
     pahtEnergy[newPixel.x][newPixel.y] = newPixel.minSeamEnergy;
     minPath[newPixel.x][newPixel.y] = lastPixel.y;
     shortPath.insert(newPixel);
    }
   }
   if (lastPixel.y < pahtEnergy[0].length - 2) {
    Pixel newPixel = new Pixel(lastPixel.x + 1, lastPixel.y + 1);
    newPixel.minSeamEnergy = lastPixel.minSeamEnergy + getPixelEnergy(newPixel, isTranspose);
    if (pahtEnergy[newPixel.x][newPixel.y] > newPixel.minSeamEnergy) {
     pahtEnergy[newPixel.x][newPixel.y] = newPixel.minSeamEnergy;
     minPath[newPixel.x][newPixel.y] = lastPixel.y;
     shortPath.insert(newPixel);
    }
   }
   Pixel newPixel = new Pixel(lastPixel.x + 1, lastPixel.y);
   newPixel.minSeamEnergy = lastPixel.minSeamEnergy + getPixelEnergy(newPixel, isTranspose);
   if (pahtEnergy[newPixel.x][newPixel.y] > newPixel.minSeamEnergy) {
    pahtEnergy[newPixel.x][newPixel.y] = newPixel.minSeamEnergy;
    minPath[newPixel.x][newPixel.y] = lastPixel.y;
    shortPath.insert(newPixel);
   }
   lastPixel = shortPath.delMin();
  }
  int[] path = new int[pahtEnergy.length];
  path[pahtEnergy.length - 1] = lastPixel.y;
  for (int i = pahtEnergy.length - 1; i > 1; i--) {
   path[i - 1] = minPath[i][path[i]];
  }
  path[0] = path[1] - 1;
  return path;
 }

 
 private double getPixelEnergy(Pixel pixel, boolean isTranspose) {
  if (isTranspose) {
   return energy(pixel.y, pixel.x);
  }
  return energy(pixel.x, pixel.y);
 }
 
 public void removeHorizontalSeam(int[] seam) {
  // remove horizontal seam from current picture
  if (seam == null) {
   throw new NullPointerException();
  }
  if (seam.length != width()) {
   throw new IllegalArgumentException();
  }
  int[][] newPicture = new int[width()][height() - 1];
  for (int i = 0; i < newPicture.length; i++) {
   if (i != 0) {
    int dif = seam[i] - seam[i - 1];
    if ((dif > 1) || (dif < -1)) {
     throw new IllegalArgumentException();
    }
   }
   int iter = 0;
   for (int j = 0; j < newPicture[0].length; j++) {
    if (seam[i] == j) {
     iter++;
    }
    newPicture[i][j] = picture[i][j + iter];
   }
  }
  picture = newPicture;
 }

 public void removeVerticalSeam(int[] seam) {
  // remove vertical seam from current picture
  if (seam == null) {
   throw new NullPointerException();
  }
  if (seam.length != height()) {
   throw new IllegalArgumentException();
  }
  int[][] newPicture = new int[width() - 1][height()];
  for (int i = 0; i < newPicture[0].length; i++) {
   if (i != 0) {
    int dif = seam[i] - seam[i - 1];
    if ((dif > 1) || (dif < -1)) {
     throw new IllegalArgumentException();
    }
   }
   int iter = 0;
   for (int j = 0; j < newPicture.length; j++) {
    if (seam[i] == j) {
     iter++;
    }
    newPicture[j][i] = picture[j + iter][i];
   }
  }
  picture = newPicture;
 }
 
}