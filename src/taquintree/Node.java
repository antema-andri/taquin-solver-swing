/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package taquintree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Andri
 */
public class Node {
    private static final int SIZE = 3;
    private int[][] state;
    private Node parent;
    private ArrayList<Node> children;
    private int depth;
        
    public Node(int[][] values, Node parent) {
        this.state = values;
        this.parent = parent;
        if (parent == null) {
            depth = 0;
        } else {
            depth = parent.depth + 1;
        }
        children = new ArrayList<>();
    }
    
    public ArrayList<Node> createChildren() {
        return Node.createChildren(this);
    }
    
    //Create Child (exchange the empty square with adjacente square)
    public static ArrayList<Node> createChildren(Node root) {
        ArrayList<Node> childs = new ArrayList<>();
        int x = 0, y = 0;

        // Trouver la case vide
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (root.getState()[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        // Créer les enfants possibles
        if (x > 0) {
            // Déplacer la case du haut
            int[][] newState = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    newState[i][j] = root.getState()[i][j];
                }
            }
            newState[x][y] = newState[x-1][y];
            newState[x-1][y] = 0;
            childs.add(new Node(newState, root));
        }

        if (x < 2) {
            // Déplacer la case du bas
            int[][] newState = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    newState[i][j] = root.getState()[i][j];
                }
            }
            newState[x][y] = newState[x+1][y];
            newState[x+1][y] = 0;
            childs.add(new Node(newState, root));
        }

        if (y > 0) {
            // Déplacer la case de gauche
            int[][] newState = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    newState[i][j] = root.getState()[i][j];
                }
            }
            newState[x][y] = newState[x][y-1];
            newState[x][y-1] = 0;
            childs.add(new Node(newState, root));
        }

        if (y < 2) {
            // Déplacer la case de droite
            int[][] newState = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    newState[i][j] = root.getState()[i][j];
                }
            }
            newState[x][y] = newState[x][y+1];
            newState[x][y+1] = 0;
            childs.add(new Node(newState, root));
        }

        return childs;
    }
    
    public HashMap<Integer, Integer> indexOfEmpty(){
        HashMap<Integer, Integer> indexMap = new HashMap <>();
        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                if(state[i][j]==0){
                    indexMap.put(0, i);
                    indexMap.put(1, j);
                    return indexMap;
                }
            }
        }
        indexMap.put(0, -1);
        indexMap.put(1, -1);
        return indexMap;
    }
    
    public void addChild(Node child) {
        child.parent = this;
        child.depth = this.depth + 1;
        this.children.add(child);
    }
    
    public int manhattanDistance() {
        int distance = 0;
        int[][] status = this.getState();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int value = status[i][j];
                if (value != 0) {
                    int x = (value - 1) / SIZE;
                    int y = (value - 1) % SIZE;
                    distance += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        return distance;
    }
    
    public static int[][] getStateSolution(){
        int size = Node.getNodeSize();
        int[][] stateSolution = new int[size][size];
        int count = 1;
        for(int i=0; i<stateSolution.length; i++){
            for(int j=0; j<stateSolution.length; j++){
                stateSolution[i][j] = count%(size*size);
                count++;
            }
        }
        return stateSolution;
    }
    
    public static int getNodeSize(){
        return SIZE;
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public int[][] getState(){
        return state;
    }

    public Node getParent(){
        return this.parent;
    }
    
    public ArrayList<Node> getChildren(){
        return this.children;
    }
    
    public int getDepth(){
        return this.depth;
    }
}
