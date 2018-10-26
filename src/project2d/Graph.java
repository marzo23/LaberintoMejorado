/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author L440
 */
public class Graph {
    public final int lenght;
    //public List<Node> nodeList = new ArrayList();
    public Node[][] nodes;
    
    public Graph(int lenght){
        this.lenght = lenght;
        nodes = new Node[lenght][lenght];
    }
    
    public void initializeMatrix(){
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                nodes[i][j] = new Node(i,j);
            }
        }
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                addRelation(i,j, i,j-1);
                addRelation(i,j, i,j+1);
                addRelation(i,j, i+1,j);
                addRelation(i,j, i-1,j);
            }
        }
    }
    
    public void initializeMatrix2(){
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                nodes[i][j] = new Node(i,j);
            }
        }
        
    }
    
    public void RandomForest(Node n, List<Node> evaluated, List<Node> toEvaluate){
        if(evaluated == null)
            evaluated = new ArrayList();
        if(toEvaluate == null)
            toEvaluate = new ArrayList();
        
        if(evaluated.contains(n))
            return;
        
        n.visited = true;
        evaluated.add(n);
        toEvaluate.remove(n);
        
        for (int i = 0; i < option.length; i++) {
            Node aux = null;
            try{
                aux = nodes[n.x+option[i][0]][n.y+option[i][1]];
            }catch(Exception e){}
            if(aux!=null){
                if(aux.prev!=null){
                    if(!aux.relations.contains(aux.prev) && aux.relations.size()<2){
                        aux.relations.add(aux.prev);
                        aux.prev.relations.add(aux);
                    }
                }else
                    aux.prev = n;
                if(!toEvaluate.contains(aux) && !evaluated.contains(aux))
                    toEvaluate.add(aux);
                
            }
        }
        
        Node next = null;
        Random rand = new Random();
        boolean flag = false;
        while(next==null){
            List<Integer> nums = new ArrayList();
            for (int i = 0; i < option.length; i++) {
                nums.add(i);
            }
            for (int i = 0; i < nums.size() && next==null; i++) {

                Integer r = nums.get(rand.nextInt(nums.size()));
                nums.remove(r);
                i=0;
                int xN = n.x+option[r][0];
                int yN = n.y+option[r][1];
                try{
                    next = nodes[xN][yN];
                }catch(Exception e){}
                if(evaluated.contains(next))
                    next=null;
            }
            if(next==null){
                if(toEvaluate.isEmpty())
                    break;
                n = toEvaluate.get(0);
                toEvaluate.remove(0);
                flag = true;
            }
            else{
                toEvaluate.remove(next);
            }
            
        }
        
        if(next!=null){
            for (int i = 0; i < n.relations.size(); i++) {
                Node aux = n.relations.get(i);
                if(aux.visited!=true && aux != next){
                    aux.relations.remove(n);
                    n.relations.remove(aux);
                }
            }
            RandomForest(next, evaluated, toEvaluate);
        }else if(flag){
            n.relations.add(n.prev);
            n.prev.relations.add(n);
            RandomForest(n, evaluated, toEvaluate);
        }
        
        
    }
    
    int[][] option = { {0,1}, {1,0}, {0,-1}, {-1,0} };
    
    public Node find(Node treeRoot, Node n, Stack<Node> toValidate, List<Node> validated){
            Node aux = null;
            if (toValidate == null)
                toValidate = new Stack<Node>();
            if (validated == null)
            {
                validated = new ArrayList();
                resetPrev();
            }
            if (treeRoot != null)
            {
                if (treeRoot==n)
                    return treeRoot;
                validated.add(treeRoot);
                for (int i = 0; i < treeRoot.relations.size(); i++)
                {
                    

                    if (!validated.contains(treeRoot.relations.get(i))){
                        toValidate.push(treeRoot.relations.get(i));
                        if(treeRoot.relations.get(i).prev==null)
                            treeRoot.relations.get(i).prev = treeRoot;
                    }
                }
            }
            if (toValidate.size() > 0)
                aux = find(toValidate.pop(), n, toValidate, validated);
            return aux;
    }
    
    void resetPrev(){
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                nodes[i][j].prev = null;
            }
        }
    }
    
    void printRout(Node n){
        List<Node> validated = new ArrayList();
        while(n.prev!=null){
            if(validated.contains(n))
                continue;
            validated.add(n);
            //System.out.println("x: "+n.x+" y: "+n.y);
            n= n.prev;
        }
    }
    
    private Node addRelation(int xOrigin, int yOrigin, int xDestination, int yDestination){
        if(xDestination>=0 && yDestination>=0 && xDestination<lenght && yDestination<lenght)
        if(!nodes[xOrigin][yOrigin].relations.contains(nodes[xDestination][yDestination])){
            nodes[xOrigin][yOrigin].relations.add(nodes[xDestination][yDestination]);
            nodes[xDestination][yDestination].relations.add(nodes[xOrigin][yOrigin]);
            return nodes[xDestination][yDestination];
        }
        return null;
    }
    
    public void drawMaze(int screenSize, Node destiny){
        MazeDrawer t = new MazeDrawer(nodes, lenght, screenSize, destiny);
        t.show();
        
    }
}