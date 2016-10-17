/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storygenerator;
import StoryGeneratorGUI.GUIWindow;
/**
 *
 * @author Lindsey Harris
 */

public class StoryGenerator {
    ProbabilityTree<Scene> sceneMap;
    Character protagonist;
    
    public StoryGenerator() {
        
    }
    
    public static void main(String[] args) {
        GUIWindow gui = new GUIWindow();
        ApplicationTester t = new ApplicationTester();
        t.initialTestOfAdvProbabilityTree(0.3, 0.3, 0.3, 
                                          0.0002, 1.0,
                                          0.0002, 1.0,
                                          0.0002, 1.0);
    }
    
    
}