package org.example;
import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    public static void main(String [] ars){
        String fromDir = "/home/nicholas/IdeaProjects/PicturesResizer/from";
        String toDir = "/home/nicholas/IdeaProjects/PicturesResizer/to";

        int newWidth = 300;
        File[] files = new File(fromDir).listFiles();
        int coresNumber = Runtime.getRuntime().availableProcessors();
        int nofost = files.length/coresNumber;

        long start = System.currentTimeMillis();

        System.out.println("Running program on " + coresNumber + " cores!");
//        System.out.println(nofost + " files on single thread!");
//        System.out.println(files.length + " files in total!");
//        System.out.println((files.length - nofost*coresNumber) + " files in plus!");

        //Dividing files array
        File[][] dividedFiles = new File[coresNumber][];

        for(int i=0; i<coresNumber-1; i++){
            dividedFiles[i] = new File[nofost];
            System.arraycopy(files, i*nofost, dividedFiles[i], 0, dividedFiles[i].length);
        }

        dividedFiles[coresNumber-1] = new File[nofost + (files.length - nofost*coresNumber)];
        System.arraycopy(files, (coresNumber-1)*nofost, dividedFiles[coresNumber-1], 0, dividedFiles[coresNumber-1].length);


//        System.out.println("\t\tChecking:");
//        for(int i=0; i<coresNumber; i++){
//            System.out.println("Array " + i);
//            System.out.println("\tlength" + dividedFiles[i].length);
//            Arrays.asList(dividedFiles[i]).forEach((a)-> System.out.println("\t" + a.getName()));
//        }

        for (int i = 0; i < coresNumber; i++) {
            new Thread(new ImageResizer(dividedFiles[i], toDir, newWidth, start, i)).start();
        }

    }
}