/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storygenerator;

/**
 *
 * @author Lindsey Harris
 */
import java.util.Random;

public class Selector {
    
    double sum;
    Random randomGenerator;
    
    public Selector() {
         randomGenerator = new Random();
         sum = 0.0;
    }
    
    public String selectOption(Choice choice) {
        sum = choice.getProbability(0);
        int randomInt = randomGenerator.nextInt(1000); // Get number in range 0 - 999
        if(randomInt <= sum*1000) {
            return choice.getOption(0);
       }
       else {
            int i = 0;
            sum = 0.0;
            while (((sum * 1000) < randomInt)&&(i<choice.getNumOptions())) {
                sum += choice.getProbability(i);
                i++;
            }
            return choice.getOption(i-1);
        }
    }
}
