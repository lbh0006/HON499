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

class AdvancedProbabilitySelector<T> 
{
    double sum;
    int upperBound;
    int lowerBound;
    Random randomGenerator;
    
    public AdvancedProbabilitySelector() 
    {
        super();
        this.randomGenerator = new Random();
        this.sum = 0.0;
        this.upperBound = 1;
        this.lowerBound = 0;
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
        upperBound = (int)(choice.upperBound * 1000);
        lowerBound = (int)(choice.lowerBound * 1000);
        
        int randomInt = randomGenerator.nextInt(1000); // get num range 0 - 999
        //Make sure chosen value is within bounds
        while((randomInt > upperBound)||(randomInt < lowerBound))
        {
            randomInt = randomGenerator.nextInt(1000);
        }
        if(randomInt <= sum*1000) 
        {
            return choice.getChildAt(0);
        }
        else 
        {
            int i = 0;
            sum = 0.0;
            while (((sum * 1000) < randomInt) && (i<choice.getNumChildren())) 
            {
                sum += choice.getChildAt(i).getProbability();
                i++;
            }
            return choice.getChildAt(i-1);
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
        super();
        this.children = new LinkedList<ProbabilityNode<T>>();
        this.upperBound = 1.0;
        this.lowerBound = 0.0;
    }
    
    public ProbabilityNode(T object, double prob) 
    {
        super();
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
        super();
        this.nodeObject = object;
        this.probability = prob;
        this.children = childList;
        this.upperBound = 1.0;
        this.lowerBound = 0.0;
    }
    
     public ProbabilityNode(T object, double prob, double upBnd, double lowBnd) 
    {
        super();
        this.children = new LinkedList<ProbabilityNode<T>>();
        this.nodeObject = object;
        this.probability = prob;
        this.upperBound = upBnd;
        this.lowerBound = lowBnd;
    }
    
    public ProbabilityNode(T object, 
                           double prob, 
                           LinkedList<ProbabilityNode<T>> childList,
                           double upBnd,
                           double lowBnd
                          ) 
    {
        super();
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
    ProbabilitySelector<T> selector;
    
    public ProbabilityTree() 
    {
        super();
        this.iterator = 0;
        this.selector = new ProbabilitySelector();
    }
    
    public ProbabilityTree(ProbabilityNode<T> rootNode) 
    {
        this.root = rootNode;
        this.iterator = 0;
        this.selector = new ProbabilitySelector();
    }
    
    public ProbabilityNode<T> selectNodeChild(ProbabilityNode<T> node)
    {
        return selector.selectOption(node);
    }

}
