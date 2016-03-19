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
public class Choice {
    private int numOpt;
    private String[] options;
    private double[] probabilities;
    
    public Choice(int numOptions) {
        numOpt = numOptions;
        options = new String[numOptions];
        probabilities = new double[numOptions];
        for(int i=0; i<numOpt; i++) {
            options[i] = "";
            probabilities[i]= -1.0;
        }
    }
    
    public String getOption(int index) {
        return options[index];
    }
    
    public double getProbability(int index) {
        return probabilities[index];
    }
    
    public int getNumOptions() {
        return numOpt;
    }
    
    public void setChoice(int index, String option, double probability ) {
        options[index] = option;
        if (probability > 1) {
            probability = 1;
        }
        if (probability < 0) {
            probability = 0;
        }
        probabilities[index] = probability;
    }
    
    public void sortChoices() {
       boolean notSorted = true;
       //The ever unefficiant bubble sort - to be made more efficient if time allows...
       while(notSorted)
       {
           notSorted = false;
           double tempD;
           String tempS;
           for(int i = 0; i < numOpt-1; i++)
           {
               if(probabilities[i] > probabilities[i+1]) {
                   tempD = probabilities[i];
                   tempS = options[i];
                   probabilities[i] = probabilities[i+1];
                   options[i] = options[i+1];
                   probabilities[i+1] = tempD;
                   options[i+1] = tempS;
                   notSorted = true;
               }
           }
       }
    }

}

