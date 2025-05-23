package com.example;

import java.awt.Color;
import java.awt.*;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.example.components.*;
import com.example.ecs.*;
import com.example.input.*;
import com.example.input.*;
import com.example.systems.*;

public class Scene extends JPanel implements Runnable {

    private GameApplication application;
    private Thread gameThread;
    private boolean running = false;
    private int entityId = 0;
    private Vector2 cameraPosition = new Vector2(0,0);

    private ECS ecs = new ECS();
    private SystemsManager manager = new SystemsManager();


    public Scene(){}

    public Scene(GameApplication application){
        this.application = application;
        setDoubleBuffered(true); // helps prevent flickering
        initInput();
    }

    private void initInput(){
        //setLayout(new GridLayout(0, 1));
        setBackground(Color.WHITE);
       setFocusable(true);


       addKeyListener(new KeyAdapter(){
               @Override
       public void keyPressed(KeyEvent e) {
           super.keyPressed(e);
       Input.addKey(e);
       }
               @Override
       public void keyReleased(KeyEvent e) {
           super.keyReleased(e);
       Input.removeKey(e);
       }
           });


       MouseAdapter mouseAdapter = new MouseAdapter(){
               @Override
       public void mousePressed(MouseEvent e) {
           super.mousePressed(e);
       Input.addMouseButton(e);
       Input.setMouseEvent(e);
       Input.setMousePressed(e.getButton());
       requestFocus();
       }
               @Override
       public void mouseReleased(MouseEvent e) {
           super.mouseReleased(e);
       Input.removeMouseButton(e);
       Input.setMouseEvent(e);

       }
               @Override
       public void mouseDragged(MouseEvent e) {
           super.mouseDragged(e);
       Input.setMousePositionOnCanvas(new Vector2((float) e.getPoint().getX(), (float) e.getPoint().getY()));
       Input.setMouseEvent(e);

       }
               @Override
       public void mouseMoved(MouseEvent e) {
           super.mouseMoved(e);
       Input.setMousePositionOnCanvas(new Vector2((float) e.getPoint().getX(), (float) e.getPoint().getY()));
       Input.setMouseEvent(e);
       requestFocus();
       }
               @Override
       public void mouseWheelMoved(MouseWheelEvent e) {
           Input.setScrollValue((float) e.getPreciseWheelRotation());
       Input.setMouseEvent(e);
       }

           };
       addMouseListener(mouseAdapter);
       addMouseMotionListener(mouseAdapter);
       addMouseWheelListener(mouseAdapter);
    }

    public ECS getEcs(){return ecs;}
    public Vector2 screenToWorld(Vector2 mousePoint) {
        int w = application.getWidth();
        int h = application.getHeight();

        int designWidth = 1920;
        int designHeight = 1080;
        double scaleX = (double) w / designWidth;
        double scaleY = (double) h / designHeight;
        double scale = Math.min(scaleX, scaleY) * 0.5;

        double tx = w / 2.0 + cameraPosition.getX();
        double ty = h / 2.0 + cameraPosition.getY();

        double wx = (mousePoint.getX() - tx) / scale;
        double wy = (mousePoint.getY() - ty) / scale;

        return new Vector2(wx, wy);
    }

    private void initGraphics2D(Graphics2D g2){

        int w = application.getWidth();
        int h = application.getHeight();

        int designWidth = 1920;
        int designHeight = 1080;

        // Calculate uniform scale to fit design inside panel
        double scaleX = (double) w / designWidth;
        double scaleY = (double) h / designHeight;
        double scale = Math.min(scaleX, scaleY)*0.5;

        // Move origin to center of panel
        g2.translate(w / 2.0+cameraPosition.getX(),
                     h / 2.0+cameraPosition.getY());

        // Scale uniformly
        g2.scale(scale, scale);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        initGraphics2D(g2);

        Input.setMousePosition(screenToWorld(Input.getMousePositionOnCanvas()));

        manager.renderSystems(this,g2);
        g2.scale(2,2);
        g2.drawString(String.valueOf(entityId),-200,-200);
    }

    public int getEntityId(){
        return entityId;
    }

    
    public GameObject createGameObject(){
        GameObject ob = new GameObject(this);
        return ob;
    }

    public void start() {
        for(Script script : ecs.getAllComponentsOfType(Script.class)){
            script.start();
        }

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(double deltaTime){
        Time.deltaTime = deltaTime;
        for(Script script : ecs.getAllComponentsOfType(Script.class)){
            script.update(deltaTime);
        }


        manager.updateSystems(this);



        Toolkit.getDefaultToolkit().sync();
        validate();
        repaint();
    }

    @Override
    public void run() {
        int frames = 0;
        long fpsTimer = System.currentTimeMillis();
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            update(deltaTime);

            frames++;
            if (System.currentTimeMillis() - fpsTimer >= 1000) {

                application.setTitle(String.valueOf(frames));
                frames = 0;
                fpsTimer += 1000;
            }
            try {
                Thread.sleep(1); // Give the CPU a breath
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
