/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2d;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import graphlib2d.Canvas;
import graphlib2d.GraphLib2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import project2d.Ant.Direction;

/**
 *
 * @author L440
 */
public class MazeDrawer extends JFrame implements KeyListener, Runnable{
    public Node[][] nodes;
    public int lenght;
    public int screenSize;
    public Node destiny;
    public Ant ant;
    int x,y;
    Point p;
    Circle[][] circles;
    MazeDrawer(Node[][] nodes, int lenght, int screenSize, Node destiny){
        super();
        p = new Point(0,0);
        x = screenSize/2;
        y = screenSize/2;
        this.addKeyListener(this);
        ant = new Ant(this, screenSize/lenght, screenSize/lenght);
        this.destiny = destiny;
        this.nodes = nodes;
        this.lenght = lenght;
        this.screenSize = screenSize;
        setSize(screenSize, screenSize+40);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        circles = new Circle[screenSize/lenght][screenSize/lenght];
        Random r = new Random();
        Color[] colors = new Color[]{ new Color(255, 255 ,80), new Color(255, 255 ,120), new Color(255, 255 ,140) };
        for (int i = 0; i < circles.length; i++) {
            for (int j = 0; j < circles[i].length; j++) {
                circles[i][j] = new Circle(i*screenSize/lenght+r.nextInt(screenSize/lenght), j*screenSize/lenght+r.nextInt(screenSize/lenght), 4+r.nextInt(screenSize/lenght), colors[r.nextInt(colors.length)]);
            }
        }
        
        ant.x = ant.ant.canvas.getWidth()/2+screenSize/2;
        ant.y = ant.ant.canvas.getHeight()/4+screenSize/2;
        ant.rotate(180);
        show();
    }
    
    public void paint(Graphics g){
        if(canvas==null){
            bg = generateBackgroundCircles();
            generateMaze();
        }
        update(g);
    }
    /*
    public BufferedImage generateBackground(){
        Canvas canvas = new Canvas(this, screenSize*2, screenSize*2);
        Color[] colors = new Color[]{ new Color(63,191,127), new Color(183,224,204), new Color(63,116,90) };
        Random rand = new Random();
        for (int i = -screenSize/2; i < screenSize*1.5; i+=3) {
            for (int j = -screenSize/2; j < screenSize*1.5; j+=3) {
                if(j+y>=0 && j+y<=screenSize && i+x>=0 && i+x<=screenSize)
                    canvas.drawPixel(i, j, canvas.graphics, 3, colors[rand.nextInt(colors.length)]);
                else
                    canvas.drawPixel(i, j, canvas.graphics, 3, Color.black);
            }
        }
        return canvas.canvas;
    }
    */
    
    public BufferedImage generateBackgroundCircles(){
        Canvas canvas = new Canvas(this, screenSize, screenSize);
        canvas.drawRec(0, 0, screenSize, screenSize, new Color(255, 255, 180),new Color(255, 255, 180));
        Random r = new Random();
        int[] inc = {-15,15};
        for (int i = 0; i < circles.length; i++) {
            for (int j = 0; j < circles[i].length; j++) {
                if(r.nextInt(2)==1)
                    canvas.drawElipse(i*(int)(screenSize*1.4)/lenght, j*(int)(screenSize*1.4)/lenght, circles[i][j].r, circles[i][j].r+ inc[r.nextInt(2)], circles[i][j].c, circles[i][j].c);
                    
                else
                    canvas.drawCircleBresenham(i*(int)(screenSize*1.4)/lenght, j*(int)(screenSize*1.4)/lenght, circles[i][j].r, circles[i][j].c, circles[i][j].c);
            }
        }
        return canvas.canvas;
    }
    
    public BufferedImage generateBackground(){
        Canvas canvas = new Canvas(this, screenSize, screenSize);
        
        Color[] colors = new Color[]{ new Color(63,191,127), new Color(183,224,204), new Color(63,116,90) };
        Random rand = new Random();
        for (int i = 0; i < screenSize; i+=3) {
            for (int j = 0; j < screenSize; j+=3) {
                canvas.drawPixel(i, j, canvas.graphics, 3, colors[rand.nextInt(colors.length)]);
            }
        }
        return canvas.canvas;
    }
    
    Canvas canvas;
    /*
    public void generateMaze(){
        canvas = new Canvas(this, screenSize, screenSize);
        canvas.drawRec(0, 0, screenSize, screenSize, Color.black,Color.black);
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                Node aux = nodes[i][j];
                
                int width = screenSize/lenght-(screenSize/lenght/5);
                int height = screenSize/lenght-(screenSize/lenght/5);
                for (int k = 0; k < aux.relations.size(); k++) {
                    Node aux2 = nodes[i][j].relations.get(k);
                    
                    if(aux2.x==i && aux2.y==(j+1)){
                        width = screenSize/lenght;
                    }else if(aux2.x==(i+1) && aux2.y==j){
                        height = screenSize/lenght;
                    }
                    
                }
                canvas.drawRec(j*(screenSize/lenght), i*(screenSize/lenght), width, height, new Color(255, 255, 255, 0), new Color(255, 255, 255, 0));
            }
        }
        canvas.drawRec(destiny.y*(screenSize/lenght), destiny.x*(screenSize/lenght), screenSize/lenght, screenSize/lenght, Color.YELLOW, Color.YELLOW);
        canvas.canvas = canvas.resize(canvas.canvas , lenght/5);
    }*/
    
    public void generateMaze(){
        int screenSize = this.screenSize*2;
        canvas = new Canvas(this, screenSize, screenSize);
        canvas.drawRec(0, 0, screenSize, screenSize, Color.black,Color.black);
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                Node aux = nodes[i][j];
                
                int width = screenSize/lenght-(screenSize/lenght/5);
                int height = screenSize/lenght-(screenSize/lenght/5);
                for (int k = 0; k < aux.relations.size(); k++) {
                    Node aux2 = nodes[i][j].relations.get(k);
                    
                    if(aux2.x==i && aux2.y==(j+1)){
                        width = screenSize/lenght;
                    }else if(aux2.x==(i+1) && aux2.y==j){
                        height = screenSize/lenght;
                    }
                    
                }
                nodes[i][j].size = width;
                nodes[i][j].position = new Position(nodes[i][j]);
                
                canvas.drawRec(j*(screenSize/lenght), i*(screenSize/lenght), width, height, new Color(255, 255, 255, 0), new Color(255, 255, 255, 0));
                String t = "::(";
//                for (int k = 0; k < nodes[i][j].relations.size(); k++) {
//                    t+=nodes[i][j].relations.get(k).x+","+nodes[i][j].relations.get(k).y+")(";
//                }
//                canvas.canvas.getGraphics().setColor(Color.red);
//                canvas.canvas.getGraphics().drawString("("+nodes[i][j].x+","+nodes[i][j].y+")"+t, j*(screenSize/lenght), i*(screenSize/lenght));
//                
            }
        }
        canvas.drawRec(destiny.y*(screenSize/lenght), destiny.x*(screenSize/lenght), screenSize/lenght, screenSize/lenght, Color.YELLOW, Color.YELLOW);
        //canvas.canvas = canvas.resize(canvas.canvas , lenght/5);
    }
    BufferedImage bg;
    public BufferedImage croptImage(){
        //BufferedImage bg = generateBackgroundCircles();
        Canvas c = new Canvas(this,screenSize,screenSize);
        c.drawRec(0, 0, screenSize, screenSize, Color.black,Color.black);
        for (int i = x>0?x:0, x1=0; i < ((x+canvas.canvas.getWidth()<screenSize)? x+canvas.canvas.getWidth():screenSize); i++, x1++) {
            for (int j = y>0?y:0, y1=0; j < ((y+canvas.canvas.getHeight()<screenSize)? y+canvas.canvas.getHeight():screenSize); j++, y1++) {
                //System.out.println("t: "+canvas.canvas.getRGB(Math.abs(x)+i, Math.abs(y)+j));
                int color = 0;
                if(canvas.canvas.getRGB((x>0?0:Math.abs(x))+x1, (y>0?0:Math.abs(y))+y1)>0)
                    color = bg.getRGB(x1,y1);
                else
                    color = canvas.canvas.getRGB((x>0?0:Math.abs(x))+x1, (y>0?0:Math.abs(y))+y1);
                c.drawPixel(i,j, color);
            }
        }
        return c.canvas;
    }
    
    BufferedImage tmp;
    public void update(Graphics g){
        //g.drawImage(generateBackgroundCircles(), 0,0, this);
        //ant.rotate(180);
        //g.drawImage(ant.ant.canvas, 270,270, this);
        this.run();        
        //g.drawImage(ant.ant.canvas, ant.ant.canvas.getWidth()/2+screenSize/2, ant.ant.canvas.getHeight()/4+screenSize/2, this);
        
    }
    boolean flag = false;
    Node toDraw = null;
    
    void PrintSearch(Node n){
        
        List<Node> validated = new ArrayList();
        while(n.prev!=null){
            toDraw = n;
            repaint();
            if(validated.contains(n))
                continue;
            validated.add(n);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(MazeDrawer.class.getName()).log(Level.SEVERE, null, ex);
            }
            n= n.prev;
        }
        
    }
    
    void search(Node n, List<Node> evaluated){
        toDraw = n;
        if(n==destiny)
            return;
        
        if(evaluated == null)
            evaluated = new ArrayList();
        
        if(evaluated.contains(n))
            return;
        
        evaluated.add(n);
        Node tmp = null;
        for (int i = 0; i < n.relations.size(); i++) {
            if(!evaluated.contains(n.relations.get(i)))
                tmp = n.relations.get(i);
        }
        if(tmp!=null)
            search(n, evaluated);
        
    }
    
    public boolean validateColors(Point newP){
        if(newP.x<0 || newP.y<0)
            return false;
        if(nodes[p.x][p.y].relations.contains(nodes[newP.x][newP.y])){
            p = newP;
            return true;
        }
        return false;
    }
    /*
    public boolean validateColors(){
        
        int x = ant.ant.canvas.getWidth()/2+screenSize/2; //+this.getLocation().x; ////////////FIX LOCATIONS
        int y = ant.ant.canvas.getHeight()/4+screenSize/2; //+this.getLocation().y;
        for (int i = 0; i < ant.ant.canvas.getHeight(); i++) {
            int tmp1 = 0;
            int tmp2 = 0;
            tmp1 = tmp.getRGB(x+ant.ant.canvas.getWidth(), y+i);//new Robot().getPixelColor(j+x, i+y);
            tmp2 = tmp.getRGB(x, y+i);
            if(tmp1==Color.BLACK.getRGB()|| tmp1==Color.BLACK.getRGB())
                return false;
        }
        
        for (int i = 0; i < ant.ant.canvas.getWidth(); i++) {
            int tmp1 = 0;
            int tmp2 = 0;
            tmp1 = tmp.getRGB(x+i, y+ant.ant.canvas.getHeight());//new Robot().getPixelColor(j+x, i+y);
            tmp2 = tmp.getRGB(x+i, y);
            if(tmp1==Color.BLACK.getRGB()|| tmp1==Color.BLACK.getRGB())
                return false;
        }
        
        return true;
    }
    */
    char direction;
    int delay = 10;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int size = 2*screenSize/lenght;
        direction = e.getKeyChar();
        switch(direction){
            case 's':
                if(!validateColors(new Point(p.x+1,p.y)))
                    break;
                y -=size;
                if(y+canvas.canvas.getHeight()<screenSize/2+size && y+size+canvas.canvas.getHeight()>screenSize/2+size){
                    y=screenSize/2-canvas.canvas.getHeight()+size;
                    repaint();
                }else if(y+canvas.canvas.getHeight()>=screenSize/2+size)
                    repaint();
                else 
                    y +=size;
                break;
            case 'a':
                if(!validateColors(new Point(p.x,p.y-1)))
                    break;
                x +=size;
                if(x>screenSize/2&& x-size<screenSize/2){
                    x=screenSize/2;
                    repaint();
                }else if(x<=screenSize/2)
                    repaint();
                else
                    x-=size;
                break;
            case 'w':
                if(!validateColors(new Point(p.x-1,p.y)))
                    break;
                y +=size;
                if(y>screenSize/2 && y-size<screenSize/2){
                    y=screenSize/2;
                    repaint();
                }else if(y<=screenSize/2)
                    repaint();
                else
                    y-=size;
                break;
            case 'd':
                if(!validateColors(new Point(p.x,p.y+1)))
                    break;
                x -=size;
                if(x+canvas.canvas.getWidth()<screenSize/2+size && x+size+canvas.canvas.getWidth()>screenSize/2+size){
                    x=screenSize/2-canvas.canvas.getWidth()+size;
                    repaint();
                }else if(x+canvas.canvas.getWidth()>=screenSize/2+size)
                    repaint();
                else
                    x+=size;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        
        int size = 2*screenSize/lenght;
        
        switch(direction){
            case 's':
                ant.newDir = Direction.Ahead;
                y +=size;
                if(ant.newDir!=ant.actualDir)
                    ant.rotate(180);
                for (int i = 0; i < 10; i++) {
                    y-=size/10;
                    
                    tmp = croptImage();
                    this.getGraphics().drawImage(tmp, 0, 0, this);
                    if(i==9)
                        this.getGraphics().drawImage(ant.ants[0].canvas, ant.x, ant.y, this);
                    else
                        this.getGraphics().drawImage(ant.ants[i%3].canvas, ant.x, ant.y, this);
                }
                break;
            case 'a':
                x -=size;
                ant.newDir = Direction.Left;
                if(ant.newDir!=ant.actualDir)
                    ant.rotate(270);
                for (int i = 0; i < 10; i++) {
                    x+=size/10;
                    tmp = croptImage();
                    this.getGraphics().drawImage(tmp, 0, 0, this);
                    if(i==9)
                        this.getGraphics().drawImage(ant.ants[0].canvas, ant.x, ant.y, this);
                    else
                        this.getGraphics().drawImage(ant.ants[i%3].canvas, ant.x, ant.y, this);
                }
                break;
            case 'w':
                ant.newDir = Direction.Behind;
                if(ant.newDir!=ant.actualDir)
                    ant.rotate(0);
                y -=size;
                for (int i = 0; i < 10; i++) {
                    y+=size/10;
                    tmp = croptImage();
                    this.getGraphics().drawImage(tmp, 0, 0, this);
                    if(i==9)
                        this.getGraphics().drawImage(ant.ants[0].canvas, ant.x, ant.y, this);
                    else
                        this.getGraphics().drawImage(ant.ants[i%3].canvas, ant.x, ant.y, this);
                }
                break;
            case 'd':
                ant.newDir = Direction.Rigth;
                if(ant.newDir!=ant.actualDir)
                    ant.rotate(90);
                x +=size;
                for (int i = 0; i < 10; i++) {
                    x-=size/10;
                    tmp = croptImage();
                    this.getGraphics().drawImage(tmp, 0, 0, this);
                    if(i==9)
                        this.getGraphics().drawImage(ant.ants[0].canvas, ant.x, ant.y, this);
                    else
                        this.getGraphics().drawImage(ant.ants[i%3].canvas, ant.x, ant.y, this);
                }
                break;
            default:
                tmp = croptImage();
                this.getGraphics().drawImage(tmp, 0, 0, this);
                this.getGraphics().drawImage(ant.ants[0].canvas, ant.x, ant.y, this);
                break;
        }
    }
}

class Circle{
    public int x = 0;
    public int y = 0;
    public int r = 0;
    public Color c;
    public Circle(int x, int y, int r, Color c){
        this.x = x;
        this.y = y;
        this.r = r;
        this.c = c;
    }
}