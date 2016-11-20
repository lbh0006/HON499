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
        sceneMap = setupDemo();
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
        Scene talkMinstrelScene1 = new Scene("Talk to Minstrel",
                "     The boy went to the Minstrel, who showed him a flute and a lyre. He told him about what a minstrel’s\n"+
                "life was like-- attending parties and events and providing entertainment for the king. He offered to teach\n"+
                "him the lyre.\n\n");
        Scene talkMinstrelScene2 = new Scene("Talk to Minstrel",
                "     The boy went to the Minstrel, who showed him a song on the lyre.\n\n");
        Scene talkKnightScene2 = new Scene("Talk to Knight",
                "     The boy went to the Captain of the Guard, who demonstrated some of the basic training techniques, telling \n"+
                "him that if he trained hard, he could join the forces to fight for the king.\n\n");
        Scene talkKnightScene1 = new Scene("Talk to Knight",
                "     The boy went to the Captain of the Guard, who showed him his sword and shield. He demonstrated some of\n"+
                "the basic training techniques, and spoke of the glory that came with being a knight and defending the kingdom.\n"+
                "Then he told him if he trained hard, he could join the forces to fight for the king.\n\n");
        Scene talkPrincessScene = new Scene("Talk to Princess",
                "The boy noticed the beautiful princess visiting the king's court, so he went to talk to her. She had a lovely\n"+
                "disposition and seemed happy to talk to him.\n\n");
        Scene playInstrumentScene = new Scene("Play Instrument",
                "The boy thought the lyre looked interesting, and so asked if he could try it. The minstrel handed it over,\n"+
                "and showed him how to play a song. He said,\n\n"+
                "     ‘If you keep practicing, then soon, you should be able to perform with me at some of the king’s events.’\n\n");
        Scene chooseMinstrelScene = new Scene("Choose Minstrel",
                "     After thinking about it for a little while, the boy decided that the life of a minstrel sounded like the life he\n"+
                "would like to have, so he went to the king and told him he would like to be a minstrel. The king\n"+
                "congratulated him on his decision and welcomed him into his court.\n\nThe End.\n\n");
        Scene trainScene = new Scene("Train",
                "The boy decided to try some of the Captain's training techniques, and so spent some time practicing his combat skills.\n");
        Scene chooseKnightScene = new Scene("Choose Knight",
                "     After thinking about it for a while, the boy decided that defending the kingdom was a task he would be proud to\n"+
                "spend his life doing, so he went to the king and told him,\n\n"+
                "     'If it pleases your magesty, I would like to study under the Captain of the Guard to become a knight of the king's\n"+
                "      court.'\n\n"+
                "This pleased the king very much, and so he congratulated him on his decision and welcomed him into his guard.\n\nThe End.\n\n");
        Scene romanceScene = new Scene("Romance Princess",
                "They went walking and talked for a while. He complemented her frequently, speaking to her quite fondly.\n\n");
        Scene proposeScene = new Scene("Propose to Princess",
                "     The young man was quite happy with the princess, and thought of her highly. After much consideration, he asked her what she\n"+
                "thought of marriage. He told her that if she was pleased with him, he could not think of anyone that he would rather marry.\n"+
                "Flattered, she thought for a moment, and pleased with the proposition, accepted his proposal--under condition that it pleased the\n"+
                "king. So the young man went to the king, and told him that he would like to settle down and that if it pleased the king, he wished\n"+
                "to marry the beautiful princess who was visiting the king's court. The king was pleased at this news, and congratulated them both.\n"+
                "So the two were married, and the king gave them land, making the young man a lord over a large area of the kingdom, where the two\n"+
                "of them lived happily for many years.\n\nThe End.\n\n");
        
        //Root
        ProbabilityNode<Scene> rootNode  = 
                new ProbabilityNode(rootScene, 1);
        
        //Level 1
        ProbabilityNode<Scene> minstrelNode1  = 
                new ProbabilityNode(talkMinstrelScene1,0.3333333);
        ProbabilityNode<Scene> knightNode1  = 
                new ProbabilityNode(talkKnightScene1,    0.3333333);
        ProbabilityNode<Scene> princessNode1 = 
                new ProbabilityNode(talkPrincessScene, 0.3333333);
        
        //Level 2
        ProbabilityNode<Scene> minstrelNode2  = 
                new ProbabilityNode(talkMinstrelScene1, 0.25, 0.03, 1.0);
        ProbabilityNode<Scene> knightNode2  = 
                new ProbabilityNode(talkKnightScene1, 0.25, 0.03, 1.0);
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