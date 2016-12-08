package org.deeplearning4j.examples.feedforward.regression;

/**
 * Created by Ankur on 12/4/2016.
 */


public class App {

    public static void load(String name){
        try{
            System.out.println("Trying to load: "+name);
            System.loadLibrary(name);
        }catch (Throwable e){
            System.out.println("Failed: "+e.getMessage());
            return;
        }
        System.out.println("Success");
    }

    public static void main(String[] args) {
        load("libwinpthread-1");
        load("libstdc++-6");
        load("libquadmath-0");
        load("libopenblas");
        load("libgomp-1");
        load("jfree");
        load("nd4j");
    }
}
