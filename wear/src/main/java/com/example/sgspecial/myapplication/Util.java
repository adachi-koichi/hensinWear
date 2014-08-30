package com.example.sgspecial.myapplication;

import java.util.Random;

/**
 * Created by 1cochan on 2014/08/30.
 */
public class Util {
    private static Random rnd = new Random();

    public static int get0to9(){
        return rnd.nextInt(10);
    }
}
