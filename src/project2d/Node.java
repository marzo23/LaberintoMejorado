/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2d;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author L440
 */
public class Node {
    public int x = -1;
    public int y = -1;
    public List<Node> relations = new ArrayList();
    public Node prev;
    public boolean visited = false;
    public Position position;
    int size = 0;
    public Node(int x, int y){
        this.x = x;
        this.y = y;
        
    }
}

class Position {
    public int x = 0;
    public int y = 0;
    public int width = 0;
    public int height = 0;
    public Position(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Position(Node n){
        this.x = n.x*n.size;
        this.y = n.y*n.size;
        this.width = n.size;
        this.height = n.size;
    }
}