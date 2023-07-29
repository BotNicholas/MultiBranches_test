package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable{

    private File[] files;
    private  String toDir;
    private int newWidth;
    private long start;
    private int threadNumber;

    public ImageResizer(File[] files, String toDir, int newWidth, long start, int threadNumber) {
        this.files = files;
        this.toDir = toDir;
        this.newWidth = newWidth;
        this.start = start;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        System.out.println("\tStarting " + threadNumber + " thread!");
        try {
            if (files != null) {
                for (File file : files) {
                    if(file.isFile()){
                        BufferedImage image = ImageIO.read(file);

                        if(image != null) {
                            int newHeight = Math.round(image.getHeight() / (image.getWidth() / (float) newWidth));

                            BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                            int widthStep = image.getWidth() / newWidth;
                            int heightStep = image.getHeight() / newHeight;

                            for (int i = 0; i < newWidth; i++) {
                                for (int j = 0; j < newHeight; j++) {
                                    //System.out.print(image.getRGB(j, i) + " ");
                                    int rgb = image.getRGB(i * widthStep, j * heightStep);
                                    newImage.setRGB(i, j, rgb);
                                }
                            }

                            ImageIO.write(newImage, "jpeg", new File(toDir + "/" + file.getName()));
                        }
                    }
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }

        System.out.println("Thread " + threadNumber + " finished in " + (System.currentTimeMillis()-start) + "ms"); //Shows, that threads are working parallel!
    }
}