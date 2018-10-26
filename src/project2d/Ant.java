/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2d;

import graphlib2d.Canvas;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
/**
 *
 * @author L440
 */
public class Ant implements Runnable{
    public int x = 0;
    public int y = 0;
    public Canvas ant;
    public Canvas antR;
    public Canvas antL;
    public Canvas[] ants;
    public final Canvas antO;
    public final Canvas antRO;
    public final Canvas antLO;
    
    String path = "C:\\Users\\L440\\Downloads\\ant.png";
    String pathR = "C:\\Users\\L440\\Downloads\\antR.png";
    String pathL = "C:\\Users\\L440\\Downloads\\antL.png";
    int delay = 200;
    Direction actualDir = Direction.Ahead;
    public Direction newDir = null;
    JFrame frame;
    
    public Ant (JFrame frame, int width, int height){
        this.frame = frame;
        ant = new Canvas(frame, 1, 1);
        antR = new Canvas(frame, 1,1);
        antL = new Canvas(frame, 1,1);
        antO = new Canvas(frame, 1, 1);
        antRO = new Canvas(frame, 1,1);
        antLO = new Canvas(frame, 1,1);
        ant.canvas = ant.renderImg(path);
        double factor = (double)width/(double)ant.canvas.getWidth();
        
        ant.canvas = ant.resize(ant.canvas, factor);
        antR.canvas = antR.resize(antR.renderImg(pathR), factor);
        antL.canvas = antL.resize(antL.renderImg(pathL), factor);
        antO.canvas = antR.resize(antR.renderImg(path), factor);
        antRO.canvas = antR.resize(antR.renderImg(pathR), factor);
        antLO.canvas = antR.resize(antR.renderImg(pathL), factor);
        ants = new Canvas[]{ ant, antR, antL};
    }
    
    public void walk(){
        frame.getGraphics().drawImage(ant.canvas, frame.getWidth()/2, frame.getHeight()/2, frame);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {}
        frame.getGraphics().drawImage(antR.canvas, frame.getWidth()/2, frame.getHeight()/2, frame);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {}
        frame.getGraphics().drawImage(antL.canvas, frame.getWidth()/2, frame.getHeight()/2, frame);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {}
        frame.getGraphics().drawImage(ant.canvas, frame.getWidth()/2, frame.getHeight()/2, frame);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {}
    }
    
    public void turnAround(Direction dir){
        int from = 0;
        if(dir!=actualDir){
            switch(actualDir){
                case Rigth:
                    rotate(0);
                    break;
                case Left:
                    rotate(180);
                    break;
                case Ahead:
                    rotate(90);
                    break;
                case Behind:
                    rotate(270);
                    break;
            }
            /*
            switch(actualDir){
                case Rigth:
                    from = 0;
                    break;
                case Left:
                    from = 180;
                    break;
                case Ahead:
                    from = 90;
                    break;
                case Behind:
                    from = 270;
                    break;
            }
            switch(dir){
                case Rigth:
                    for (int i = from; i >= 0; i-=from/10) {
                        rotate(i);
                    }
                    break;
                case Left:
                    for (int i = 0; i <= Math.abs(from-180); i+=from/10) {
                        rotate(from-180<0?from+i:from-i);
                    }
                    break;
                case Ahead:
                    for (int i = 0; i <= Math.abs(from-90); i+=from/10) {
                        rotate(from-90<0?from+i:from-i);
                    }
                    break;
                case Behind:
                    for (int i = 0; i <= Math.abs(from-270); i+=from/10) {
                        rotate(from-270<0?from+i:from-i);
                    }
                    break;
            }*/
        }
    }
    
    public void rotate(int angle){
        ant.canvas = ant.rotateImage(antO.canvas, angle);
        antR.canvas = ant.rotateImage(antRO.canvas, angle);
        antL.canvas = ant.rotateImage(antLO.canvas, angle);
        actualDir = newDir;
        //frame.getGraphics().drawImage(ant.canvas, x, y, frame);
    }

    @Override
    public void run() {
        if(newDir==null)
            walk();
        else{
            turnAround(newDir);
            actualDir = newDir;
        }
    }
    
    enum Direction{
        Rigth, Left, Ahead, Behind
    }
}
