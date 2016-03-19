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
public class Scene {
    String sceneText;
    String sceneName;
    
    public Scene(String scene_name, String scene_text) {
        this.sceneText = scene_text;
        this.sceneName = scene_name;
    }
    
    public void displaySceneText() {
        System.out.print(sceneText);
    }
    
    public String getSceneText() {
        return sceneText;
    }

    public String getSceneName() {
        return sceneName;
    }
    
    public void displaySceneName() {
        System.out.print(sceneName);
    }
}
