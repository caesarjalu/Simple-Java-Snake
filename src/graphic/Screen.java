package graphic;

import entities.Apple;
import gameplay.Gameplay;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Screen extends JPanel implements ActionListener {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 700;
    public static final int TILE_SIZE = 10;
    public static final int TITLE_HEIGHT = 50;
    public static final int INITIAL_LENGTH = 5;
    private final Timer timer;
    private boolean paused = false;
    private final Gameplay gameplay;
    private final Key key;

    public Screen() {
        setFocusable(true);
        gameplay = new Gameplay();
        key = new Key();
        addKeyListener(key);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        int delay = 50;
        timer = new Timer(delay, this);
        timer.start(); //start actionPerformed()
    }

    //run this method every delay
    @Override
    public void actionPerformed(ActionEvent e) {
        key.keyQueue();
        gameplay.actions();
        if (gameplay.isSnakeHit())
            stop();
        repaint(); //update the paint() method
    }

    //stop the game
    public void stop() {
        timer.stop();
        //show the game over dialog
        String[] options = {"Retry", "Quit"};
        int x = JOptionPane.showOptionDialog(null,
                "Your score is: " + (gameplay.getLength() - INITIAL_LENGTH) + "\nWould you like to retry?",
                "Game Over",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (x == 0)
            restart();
        else
            System.exit(0);
    }

    //restart the game
    public void restart() {
        gameplay.reset();
        key.q.clear();
        timer.start();
        repaint();
    }

    //draw graphics into panel
    @Override
    public void paint(Graphics g) {
        //draw background
        g.setColor(new Color(10, 50,0));
        g.fillRect(0,0, WIDTH, HEIGHT);
        //draw title box
        g.setColor(new Color(10, 25,0));
        g.fillRect(0, 0, WIDTH, TITLE_HEIGHT);
        //drawing grid
        g.setColor(Color.BLACK);
        //draw grid vertically
        for (int i = 0; i < WIDTH; i += TILE_SIZE)
            g.drawLine(i, TITLE_HEIGHT, i, HEIGHT);
        //draw grid horizontally
        for (int i = TITLE_HEIGHT; i < HEIGHT; i += TILE_SIZE)
            g.drawLine(0, i, WIDTH, i);
        //draw snake body
        g.setColor(Color.GREEN);
        for (int i = 0; i < gameplay.getSnakeBody().size(); i++) {
            if (i == 0) //draw snake tail
                gameplay.getSnakeBody().get(i).drawTail(g);
            else if (i == gameplay.getSnakeBody().size() - 1) //draw snake head
                gameplay.getSnakeBody().get(i).drawHead(g);
            else //draw snake body
                gameplay.getSnakeBody().get(i).drawBody(g);
        }
        //draw apple
        for (Apple apple : gameplay.getApples()) apple.draw(g);
        //draw scoreboard
        g.setColor(Color.GREEN);
        g.setFont(new Font("arial", Font.PLAIN, 35));
        g.drawString("Score : " + (gameplay.getLength() - INITIAL_LENGTH), 10, TITLE_HEIGHT - 12);
        //draw pause button when paused
        if (paused) {
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString("II", WIDTH - 45, 50 + TITLE_HEIGHT);
        }
        //clear panel to update them
        g.dispose();
    }

    private class Key extends KeyAdapter {
        public Queue<String> q = new LinkedList<>();

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode(); //get the keypress
            //if right pressed
            if (key == KeyEvent.VK_RIGHT && !gameplay.right)
                q.add("right");
            //if left pressed
            else if (key == KeyEvent.VK_LEFT && !gameplay.left)
                q.add("left");
            //if up pressed
            else if (key == KeyEvent.VK_UP && !gameplay.up)
                q.add("up");
            //if down pressed
            else if (key == KeyEvent.VK_DOWN && !gameplay.down)
                q.add("down");
            //press space to pause and resume the game
            else if (key == KeyEvent.VK_SPACE) {
                if (timer.isRunning()) {
                    paused = true;
                    timer.stop();
                    repaint();
                }
                else if (!timer.isRunning() && paused) {
                    paused = false;
                    q.clear();
                    timer.start();
                }
            }
        }

        //change snake direction depending on key queue
        public void keyQueue() {
            if (!q.isEmpty()) {
                String pos = q.poll();
                if (pos.equals("right") && !gameplay.left) {
                    gameplay.up = false;
                    gameplay.down = false;
                    gameplay.right = true;
                }
                else if (pos.equals("left") && !gameplay.right) {
                    gameplay.up = false;
                    gameplay.down = false;
                    gameplay.left = true;
                }
                else if (pos.equals("up") && !gameplay.down) {
                    gameplay.left = false;
                    gameplay.right = false;
                    gameplay.up = true;
                }
                else if (pos.equals("down") && !gameplay.up) {
                    gameplay.left = false;
                    gameplay.right = false;
                    gameplay.down = true;
                }
            }
        }
    }
}
