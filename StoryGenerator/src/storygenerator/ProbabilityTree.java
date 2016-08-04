/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storygenerator;
import java.util.List;
import java.util.LinkedList;
/**
 *
 * @author Lindsey Harris
 */
import java.util.Random;

class ProbabilitySelector<T> 
{
    double sum;
    Random randomGenerator;
    
    public ProbabilitySelector() 
    {
        super();
        randomGenerator = new Random();
        sum = 0.0;
    }
    
    public ProbabilityNode<T> selectOption(ProbabilityNode<T> choice) 
    {
        if(!choice.hasChildren()) 
        {
            ProbabilityNode<T> nullNode = new ProbabilityNode();
            return nullNode;
        }
        double probSum = 0.0;
       
        for(int i = 0; i < choice.getNumChildren(); i++)
        {
            probSum += choice.getChildAt(i).getProbability();
        }
        
        if(probSum != 1.0) 
        {
            System.out.print("ERROR: Invalid Probability Setup\n\n");
            return null;
        }
        sum = choice.getChildAt(0).getProbability();
        int randomInt = randomGenerator.nextInt(1000); // Get number in range 0 - 999
        if(randomInt <= sum*1000) 
        {
            return choice.getChildAt(0);
        }
        else 
        {
            int i = 0;
            sum = 0.0;
            while (((sum * 1000) < randomInt)&&(i<choice.getNumChildren())) 
            {
                sum += choice.getChildAt(i).getProbability();
                i++;
            }
            return choice.getChildAt(i-1);
        }
    }
}

class AdvProbabilitySelector<T> 
{
    double sum;
    double upperBound;
    double lowerBound;
    Random randomGenerator;
    
    public AdvProbabilitySelector() 
    {
        super();
        this.randomGenerator = new Random();
        this.sum = 0.0;
        this.upperBound = 1.0;
        this.lowerBound = 0.0;
    }
    
    public ProbabilityNode<T> selectOption(ProbabilityNode<T> choice) 
    {
        if(!choice.hasChildren()) 
        {
            ProbabilityNode<T> nullNode = new ProbabilityNode();
            return nullNode;
        }
        double probSum = 0.0;
        for(int i = 0; i < choice.getNumChildren(); i++)
        {
            probSum += choice.getChildAt(i).getProbability();
            // System.out.println(i + " at " +
            //                    choice.getChildAt(i).getProbability() + 
            //                    " is " + probSum);
        } 
        probSum = probSum * 100000000;
        
        if((int)(probSum/100000000) != 1)
        {
            System.out.print("ERROR: Invalid Probability Setup\n\n");
            return null;
        }
        
        
        probSum = 0.0;
        upperBound = choice.upperBound;
        lowerBound = choice.lowerBound;
        
        int i = 0;
        while(i < choice.getNumChildren())
        {
            double p = choice.getChildAt(i).getProbability();
            if(p >= lowerBound)
            {
                if(p > upperBound) 
                    p = upperBound;
                probSum += p;
            }
            i++;
        }
        
        // get num range 0 to (probSum*1000)-1
        int randomInt = randomGenerator.nextInt((int)(probSum * 1000));
        System.out.println("randomInt: "+randomInt);
        System.out.println("probSum: "+probSum);
        i = 0;
        while(((sum * 1000) < randomInt) && (i < choice.getNumChildren()))
        {
            double p = choice.getChildAt(i).getProbability();
            if(p >= lowerBound)
            {
                if(p > upperBound) 
                    p = upperBound;
                sum += p;
            }
            if(randomInt <= sum*1000) 
            {
                return choice.getChildAt(i);
            }
            i++;
        }
        
        if((choice.getChildAt(i-1).getProbability() >= lowerBound) &&
           (choice.getChildAt(i-1).getProbability() <= upperBound))
            return choice.getChildAt(i-1);
        else
        {
            System.out.println("ERROR: This algorithm is Wrong.");
            return new ProbabilityNode();
        }
    }
}

class ProbabilityNode<T> 
{
    public T nodeObject;
    double probability;
    double upperBound;
    double lowerBound;
    public List<ProbabilityNode<T>> children;
    
    public ProbabilityNode() 
    {
        this.children = new LinkedList<ProbabilityNode<T>>();
        this.upperBound = 1.0;
        this.lowerBound = 0.0;
    }
    
    public ProbabilityNode(T object, double prob) 
    {
        this.children = new LinkedList<ProbabilityNode<T>>();
        this.nodeObject = object;
        this.probability = prob;
        this.upperBound = 1.0;
        this.lowerBound = 0.0;
    }
    
    public ProbabilityNode(T object, 
                           double prob, 
                           LinkedList<ProbabilityNode<T>> childList
                          ) 
    {
        this.nodeObject = object;
        this.probability = prob;
        this.children = childList;
        this.upperBound = 1.0;
        this.lowerBound = 0.0;
    }
    
     public ProbabilityNode(T object, double prob, double lowBnd, double upBnd) 
    {
        this.children = new LinkedList<ProbabilityNode<T>>();
        this.nodeObject = object;
        this.probability = prob;
        this.upperBound = upBnd;
        this.lowerBound = lowBnd;
    }
    
    public ProbabilityNode(T object, 
                           double prob, 
                           LinkedList<ProbabilityNode<T>> childList,
                           double lowBnd,
                           double upBnd
                          ) 
    {
        this.nodeObject = object;
        this.probability = prob;
        this.children = childList;
        this.upperBound = upBnd;
        this.lowerBound = lowBnd;
    }
    
    public void setNodeObject(T object) 
    {
        this.nodeObject = object;
    }
    
    public T getNodeObject() 
    {
        return this.nodeObject;
    }
    
    public void setProbability(double prob) 
    {
        this.probability = prob;
    }
    
    public double getProbability() 
    {
        return this.probability;
    }
    
    public List<ProbabilityNode<T>> getChildren()
    {
        return children;
    }
    
    public void setChildren(List<ProbabilityNode<T>> children) 
    {
        this.children = children;
    }
    
    public void addChild(ProbabilityNode<T> childNode) 
    {
        this.children.add(childNode);
    }
    
    public void addChildAt(int index, ProbabilityNode<T> child) 
            throws IndexOutOfBoundsException 
    {
        this.children.add(index, child);
    }
    
    public ProbabilityNode<T> getChildAt(int index) 
            throws IndexOutOfBoundsException 
    {
        return this.children.get(index);
    }
    
    public void removeChildAt(int index) 
            throws IndexOutOfBoundsException 
    {
        this.children.remove(index);
    }
    
    public int getNumChildren() 
    {
        return this.children.size();
    }
    
    public boolean hasChildren() 
    {
        return (getNumChildren() > 0);
    }
}

public class ProbabilityTree<T> 
{
    ProbabilityNode<T> root;
    int iterator;
    AdvProbabilitySelector<T> selector;
    
    public ProbabilityTree() 
    {
        super();
        this.iterator = 0;
        this.selector = new AdvProbabilitySelector();
    }
    
    public ProbabilityTree(ProbabilityNode<T> rootNode) 
    {
        this.root = rootNode;
        this.iterator = 0;
        this.selector = new AdvProbabilitySelector();
    }
    
    public ProbabilityNode<T> selectNodeChild(ProbabilityNode<T> node)
    {
        return selector.selectOption(node);
    }
}

// ORIGINAL WORKING PROBABILITY TREE

//public class ProbabilityTree<T> 
//{
//    ProbabilityNode<T> root;
//    int iterator;
//    ProbabilitySelector<T> selector;
//    
//    public ProbabilityTree() 
//    {
//        super();
//        this.iterator = 0;
//        this.selector = new ProbabilitySelector();
//    }
//    
//    public ProbabilityTree(ProbabilityNode<T> rootNode) 
//    {
//        this.root = rootNode;
//        this.iterator = 0;
//        this.selector = new ProbabilitySelector();
//    }
//    
//    public ProbabilityNode<T> selectNodeChild(ProbabilityNode<T> node)
//    {
//        return selector.selectOption(node);
//    }
//
//}