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



    public Scene(GameApplication application){
        this.application = application;
        setDoubleBuffered(true); // helps prevent flickering
        initInput();
    }

    private void initInput(){
        setLayout(new GridLayout(0, 1));
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

    private void initGraphics2D(Graphics2D g2){

        int w = getWidth();
        int h = getHeight();

        int designWidth = 400;
        int designHeight = 300;

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

    public GameObject createGameObject(){
        GameObject ob = new GameObject(this,entityId);
        entityId++;
        ob.addComponent(new Transform());
        return ob;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        initGraphics2D(g2);

        manager.renderSystems(this,g2);

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
            repaint();

            frames++;
            if (System.currentTimeMillis() - fpsTimer >= 1000) {
//                System.out.println("DeltaTime: "+deltaTime);
//                System.out.println("FPS: " + frames);
                frames = 0;
                fpsTimer += 1000;
            }
            try {
                Thread.sleep(12); // Give the CPU a breath
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
