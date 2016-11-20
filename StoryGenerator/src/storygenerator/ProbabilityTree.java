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
        choice.normalize();
        
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
    int upperBound;
    int lowerBound;
    Random randomGenerator;
    
    public AdvProbabilitySelector() 
    {
        super();
        this.randomGenerator = new Random();
        this.sum = 0.0;
        this.upperBound = 1;
        this.lowerBound = 0;
    }
    
    public ProbabilityNode<T> selectOption(ProbabilityNode<T> choice) 
    {
        // Make sure choice has options to choose from
        if(!choice.hasChildren()) 
        {
            ProbabilityNode<T> nullNode = new ProbabilityNode();
            return nullNode;
        }
        //Make sure choice probabilities are distributed properly
        choice.normalize();
        
        // Initialize randomInt and sum
        int randomInt = randomGenerator.nextInt(1000); // get num range 0 - 999
        sum = choice.getChildAt(0).getProbability();
        ProbabilityNode<T> selectedNode;
        if(randomInt <= sum*1000) {
            selectedNode = choice.getChildAt(0);
        }
        else 
        {
            int i = 0;
            sum = 0.0;
            while (((sum * 1000) < randomInt) && (i < choice.getNumChildren()))
            {
                sum += choice.getChildAt(i).getProbability();
                i++;
            }
            if(i>0){ selectedNode = choice.getChildAt(i-1); }
            else   { selectedNode = choice.getChildAt(i);}
        }
        
        double lb = selectedNode.lowerBound;
        double ub = selectedNode.upperBound;
        selectedNode.setProbability(selectedNode.getProbability() + lb);
        if(selectedNode.getProbability() < 0)
        {
            selectedNode.setProbability(0.0);
        }
        if(lb < 0)
        {
            lb = 0.0;
        }

        for(int i = 0; i < choice.getNumChildren(); i++)
        {
            ProbabilityNode<T> temp = choice.getChildAt(i);
            if(temp.getProbability() > ub)
            {
		choice.getChildAt(i).setProbability(ub);
            }
            double newProb = temp.getProbability()-lb;
            if(newProb < 0.0){newProb = 0.001;}
            choice.getChildAt(i).setProbability(newProb);
        }

        choice.normalize();
        
        return selectedNode;
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
                           double upBnd,
                           double lowBnd
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
    
    public void normalize()
    {
        double newProbSum = 0;
	
	for(int i = 0; i < this.getNumChildren(); i++)
	{
		newProbSum += this.getChildAt(i).getProbability();
	}
	for(int i = 0; i < this.getNumChildren(); i++)
	{
		double prob = this.getChildAt(i).getProbability();
		this.getChildAt(i).setProbability(prob / newProbSum);
	}
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