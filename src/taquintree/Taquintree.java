/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package taquintree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Andri
 */
public class Taquintree {
    
    public static void displayNode(Node node){
       int size = 3;
       for(int i=0; i<size; i++){
           for(int j=0; j<size; j++){
               System.out.print(node.getState()[i][j] + " | ");
           }
           System.out.println();
       }
       System.out.println("---------------");
    }
    
    public static void displaySolution(Node treeNode){
        ArrayList<Node> solution = Taquintree.getSolution(treeNode);
        solution.stream().forEach((node) -> {
            Taquintree.displayNode(node);
        });
    }
    
    /**
     *
     * @param finalNode
     * @return
     */
    public static ArrayList<Node> getSolution(Node finalNode) {
        ArrayList<Node> solution = new ArrayList<>();
        Node currentNode = finalNode;

        while (currentNode != null) {
            solution.add(currentNode);
            currentNode = currentNode.getParent();
        }

        Collections.reverse(solution);
        return solution;
    }
    
    public static void displayDepth(Node root){
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            String spc = " ";
            for(int i=0; i<node.getDepth(); i++){
                spc += spc;
            }
            System.out.println(spc+"    "+Arrays.deepToString(node.getState())+"/"+node.manhattanDistance());

            for (Node child : node.getChildren()) {
                queue.add(child);
            }
        }
    }
    
    public static void generateTree(Node rootNode) {
        Stack<Node> stack = new Stack<>();
        HashSet<String> visited = new HashSet<>();
        stack.push(rootNode);

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            visited.add(Arrays.deepToString(currentNode.getState()));
            ArrayList<Node> children = currentNode.createChildren();                        
            for (Node child : children) {
                if (!visited.contains(Arrays.deepToString(child.getState())) && child.manhattanDistance()<=currentNode.manhattanDistance()) {
                    currentNode.addChild(child);
                    stack.push(child);
                }
            }
        }
    }
    
    ///////////
    
    public static Node solve(Node initialNode) {
        Stack<Node> stack = new Stack<>();
        stack.add(initialNode);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (isSolution(node)) {
                return node;
            }
            
            for(Node child: node.getChildren()){
                stack.add(child);
            }
        }
        return null;
    }
    
    public static Node getBestChild(Node node){
        Node bestChild = null;
        int minDistance = Integer.MAX_VALUE;
        for(Node child: node.getChildren()){
            if(child.manhattanDistance() <= minDistance && child.getChildren().size()!=0) {
                minDistance = child.manhattanDistance();
                bestChild = child;
                System.out.println("*****"+Arrays.deepToString(child.getState())+"/"+child.manhattanDistance());
            }
        }
        return bestChild;
    }

    public static boolean isSolution(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("The parmeter cannot be null");
        }
        int[][] state = node.getState();
        int count = 1;
        for (int i = 0; i < node.getState().length; i++) {
            for (int j = 0; j < node.getState().length; j++) {
                if (state[i][j] != count % 9) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }
    
    public static Node mixSolution(){
        Node randumChild = new Node(Node.getStateSolution(), null);
        for(int i=0; i<10; i++){
            ArrayList<Node> children= Node.createChildren(randumChild);
            int indexRandom = (int)(Math.random() * children.size());
            if(!Taquintree.isSolution(children.get(indexRandom))){
                randumChild = children.get(indexRandom);
            }
        }
        randumChild.setParent(null);
        return randumChild;
    }
    
}
