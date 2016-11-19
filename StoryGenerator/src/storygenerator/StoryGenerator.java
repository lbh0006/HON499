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
    public static String storyText;
    public static ProbabilityTree<Scene> sceneMap;
    Character protagonist;
    
    public StoryGenerator() {
        storyText = "";
    }
    
    public static void main(String[] args) {
        
        sceneMap = setupDemo(); 
        GUIWindow gui = new GUIWindow();
    }
    
    public static String getText()
    {
        return storyText;
    }
    
    public static void generateStory(ProbabilityTree<Scene> tree)
    {
        ProbabilityNode<Scene> temp = tree.root;
        storyText = temp.nodeObject.sceneText;
        while(temp.hasChildren())
        {
            temp = tree.selectNodeChild(temp);
            System.out.print(temp.nodeObject.sceneName+"\n");
            storyText = storyText+temp.nodeObject.sceneText;
        }
        System.out.print(storyText);
    }
    
    public static ProbabilityTree<Scene> setupDemo() {
        Scene rootScene = new Scene("Talk to King",
                "     Once upon a time, in a faraway kingdom, there was a sickness in the land. The king’s sister, who\n"+
                "was loved by all, was one of the many who became ill, and much to the sorrow of the kingdom, she\n"+
                "passed away. She had a son, whom the king adored, but who had not yet begun working on a trade,\n"+
                "so the king decided to take him under his wing.\n\n"+
                "The king summoned him into the throne room and told him:\n\n"+
                "     ‘You are growing older, and have still not decided what you are going to do with your life. It is time\n"+
                "     you learned a trade of some sort, and here, today, I have summoned the Captain of the Guard and a\n"+
                "     talented Minstrel of the King’s court for you to talk to. See if you would like to study under one of them.\n"+
                "     If so, you can become something great, but if you do not find something to do here, you will have to\n"+
                "     leave and make a living elsewhere, so let me know your decision by the end of the day.’\n\n"+
                "And with that the king dismissed him from his presence.\n\n"
                );
        Scene talkMinstrelScene = new Scene("Talk to Minstrel",
                "     The boy went to the Minstrel, who showed him a flute and a lyre. He told him about what a minstrel’s\n"+
                "life was like-- attending parties and events and providing entertainment for the king. He offered to teach\n"+
                "him the lyre.\n\n");
        Scene talkKnightScene = new Scene("Talk to Knight",
                "talk knight\n");
        Scene talkPrincessScene = new Scene("Talk to Princess",
                "talk princess\n");
        Scene playInstrumentScene = new Scene("Play Instrument",
                "The boy thought the lyre looked interesting, and so asked if he could try it. The minstrel handed it over,\n"+
                "and showed him how to play a song. He said,\n\n"+
                "     ‘If you keep practicing, then soon, you should be able to perform with me at some of the king’s events.’\n\n");
        Scene chooseMinstrelScene = new Scene("Choose Minstrel",
                "     After thinking about it for a little while, the boy decided that the life of a minstrel sounded like the life he\n"+
                "would like to have, so he went to the king and told him he would like to be a minstrel. The king\n"+
                "congratulated him on his decision and welcomed him into his court.\n\nThe End.\n\n");
        Scene trainScene = new Scene("Train",
                "train\n");
        Scene chooseKnightScene = new Scene("Choose Knight",
                "choose knight\n");
        Scene romanceScene = new Scene("Romance Princess",
                "romance princess\n");
        Scene proposeScene = new Scene("Propose to Princess",
                "propose to princess\n");
        
        //Root
        ProbabilityNode<Scene> rootNode  = 
                new ProbabilityNode(rootScene, 1);
        
        //Level 1
        ProbabilityNode<Scene> minstrelNode1  = 
                new ProbabilityNode(talkMinstrelScene,0.3333333);
        ProbabilityNode<Scene> knightNode1  = 
                new ProbabilityNode(talkKnightScene,    0.3333333);
        ProbabilityNode<Scene> princessNode1 = 
                new ProbabilityNode(talkPrincessScene, 0.3333333);
        
        //Level 2
        ProbabilityNode<Scene> minstrelNode2  = 
                new ProbabilityNode(talkMinstrelScene, 0.25, 0.03, 1.0);
        ProbabilityNode<Scene> knightNode2  = 
                new ProbabilityNode(talkKnightScene, 0.25, 0.03, 1.0);
        ProbabilityNode<Scene> princessNode2 = 
                new ProbabilityNode(talkPrincessScene, 0.25, 0.03, 1.0);
        ProbabilityNode<Scene> playNode  = 
                new ProbabilityNode(playInstrumentScene, 0.25, 0.03, 1.0);
        ProbabilityNode<Scene> chooseMinstrelNode = 
                new ProbabilityNode(chooseMinstrelScene, 0.25, 0.03, 1.0);
        ProbabilityNode<Scene> trainNode =
                new ProbabilityNode(trainScene, 0.25, 0.03, 1.0);
        ProbabilityNode<Scene> chooseKnightNode = 
                new ProbabilityNode(chooseKnightScene, 0.25, 0.03, 1.0);
        ProbabilityNode<Scene> romanceNode =
                new ProbabilityNode(romanceScene, 0.25, 0.03, 1.0);
        ProbabilityNode<Scene> proposeNode =
                new ProbabilityNode(proposeScene, 0.25, 0.03, 1.0);
        
        //Build Tree from the bottom up
        // Level 4
        minstrelNode2.addChild(playNode);
        minstrelNode2.addChild(knightNode2);
        minstrelNode2.addChild(princessNode2);
        minstrelNode2.addChild(chooseMinstrelNode);
        
        knightNode2.addChild(trainNode);
        knightNode2.addChild(minstrelNode2);
        knightNode2.addChild(princessNode2);
        knightNode2.addChild(chooseKnightNode);
        
        princessNode2.addChild(romanceNode);
        princessNode2.addChild(knightNode2);
        princessNode2.addChild(minstrelNode2);
        princessNode2.addChild(proposeNode);
        
        // Level 3
        playNode.addChild(knightNode2);
        playNode.addChild(princessNode2);
        playNode.addChild(chooseMinstrelNode);
        playNode.addChild(playNode); //Add recursive element
        
        trainNode.addChild(princessNode2);
        trainNode.addChild(minstrelNode2);
        trainNode.addChild(chooseKnightNode);
        trainNode.addChild(trainNode); //Add recursive element
        
        romanceNode.addChild(minstrelNode2);
        romanceNode.addChild(knightNode2);
        romanceNode.addChild(proposeNode);
        romanceNode.addChild(romanceNode); //Add recursive element
        
        // Level 2
        minstrelNode1.addChild(playNode);
        minstrelNode1.addChild(knightNode2);
        minstrelNode1.addChild(princessNode2);
        minstrelNode1.addChild(chooseMinstrelNode);
        
        knightNode1.addChild(trainNode);
        knightNode1.addChild(minstrelNode2);
        knightNode1.addChild(princessNode2);
        knightNode1.addChild(chooseKnightNode);
        
        princessNode1.addChild(romanceNode);
        princessNode1.addChild(knightNode2);
        princessNode1.addChild(minstrelNode2);
        princessNode1.addChild(proposeNode);
        
        // Level 1
        rootNode.addChild(minstrelNode1);
        rootNode.addChild(knightNode1);
        rootNode.addChild(princessNode1);
        
        ProbabilityTree<Scene> demoTree = new ProbabilityTree(rootNode);
        return demoTree;
    }
}