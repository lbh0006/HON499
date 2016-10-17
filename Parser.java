/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storygenerator;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Lindsey Harris
 */
public class Parser {
    // Singleton
    private static Parser parser = null;

    private Parser() {};

    public static Parser getParser() {
        if (parser == null) {
            parser = new Parser();
        }
        return parser;
    }
    
    public void readFile(String filename) {
        
        InputStream instream = StoryGenerator.class.getResourceAsStream(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
        
        try {
            String line = reader.readLine(); // Get Root data
            List<String> dataList = Arrays.asList(line.split(","));
            
            //place variables in correct variable based on position
            String sceneName = dataList.get(0);
            String sceneText = dataList.get(1);
            double probability = Double.parseDouble(dataList.get(2));
            double lowBound = Double.parseDouble(dataList.get(3));
            double hiBound = Double.parseDouble(dataList.get(4));
            
            Scene s = new Scene(sceneName,sceneText);
            ProbabilityNode<Scene> root = new ProbabilityNode<Scene>(s,
                                                                     probability,
                                                                     lowBound,
                                                                     hiBound);
            
            while ((line = reader.readLine()) != null) {
                dataList = Arrays.asList(line.split(","));

                //place variables in correct variable based on position
                sceneText = dataList.get(2);
                probability = Double.parseDouble(dataList.get(3));
                lowBound = Double.parseDouble(dataList.get(4));
                hiBound = Double.parseDouble(dataList.get(5));
                
                ProbabilityTree<Scene> tree = new ProbabilityTree<Scene>(root);

            }
            reader.close();
        } //Catch any file read errors
        catch (IOException IO) {

        }
    }
}
