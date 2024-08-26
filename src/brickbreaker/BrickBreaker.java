package brickbreaker;

import javax.swing.JFrame;


public class BrickBreaker {

    public static void main(String[] args) {
        // TODO code application logic here
        JFrame obj = new JFrame();
        Physics jugar = new Physics();

        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(jugar);
    }   
}