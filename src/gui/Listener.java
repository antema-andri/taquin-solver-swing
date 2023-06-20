/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import taquintree.Node;
import taquintree.Taquintree;

/**
 *
 * @author Andri
 */
public class Listener implements ActionListener {
    private TaquinFrame taquinFrame;

    public Listener(TaquinFrame taquinFrame) {
        setTaquinFrame(taquinFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Shuffle")){
            getTaquinFrame().mixer();
        } else if(e.getActionCommand().equals("Solve")){
            getTaquinFrame().mouvScreen();
        }
    }
    
    public TaquinFrame getTaquinFrame() {
        return taquinFrame;
    }

    public void setTaquinFrame(TaquinFrame taquinFrame) {
        this.taquinFrame = taquinFrame;
    }
}
