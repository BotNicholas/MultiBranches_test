package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String [] ars){
        String fromDir = "/home/nicholas/IdeaProjects/PicturesResizer/from";
        String toDir = "/home/nicholas/IdeaProjects/PicturesResizer/to";

        int newWidth = 300;
        long start = System.currentTimeMillis();

        File[] files = new File(fromDir).listFiles();

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

        System.out.println("Finished in " + (System.currentTimeMillis()-start) + "ms");
    }
}