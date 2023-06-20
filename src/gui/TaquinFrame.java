/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import taquintree.Node;
import taquintree.Taquintree;

/**
 *
 * @author Andri
 */
public class TaquinFrame extends JFrame {
    private static final int SIZE = 3;
    private static final int WINDOW_WIDTH = 350;
    private static final int WINDOW_HEIGHT = 385; // 35 extra for title bar
    private static final int SQUARE_SIZE = WINDOW_WIDTH/SIZE;;
    private JButton[][] buttons;
    private JPanel panel;
    private JPanel buttonPanel;
    private Node initialNode;
    private JButton shuffleButton, startButton;

    public TaquinFrame(Node initialNode) {
        setTitle("Taquin game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setPanel(new JPanel(new GridLayout(SIZE, SIZE)));
        loadButtons();
        putPanels();
        chooseInitialNode();
        changeState(getInitialNode().getState());
        getButtons()[SIZE-1][SIZE-1].setVisible(false);
        actions();
        setResizable(false);
        setVisible(true);
    }
    
    public void putPanels(){
        setButtonPanel(new JPanel(new FlowLayout()));
        buttonPanel.setBackground(Color.red);
        shuffleButton = new JButton("Shuffle");
        startButton = new JButton("Solve");
        getButtonPanel().add(shuffleButton);
        getButtonPanel().add(startButton);

        getContentPane().add(getButtonPanel(), BorderLayout.NORTH);
        getContentPane().add(getPanel(), BorderLayout.CENTER);
    }
    
    public void loadButtons(){
        setButtons(new JButton[SIZE][SIZE]);
        int numButton = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                getButtons()[i][j] = new JButton(String.valueOf(numButton%9));
                numButton++;
            }
        }
    }
    
    public void setButtonsByState(int[][] state){
        //get coordinates of the empty case
        int x=0, y=0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(state[i][j]==0){
                    x = i;
                    y = j;
                }
            }
        }
        
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                HashMap<Integer, Integer> indexButton = getCoodinateButtonValue(state[i][j]);
                placeButton(indexButton.get(0), indexButton.get(1), i, j);
            }
        }
    }
    
    public void placeButton(int i, int j, int newi, int newj){
        getButtons()[i][j].setVisible(false);
        getButtons()[i][j].setBounds(newj*SQUARE_SIZE, newi*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        getPanel().add(getButtons()[i][j]);
        if(!getButtons()[i][j].getText().equals("0"))
            getButtons()[i][j].setVisible(true);
    }
    
    public HashMap<Integer, Integer> getCoodinateButtonValue(int valueButton){
        HashMap<Integer, Integer> indexes = new HashMap<>();
        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                if(Integer.toString(valueButton).equals(getButtons()[i][j].getText())){
                    indexes.put(0, i);
                    indexes.put(1, j);
                }
            }
        }
        return indexes;
    }
    
    public void changeState(int[][] newState){
        setButtonsByState(newState);
        getPanel().repaint();
        changeButtonPanelColor(newState);
        changeButtonState(newState);
    }
    
    public void changeButtonPanelColor(int[][] newState){
        Color colorPanel = Taquintree.isSolution(new Node(newState, null)) ? Color.green : Color.red;
        getButtonPanel().setBackground(colorPanel);
        getButtonPanel().repaint();
    }
    
    public void changeButtonState(int[][] newState){
        boolean enableSolveButton = Taquintree.isSolution(new Node(newState, null)) ? false : true;
        startButton.setEnabled(enableSolveButton);
        shuffleButton.setEnabled(!enableSolveButton);
    }
    
    public void mixer(){
        Node rootRandom = Taquintree.mixSolution();
        Taquintree.generateTree(rootRandom);
        setInitialNode(rootRandom);
        changeState(getInitialNode().getState());
    }
    
    public void mouvScreen() {
        Node finalNode = Taquintree.solve(getInitialNode());
        ArrayList<Node> solutionPath = Taquintree.getSolution(finalNode);
        Thread mvThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Node path1 : solutionPath) {
                    changeState(path1.getState());
                    Taquintree.displayNode(path1);
                    try {
                        Thread.sleep(2*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mvThread.start();
    }
    
    public void actions(){
        startButton.addActionListener(new Listener(this));
        shuffleButton.addActionListener(new Listener(this));
    }
    
    public void chooseInitialNode(){
        setInitialNode(initialNode);
        if(getInitialNode()==null){
            mixer();
        }
    }
    
    public JButton[][] getButtons() {
        return buttons;
    }

    public void setButtons(JButton[][] buttons) {
        this.buttons = buttons;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }
    
    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }
}
