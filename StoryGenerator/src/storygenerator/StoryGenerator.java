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

public class StoryGenerator {
    ProbabilityTree<Scene> sceneMap;
    Character protagonist;
    
    public StoryGenerator() {
        
    }
    
    public static void main(String[] args) {
//        initialTestOfAdvProbabilityTree(0.3333333, 0.5, 0.0,
//                                        0.3333333, 0.5, 0.0,
//                                        0.3333334, 0.5, 0.0);
        repeatOneChoice();
    }
    
    public static void repeatOneChoice() {
        ProbabilityTree<Scene> repeatOneTree = repeatOneSetup();
        ProbabilityNode<Scene> temp = repeatOneTree.selectNodeChild(repeatOneTree.root);

        if(temp.getNodeObject() != null) {
            System.out.print(temp.getNodeObject().sceneName + "\n");
        }
        while(temp.hasChildren()) {
            temp = repeatOneTree.selectNodeChild(temp);
            System.out.print(temp.getNodeObject().sceneName + "\n");
        }
        
        
    }
    
    public static ProbabilityTree<Scene> repeatOneSetup() {
        Scene rootScene = new Scene("Stretch Legs",
                                    "You decide to stretch your legs. ");
        Scene child1    = new Scene("Talk to your co-worker. ",
                                    "You decide to talk to your Co-worker. ");
        Scene child2    = new Scene("Work. ",
                                    "You decide to get some work done. ");
        Scene child3    = new Scene("Phone. ",
                                    "You decide to make a phone call. ");
        Scene exitOp    = new Scene("Exit the Room. ",
                                    "You decide to exit the Room. ");
        
        ProbabilityNode<Scene> rootNode  = new ProbabilityNode(rootScene, 0.2);
        ProbabilityNode<Scene> talkNode  = new ProbabilityNode(child1,    0.39);
        ProbabilityNode<Scene> workNode  = new ProbabilityNode(child2,    0.2);
        ProbabilityNode<Scene> phoneNode = new ProbabilityNode(child3,    0.2);
        ProbabilityNode<Scene> exitNode  = new ProbabilityNode(exitOp,    0.01);
        
        talkNode.addChild(rootNode);
        talkNode.addChild(talkNode);
        talkNode.addChild(workNode);
        talkNode.addChild(phoneNode);
        talkNode.addChild(exitNode);
        
        workNode.addChild(rootNode);
        workNode.addChild(talkNode);
        workNode.addChild(workNode);
        workNode.addChild(phoneNode);
        workNode.addChild(exitNode);
        
        phoneNode.addChild(rootNode);
        phoneNode.addChild(talkNode);
        phoneNode.addChild(workNode);
        phoneNode.addChild(phoneNode);
        phoneNode.addChild(exitNode);
        
        rootNode.addChild(talkNode);
        rootNode.addChild(workNode);
        rootNode.addChild(phoneNode);
        rootNode.addChild(exitNode);
        rootNode.addChildAt(0,rootNode);
        
        ProbabilityTree<Scene> repeatOneTree = new ProbabilityTree(rootNode);
        return repeatOneTree;
    }
    
    public static void testTwoLevels(double outProb1, double ehProb1, 
                                     double inProb1, double outProb2,
                                     double ehProb2, double inProb2) {
        Scene rootScene = new Scene("root","This is the root.\n");
        Scene ehScene = new Scene("eh","This is the Eh? scene.\n");
        Scene InScene = new Scene("in","This is the In scene.\n");
        Scene OutScene = new Scene("out","This is the Out scene.\n");
        ProbabilityNode<Scene> Out1 = new ProbabilityNode(OutScene, outProb1);
        ProbabilityNode<Scene> eh1 = new ProbabilityNode(ehScene, ehProb1);
        ProbabilityNode<Scene> In1 = new ProbabilityNode(InScene, inProb1);
        ProbabilityNode<Scene> Out2 = new ProbabilityNode(OutScene, outProb2);
        ProbabilityNode<Scene> eh2 = new ProbabilityNode(ehScene, ehProb2);
        ProbabilityNode<Scene> In2 = new ProbabilityNode(InScene, inProb2);
        ProbabilityNode<Scene> root = new ProbabilityNode(rootScene,1.0);
        Out1.addChild(Out2);
        Out1.addChild(eh2);
        Out1.addChild(In2);
        In1.addChild(Out2);
        In1.addChild(eh2);
        In1.addChild(In2);
        eh1.addChild(Out2);
        eh1.addChild(eh2);
        eh1.addChild(In2);
        root.addChild(Out1);
        root.addChild(In1);
        root.addChildAt(1, eh1);
        ProbabilityTree<Scene> basicTree = new ProbabilityTree(root);
        ProbabilityNode<Scene> tempNode = new ProbabilityNode();
        ProbabilityNode<Scene> tempNode2 = new ProbabilityNode();
        
        int OutOutCount = 0;
        int OutEhCount = 0;
        int OutInCount = 0;
        int EhOutCount = 0;
        int EhEhCount = 0;
        int EhInCount = 0;
        int InOutCount = 0;
        int InEhCount = 0;
        int InInCount = 0;
        int error = 0;
        
        for(int i=0; i<10000; i++){
            tempNode = basicTree.selectNodeChild(root);
            tempNode2 = basicTree.selectNodeChild(tempNode);
            if(tempNode == In1) {
                if(tempNode2 == Out2) {
                    InOutCount++;
                }
                else if(tempNode2 == eh2) {
                    InEhCount++;
                }
                else if(tempNode2 == In2) {
                    InInCount++;
                }
                else {
                    error++;
                }
            }
            else if(tempNode == Out1) {
                if(tempNode2 == Out2) {
                    OutOutCount++;
                }
                else if(tempNode2 == eh2) {
                    OutEhCount++;
                }
                else if(tempNode2 == In2) {
                    OutInCount++;
                }
                else {
                    error++;
                }
            }
            else if(tempNode == eh1) {
                if(tempNode2 == Out2) {
                    EhOutCount++;
                }
                else if(tempNode2 == eh2) {
                    EhEhCount++;
                }
                else if(tempNode2 == In2) {
                    EhInCount++;
                }
                else {
                    error++;
                }
            }
            else {
                error++;
            }
        }
            
        System.out.print("Out-Out count: "+ OutOutCount +"\n");    
        System.out.print("Out-Eh count: "+ OutEhCount +"\n");    
        System.out.print("Out-In count: "+ OutInCount +"\n\n");
        
        System.out.print("Eh-Out count: "+ EhOutCount +"\n");    
        System.out.print("Eh-Eh count: "+ EhEhCount +"\n");    
        System.out.print("Eh-In count: "+ EhInCount +"\n\n");
            
        System.out.print("In-Out count: "+ InOutCount +"\n");
        System.out.print("In-Eh count: "+ InEhCount +"\n");
        System.out.print("In-In count: "+ InInCount +"\n\n");
        
        System.out.print("Error count: "+ error +"\n\n");
    }
        
    public static void initialTestOfProbabilityTree(double outProb, 
                                                    double ehProb, 
                                                    double inProb) 
    {
        Scene rootScene = new Scene("root","This is the root.\n");
        Scene ehScene = new Scene("eh","This is the Eh? scene.\n");
        Scene InScene = new Scene("in","This is the In scene.\n");
        Scene OutScene = new Scene("out","This is the Out scene.\n");
        ProbabilityNode<Scene> Out = new ProbabilityNode(OutScene, outProb);
        ProbabilityNode<Scene> eh = new ProbabilityNode(ehScene, ehProb);
        ProbabilityNode<Scene> In = new ProbabilityNode(InScene, inProb);
        ProbabilityNode<Scene> root = new ProbabilityNode(rootScene,1.0);
        root.addChild(Out);
        root.addChild(In);
        root.addChildAt(1, eh);
        ProbabilityTree<Scene> basicTree = new ProbabilityTree(root);
        ProbabilityNode<Scene> tempNode = new ProbabilityNode();
        
        int ehCount = 0;
        int inCount = 0;
        int outCount = 0;
        int error = 0;
        
        for(int i=0; i<10000; i++){
            tempNode = basicTree.selectNodeChild(root);
            if(tempNode == In) {
                inCount++;
            }
            else if(tempNode == Out) {
                outCount++;
            }
            else if(tempNode == eh) {
                ehCount++;
            }
            else {
                error++;
            }
        }
            
        System.out.print("Eh? count: "+ ehCount +"\n");
        System.out.print("In count: "+ inCount +"\n");
        System.out.print("Out count: "+ outCount +"\n");
        System.out.print("Error count: "+ error +"\n");
    }    
    
    public static void initialTestOfAdvProbabilityTree(double outProb,
                                                       double outUpB,
                                                       double outLoB,
                                                       double ehProb,
                                                       double ehUpB,
                                                       double ehLoB, 
                                                       double inProb,
                                                       double inUpB,
                                                       double inLoB) 
    {
        Scene rootScene = new Scene("root","This is the root.\n");
        Scene ehScene = new Scene("eh","This is the Eh? scene.\n");
        Scene InScene = new Scene("in","This is the In scene.\n");
        Scene OutScene = new Scene("out","This is the Out scene.\n");
        ProbabilityNode<Scene> Out = new ProbabilityNode(OutScene, 
                                                         outProb, 
                                                         outUpB, 
                                                         outLoB);
        ProbabilityNode<Scene> eh = new ProbabilityNode(ehScene, 
                                                        ehProb,
                                                        ehUpB,
                                                        ehLoB);
        ProbabilityNode<Scene> In = new ProbabilityNode(InScene, 
                                                        inProb,
                                                        inUpB,
                                                        inLoB);
        ProbabilityNode<Scene> root = new ProbabilityNode(rootScene,1.0);
        root.addChild(Out);
        root.addChild(In);
        root.addChildAt(1, eh);
        ProbabilityTree<Scene> basicTree = new ProbabilityTree(root);
        ProbabilityNode<Scene> tempNode = new ProbabilityNode();
        
        int ehCount = 0;
        int inCount = 0;
        int outCount = 0;
        int error = 0;
        
        for(int i=0; i<10000; i++){
            tempNode = basicTree.selectNodeChild(root);
            if(tempNode == In) {
                inCount++;
            }
            else if(tempNode == Out) {
                outCount++;
            }
            else if(tempNode == eh) {
                ehCount++;
            }
            else {
                error++;
            }
        }
            
        System.out.print("Eh? count: "+ ehCount +"\n");
        System.out.print("In count: "+ inCount +"\n");
        System.out.print("Out count: "+ outCount +"\n");
        System.out.print("Error count: "+ error +"\n");
    }
}