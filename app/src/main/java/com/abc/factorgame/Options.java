package com.abc.factorgame;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class Options {


    public int getfactor(int number){

         Vector<Integer> vector_factor=new Vector<>();

       for(int i=1;i<=number;i++) {
           if (number % i == 0)
               vector_factor.add(i);

       }

       Collections.shuffle(vector_factor);

      int value=vector_factor.get(0);
        return value;
    }


    public int getOption(int number) {
        Vector<Integer> vector = new Vector<>();
for(int i=1; i<=number; i++) {
    if (number % i != 0)
        vector.add(i);
}
vector.add(0);
        Collections.shuffle(vector);
       int value=vector.get(0);
       return value;
    }


}
