package main;

import javax.swing.*;
import java.awt.*;
import graphic.Screen;

public class Main extends JFrame {
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //program terminated when closed
        setTitle("Snake"); //set title of the frame to "Snake"
        setResizable(false); //make the frame not resizable
        init();
    }

    public void init() {
        setLayout(new GridLayout(1, 1, 0, 0)); // set the panel on center of the screen
        Screen s = new Screen();
        add(s);
        pack(); //set the frame size same as preferred screen class size
        setLocationRelativeTo(null); //center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
